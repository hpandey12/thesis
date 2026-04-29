import os
import fnmatch
import sys
import logging
from abc import ABC, abstractmethod
import pandas as pd
import numpy as np

from typing import Optional
from mod_solver import Solver

class DynamicModel:
    def __init__(self, params: dict = None, solver: Optional[Solver] = None)-> None:
        """
        Initialize the dynamic model with given parameters.

        Args:
            params (dict, optional): A dictionary of parameters for the model.
        """
        self.params = params if params else {}
        self.non_dim_nums = {}
        self.solution_data = {}
        self.post_process_data = {}
        self.analysis_data = {}
        self.solver = solver
    def set_solver(self, solver_cls, **kwargs):
        """
        Instantiate a solver of type solver_cls (which must inherit DynamicModel.Solver)
        and attach it to this model.

        Args:
            solver_cls: A subclass of DynamicModel.Solver.
            **kwargs: Additional keyword arguments passed to the solver's constructor.
        """
        self.solver = solver_cls(self, **kwargs)

    def _evaluate_param_value(self, key: str, value) -> float:
        """
        Evaluate a parameter value which can be:
        1) A string referencing another parameter in self.params,
        2) A float/int, or
        3) A callable function that takes existing parameters as input.

        Args:
            key (str): The parameter key we are evaluating for (used for logging only).
            value (Any): The raw value (str, int, float, or callable).

        Returns:
            float or None: The value evaluated as a float, or None if evaluation fails.
        """
        if isinstance(value, str):
            if value in self.params:
                return self.params[value]
            else:
                logging.warning(f"String '{value}' not found in params.")
                return None
        elif isinstance(value, (int, float)):
            return float(value)
        elif callable(value):
            required_params = value.__code__.co_varnames[:value.__code__.co_argcount]
            if all(param in self.params for param in required_params):
                # Gather the required parameters from self.params
                return value(*[self.params[p] for p in required_params])
            else:
                logging.warning(f"Callable missing required parameters for '{key}'.")
                return None
        else:
            logging.warning(f"Invalid type for {key}. Must be string, int, float, or callable.")
            return None

    def update_params(self, overwrite: bool = False, **kwargs) -> None:
        """
        Update the model parameters with the given keyword arguments.
        If overwrite is True, existing parameters are overwritten.
        Otherwise, only new parameters are added.

        Args:
            overwrite (bool): Whether to overwrite existing parameters.
            **kwargs: Key-value pairs to set or update in self.params.
        """
        for k, v in kwargs.items():
            if overwrite or k not in self.params:
                evaluated_value = self._evaluate_param_value(k, v)
                if evaluated_value is not None:
                    self.params[k] = evaluated_value


    def set_nondim_consts(self, overwrite: bool = False, **kwargs) -> None:
        """
        Define or update the dimensionless constants in self.params.

        Args:
            overwrite (bool): Whether to overwrite existing parameters.
            **kwargs: Key-value pairs for dimensionless constants.
        """
        # Use the centralized parameter updating logic
        self.update_params(overwrite=overwrite, **kwargs)

        # If 'ref_t' is not defined but 'ref_l' and 'ref_u' exist, define ref_t = ref_l/ref_u
        if 'ref_t' not in self.params and all(param in self.params for param in ['ref_l', 'ref_u']):
            self.params['ref_t'] = self.params['ref_l'] / self.params['ref_u']


    def calculate_reynolds_num(self) -> float:
        """
        Calculate the Reynolds number based on the current parameters.

        Returns:
            float or None: The Reynolds number, or None if required parameters are missing.
        """
        if all(param in self.params for param in ['ref_l', 'ref_rho', 'ref_u', 'ref_visc']):
            return (self.params["ref_l"] * self.params["ref_rho"] *
                    self.params["ref_u"] / self.params["ref_visc"])
        elif all(param in self.params for param in ['ref_l', 'ref_u', 'ref_visc_kin']):
            return (self.params["ref_l"] * self.params["ref_u"] /
                    self.params["ref_visc_kin"])
        else:
            return None

    def calculate_nondim_nums(self, **kwargs) -> None:
        """
        Calculate the requested dimensionless numbers using either built-in
        methods or user-provided callables.

        Each requested dimensionless number is keyed by e.g. 'reynolds',
        and stored in self.non_dim_nums and self.params under '{key}_num'.

        Usage examples:
            self.calculate_nondim_nums(reynolds=False, prandtl=my_prandtl_function)

        Args:
            **kwargs: Key-value pairs where:
                - key is the dimensionless number name (e.g. 'reynolds'),
                - value is either False (to use built-in method if any),
                  or a callable for custom calculations.
        """
        results = {}
        for key, value in kwargs.items():
            if not value:  # value == False or None
                # Check if there is a method in the class containing the key in its name
                for function_name in self.__class__.__dict__:
                    if key in function_name and hasattr(self, function_name):
                        var_name = key + "_num"
                        results[var_name] = getattr(self, function_name)()
                    else:
                        logging.warning(f"No method found for key '{key}'. Skipping.")
            elif callable(value):
                required_params = value.__code__.co_varnames[:value.__code__.co_argcount]
                if all(param_name in self.params for param_name in required_params):
                    val_ar = [self.params[p] for p in required_params]
                    var_name = key + "_num"
                    results[var_name] = value(*val_ar)

        for result_key, result_val in results.items():
            self.params[result_key] = result_val

        self.non_dim_nums = results
        logging.info(f"Calculated non-dimensional numbers: {self.non_dim_nums}")


    def load_solution_data(self,
                       dir: str,
                       name: str,
                       name_append: str,
                       name_vars: dict,
                       ref_t=None,
                       update_params: bool = True,
                       timestep_from_file: bool = False,
                       sort_by: str = None) -> None:
        """
        Load solution data from CSV files matching the pattern {name}*{name_append} in 'dir'.
        Optionally updates self.params from an optional 'params.ini', calculates timesteps,
        and optionally calculates nondimensional time if 'ref_t' is defined or provided.

        Args:
            dir (str): Directory path containing the data files.
            name (str): Base name for data files, e.g. 'solution_'.
            name_append (str): Suffix pattern for data files, e.g. '.csv'.
            name_vars (dict): Mapping from desired column name to CSV column name.
            ref_t (callable, optional): Function to calculate 'ref_t'; if provided,
                                        we update self.params with its evaluation.
            update_params (bool): Whether to read from and update params using params.ini.
            timestep_from_file (bool): If True, the numeric part of each file name is taken
                                    as the timestep directly (including floats). If False,
                                    we multiply the parsed index by self.params["timestep"].
        """
        range_var = []
        name_range_var = []
        for file in os.listdir(dir):
            # Match files of the form {name}*{name_append}
            if fnmatch.fnmatch(file, name + '*' + name_append):
                # Strip off the known prefix and suffix to isolate the numeric part
                if len(name_append) > 0:
                    step_str = file[len(name):-len(name_append)]
                else:
                    # If name_append is empty, only remove the prefix
                    step_str = file[len(name):]

                try:
                    # Attempt to parse as float (handles scientific notation, e.g. 2.000e+02)
                    range_var.append(float(step_str))
                    name_range_var.append(str(step_str))
                except ValueError:
                    logging.warning(f"Could not parse float timestep from file: {file}")

        range_var, name_range_var = zip(*sorted(zip(range_var, name_range_var)))#np.sort(range_var)

        # Only read or compare params.ini if update_params is True
        if update_params:
            if self.params:
                self.compare_params(dir, update_params=True)
            else:
                self.set_params(dir)

        # Either use the float values from the file as timesteps directly,
        # or multiply them by self.params["timestep"] if available
        if timestep_from_file:
            # Each filename's float value is the timestep
            self.params['timesteps'] = np.array(range_var)
        else:
            try:
                if self.params["timestep"]:
                    self.params['timesteps'] = np.array([i * self.params["timestep"] for i in range_var])
            except KeyError:
                logging.warning("No parameter 'timestep' defined in the model. Cannot create timesteps array.")

        # If a callable ref_t is provided, update the model's parameters
        if ref_t and callable(ref_t):
            self.update_params(overwrite=True, ref_t=ref_t)

        # Build a nondimensional time array if possible
        if "ref_t" in self.params and "timesteps" in self.params:
            self.params["nondim_time_time"] = self.params["timesteps"] * self.params["ref_t"]

        # Finally, load the actual solution data from files
        self.read_file_solution_data(dir, name_append, name_range_var, name, name_vars, sort_by)


    def set_params(self, dir: str) -> None:
        """
        Read parameters from a 'params.ini' file in the given directory and store them in self.params.

        Args:
            dir (str): Directory path expected to contain 'params.ini'.
        """
        filepath = os.path.join(dir, "params.ini")
        if not os.path.exists(filepath):
            logging.warning(f"params.ini not found in directory {dir}")
            return

        with open(filepath, "r") as f:
            lines = f.readlines()

        file_params = {}
        for line in lines:
            parts = line.split()
            if len(parts) >= 2:
                param_name = parts[1]
                param_val = parts[-1]
                try:
                    file_params[param_name] = float(param_val)
                except ValueError:
                    logging.warning(f"Could not convert '{param_val}' to float in params.ini.")
        self.params = file_params


    def compare_params(self, dir: str, update_params: bool) -> None:
        """
        Compare parameters loaded from 'params.ini' in the given directory with
        the model's current parameters. Optionally update them if update_params is True.

        Args:
            dir (str): Directory path expected to contain 'params.ini'.
            update_params (bool): Whether to update the model's params with file values.
        """
        filepath = os.path.join(dir, "params.ini")
        if not os.path.exists(filepath):
            logging.warning(f"params.ini not found in directory {dir}")
            return

        with open(filepath, "r") as f:
            lines = f.readlines()

        logging.info("Comparing model params with file params from params.ini ...")

        file_params = {}
        for line in lines:
            parts = line.split()
            if len(parts) < 2:
                continue
            param_name = parts[1]
            param_val = parts[-1]
            try:
                file_params[param_name] = float(param_val)
            except ValueError:
                continue

            if param_name in self.params:
                logging.info(f"{param_name} - file: {param_val}, model: {self.params[param_name]}")
                if update_params:
                    self.params[param_name] = file_params[param_name]
            else:
                if update_params:
                    self.params[param_name] = file_params[param_name]

    def read_file_solution_data(self,
                          dir: str,
                          filename_append: str,
                          range_var: np.ndarray,
                          name: str,
                          name_params: dict,
                          sort_by: str = None) -> None:
        """
        Read solution data CSV files based on the sorted list of integer indices in range_var.
        Store the data in self.solution_data as a list of dictionaries containing NumPy arrays.

        Args:
            dir (str): The directory containing the CSV files.
            filename_append (str): The suffix pattern for each file (e.g., '.csv').
            range_var (np.ndarray): Sorted list/array of integer file suffixes.
            name (str): The base name pattern of the files (e.g., 'solution_').
            name_params (dict): Mapping from user-friendly column names to CSV column names.
        """
        df_list = []
        for idx, val in enumerate(range_var):
            filename = f"{name}{val}{filename_append}"
            filepath = os.path.join(dir, filename)
            try:
                df = pd.read_csv(filepath)
                # Extract only desired columns
                filtered_df = df.loc[:, [value for value in name_params.values() if value in df.columns]]
                # Rename columns: CSV column -> friendly name
                filtered_df.rename(columns={v: k for k, v in name_params.items() if v in filtered_df.columns},
                                   inplace=True)
                if sort_by:
                    filtered_df = filtered_df.sort_values(by=sort_by, ascending=True)
                # Convert columns to NumPy arrays
                np_df = {col_name: filtered_df[col_name].to_numpy()
                         for col_name in filtered_df.columns}
                df_list.append(np_df)
            except FileNotFoundError:
                logging.warning(f"File {filepath} not found. Skipping.")
            except KeyError as e:
                logging.warning(f"Column {e} not found in {filepath}. Skipping.")

        self.solution_data = df_list