function [N, dpN, ddpN] = shpL3(xi, comp)
% -------------------------------------------------------------------------
% Funciton calculate the value of third order Lagrange polynomial at point 
% xi.
% If comp(1) = 1 function evaluates first derivative of the Lagrange
% polynomial at point xi.
% If comp(2) = 1 funciton evaluates second derivative of the Lagrange
% polynomial at point xi.
%
% The element in the parametric space can be seen below:
%
%   x ----- x ----- x ----- x
%  -1     -.33     .33      1
%
% -------------------------------------------------------------------------
N = [ -(3*((3*xi)/2 + 1/2)*(xi - 1)*(xi - 1/3))/8;
    (9*((3*xi)/2 + 3/2)*(xi - 1)*(xi - 1/3))/8;
    -(9*((3*xi)/4 + 3/4)*(xi - 1)*(xi + 1/3))/4;
    (9*(xi/2 + 1/2)*(xi - 1/3)*(xi + 1/3))/8 ];
% -------------------------------------------------------------------------
if comp(1)
    dpN = [ (9*xi)/8 - (27*xi^2)/16 + 1/16;
        (81*xi^2)/16 - (9*xi)/8 - 27/16;
        27/16 - (81*xi^2)/16 - (9*xi)/8;
        (27*xi^2)/16 + (9*xi)/8 - 1/16 ];
else
    dpN = [ 0; 0; 0; 0 ];
end
% -------------------------------------------------------------------------
if comp(2)
    ddpN = [ 9/8 - (27*xi)/8;
        (81*xi)/8 - 9/8;
        -(81*xi)/8 - 9/8;
        (27*xi)/8 + 9/8 ];
else
    ddpN = [ 0; 0; 0; 0 ];
end
end