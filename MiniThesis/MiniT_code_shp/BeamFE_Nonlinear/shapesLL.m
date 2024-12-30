function [N, dpN] = shapesLL(xi, p)
% -------------------------------------------------------------------------
% Function evaluates Lagrange polynomial of order p at point xi.
% -------------------------------------------------------------------------
N = zeros(1, p + 1);
dpN = zeros(1, p + 1);
if p == 1
    [N, dpN] = shpL1(xi);
elseif p == 2
    [N, dpN] = shpL2(xi);
elseif p == 3
    [N, dpN] = shpL3(xi);
elseif p == 4
    [N, dpN] = shpL4(xi);
elseif p == 5
    [N, dpN] = shpL5(xi);
else
    fprintf('Order of Lagrange polynomial higher then 5 not implemented. \n');
end
end