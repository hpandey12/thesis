function [N, dpN, ddpN] = shpL1(xi, comp)
% -------------------------------------------------------------------------
% Funciton calculate the value of first order Lagrange polynomial at point 
% xi.
% If comp(1) = 1 function evaluates first derivative of the Lagrange
% polynomial at point xi.
% If comp(2) = 1 funciton evaluates second derivative of the Lagrange
% polynomial at point xi.
%
% The element in the parametric space can be seen below:
%
%   x ---------------------- x
%  -1                        1
%
% -------------------------------------------------------------------------
N = [ 1/2 - xi/2; xi/2 + 1/2 ];
% -------------------------------------------------------------------------
if comp(1)
    dpN = [ -1/2; 1/2 ];
else 
    dpN = [ 0; 0 ];
end
% -------------------------------------------------------------------------
if comp(2)
    ddpN = [ 0; 0 ];
else 
    ddpN = [ 0; 0 ];
end
end