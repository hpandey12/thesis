function [N, dpN] = shapesQL(xi, eta, p, q)
[N1, dpN1] = shapesLL(xi, p);
[N2, dpN2] = shapesLL(eta, q);

dpN = zeros(2,(p+1)*(q+1));
N = reshape(N2*N1', [(p+1)*(q+1),1]);

dpN(1,:) = reshape(N2*dpN1', [(p+1)*(q+1),1]); 
dpN(2,:) = reshape(dpN2*N1', [(p+1)*(q+1),1]);
end