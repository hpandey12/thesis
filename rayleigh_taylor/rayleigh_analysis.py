# %%
import numpy
import pandas
import os
import numpy
import scipy
import mod_utils as utils
import matplotlib.pyplot as plt
import os
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
# %% ###############################################################
######################## Convergence Study #########################
####################################################################
import scienceplots
dir_list = [
    r'/hpcwork/yy310050/thesis/HARD_DATA/RAYLEIGH_TAYLOR/VOF/re3000_at05_01',
    r"/hpcwork/yy310050/thesis/HARD_DATA/RAYLEIGH_TAYLOR/VOF/re3000_at05_r65",
    r'/hpcwork/yy310050/thesis/HARD_DATA/RAYLEIGH_TAYLOR/VOF/re3000_at05_r95',
    r"/hpcwork/yy310050/thesis/rayleigh_taylor/VOF/final_sims/Re3000_Amp01/Re3000_At0.5_sigma1e-05_dh0.0045",
    #r"/hpcwork/yy310050/thesis/rayleigh_taylor/2Fluid/final_sims/at05_re3000/more_intensity",
    #r"/hpcwork/yy310050/thesis/rayleigh_taylor/2Fluid/final_sims/at05_re3000/adis",
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
dns_df = pandas.read_csv(r'/home/yy310050/Desktop/thesis/rayleigh_taylor/dns_compare_data/2D_Single-Mode_At0.5_Re3000_A0.05.csv')
#----------------------------------------------------
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import scienceplots
import tol_colors

plt.rcParams['axes.linewidth'] = 10
plt.rcParams['font.family'] = 'Arial'
plt.rcParams['font.size'] = 12
plt.rcParams['xtick.labelsize'] = 10
plt.rcParams['ytick.labelsize'] = 10

plt.style.use(['science', 'no-latex'])
tol_colors.set_default_colors(cset='bright')
fig, ax = plt.subplots(figsize=(8, 8), dpi=900)

L2_data = []
Linf_data = []
for h in h_data:
    plt.plot(
        h['timesteps'][h['timesteps'] < dns_df['Time'].max()], 
        h['y_bub'][h['timesteps'] < dns_df['Time'].max()], 
        label=str(r'Bubble \Delta dh: ' + h['dh']))#, marker = 'o',markerfacecolor='none')
    plt.plot(
        h['timesteps'][h['timesteps'] < dns_df['Time'].max()], 
        h['y_spike'][h['timesteps'] < dns_df['Time'].max()], 
        label=str(r'Spike \Delta dh: ' + h['dh']))
    #, marker = 'x')
    L2_err_spike, Linf_err_spike = calc_errors(dns_df['Spike'], interpolate_central_difference(h['y_spike'], h['timesteps'], dns_df['Time']))
    L2_err_bubble, Linf_err_bubble = calc_errors(dns_df['Bubble'], interpolate_central_difference(h['y_bub'], h['timesteps'], dns_df['Time']))
    L2_data.append([L2_err_spike, L2_err_bubble])
    Linf_data.append([numpy.max(Linf_err_spike), numpy.max(Linf_err_bubble)])
plt.plot(dns_df['Time'], dns_df['Bubble'], label='DNS_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
plt.plot(dns_df['Time'], dns_df['Spike'], label='DNS_spike', marker = 'x', linestyle='None', markerfacecolor='none')
#plt.plot(dns_df['Time'], dns_df2['Bubble'], label='dn2s_bubble', marker = 'o', linestyle='None', markerfacecolor='none')
#plt.plot(dns_df['Time'], dns_df2['Spike'], label='dn2s_spike', marker = 'x', linestyle='None', markerfacecolor='none')

#ax.set_ylim(0.0050, 0.025)
#ax.set_xlim(0, None)
#ax.yaxis.set_major_locator(ticker.MultipleLocator(0.0025))
#ax.yaxis.set_minor_locator(ticker.MultipleLocator(0.0005))
#ax.xaxis.set_major_locator(ticker.MultipleLocator(100))
#ax.xaxis.set_minor_locator(ticker.NullLocator())
label_font = {'fontsize': 12, 'fontfamily': 'Arial'}
ax.set_xlabel(r"\Delta \text{h} (m)" , **label_font)
ax.set_ylabel(r"L_{2}\,\text{error}", **label_font)

plt.xlabel("time")
plt.ylabel("bubble and spike distances")
plt.title("single-mode_At0.5_Re300_A0.1")


legend_box = ax.legend(
    frameon=True,
    prop={'family': 'Arial', 'size': 10}
)

ax.grid(True)
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
#%%
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle
from pathlib import Path


# ------------------------------------------------------------------
# 2) Read the CSV
# ------------------------------------------------------------------
csv_files = [
    Path("/hpcwork/yy310050/thesis/rayleigh_taylor/VOF/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.05/isosurface_table_1406.csv"),
    Path("/hpcwork/yy310050/thesis/rayleigh_taylor/VOF/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.007/isosurface_table_1406.csv"),
    Path("/hpcwork/yy310050/thesis/rayleigh_taylor/VOF/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.0045/isosurface_table_1406.csv"),
     Path("/hpcwork/yy310050/thesis/rayleigh_taylor/VOF/final_sims/convergence/Re300_At0.5_sigma1e-05_dh0.0025/isosurface_table_1406.csv")
]
x_len     = 1   
data_frames = []
for fp in csv_files:
    if fp.is_file():
        df = pd.read_csv(fp)
        data_frames.append((fp.stem, df))      # store a name + DataFrame
    else:
        print(f"Warning: {fp} does not exist and will be skipped.")

if not data_frames:
    raise RuntimeError("No CSV files found — nothing to plot.")


# ------------------------------------------------------------------
# 3) Prepare the plot
# ------------------------------------------------------------------
fig, ax = plt.subplots(figsize=(2.5, 10))    # keeps the 1 : 4 aspect ratio

# Colours — one for each file (repeat if there are more than the colormap size)
cmap   = plt.cm.get_cmap("tab20")
colors = cmap(numpy.linspace(0, 1, len(data_frames)))

# -----------------------------------------------------------------------------
# 4) Scatter plot for every file
# -----------------------------------------------------------------------------
for (label, df), col in zip(data_frames, colors):
    ax.scatter(df["X (m)"], df["Y (m)"], s=0.5, alpha=0.8, color=col, label=label)

# ------------------------------------------------------------------
# 4) Define and draw the bounding box
# ------------------------------------------------------------------
# You can choose any anchor; here we use the minimum X and Y in the data
x0, y0 = df["X (m)"].min(), df["Y (m)"].min()

# Add visible rectangle
bbox = Rectangle((0, 2), x_len, 4 * x_len,
                 linewidth=2, edgecolor="none", facecolor="none")
ax.add_patch(bbox)

# Limit the axes to that rectangle
ax.set_xlim(x0, x0 + x_len)
ax.set_ylim(0, 4)
ax.set_aspect("equal", adjustable="box")

# ------------------------------------------------------------------
# 5) Decorations
# ------------------------------------------------------------------
ax.set_xlabel("X (m)")
ax.set_ylabel("Y (m)")
ax.set_title(f"Scatter of X-Y points inside a {x_len} m × {4*x_len} m box")
ax.grid(True, linestyle="--", linewidth=0.5, alpha=0.6)

plt.tight_layout()
plt.show()

# %% 
####################################################################
############# single-mode_At0.2_Re3000_A0.1 ########################
####################################################################
dir = r'/home/yy310050/Desktop/thesis/rayleigh_taylor/final_sims/Re3000_At0.2_sigma1e-05_dh0.0045'
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
