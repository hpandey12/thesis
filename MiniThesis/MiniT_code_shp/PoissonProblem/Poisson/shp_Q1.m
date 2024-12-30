function [ N, dN, ddN ] = shp_Q1( xi, eta, comp )
%DDL1_SHAPES2D Linear Lagrange shape functions and derivatives

N   = zeros(4,1) ;
dN  = zeros(4,2) ;
ddN = zeros(4,3) ;

% Shape Functions
if comp(1)
  N = [ (1-xi )*(1-eta) ;
        (1+xi )*(1-eta) ;
        (1+xi )*(1+eta) ;
        (1-xi )*(1+eta) ]* 0.25 ;
end
  
% Shape Fct first derivatives
if comp(2)
  dN = [ -(1-eta)  -(1-xi) ;
           (1-eta)  -(1+xi) ;
           (1+eta)   (1+xi) ;
          -(1+eta)   (1-xi) ] * 0.25 ;
end

% Shape Fct second derivatives (order: 11 22 12)
if comp(3)
  ddN(:,3) = [  0.25 ; -0.25 ; 0.25 ; -0.25 ] ;
end

end