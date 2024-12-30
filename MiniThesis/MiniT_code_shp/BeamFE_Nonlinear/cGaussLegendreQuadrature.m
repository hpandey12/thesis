function [x,w] = cGaussLegendreQuadrature(N)
% This helper function returns the quadrature points x and weights w for 1D
% Gauss-Legendre quadrature of order N on the interval [-1 1].
N=N-1;
N1=N+1; N2=N+2;
xu=linspace(-1,1,N1)';

% Initial guess
x= - (cos((2*(0:N)'+1)*pi/(2*N+2))+(0.27/N1)*sin(pi*xu*N/N2));

% Legendre-Gauss Vandermonde Matrix
L=zeros(N1,N2);

% Derivative of LGVM
Lp=zeros(N1,N2);

% Compute the zeros of the N+1 Legendre Polynomial
% using the recursion relation and the Newton-Raphson method

x0=2;
% Iterate until new points are uniformly within epsilon of old points
while max(abs(x-x0))>eps
    L(:,1)=1;
    Lp(:,1)=0;
    L(:,2)=x;
    Lp(:,2)=1;
    for m=2:N1
        L(:,m+1)=( (2*m-1)*x.*L(:,m)-(m-1)*L(:,m-1) )/m;
    end
    Lp=(N2)*( L(:,N1)-x.*L(:,N2) )./(1-x.^2);
    x0=x;
    x=x0-L(:,N2)./Lp;
end
% Compute the weights
w=2./((1-x.^2).*Lp.^2)*(N2/N1)^2;
end