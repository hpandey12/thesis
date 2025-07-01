# stefan_solver.py

from __future__ import annotations
import math
import numpy as np
from typing import List
from scipy.integrate import quad
from scipy.optimize import fsolve
from scipy import special

# Import the abstract base class
from mod_solver import Solver

class StefanSolver(Solver):
    """
    A concrete solver for the one-dimensional Stefan problem, encapsulating
    computations like error function integrals, finding lambda, computing
    temperature profiles, etc.
    """

    def __init__(self, model: 'DynamicModel'):
        """
        StefanSolver expects model.params to contain:
            c_p, T_l, T_m, h_m, alpha, etc.
        """
        super().__init__(model)  # call Solver.__init__(model)

    def sci_erf(self, x: float) -> float:
        """Simple wrapper for scipy.special.erf."""
        return special.erf(x)

    def errfunc(self, x: float) -> float:
        """
        The integrand for our custom error function:
          erf_custom(x) := (2/π) * ∫(0->x) exp(-y^2) dy
        => derivative = (2/π)*exp(-x^2).
        """
        return 2.0 / math.pi * np.exp(-x**2)

    def integrate_erf(self, x: float) -> float:
        """
        Integrate errfunc from 0 to x using scipy.integrate.quad.
        Returns ∫(0->x) [2/π * exp(-t^2)] dt.
        """
        integral_value, _ = quad(self.errfunc, 0, x)
        return integral_value

    def return_lambda(self, lam_init: float = 1.0) -> float:
        """
        Solve for lambda using fsolve, where:
          f(lambda) = stef_num * exp(-lambda^2) / (sqrt(pi) * erf(lambda)) - lambda = 0
        """
        c_p = self.model.params["c_p"]
        T_l = self.model.params["T_l"]
        T_m = self.model.params["T_m"]
        h_m = self.model.params["h_m"]

        stef_num = c_p * ((T_m - T_l) / h_m)

        def f_lam(lam):
            return stef_num * np.exp(-lam**2) / (math.sqrt(math.pi) * special.erf(lam)) - lam

        root, = fsolve(f_lam, lam_init)
        return root

    def plot_lam(self):
        """
        Compare two ways of computing the same function for lam in [-1.9, 1.9]:
          1) Using our custom-integrated error function,
          2) Using scipy.special.erf directly.
        Returns arrays for plotting.
        """
        c_p = self.model.params["c_p"]
        T_l = self.model.params["T_l"]
        T_m = self.model.params["T_m"]
        h_m = self.model.params["h_m"]

        def f_l(lam):
            erf_val = self.integrate_erf(lam)
            return c_p * ((T_m - T_l)/h_m) * np.exp(-lam**2) / (math.sqrt(math.pi)*erf_val) - lam

        def f_l_np(lam):
            return c_p * ((T_m - T_l)/h_m) * np.exp(-lam**2) / (math.sqrt(math.pi)*special.erf(lam)) - lam

        x_val = np.linspace(-1.9, 1.9, 100)
        y_val = [f_l(x) - f_l_np(x) for x in x_val]
        return x_val, y_val

    def interface_position(self, lam_init: float = 1.7, t_all: List[float] = None, t: float = -7.0):
        """
        Compute X(t) = 2 * sqrt(alpha * t) * lambda
        If t_all is given, compute multiple values; otherwise single time t.
        """
        alpha = self.model.params["alpha"]

        if t == -7.0 and t_all is not None:
            X_list = []
            for t_i in t_all:
                lam = self.return_lambda(lam_init)
                X_list.append(2.0 * np.sqrt(alpha * t_i) * lam)
            return X_list
        elif t_all is None and t != -7.0:
            lam = self.return_lambda(lam_init)
            return 2.0 * np.sqrt(alpha * t) * lam

    def temperature_profile(self, x_all: bool = False, x_loc: float = 100, t: float = -1.0, t_all: List[float] = None):
        """
        Compute: T(x,t) = T_l - (T_l - T_m)* erf(x/(2√(αt))) / erf(X/(2√(αt)))
        for x < X(t), else T_m.
        """
        T_l = self.model.params["T_l"]
        T_m = self.model.params["T_m"]
        alpha = self.model.params["alpha"]

        def return_single(x_loc_in, t_in):
            pos_int = self.interface_position(t=t_in)
            num = special.erf(x_loc_in / (2.0 * np.sqrt(alpha * t_in)))
            den = special.erf(pos_int / (2.0 * np.sqrt(alpha * t_in)))
            return T_l - ((T_l - T_m) * (num / den))

        if not x_all:
            if t_all is None:
                pos_int = self.interface_position(t=t)
                return return_single(x_loc, t) if x_loc < pos_int else T_m
            else:
                results = []
                for t_i in t_all:
                    pos_int = self.interface_position(t=t_i)
                    val = return_single(x_loc, t_i) if x_loc < pos_int else T_m
                    results.append(val)
                return results
        else:
            x_vals = np.linspace(0, int(x_loc), 1000)
            T_profiles = []

            if t_all is None:
                pos_int = self.interface_position(t=t)
                T_temp = [
                    return_single(x_i, t) if x_i < pos_int else T_m
                    for x_i in x_vals
                ]
                T_profiles.append(np.reshape(T_temp, (1, -1)))
            else:
                for t_i in t_all:
                    pos_int = self.interface_position(t=t_i)
                    T_temp = [
                        return_single(x_i, t_i) if x_i < pos_int else T_m
                        for x_i in x_vals
                    ]
                    T_profiles.append(np.reshape(T_temp, (1, -1)))

            return T_profiles