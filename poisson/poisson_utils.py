import pandas
import os
import numpy
import scipy
import math
import matplotlib.pyplot as plt

def return_outputframe_list(dir, filename_append, range_var, name):
    df_list = []
    for idx, i in enumerate(range_var):
        filename = name + str(i) + filename_append
        filepath = os.path.join(dir, filename)
        #print(filepath)
        try:
            output_frame = pandas.read_csv(filepath)
            [out_X, out_Y, out_T, el_vol] = [
                output_frame["X (m)"].to_numpy(), 
                output_frame["Y (m)"].to_numpy(), 
                output_frame["Temperature (K)"].to_numpy(),
                output_frame["Volume (m^3)"].to_numpy()]
            df = {
                'range_var': i,
                'x_coords': out_X,
                'y_coords': out_Y,
                'numerical_T': out_T,
                'elem_volume': el_vol
            }
            df_list.append(df)
        except FileNotFoundError:
            print(f"File not found: {filepath}")
        except pandas.errors.EmptyDataError:
            print(f"File is empty: {filepath}")
        except Exception as e:
            print(f"An error occurred while reading {filepath}: {e}")
        
    return df_list

#analytical solution
#T = (4V/pi) SUM_{n=0}^{inf}[(1/(2n+1)) sin(2n+1)pi*x/a sinh(b-y)(2n+1)pi/a cosech(2n+1)pi*b/a
def get_sinh(x):
    try:
        sinh = (1-math.expm1(-2*x))/(2*math.expm1(-x))
        return sinh
    except:
        print(f"wrong", {x})
    
def getTemp_xy(x: float = None, y: float = None, a: float = 1, b: float = 1 , BC: float = 1):
    SUM = 0
    errList = []
    for n in range(0,100):
        try:
            A = math.sin((2*n+1)*(math.pi*x)/a)
            B = math.sinh(((b-y)*(2*n+1)*math.pi/a))
            C = math.sinh((2*n+1)*math.pi*b/a)
            SUM += (1/((2*n)+1)) * (A * B / C) 
        except (RuntimeWarning, OverflowError) as e:
            errList.append(n)
            continue
        
    T_xy = (4*BC/math.pi)*SUM
    #print("---------------------------------------")
    if errList != []:
        print(f"first error at:", {errList[0]})
    return T_xy


def plot_temperature(final_T):
    """
    Plots the temperature distribution over a 2D domain.

    Parameters:
        final_T (list of lists): A list of the form [x_coord, y_coord, temperature]
    """
    # Extract x, y, and temperature values
    x_coords = [item[0] for item in final_T]
    y_coords = [item[1] for item in final_T]
    temperatures = [item[2] for item in final_T]
    
    # Create a scatter plot
    plt.figure(figsize=(8, 6))
    scatter = plt.scatter(x_coords, y_coords, c=temperatures, cmap='viridis', s=2.5)
    plt.colorbar(scatter, label="Temperature")
    plt.xlabel("X Coordinate")
    plt.ylabel("Y Coordinate")
    plt.title("2D Temperature Distribution")
    plt.grid(True)
    plt.show()

def plot_error_data(L2_data, log_bool):
    """
    Plots the given L2_data as a scatter plot with a line connecting the points.

    Parameters:
        L2_data (list): A list of [x, y] pairs to be plotted.
    """
    # Extract x and y values from the data
    x_values = [item[0] for item in L2_data]
    y_values = [item[1] for item in L2_data]

    # Create the plot
    plt.figure(figsize=(8, 6))
    plt.plot(x_values, y_values, marker='o', markersize = '4', label='L2 Data')
    if log_bool is True:
        plt.xscale('log')
        plt.yscale('log')
    # Customize the plot
    #plt.title("L2 Data Plot", fontsize=16)
    plt.xlabel(r"Number of elements", fontsize=12)
    plt.ylabel(r"L2 error", fontsize=12)
    plt.grid(True, which='both', linestyle='--', linewidth=0.5)
    #plt.legend(fontsize=12)

    # Display the plot
    plt.show()

def XY_steady_state_temperature(X, Y, a, b, BC): #BC = [x_min, x_max, y_min, y_max]
    final_T = []
    #print(numpy.min(X), numpy.max(X), numpy.min(Y), numpy.max(Y))
    for x, y in zip(X,Y):
        if x == numpy.min(X):
            final_T.append([float(x),float(y), BC[0]])
        elif x == numpy.max(X):
            final_T.append([float(x),float(y), BC[1]])
        elif y == numpy.min(Y):
            final_T.append([float(x),float(y), BC[2]])
        elif y == numpy.max(Y):
            final_T.append([float(x),float(y), BC[3]])
        else:
            bc = 0
            for num in BC:
                if num != 0:
                    bc = num
            final_T.append([float(x),float(y), getTemp_xy(x, y, a, b, bc)])
    return final_T


def return_L_errors(out_X, out_Y, out_T, el_vol, steady_state_data):
    a, b, BC = steady_state_data[0], steady_state_data[1], steady_state_data[2]
    steady_state_temp = XY_steady_state_temperature(out_X, out_Y, a, b, BC)
    calculated_T = []
    for temp_data in steady_state_temp:
        calculated_T.append(temp_data[-1])

    calculated_L2 = 0
    abs_err = []
    for num_T, act_T, vol in zip(out_T, calculated_T, el_vol):
        abs_err.append(numpy.abs(num_T-act_T))
        calculated_L2 += (numpy.abs(num_T-act_T)**2)*vol
        
    L2_err = numpy.sqrt(calculated_L2/len(out_T))
    L_inf = max(abs_err)
    return L_inf, L2_err
