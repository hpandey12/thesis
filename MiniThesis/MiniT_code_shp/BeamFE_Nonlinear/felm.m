% Initialise 
I2 = eye(2);
R = zeros(2*node.n, 1);
K = zeros(2*node.n, 2*node.n);
sigma_ = zeros(4, elem.n);
vonMises = zeros(1, elem.n);
%Elemental Loop
for i = 1:elem.n
    ie = zeros(1, (pp+1)*(pp+1)*2);
    ie(1:2:end) = 2*elem.con{i}-1;
    ie(2:2:end) = 2*elem.con{i};
    
    XI = node.X(elem.con{i}, :);
    xI = node.x(elem.con{i}, :);
    
    %Initialise elemental matrices
    rel = zeros(2*(pp+1)*(pp+1), 1);
    kmat = zeros(2*(pp+1)*(pp+1), 2*(pp+1)*(pp+1));
    kgeo = zeros(2*(pp+1)*(pp+1), 2*(pp+1)*(pp+1));
    sig_e = zeros(4, 1);
    NI = zeros(2, 2*(pp+1)*(pp+1));
    %Gauss Point Loop (Volume Integrals)
    for gp = 1:quadr.ngp
        [N, dpN] = shapesQL(quadr.gps(gp,1), quadr.gps(gp,2), pp, pp);
        NI(1, 1:2:end) = N';
        NI(2, 2:2:end) = N';
        
        Jac = xI'*dpN';
        detJ = det(Jac);
        invJ = inv(Jac);
        
        jac = XI'*dpN';
        detj = det(jac);
        invj = inv(jac);
        
        %Deformation check
        if detJ < 0
            jterm = 1;
            fprintf('\n Negative Jacobian determinant in element %5i \n', i);
        end
        if jterm == 1 ; break; end
        
        %Shape Fct (x,y) - Spatial Derivatives
        dN_dxy = dpN'*invJ;
        
        %B-Matrix
        B = zeros (4, 2*(pp+1)*(pp+1));
        for j = 1:(pp+1)*(pp+1)
           B(:,2*j-1:2*j) = [ dN_dxy(j,1)        0 ;
                                    0  dN_dxy(j,2) ;
                                    0        0 ;
                              dN_dxy(j,2)  dN_dxy(j,1) ];
        end
        
        % Deformation Gradient
        invF = zeros(2,2);
        Fgr = Jac*invj;
        detF = det(Fgr);
        
        % Cauchy Stress, Spatial Tangent (in Voigt notation; plane strain NH only)     
        Upr = lam/detF * log(detF) ;
        sit = mu/detF * ( Fgr*Fgr' - I2 ) ;
        
        tmp = Upr*I2 + sit ;
        
        sig(1,1) = tmp(1,1) ;
        sig(1,2) = tmp(2,2) ;
        sig(1,3) = 0 ;
        sig(1,4) = tmp(1,2) ;
        
        sig_e = sig_e + sig';
        %sig_e = sig(1,2);
        Dnl = D/detF - Upr*Is4 ;
                    
        % Elemental Force Vector (current-rho = rho0/detF)
        rel = rel + B'*sig'*detJ*quadr.w(gp) - NI'*g*detJ*quadr.w(gp) ;
        
        % Material Stiffness
        kmat = kmat + B'*Dnl*B*detJ*quadr.w(gp) ;
               
        % Geometrical Stiffness
        dNm1 = kron(dN_dxy(:,1)*dN_dxy(:,1)',I2);
        dNm2 = kron(dN_dxy(:,2)*dN_dxy(:,2)',I2);
        dNm3 = kron(dN_dxy(:,1)*dN_dxy(:,2)',I2);
        kgeo = kgeo + ( dNm1*sig(1)+dNm2*sig(2)+(dNm3 + dNm3')*sig(4) ) * detJ * quadr.w(gp);          
    end
    % Terminate Computation
    if jterm == 1 ; break ; end
    
    % Local Element Stiffness
    kel = kmat + kgeo ;
    
    % Structure Stiffness and Stress Divergence Term
    R(ie,1)  = R(ie,1)  + rel ;
    K(ie,ie) = K(ie,ie) + kel ;
    
    % Stress
    sigma_(:, i) = sigma_(:, i) + sig_e; 
    vonMises(1, i) = sqrt(sigma_(1,i)^2 + sigma_(2,i)^2 - (sigma_(1,i)*sigma_(2,i)) + 3*(sigma_(4,i)^2));
    %sigma_(:, i) = sigma_(:, i) + sig(1,2);
    %vonMises(1, i) = sig_e;
end