function [N, dpN, ddpN] = shpL2(xi, comp)
% -------------------------------------------------------------------------
% Funciton calculate the value of second order Lagrange polynomial at point 
% xi.
% If comp(1) = 1 function evaluates first derivative of the Lagrange
% polynomial at point xi.
% If comp(2) = 1 funciton evaluates second derivative of the Lagrange
% polynomial at point xi.
%
% The element in the parametric space can be seen below:
%
%   x ----------- x ----------- x
%  -1             0             1
%
% -------------------------------------------------------------------------
N = [ (xi*(xi - 1))/2; -(xi - 1)*(xi + 1); xi*(xi/2 + 1/2) ];
% -------------------------------------------------------------------------
if comp(1)
    dpN = [ xi - 1/2; -2*xi; xi + 1/2 ];
else
    dpN = [ 0; 0; 0 ];
end
% -------------------------------------------------------------------------
if comp(2)
    ddpN = [ 1; -2; 1 ];
else
    ddpN = [ 0; 0; 0; ];
end
end