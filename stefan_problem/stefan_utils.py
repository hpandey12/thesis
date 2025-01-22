import pandas
import os
import numpy
import scipy
import csv
import matplotlib.pyplot as plt
import mod_gaussQuad
import mod_stefan as analytical_stefan
from mod_stefan import stefan_variables

class output_list:
    def __init__(self, dir, name_append, range_var, name, timesteps, temp_thresh, vol_frac_thresh, el_vol, analytical_vars):
        self.timesteps = timesteps
        
        self.df_list = self.return_outputframe_list(dir, name_append, range_var, name)
        
        [self.num_pos_interface_verbose, self.num_pos_interface, 
         self.analytical_pos_interface, self.abs_pos_err] = self.post_process_interface_position_data(timesteps,
                                                                                                      temp_thresh, vol_frac_thresh, analytical_vars)
        
        [self.L_inf_data, self.L2_data] = self.post_process_temperature_data(timesteps, analytical_vars, el_vol)
    
    def return_outputframe_list(self, dir, filename_append, range_var, name):
        df_list = []
        for idx, i in enumerate(range_var):
            filename = name + str(i) + filename_append
            filepath = os.path.join(dir, filename)
            #print(filepath)
            try:
                output_frame = pandas.read_csv(filepath)
                [out_X, out_Y, out_T, vol_frac] = [
                    output_frame["X (m)"].to_numpy(), 
                    output_frame["Y (m)"].to_numpy(), 
                    output_frame["Temperature (K)"].to_numpy(),
                    output_frame["Solid Volume Fraction of h2o"].to_numpy()]
                df = {
                    'range_var': i,
                    'x_coords': out_X,#[1::2],
                    'y_coords': out_Y,#[1::2],
                    'temperature': out_T,#[1::2],
                    'volume_fraction': vol_frac#[1::2]
                }
                df_list.append(df)
            except FileNotFoundError:
                print(f"File not found: {filepath}")
            except pandas.errors.EmptyDataError:
                print(f"File is empty: {filepath}")
            except Exception as e:
                print(f"An error occurred while reading {filepath}: {e}")
            
        return df_list
    
    def post_process_interface_position_data(self, timesteps, temp_thresh, vol_frac_thresh, analytical_vars): 
        num_pos_interface_verbose = self.return_approximate_interface_location(self.df_list, temp_thresh, vol_frac_thresh)
        num_pos_interface = []
        for pos_data in num_pos_interface_verbose:
            num_pos_interface.append(pos_data['x_coords'][numpy.min((pos_data['temp_idx'], pos_data['vol_frac_idx']))])
            
        analytical_pos_interface = analytical_stefan.interface_position(vars= analytical_vars, t_all = timesteps)
        abs_pos_err = []
        [abs_pos_err.append(abs(an_pos - num_pos)) for an_pos, num_pos in zip(analytical_pos_interface, num_pos_interface)]

        return num_pos_interface_verbose, num_pos_interface, analytical_pos_interface, abs_pos_err

    def post_process_temperature_data(self, timesteps, analytical_vars, el_vol):    
        temp_data = self.return_temperature_data(self.df_list)
        L2_data = []
        L_inf_data = []
        for t, data in zip(timesteps, temp_data):
            out_X = data['x_coords']
            #temperature_profile(vars, x_all: bool = False, x_loc: float = 100, t: float = -1.0, t_all: List[float] = None):
            analytical_temp = analytical_stefan.temperature_profile(vars= analytical_vars, x_all= True, x_loc= out_X, t= t)
            #print(analytical_temp[0][0][0:5])
            #print(data['temperature'][0:5])
            #print('-----')
            abs_err = []
            for num_temp, an_temp in zip(data['temperature'],analytical_temp[0][0]):
                abs_err.append(abs(num_temp - an_temp))
            L2_err = 0
            for error in abs_err:
                L2_err += (error**2)*el_vol
            L2_data.append(numpy.sqrt(L2_err/len(abs_err)))
            L_inf_data.append(numpy.max(abs_err))

        return L_inf_data, L2_data
    
    def plot_interface_pos_data(self):
        fig, ax = plt.subplots(1, 2, figsize=(10, 4))
        # Plot the first subplot
        ax[0].plot(self.timesteps, self.num_pos_interface, label="Numerical Position Interface", marker='o', linestyle='-')
        ax[0].plot(self.timesteps, self.analytical_pos_interface, label="Analytical Position Interface", marker='s', linestyle='--')
        ax[0].set_xlabel("Time")
        ax[0].set_ylabel("Interface Position")
        ax[0].legend()
        ax[0].grid()

        # Plot the second subplot
        ax[1].plot(self.timesteps, self.abs_pos_err, label="Absolute Error", marker='o', linestyle='-')
        ax[1].set_xlabel("Time")
        ax[1].set_ylabel("|numerical_X - analytical_X|")
        ax[1].set_yscale('log')
        ax[1].grid(True, which='both', linestyle='--', linewidth=0.5)

        # Adjust layout and show the plot
        plt.tight_layout()
        plt.show()

    def plot_temperature_err_data(self):
        fig, ax = plt.subplots(1, 2, figsize=(10, 4))

        # First subplot: L2 error
        ax[0].plot(self.timesteps, self.L2_data, marker='o', markersize=4, label='L2 Data')
        ax[0].set_xlabel(r"Time Steps", fontsize=12)
        ax[0].set_ylabel(r"L2 error", fontsize=12)
        ax[0].set_yscale('log')
        ax[0].grid(True, which='both', linestyle='--', linewidth=0.5)

        # Second subplot: Absolute error
        ax[1].plot(self.timesteps, self.L_inf_data, marker='o', markersize=4, label='L_inf Data')
        ax[1].set_xlabel(r"Time Steps", fontsize=12)
        ax[1].set_ylabel(r"Absolute error", fontsize=12)
        # No log scale for the second plot as per your code
        ax[1].grid(True, which='both', linestyle='--', linewidth=0.5)

        # Adjust layout and show the plot
        plt.tight_layout()
        plt.show()

    def return_approximate_interface_location(self, output_data_list, temp_thresh, vol_frac_thresh):
        temp_val = float(temp_thresh[0])
        temp_tol = float(temp_thresh[1])
        vol_frac_val = float(vol_frac_thresh[0])
        vol_frac_tol = float(vol_frac_thresh[1])
        interface_loc_list = []
        for df in output_data_list:
            temp_check = numpy.isclose(df['temperature'], temp_val, rtol=temp_tol)
            vol_frac_check = numpy.isclose(df['volume_fraction'], vol_frac_val, rtol=vol_frac_tol)
            temp_idx = numpy.argmax(temp_check)
            vol_frac_idx = numpy.argmax(vol_frac_check)
            
            temp_check = temp_check[temp_idx-2:temp_idx+3]
            vol_frac_check= vol_frac_check[vol_frac_idx-2:vol_frac_idx+3]
            mod_temp_idx = numpy.argmax(temp_check)
            mod_vol_frac_idx = numpy.argmax(vol_frac_check)

            location_df = {
                'temp_idx': mod_temp_idx,
                'vol_frac_idx': mod_vol_frac_idx,
                'temperature': df['temperature'][temp_idx-2:temp_idx+3],
                'volume_fraction': df['volume_fraction'][vol_frac_idx-2:vol_frac_idx+3],
                'x_coords': df['x_coords'][temp_idx-2:temp_idx+3]
            }
            interface_loc_list.append(location_df)

        return interface_loc_list

    def return_temperature_data(self, output_data_list):
        temperature_data = []
        for df in output_data_list:
            df1 = {
                    #'range_var': i,
                    'x_coords': df['x_coords'][1::2],
                    'y_coords': df['y_coords'][1::2],
                    'temperature': df['temperature'][1::2],
                    #'volume_fraction': vol_frac#[1::2]
                }
            temperature_data.append(df1)

        return temperature_data
###################################################################################



def sort_csv_files(input_file, output_file, column_name):
# Input and output file paths
# Read, sort, and write the CSV file
    with open(input_file, mode='r') as infile:
        # Read CSV
        reader = csv.DictReader(infile)
        data = list(reader)  # Convert to list for sorting

        # Sort data by the "X (m)" column (ascending order)
        sorted_data = sorted(data, key=lambda row: float(row[column_name]))

    # Write sorted data back to a new CSV file
    with open(output_file, mode='w', newline='') as outfile:
        # Create CSV writer with the same headers
        writer = csv.DictWriter(outfile, fieldnames=reader.fieldnames)
        writer.writeheader()  # Write header row
        writer.writerows(sorted_data)  # Write sorted rows
