import matplotlib.pyplot as plt
import numpy
import math
from typing import List
from dataclasses import dataclass
from scipy.integrate import quad
from scipy.optimize import fsolve
from scipy import special

def one_GaussLegendreQuadrature(self, n):
    N = n - 1
    N1 = n
    N2 = n + 1
    xu = numpy.linspace(-1, 1, N1).reshape(-1,1)
    x= - numpy.cos((2*(numpy.linspace(0,N,N1).reshape(-1,1)) + 1)*numpy.pi/(2*N+2)) + (0.27/N1)*numpy.sin(numpy.pi*xu*N/N2)
    L = numpy.zeros((n, n+1))
    x0 = 2

    while numpy.max(numpy.absolute(x-x0)) > numpy.finfo(numpy.float64).eps:
        L[:, 0] = 1
        L[: , 1] = numpy.transpose(x)
        for i in range(1,n):
            temp_Li = ((2*i+1)*(x.T*L[:,i]) - i*L[:,i-1])/(i+1)
            L[:, i+1] = numpy.reshape(temp_Li, (1,-1))
        temp_dLp = ((n)*(x.T*L[:, n]) - (n)*L[:, n-1])/((x.T*x.T) - 1)
        dLp = temp_dLp
        x0 = x
        x = x0 - numpy.reshape((L[:, n]/dLp),(-1,1))
    w = (2/((1-(x.T*x.T))*(dLp*dLp)))
    return x.T, w


class legendreGaussQuad():
    def __init__(self, ngp, dim):
        self.gps1d, self.w1d = self.oneD_GaussLegendreQuadrature(ngp)
        self.gps, self.w, self.ngp = self.multiD_GaussLegendreQuadrature(ngp, dim)
    
    def oneD_GaussLegendreQuadrature(self, n):
        N = n - 1
        N1 = n
        N2 = n + 1
        xu = numpy.linspace(-1, 1, N1).reshape(-1,1)
        x= - numpy.cos((2*(numpy.linspace(0,N,N1).reshape(-1,1)) + 1)*numpy.pi/(2*N+2)) + (0.27/N1)*numpy.sin(numpy.pi*xu*N/N2)
        L = numpy.zeros((n, n+1))
        x0 = 2

        while numpy.max(numpy.absolute(x-x0)) > numpy.finfo(numpy.float64).eps:
            L[:, 0] = 1
            L[: , 1] = numpy.transpose(x)
            for i in range(1,n):
                #using the recurrence relation:
                # (n+1)*P_n+1 = (2n+1)*x*P_n - n*P_n-1
                temp_Li = ((2*i+1)*(x.T*L[:,i]) - i*L[:,i-1])/(i+1)
                L[:, i+1] = numpy.reshape(temp_Li, (1,-1))
            #using the recurrence relation:
            #(1-x^2)*P'_n = (n)*[P_n-1 - x*P_n]
            temp_dLp = ((n)*(x.T*L[:, n]) - (n)*L[:, n-1])/((x.T*x.T) - 1)
            dLp = temp_dLp
            x0 = x
            x = x0 - numpy.reshape((L[:, n]/dLp),(-1,1))
        w = (2/((1-(x.T*x.T))*(dLp*dLp)))
        return x.T, w
    
    def multiD_GaussLegendreQuadrature(self, ngp, dim):
        gps = numpy.zeros((ngp**dim, dim))
        gps[:, 0] = numpy.tile(self.gps1d, ngp**(dim-1))
        if dim == 1:
            w = numpy.reshape(self.w1d, (-1,1))
        elif dim == 3:
            gps[:, 1] = numpy.tile(numpy.repeat(self.gps1d,ngp),ngp)
            gps[:, 2] = numpy.repeat(self.gps1d, ngp**(dim-1))
        else:
            gps[:, 1] = numpy.repeat(self.gps1d, ngp**(dim-1))
            w1 = numpy.tile(self.w1d, ngp**(dim-1))
            w2 = numpy.repeat(self.w1d, ngp**(dim-1))
            w = (w1*w2).T
        
        return gps, w, ngp**dim
    
## quadrature scaling: w_scaled_j = ((b-a)/(d-c))/w_j -> for [c,d] = [-1,1], w_scaled_j = (b-a)/2 * w_j
##                     x_scaled_j = c + (d-c)/(b-a) * (x_j-a) -> for [c,d] = [-1, 1], s_scaled_j = -1 + 2/(b-a) * (x_j-a) 
    def integrate_func(self, func, lim):
        if len(lim) > 1:
            a, b = lim
        else:
            a = 0
            b = lim
        integral = 0
        #print(self.w1d)
        #print(self.gps1d)
        for wx, gpsx in zip(self.w, self.gps):
            #print(gpsx)
            integral += wx*func((b-a)*0.5*gpsx + (b+a)*0.5)
            #print(integral)
        #print('-----', (b-a)*0.5*integral)
        return (b-a)*0.5*integral