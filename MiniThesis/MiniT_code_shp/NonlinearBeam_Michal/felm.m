% Element Loop: 11. Finite Bilinear Quad
% Roger Sauer - 15.12.10


% Initialize
I2 = eye(2); 
R  = zeros(2*node.n,1);
K  = zeros(2*node.n,2*node.n);

% Elemental Loop
for i = 1 : elem.n   
    ie = zeros(1,(p+1)*(q+1)*2);
    ie(1:2:end) = 2*elem.con{i}-1;
    ie(2:2:end) = 2*elem.con{i};
    % Initial and Current Nodal Coordinates and Displacements
    XI = node.X(elem.con{i},:); 
    xI = node.x(elem.con{i},:); 
    
    % Initialize 
    rel  = zeros((p+1)*(q+1)*2,1);
    kmat = zeros((p+1)*(q+1)*2,(p+1)*(q+1)*2);
    kgeo = zeros((p+1)*(q+1)*2,(p+1)*(q+1)*2);
    
    N = zeros(2,(p+1)*(q+1)*2);
    % Gauss Point Loop (Volume Intgrals)
    for gp = 1 : quadr.ngp
        % Shape Functions
        [NR, dpN, ~] = shapesQL(quadr.gps(gp,:), epro{i}.p, epro{i}.q, [1 0]);
        N(1,1:2:end) = NR';
        N(2,2:2:end) = NR';
        % Jacobian between Current Configuration and Master Element
        Jac = xI'*dpN;
        detJ = det(Jac); 
        invJ = inv(Jac);
        
        jac = XI'*dpN;
        detj = det(jac);
        invj = inv(jac);
        
        % Deformation check
        if detJ < 0 
           jterm = 1;
           fprintf('\n Negative Jacobian determinant in element %5i \n',i)
        end
        if jterm == 1 ; break ; end
        
        % Shape Fct (x,y)- Spatial Derivatives
        dN = dpN*invJ;
        
        % B-Matrix
        B = zeros(4,(p+1)*(q+1)*2) ;
        for j = 1 : (p+1)*(q+1)
           B(:,2*j-1:2*j) = [ dN(j,1)        0 ;
                                    0  dN(j,2) ;
                                    0        0 ;
                              dN(j,2)  dN(j,1) ];
        end
           
        % Deformation Gradient
        invF = zeros(2,2);
        Fgr = Jac*invj;
        detF = det(Fgr);
        
        % Cauchy Stress, Spatial Tangent (in Voigt notation; plane strain NH only)     
        Upr = lam/detF * log(detF);
        sit = mu/detF * (Fgr*Fgr' - I2);
        
        tmp = Upr*I2 + sit;
        
        sig(1,1) = tmp(1,1);
        sig(1,2) = tmp(2,2);
        sig(1,3) = 0;
        sig(1,4) = tmp(1,2);
        
        Dnl = D/detF - Upr*Is4;
                    
        % Elemental Force Vector (current-rho = rho0/detF)
        rel = rel + B'*sig'*detJ*quadr.w(gp) - N'*g*detJ*quadr.w(gp);
        
        % Material Stiffness
        kmat = kmat + B'*Dnl*B*detJ*quadr.w(gp);
               
        % Geometrical Stiffness
        dNm1 = kron(dN(:,1)*dN(:,1)',I2);
        dNm2 = kron(dN(:,2)*dN(:,2)',I2);
        dNm3 = kron(dN(:,1)*dN(:,2)',I2);
        kgeo = kgeo + (dNm1*sig(1)+dNm2*sig(2)+(dNm3 + dNm3')*sig(4)) * detJ * quadr.w(gp);          
    end
 
    % Terminate Computation
    if jterm == 1 ; break ; end
    
    % Local Element Stiffness
    kel = kmat + kgeo ;
    
    % Structure Stiffness and Stress Divergence Term
    R(ie,1)  = R(ie,1)  + rel ;
    K(ie,ie) = K(ie,ie) + kel ;
end