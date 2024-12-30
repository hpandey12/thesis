function [N, dpN, ddpN] = shapesLL(xi, p, comp)
% -------------------------------------------------------------------------
% Function evaluates Lagrange polynomial of order p at point xi.
% If comp(1) = 1 function evaluates first derivative of the Lagrange
% polynomial at point xi.
% If comp(2) = 1 funciton evaluates second derivative of the Lagrange
% polynomial at point xi.
% -------------------------------------------------------------------------
N = zeros(1, p + 1);
dpN = zeros(1, p + 1);
ddpN = zeros(1, p + 1);
if p == 1
    [N, dpN, ddpN] = shpL1(xi, comp);
elseif p == 2
    [N, dpN, ddpN] = shpL2(xi, comp);
elseif p == 3
    [N, dpN, ddpN] = shpL3(xi, comp);
elseif p == 4
    [N, dpN, ddpN] = shpL4(xi, comp);
elseif p == 5
    [N, dpN, ddpN] = shpL5(xi, comp);
else
    fprintf('Order of Lagrange polynomial higher then 5 not implemented. \n');
end
end