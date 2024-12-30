function [N, dpN] = shapesQL(xi, eta, p, q)
[N1, dpN1] = shapesLL(xi, p);
[N2, dpN2] = shapesLL(eta, q);

dpN = zeros(2,(p+1)*(q+1));
N = reshape(N1*N2', [(p+1)*(q+1),1]);

dpN(1,:) = reshape(dpN1*N2', [(p+1)*(q+1),1]); 
dpN(2,:) = reshape(N1*dpN2', [(p+1)*(q+1),1]);
end