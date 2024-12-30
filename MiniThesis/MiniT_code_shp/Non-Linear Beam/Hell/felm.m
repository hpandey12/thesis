% Element Loop: 11. Finite Bilinear Quad
% Roger Sauer - 15.12.10


% Initialize
I2 = eye(2); 
R  = zeros(2*nno,1);
K  = zeros(2*nno,2*nno);
dim = (pp+1)*(pp+1);
dof = 2;
% if size(sig) == [4 1]
%    sig = sig'; 
% end
% -------------------------------------------------------------------------
ngp = 4;
[gps1d, w1d] = cGaussLegendreQuadrature(ngp);
% -------------------------------------------------------------------------
quadr.gps = zeros(ngp^2,2);
quadr.gps(:,1) = (repmat(gps1d', 1, ngp))';
quadr.gps(:,2) = (reshape(repmat(gps1d', ngp, 1), 1, ngp^2))';
quadr.ngp = size(quadr.gps,1);
% -------------------------------------------------------------------------
w1 = (repmat(w1d', 1, ngp))';
w2 = (reshape(repmat(w1d', ngp,1), 1, ngp^2))';
quadr.w = w1.*w2;
% -------------------------------------------------------------------------

% Elemental Loop
for i = 1 : nel   
    % Previous Timestep Dofs
    con = 2*CON(i,:);
    ie = zeros(1, 2*numel(con));
    ie_cnt = 1;
    for con_cnt = 1:numel(con)
        ie(ie_cnt) = con(con_cnt) - 1;
        ie_cnt = ie_cnt + 1;
        ie(ie_cnt) = con(con_cnt);
        ie_cnt = ie_cnt + 1;
    end
    
    % Initial and Current Nodal Coordinates and Displacements
    XI = Xn(CON(i,:),:); 
    xI = xn(CON(i,:),:); 
    
    % Initialize 
    rel  = zeros(dof*dim,1);
    kmat = zeros(dof*dim, dof*dim);
    kgeo = zeros(dof*dim, dof*dim);
    
    % Gauss Point Loop (Volume Intgrals)
    for gp = 1:ngp
        % Gauss point coordinates and weights
        [N, dpN] = shapesQL(quadr.gps(gp,1), quadr.gps(gp,2), pp, pp);
        NI(1, 1:2:(dof*dim)) = N';
        NI(2, 2:2:(dof*dim)) = N';
        
        % Jacobian between Current Configuration and Master Element
        Jac = dpN*xI;
        Jac = Jac';
        detJ = det(Jac); 
        invJ = inv(Jac);
        
        jac = dpN*XI;
        jac = jac';
        detj = det(jac);
        invj = inv(jac);
        
        % Deformation check
        if detJ < 0 
           jterm = 1;
           fprintf('\n Negative Jacobian determinant in element %5i \n',i)
        end
        if jterm == 1 ; break ; end
        
        % Shape Fct (x,y)- Spatial Derivatives
        dN_dxy = (Jac\dpN)';
        
        % B-Matrix
        B = zeros(4,dof*dim) ;
        for j = 1:(dof*dim/2)
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
end
