import matplotlib.pyplot as plt
import numpy
import math
from typing import List
from dataclasses import dataclass
from scipy.integrate import quad
from scipy.optimize import fsolve
from scipy import special
from mod_gaussQuad import legendreGaussQuad



#alpha = kappa/(rho*c_p) 
@dataclass
class stefan_variables:
    c_p: float = 1
    kappa: float = 1
    rho: float = 1
    T_l: float = 1
    T_m: float = 1
    h_m: float = 1
    quadrature: legendreGaussQuad = None
    @property
    def alpha(self):
        return self.kappa / (self.rho * self.c_p)

def sci_erf(x: float = None):
    return special.erf(x)

def errfunc(x: float = None):
    erf = 2/(math.pi)*numpy.exp(-x**2)
    return erf

#erf(x) := (2/pi) * int^x_0 exp(-1*y**2) dy
def integrate_erf(x: float = None, quadr: legendreGaussQuad = None):
    integral = quadr.integrate_func(errfunc, [0, x])
    return integral 

#lambda is the unique root of the monotonic function,
#f(lambda) = c_p*((T_m - T_l)/h_m) * exp(-1*lambda**2)/(sqrt(pi)*erf(lambda)) - lambda
def return_lambda(vars, lam_init: float = 1.11125):
    stef_num = vars.c_p * ((vars.T_m - vars.T_l)/vars.h_m)
    # = lambda x: c_p*((T_m - T_l)/h_m) * numpy.exp(-1*(x**2))/(numpy.sqrt(math.pi)*integrate_erf(x, quadr)) - x
    def f_lam(lam):
        #erf = integrate_erf(lam, quadr)
        return stef_num * numpy.exp(-1*(lam**2))/(numpy.sqrt(math.pi)*special.erf(lam)) - lam
    roots = fsolve(f_lam, lam_init)
    return roots[0] 

def plot_lam(vars):
    c_p = vars.c_p
    kappa = vars.kappa
    rho = vars.rho
    T_l = vars.T_l
    T_m = vars.T_m
    h_m = vars.h_m
    alpha = vars.alpha
    quadr = vars.quadrature
    def f_l(lam):
            #print(lam)
            erf = integrate_erf(lam, quadr)
            #print(lam, erf)
            return c_p*((T_m - T_l)/h_m) * numpy.exp(-1*(lam**2))/(numpy.sqrt(math.pi)*erf) - lam
    def f_l_np(lam):
            #print(lam)
            erf = integrate_erf(lam, quadr)
            #print(lam, erf)
            return c_p*((T_m - T_l)/h_m) * numpy.exp(-1*(lam**2))/(numpy.sqrt(math.pi)*sci_erf(lam)) - lam
            
    x_val = numpy.linspace(-1.9, 1.9, 100)
    y_val = []
    for x in x_val:
        y_val.append(f_l(x)-f_l_np(x))
    return x_val, y_val
    
#X = 2*sqrt(alpha)*lambda*sqrt(t), for 0 < t < t_end
def interface_position(vars, lam_init: float = 1.7, t_all: List[float] = None, t: float = -7.0):
    if t == -7.0 and t_all is not None:
        X = []
        for idx, t_i in enumerate(t_all):
            lam = return_lambda(vars= vars, lam_init= lam_init)
            X.append(2*numpy.sqrt(vars.alpha*t_i)*lam)
        return X
    elif t_all is None and t is not None:
        lam = return_lambda(vars= vars, lam_init= lam_init)
        X = 2*numpy.sqrt(vars.alpha*t)*lam
        return X

#T = T_l - (T_l - T_m)*erf(x/2*sqrt(alpha*t))/erf(X(t)/2*sqrt(alpha*t)) for 0 <= x < X(t), 0 < t < t_end
def temperature_profile(vars, x_all: bool = False, x_loc: float = 100, t: float = -1.0, t_all: List[float] = None):
    def return_single(x_loc, t):
        #print(pos_interface)
        #print(vars.alpha)
        num = special.erf(x_loc/(2*numpy.sqrt(vars.alpha*t)))
        denom = special.erf(pos_interface/(2*numpy.sqrt(vars.alpha*t)))
        return vars.T_l - ((vars.T_l - vars.T_m)*(num/denom))

    if x_all is False:
        if t_all is None:
            pos_interface = interface_position(vars= vars, t= t)
            #print(pos_interface)
            if x_loc < pos_interface:
                return return_single(x_loc, t)
            else:
                return vars.T_m
        
        elif t == -1.0 and t_all is not None:
            T_probe = []
            for t_i in t_all:
                pos_interface = interface_position(vars= vars, t= t_i)
                if x_loc < pos_interface:
                    T_probe.append(return_single(x_loc, t_i))
                else:
                    T_probe.append(vars.T_m)
            return T_probe
    
    elif x_all is True:
        #x = numpy.linspace(0, int(x_loc), 1000)
        #print('x = linspace(0, ', int(x_loc),', 1000)')
        T_profile = []
        
        if t_all is None:
            pos_interface = interface_position(vars= vars, t= t)
            T_temp = []
            for x_i in x_loc:
                if x_i < pos_interface:
                    T_temp.append(return_single(x_i, t))
                
                else:
                    T_temp.append(vars.T_m)
            T_profile.append(numpy.reshape(T_temp,(1,-1)))

        elif t == -1.0 and t_all is not None:
            for t_i in t_all:
                pos_interface = interface_position(vars= vars, t= t_i)
                T_temp = []
                for x_i in x_loc:
                    if x_i < pos_interface:
                        T_temp.append(return_single(x_i, t_i))
                    
                    else:
                        T_temp.append(vars.T_m)
                T_profile.append(numpy.reshape(T_temp,(1,-1)))

        return T_profile