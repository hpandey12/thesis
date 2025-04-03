import numpy
import pandas
import os


'''Example Usage to extract columns (name_params) from csv files with iterating name
range_var = [4, 8, 12, 16, 20]
name_vars = {
    'x_dat': "X (m)",
    'y_dat': "Y (m)",
    'v_y': "Velocity[j] (m/s)",
    'volFrac_high': "Volume Fraction of high_rho",
    'volFrac_low': "Volume Fraction of low_rho",
    'min_mixWidth': "minMixWidthEval",
    'max_mixWidth': "maxMixWidthEval",
    'massFlux': "Report: massImbalance_highRho (kg)"
}

dir = r'D:\thesis\rayleigh_taylor'
name = 'line_probe_table_test_'
name_append = '.csv'
'''
class dynamic_model:
    def __init__(self, params):
        self.params = params

        self.non_dim_nums = {}
        self.solution_data = {}
        
    def set_nondim_consts(self, overwrite = False, **kwargs):
        def get_param_val(key, value):
            if isinstance(value, str) and value in self.params:
                        # If it's a string referring to an existing param
                return self.params[value]
            elif isinstance(value, (int, float)):
                # If it's a float or integer value
                return float(value)
            elif callable(value):
                # If it's a function that takes params as input
                required_params = value.__code__.co_varnames[:value.__code__.co_argcount]
                
                if all(param_chk in self.params for param_chk in required_params):
                    val_ar = []
                    for param in required_params:
                        val_ar.append(self.params[str(param)])
                    return value(*val_ar)
            else:
                print(f"Warning: Invalid type for {key}. Must be string (param name), int or float.")

        if overwrite is True:
            for key, value in kwargs.items():
                self.params[key] = get_param_val(key, value)               
        else:
            for key, value in kwargs.items():
                if key not in self.params:
                    self.params[key] = get_param_val(key, value)
                
                    
        if 'ref_t' not in self.params and all(param in self.params for param in ['ref_l', 'ref_u']):
            self.params['ref_t'] = self.params['ref_l']/self.params['ref_u']

        if 'ref_grev' not in self.params and 'grev' in self.params:
            self.params['ref_grev'] = self.params['grev']

    
    def calculate_atwood_number(self):
        if 'high_rho' in self.params and 'low_rho' in self.params:
            return (self.params["high_rho"] - self.params["low_rho"]) / (self.params["high_rho"] + self.params["low_rho"])
        return None

    def calculate_reynolds_num(self):
        if all(param in self.params for param in ['ref_l', 'ref_rho', 'ref_u', 'ref_visc']):
            return (self.params["ref_l"] * self.params["ref_rho"] * self.params["ref_u"] / 
                    self.params["ref_visc"])
        elif all(param in self.params for param in ['ref_l', 'ref_u', 'ref_visc_kin']):
            return(self.params["ref_l"] * self.params["ref_u"] / 
                    self.params["ref_visc_kin"])
        else:
            return None

    def get_mesh_grashof_num(self):
        if 'ref_visc_kin' in self.params:
            if all(param in self.params for param in ['ref_grev', 'delta_h', 'ref_u', 'high_rho', 'low_rho']):
                return (2 * self.calculate_atwood_number() * numpy.abs(self.params["ref_grev"]) * numpy.power(self.params["delta_h"], 3) /
                        numpy.power(self.params["ref_visc_kin"], 2))
        elif all(param in self.params for param in ['ref_grev', 'delta_h', 'ref_u', 'ref_rho', 'ref_visc', 'high_rho', 'low_rho']):
            return (2 * self.calculate_atwood_number() * numpy.abs(self.params["ref_grev"]) * numpy.power(self.params["delta_h"], 3) / numpy.power(self.params['ref_visc']/self.params['ref_rho'], 2))
        else:
            return None
        
    def calculate_froude_num(self):
        if all(param in self.params for param in ['ref_grev', 'ref_l', 'ref_u']):
            return ((self.params['ref_u']*self.params['ref_u'])/(self.params['ref_grev'] * self.params['ref_l']))
        return None
    
    def calculate_nondim_nums(self, **kwargs):
        results = {}
        for key, value in kwargs.items():
            if bool(value) is False:
                for function in self.__class__.__dict__.keys():
                    if key in function and hasattr(self, function):
                        var_name = key + "_num"
                        results[var_name] = getattr(self, function)()

            elif callable(value):
                required_params = value.__code__.co_varnames[:value.__code__.co_argcount]
                
                if all(param_chk in self.params for param_chk in required_params):
                    val_ar = []
                    var_name = key + "_num"
                    for param in required_params:
                        val_ar.append(self.params[str(param)])
                    results[var_name] = value(*val_ar)
    
        
        for result in results:
            self.params[result] = results[result]


        self.non_dim_nums = results
        print(self.non_dim_nums)
    
    def set_solution_data(self, dir, filename_append, range_var, name, name_params):
        df_list = []
        for idx, i in enumerate(range_var):
            filename = name + str(i) + filename_append
            filepath = os.path.join(dir, filename)
            try:
                df = pandas.read_csv(filepath)
                # Extract only the specified columns and rename them according to name_params
                filtered_df = df.loc[:, [value for value in name_params.values() if value in df.columns]]
                filtered_df.rename(columns={value: key for key, value in name_params.items() if value in filtered_df.columns}, inplace=True)
       
                np_df = {col_name: filtered_df[col_name].to_numpy() for col_name in filtered_df.columns}
                df_list.append(np_df)
            except FileNotFoundError:
                print(f"File {filepath} not found. Skipping.")
                
            except KeyError as e:
                print(f"Column {e} not found in {filepath}. Skipping.")

        self.solution_data = df_list

class output_list:
    def __init__(self, dir, name_append, range_var, name, name_params, timesteps, math_vars):
        if math_vars is None:
            pass
        else: 
            self.__dict__ = math_vars
        self.df_list = self.return_outputframe_list(dir, name_append, range_var, name, name_params)
        self.timesteps = timesteps
        

    def return_outputframe_list(self, dir, filename_append, range_var, name, name_params):
        df_list = []
        for idx, i in enumerate(range_var):
            filename = name + str(i) + filename_append
            filepath = os.path.join(dir, filename)
            try:
                df = pandas.read_csv(filepath)
                # Extract only the specified columns and rename them according to name_params
                filtered_df = df.loc[:, [value for value in name_params.values() if value in df.columns]]
                filtered_df.rename(columns={value: key for key, value in name_params.items() if value in filtered_df.columns}, inplace=True)
       
                np_df = {col_name: filtered_df[col_name].to_numpy() for col_name in filtered_df.columns}
                df_list.append(np_df)
            except FileNotFoundError:
                print(f"File {filepath} not found. Skipping.")
                
            except KeyError as e:
                print(f"Column {e} not found in {filepath}. Skipping.")

        return df_list