import numpy
import pandas
import os
import numpy
import scipy
import csv

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