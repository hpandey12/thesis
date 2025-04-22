import numpy
import pandas
import os
import fnmatch
import sys
def generate_ini(path, delta_h, amplitude, rho_high, rho_low, sigma, time_end, timestep, timestep_freq, viscosity):
    ini_template = """-param delta_h {delta_h}
-param amplitude 0.05
-param high_rho {rho_high}
-param low_rho {rho_low}
-param surface_tension {surface_tension}
-param time_end {time_end}
-param timestep {timestep}
-param timestep_freq {timestep_freq}
-param viscosity_high {viscosity}
-param viscosity_low {viscosity}
"""

    ini_contents = ini_template.format(
        delta_h=delta_h,
        amplitude=amplitude,
        rho_high=rho_high,
        rho_low=rho_low,
        surface_tension=sigma,
        time_end=time_end,
        timestep=timestep,
        timestep_freq = timestep_freq,
        viscosity=viscosity
    )
    with open(os.path.join(path, "params.ini"), "w") as f:
        f.write(ini_contents)
    return ini_contents

def generate_slurm(A, Re, sigma, path):
    slurm_script = f"""#!/usr/bin/zsh
#SBATCH --job-name=A{A}_Re{Re}_sig{sigma:.0e}
#SBATCH --output=A{A}_Re{Re}_sig{sigma:.0e}_%J.out
#SBATCH --time=08:30:00
#SBATCH --ntasks-per-node=4
#SBATCH --nodes=2

cd {os.path.abspath(path)}
module load STAR-CCM+/18.06.006

export CDLMD_LICENSE_FILE=50015@license.itc.rwth-aachen.de

starccm+ -ini params.ini -bs slurm -mpi intel -xsystemucx -rsh ssh -power -batch mesh,run 0main_surf.sim
"""
    with open(os.path.join(path, "run_sim.sh"), "w") as f:
        f.write(slurm_script)   


class dynamic_model:
    def __init__(self, params):
        if params:
            self.params = params
        else:
            self.params = {}
        self.non_dim_nums = {}
        self.solution_data = {}
        self.analysis_data = {}
        
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

    def load_solution_data(self, dir, name, name_append, name_vars, ref_t = None, update_params: bool = True):
        range_var = []
        for file in os.listdir(dir):
            if fnmatch.fnmatch(file, name+'*'+name_append):
                #print(file.split('_')[-1].split(".")[0])
                range_var.append(int(file.split('_')[-1].split(".")[0]))

        range_var = numpy.sort(range_var)
        if self.params:
            self.compare_params(dir, update_params)
        else:
            self.set_params(dir)
        try:
            if self.params["timestep"]:
                self.params['timesteps'] = []
        #else:
        #    self.params["timestep"] = int(range_var[1] - range_var[0])
        #print('comparing timesteps as read from filenames versus the intial model\'s: ', range_var[1] - range_var[0], self.params["timestep"])
            [self.params['timesteps'].append(i*self.params["timestep"]) for i in range_var]
        except:
            print('no parameter timestep defined in the model. cannot create timesteps array. problem in load solution data.')

        if ref_t and callable(ref_t):
            self.set_nondim_consts(overwrite= True, ref_t = ref_t)
        self.params["nondim_time_time"] = []
        for i in self.params['timesteps']:
            self.params["nondim_time_time"].append(i*self.params["ref_t"])
        
        self.params['timesteps'] = numpy.array(self.params['timesteps'])
        self.params["nondim_time_time"] = numpy.array(self.params["nondim_time_time"])
        self.set_solution_data(dir, name_append, range_var, name, name_vars)
    
    def set_params(self, dir):
        with open(os.path.join(dir,"params.ini"),"r") as f:
            lines = f.readlines()
        file_params = {}
        for line in lines:
            param_name = line.split()[1]
            param_val = line.split()[-1]
            try:
                file_params.update({param_name: float(param_val)})
            except:
                pass
        self.params = file_params

    def compare_params(self, dir, update_params):
        with open(os.path.join(dir,"params.ini"),"r") as f:
            lines = f.readlines()
        print('comparing file params with model params with params.ini \n')

        file_params = {}
        for line in lines:
            param_name = line.split()[1]
            param_val = line.split()[-1]
            try:
                file_params.update({param_name: float(param_val)})
            except:
                pass

            if param_name in self.params:
                print(param_name,'-', param_val, ",", self.params[param_name])
                if update_params:
                    self.params[param_name] = float(param_val)
            else:
                continue    
            sys.stdout.flush()
    
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

    def calculate_stuff(self):
        y_bub = []
        y_spike = []
        v_bub_file = [] 
        v_spike_file = []
        for idx, timestep_data in enumerate(self.solution_data):
            y_bub_inst = float(numpy.max(timestep_data["y_dat"]) - 2.0)
            y_spike_inst = float(numpy.min(timestep_data["y_dat"]) - 2.0)
            self.solution_data[idx]['y_bub'] = y_bub
            self.solution_data[idx]['y_spike'] = y_spike

            v_bub_file.append(float(timestep_data["v_y"][numpy.argmax(timestep_data["y_dat"])]))
            v_spike_file.append(float(timestep_data["v_y"][numpy.argmin(timestep_data["y_dat"])]))
            y_bub.append(y_bub_inst)
            y_spike.append(y_spike_inst)

        v_bub = numpy.abs(numpy.diff(y_bub))
        v_spike = numpy.abs(numpy.diff(y_spike))
        
        self.analysis_data['y_bub'] = numpy.array(y_bub)
        self.analysis_data['y_spike'] = numpy.array(y_spike)
        self.analysis_data['v_bub'] = numpy.array(v_bub)
        self.analysis_data['v_spike'] = numpy.array(v_spike)
        self.analysis_data['v_bub_file'] = numpy.array(v_bub_file)
        self.analysis_data['v_spike_file'] = numpy.array(v_spike_file)
        self.analysis_data['nondim_time'] = numpy.array(self.params["nondim_time_time"])