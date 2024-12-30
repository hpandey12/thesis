function [N, dpN, ddpN] = shapesQL(xi, p, q, comp)
N1 = zeros(p+1,1);
dN1 = zeros(p+1,1);
ddN1 = zeros(p+1,1);
N2 = zeros(q+1,1);
dN2 = zeros(q+1,1);
ddN2 = zeros(q+1,1);

[N1(1:p+1), dN1(1:p+1), ddN1(1:p+1)] = shapesLL(xi(1), p, comp);
[N2(1:q+1), dN2(1:q+1), ddN2(1:q+1)] = shapesLL(xi(2), q, comp);

dpN = zeros((p+1)*(q+1),2);
ddpN = zeros((p+1)*(q+1),3);

N = reshape(N1(1:p+1) * N2(1:q+1)', (p+1)*(q+1), 1);
dpN(:,1) = reshape(dN1(1:p+1) * N2(1:q+1)', (p+1)*(q+1), 1);
dpN(:,2) = reshape(N1(1:p+1) * dN2(1:q+1)', (p+1)*(q+1), 1);
ddpN(:,1) = reshape(ddN1(1:p+1) * dN2(1:q+1)', (p+1)*(q+1), 1);
ddpN(:,2) = reshape(dN1(1:p+1) * ddN2(1:q+1)', (p+1)*(q+1), 1);
ddpN(:,3) = reshape(dN1(1:p+1) * dN2(1:q+1)', (p+1)*(q+1), 1);
end