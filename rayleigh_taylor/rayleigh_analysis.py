# %%
import numpy
import pandas
import os
import numpy
import scipy
import mod_utils as utils
import matplotlib.pyplot as plt
import os

# %%
math_vars = {
    'high_rho': 3,
    'low_rho' : 1,
    'high_visc' : 0.001, #dynamic
    'low_visc' : 0.001,#0.0005,
    'wavelength' : 1,
    'grev': 1,
    'x_L' : 1,
    'y_L' : 4,
    'delta_h': 0.05,
    'timestep': 0.0035,
    'time_end': 8,
    'maxWidthCutOff': 0.01,
    'surface_tension': 1e-05
}

model = utils.dynamic_model(math_vars)

model.set_nondim_consts(
    ref_l = 'x_L',
    ref_rho = 'high_rho',
    ref_grev = 'grev',
    ref_visc = 'low_visc',
    ref_visc_kin = lambda ref_visc, ref_rho: ref_visc/ref_rho, 
    ref_u = lambda ref_grev, ref_l: numpy.sqrt(ref_grev*ref_l),
    ref_surface_tension = lambda ref_l, ref_rho, ref_u, surface_tension: ref_rho*ref_l*numpy.pow(ref_u, 2)*surface_tension
)

#{} means it's in the mod_utils as a seperate class method, otherwise lambda
model.calculate_nondim_nums(
    atwood = {},
    reynolds = {},
    froude = {},
    #grashof = {},
    grash2 = lambda high_rho, low_rho, ref_grev, ref_l, ref_visc, ref_rho: ((high_rho - low_rho)/(high_rho + low_rho))*ref_grev*numpy.pow(ref_l, 3)/numpy.pow((ref_visc/ref_rho), 2),#Aglam^3/kin_visc^2
    #ren2 = lambda ref_grev, ref_l, ref_rho, ref_visc: (numpy.sqrt(numpy.pow(ref_l,3)*ref_grev)*ref_rho)/ref_visc
    weber = lambda ref_rho, ref_l, ref_u, ref_surface_tension: ref_rho*ref_l*(numpy.pow(ref_u, 2))/ref_surface_tension,
    eotvos = lambda high_rho, low_rho, ref_grev, ref_l, ref_surface_tension: (high_rho - low_rho)*ref_grev*numpy.pow(ref_l, 2)/ref_surface_tension
    )

# %%
#edit as required
name_vars = {
    'x_dat': "X (m)",
    'y_dat': "Y (m)",
    'v_x': "Velocity[i] (m/s)",
    'v_y': "Velocity[j] (m/s)",
    'rel_v_x': "Relative Velocity[i] (m/s)",
    "rel_v_y": "Relative Velocity[j] (m/s)",
    'volFrac_high': "Volume Fraction of high_rho",
    'volFrac_low': "Volume Fraction of low_rho",
    'min_mixWidth': "minMixWidthEval",
    'max_mixWidth': "maxMixWidthEval",
    'massFlux': "Report: massImbalance_highRho (kg)",
    'mean_rho': "Density (kg/m^3)"
}

dir = r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_Amp01/Re3000_At0.2_sigma1e-05_dh0.0045'
name = 'isosurface_table_'
name_append = '.csv'

#if reference time is different from the reference time initially set
ref_t = lambda atwood_num: numpy.sqrt(atwood_num)

model.load_solution_data(dir, name, name_append, name_vars, ref_t= ref_t)

# %%
model.calculate_stuff()

# %% Error Calculations and Interpolations
##########################################################################
##########################################################################
def find_nearest(array, value):
    array = numpy.asarray(array)
    idx = (numpy.abs(array - value)).argmin()
    return idx, array[idx]
