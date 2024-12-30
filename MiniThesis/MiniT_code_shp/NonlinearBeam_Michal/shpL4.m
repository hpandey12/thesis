function [ N, dpN, ddpN ] = shpL4(xi, comp)
% -------------------------------------------------------------------------
% Funciton calculate the value of fourth order Lagrange polynomial at point 
% xi.
% If comp(1) = 1 function evaluates first derivative of the Lagrange
% polynomial at point xi.
% If comp(2) = 1 funciton evaluates second derivative of the Lagrange
% polynomial at point xi.
%
% The element in the parametric space can be seen below:
%
%   x --- x --- x --- x --- x
%  -1   -.5     0    .5     1
%
% -------------------------------------------------------------------------
N = [ (xi*(2*xi + 1)*(xi - 1)*(xi - 1/2))/3; 
    -(4*xi*(2*xi + 2)*(xi - 1)*(xi - 1/2))/3; 
    4*(xi - 1)*(xi + 1)*(xi - 1/2)*(xi + 1/2); 
    -4*xi*((2*xi)/3 + 2/3)*(xi - 1)*(xi + 1/2); 
    (4*xi*(xi/2 + 1/2)*(xi - 1/2)*(xi + 1/2))/3 ];
% -------------------------------------------------------------------------
if comp(1)
    dpN = [ -((4*xi - 1)*(- 4*xi^2 + 2*xi + 1))/6;
        -(32*xi^3)/3 + 4*xi^2 + (16*xi)/3 - 4/3;
        16*xi^3 - 10*xi;
        -(32*xi^3)/3 - 4*xi^2 + (16*xi)/3 + 4/3;
        ((4*xi + 1)*(4*xi^2 + 2*xi - 1))/6 ];
else
    dpN = [0; 0; 0; 0; 0];
end
% -------------------------------------------------------------------------
if comp(2)
    ddpN = [ 8*xi^2 - 4*xi - 1/3;
        -32*xi^2 + 8*xi + 16/3;
        48*xi^2 - 10;
        -32*xi^2 - 8*xi + 16/3;
        8*xi^2 + 4*xi - 1/3 ];
else
    ddpN = [ 0; 0; 0; 0; 0 ];
end
end