def interpolate_central_difference(y_val, timesteps, time):
    interpolated_values = []
    
    for t in time:
        # Find the nearest index and corresponding timestep
        idx_nearest, t_nearest = find_nearest(timesteps, t)

        # Check if we can apply central difference
        if idx_nearest > 0 and idx_nearest < len(timesteps) - 1:
            # Central difference interpolation
            delta_t = timesteps[idx_nearest + 1] - timesteps[idx_nearest - 1]
            f_plus = y_val[idx_nearest + 1]
            f_minus = y_val[idx_nearest - 1]
            
            # Linear interpolation using neighbors
            interp_value = f_minus + ((f_plus - f_minus) / delta_t) * (t - timesteps[idx_nearest - 1])
        
        elif idx_nearest == 0:  # Forward difference for first element
            delta_t = timesteps[1] - timesteps[0]
            f_plus = y_val[1]
            f_current = y_val[0]
            
            interp_value = f_current + ((f_plus - f_current) / delta_t) * (t - timesteps[0])
        
        else:  # Backward difference for last element
            delta_t = timesteps[-1] - timesteps[-2]
            f_minus = y_val[-2]
            f_current = y_val[-1]

            interp_value = f_minus + ((f_current - f_minus) / delta_t) * (t - timesteps[-2])

        interpolated_values.append(interp_value)
    return numpy.array(interpolated_values)
def calc_errors(dns_ar, model_ar):
    L2_err = 0
    Linf_err = []
    for idx, model_val in enumerate(model_ar):
        dns_val = dns_ar[idx]
        err = numpy.abs(dns_val - model_val)
        Linf_err.append(err)
        L2_err = L2_err + numpy.square(err)
    L2_err = numpy.sqrt(L2_err)
    return L2_err, Linf_err
####################################################################
####################################################################
####################################################################


# %% 
####################################################################
############# single-mode_At0.2_Re3000_A0.1 ########################
####################################################################
dir = r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_Amp01/Re3000_At0.2_sigma1e-05_dh0.0045'
name = 'isosurface_table_'
name_append = '.csv'
#if reference time is different from the reference time initially set
ref_t = lambda atwood_num: numpy.sqrt(atwood_num)
model.load_solution_data(dir, name, name_append, name_vars, ref_t= ref_t)
model.calculate_stuff()
#----------------------------------------------------
dns_df = pandas.read_csv(r'/home/yy310050/Desktop/thesis/rayleigh_taylor/dns_compare_data/2D_Single-Mode_At0.2_Re3000_A0.05.csv')
#----------------------------------------------------
plt.figure(figsize=(8, 5))
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_bub'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_bubble')
plt.plot(dns_df['Time'], dns_df['Bubble'], label='dns_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_spike'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_spike')
plt.plot(dns_df['Time'], dns_df['Spike'], label='dns_spike', marker = 'o', linestyle='None', markerfacecolor='none')
plt.xlabel("time")
plt.ylabel("bubble and spike distances")
plt.title("single-mode_At0.2_Re3000_A0.1")
plt.legend()
plt.grid(True)
plt.show()
#----------------------------------------------------
L2_err_spike, Linf_err_spike = calc_errors(dns_df['Spike'], interpolate_central_difference(model.analysis_data['y_spike'], model.params['timesteps'], dns_df['Time']))
print("spike L2 error: ", L2_err_spike)
print("spike L_inf error: ", numpy.max(Linf_err_spike))
L2_err_bubble, Linf_err_bubble = calc_errors(dns_df['Bubble'], interpolate_central_difference(model.analysis_data['y_bub'], model.params['timesteps'], dns_df['Time']))
print("bubble L2 error: ", L2_err_bubble)
print("bubble L_inf error: ", numpy.max(Linf_err_bubble))

# %% ###############################################################
############# single-mode_At0.5_Re300_A0.1 #########################
####################################################################
dir = r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_Amp01/Re300_At0.5_sigma1e-05_dh0.0045'
name = 'isosurface_table_'
name_append = '.csv'
#if reference time is different from the reference time initially set
ref_t = lambda atwood_num: numpy.sqrt(atwood_num)
model.load_solution_data(dir, name, name_append, name_vars, ref_t= ref_t)
model.calculate_stuff()
#----------------------------------------------------
dns_df = pandas.read_csv(r'/home/yy310050/Desktop/thesis/rayleigh_taylor/dns_compare_data/2D_Single-Mode_At0.5_Re300_A0.05.csv')
#----------------------------------------------------
plt.figure(figsize=(8, 5))
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_bub'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_bubble')
plt.plot(dns_df['Time'], dns_df['Bubble'], label='dns_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_spike'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_spike')
plt.plot(dns_df['Time'], dns_df['Spike'], label='dns_spike', marker = 'o', linestyle='None', markerfacecolor='none')
plt.xlabel("time")
plt.ylabel("bubble and spike distances")
plt.title("single-mode_At0.5_Re300_A0.1")
plt.legend()
plt.grid(True)
plt.show()
#----------------------------------------------------
L2_err_spike, Linf_err_spike = calc_errors(dns_df['Spike'], interpolate_central_difference(model.analysis_data['y_spike'], model.params['timesteps'], dns_df['Time']))
print("spike L2 error: ", L2_err_spike)
print("spike L_inf error: ", numpy.max(Linf_err_spike))
L2_err_bubble, Linf_err_bubble = calc_errors(dns_df['Bubble'], interpolate_central_difference(model.analysis_data['y_bub'], model.params['timesteps'], dns_df['Time']))
print("bubble L2 error: ", L2_err_bubble)
print("bubble L_inf error: ", numpy.max(Linf_err_bubble))

# %% ###############################################################
############# single-mode_At0.5_Re1000_A0.1 ########################
####################################################################
dir = r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_Amp01/Re1000_At0.5_sigma1e-05_dh0.0045'
name = 'isosurface_table_'
name_append = '.csv'
#if reference time is different from the reference time initially set
ref_t = lambda atwood_num: numpy.sqrt(atwood_num)
model.load_solution_data(dir, name, name_append, name_vars, ref_t= ref_t)
model.calculate_stuff()
#----------------------------------------------------
dns_df = pandas.read_csv(r'/home/yy310050/Desktop/thesis/rayleigh_taylor/dns_compare_data/2D_Single-Mode_At0.5_Re1000_A0.05.csv')
#----------------------------------------------------
plt.figure(figsize=(8, 5))
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_bub'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_bubble')
plt.plot(dns_df['Time'], dns_df['Bubble'], label='dns_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_spike'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_spike')
plt.plot(dns_df['Time'], dns_df['Spike'], label='dns_spike', marker = 'o', linestyle='None', markerfacecolor='none')
plt.xlabel("time")
plt.ylabel("bubble and spike distances")
plt.title("single-mode_At0.5_Re1000_A0.1")
plt.legend()
plt.grid(True)
plt.show()
#----------------------------------------------------
L2_err_spike, Linf_err_spike = calc_errors(dns_df['Spike'], interpolate_central_difference(model.analysis_data['y_spike'], model.params['timesteps'], dns_df['Time']))
print("spike L2 error: ", L2_err_spike)
print("spike L_inf error: ", numpy.max(Linf_err_spike))
L2_err_bubble, Linf_err_bubble = calc_errors(dns_df['Bubble'], interpolate_central_difference(model.analysis_data['y_bub'], model.params['timesteps'], dns_df['Time']))
print("bubble L2 error: ", L2_err_bubble)
print("bubble L_inf error: ", numpy.max(Linf_err_bubble))
# %% ###############################################################
############# single-mode_At0.5_Re3000_A0.1 ########################
####################################################################
dir = r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_Amp01/Re3000_At0.5_sigma1e-05_dh0.0045'
name = 'isosurface_table_'
name_append = '.csv'
#if reference time is different from the reference time initially set
ref_t = lambda atwood_num: numpy.sqrt(atwood_num)
model.load_solution_data(dir, name, name_append, name_vars, ref_t= ref_t)
model.calculate_stuff()
#----------------------------------------------------
dns_df = pandas.read_csv(r'/home/yy310050/Desktop/thesis/rayleigh_taylor/dns_compare_data/2D_Single-Mode_At0.5_Re3000_A0.05.csv')
#----------------------------------------------------
plt.figure(figsize=(8, 5))
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_bub'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_bubble')
plt.plot(dns_df['Time'], dns_df['Bubble'], label='dns_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
plt.plot(
    model.params['timesteps'][model.params['timesteps'] < dns_df['Time'].max()], 
    model.analysis_data['y_spike'][model.params['timesteps'] < dns_df['Time'].max()], 
    label='sim_spike')
plt.plot(dns_df['Time'], dns_df['Spike'], label='dns_spike', marker = 'o', linestyle='None', markerfacecolor='none')
plt.xlabel("time")
plt.ylabel("bubble and spike distances")
plt.title("single-mode_At0.5_Re3000_A0.1")
plt.legend()
plt.grid(True)
plt.show()
#----------------------------------------------------
L2_err_spike, Linf_err_spike = calc_errors(dns_df['Spike'], interpolate_central_difference(model.analysis_data['y_spike'], model.params['timesteps'], dns_df['Time']))
print("spike L2 error: ", L2_err_spike)
print("spike L_inf error: ", numpy.max(Linf_err_spike))
L2_err_bubble, Linf_err_bubble = calc_errors(dns_df['Bubble'], interpolate_central_difference(model.analysis_data['y_bub'], model.params['timesteps'], dns_df['Time']))
print("bubble L2 error: ", L2_err_bubble)
print("bubble L_inf error: ", numpy.max(Linf_err_bubble))

# %% ###############################################################
######################## Convergence Study #########################
####################################################################
dir_list = [
    r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.05',
    r"/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.007",
    #r"/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_At0.5_sigma1e-05_dh0.0045",
    r"/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.0025"
]
name = 'isosurface_table_'
name_append = '.csv'
#if reference time is different from the reference time initially set
ref_t = lambda atwood_num: numpy.sqrt(atwood_num)
#model.load_solution_data(dir, name, name_append, name_vars, ref_t= ref_t)
#model.calculate_stuff()

h_data = []
for path in dir_list:
    model.load_solution_data(path, name, name_append, name_vars, ref_t= ref_t)
    model.calculate_stuff()
    cur_h_data = {}
    cur_h_data['dh'] = path.split('_')[-1].split('dh')[-1]
    cur_h_data['timesteps'] = model.params['timesteps']
    cur_h_data.update(model.analysis_data)
    h_data.append(cur_h_data)
#----------------------------------------------------
dns_df = pandas.read_csv(r'/home/yy310050/Desktop/thesis/rayleigh_taylor/dns_compare_data/2D_Single-Mode_At0.5_Re300_A0.05.csv')
#----------------------------------------------------
plt.figure(figsize=(8, 5))
L2_data = []
Linf_data = []
for h in h_data:
    plt.plot(
        h['timesteps'][h['timesteps'] < dns_df['Time'].max()], 
        h['y_bub'][h['timesteps'] < dns_df['Time'].max()], 
        label=str('sim_bubble, dh: ' + h['dh']))
    plt.plot(
        h['timesteps'][h['timesteps'] < dns_df['Time'].max()], 
        h['y_spike'][h['timesteps'] < dns_df['Time'].max()], 
        label=str('sim_spike, dh: ' + h['dh']))
    L2_err_spike, Linf_err_spike = calc_errors(dns_df['Spike'], interpolate_central_difference(h['y_spike'], h['timesteps'], dns_df['Time']))
    L2_err_bubble, Linf_err_bubble = calc_errors(dns_df['Bubble'], interpolate_central_difference(h['y_bub'], h['timesteps'], dns_df['Time']))
    L2_data.append([L2_err_spike, L2_err_bubble])
    Linf_data.append([numpy.max(Linf_err_spike), numpy.max(Linf_err_bubble)])
plt.plot(dns_df['Time'], dns_df['Bubble'], label='dns_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
plt.plot(dns_df['Time'], dns_df['Spike'], label='dns_spike', marker = 'o', linestyle='None', markerfacecolor='none')
plt.xlabel("time")
plt.ylabel("bubble and spike distances")
plt.title("single-mode_At0.5_Re300_A0.1")
plt.legend()
plt.grid(True)
plt.show()
#----------------------------------------------------
print("L2 errors: ")
for i, h in enumerate(h_data):
    print("dh: ", h['dh'])
    print("spike L2 error: ", L2_data[i][0])
    print("bubble L2 error: ", L2_data[i][1])
print("L_inf errors: ")
for i, h in enumerate(h_data):
    print("dh: ", h['dh'])
    print("spike L_inf error: ", Linf_data[i][0])
    print("bubble L_inf error: ", Linf_data[i][1])

# %%
dh_values = [h['dh'] for h in h_data]
print(dh_values)
spike_L2_errors = [data[0] for data in L2_data]
bubble_L2_errors = [data[1] for data in L2_data]

# Plotting L2 errors on a log-log scale
plt.figure()
plt.plot(dh_values, spike_L2_errors, marker='o', label='Spike L2 Error')
plt.plot(dh_values, bubble_L2_errors, marker='s', label='Bubble L2 Error')
#plt.xscale('log')
# Set x-axis limits with consideration of valid logarithmic range
plt.xlabel("dh (grid spacing)")
plt.ylabel("L2 error")
plt.title("Log-Log Plot of L2 Errors")
plt.legend()
plt.grid(True)
plt.show()

# %%
