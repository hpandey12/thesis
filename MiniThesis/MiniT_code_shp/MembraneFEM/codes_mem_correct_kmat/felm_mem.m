% Initialize
if volc == 1
    R = zeros(3*node.n+1,1);
    K  = zeros(3*node.n+1,3*node.n+1) ;
else
    R = zeros(3*node.n,1);
    K  = zeros(3*node.n,3*node.n) ;
end
vonMises = zeros(1, elem.n);

for e = 1 : elem.n
    sigma = zeros(2,2);
    nne = (epro{e}.p+1)*(epro{e}.q+1);
    % dofs per element
    el_dof = 3*nne;
    % Previous Timestep Dofs
    con = 3*elem.con{e};
    ie = zeros(1,el_dof);
    ie(1:3:el_dof) = con-2;
    ie(2:3:el_dof) = con-1;
    ie(3:3:el_dof) = con;
    
    if volc == 1
        ie = [ie dof.n] ;
        pextv = Load.p + Load.pext;  % pressure due to volume constraint and ext. loading
        len = (el_dof+1)*(el_dof+1);
    else
        len = (el_dof)*(el_dof);
        pextv = Load.pex0;
    end
    
    % Initial and Current Nodal Coordinates
    Xn = node.X(elem.con{e},:);
    xn = node.x(elem.con{e},:);
    % Vector representation
    XIs = reshape(Xn',el_dof,1);
    xIs = reshape(xn',el_dof,1);
    
    % pressure loading and extended system for constrained volume
    %pextv = Load.pext;
    
    % Initialize
    rint   = zeros(el_dof,1);
    rextg  = zeros(el_dof,1);
    rextp  = zeros(el_dof,1);
    kmat   = zeros(el_dof,el_dof);
    kgeo   = zeros(el_dof,el_dof);
    kgeoh  = zeros(nne,nne);
    kext   = zeros(el_dof,el_dof);
    lext   = zeros(el_dof,1);
    gvel   = 0;
    kvel   = zeros(1,el_dof);
    N      = zeros(3,el_dof);
    dN1    = zeros(3,el_dof);
    dN2    = zeros(3,el_dof);
    
    % quadrature loop
    for gp = 1 : quadr.ngp
        % Shape Functions
        [NR, dpN, ~] = shapesQL(quadr.gps(gp,:), epro{e}.p, epro{e}.q, [1 0]);
        
        % shape function arrays
        N(1,1:3:el_dof) = NR;
        N(2,2:3:el_dof) = NR;
        N(3,3:3:el_dof) = NR;
        
        dN1(1,1:3:el_dof) = dpN(:,1);
        dN1(2,2:3:el_dof) = dpN(:,1);
        dN1(3,3:3:el_dof) = dpN(:,1);
        dN2(1,1:3:el_dof) = dpN(:,2);
        dN2(2,2:3:el_dof) = dpN(:,2);
        dN2(3,3:3:el_dof) = dpN(:,2);
        
        dN1h = dpN(:,1)';
        dN2h = dpN(:,2)';
        
        % quadrature point
        xgp  = N * xIs ;
        
        % co-variant basis
        A_1 = dN1 * XIs;
        A_2 = dN2 * XIs;
        
        a_1 = dN1 * xIs;
        a_2 = dN2 * xIs;
        
        % co-variant surface metric
        A_ab = [A_1'*A_1 A_1'*A_2; A_2'*A_1 A_2'*A_2];
        a_ab = [a_1'*a_1 a_1'*a_2; a_2'*a_1 a_2'*a_2];
        
        % contra-variant surface metric
        Aab  = inv(A_ab);
        aab = inv(a_ab);
        
        % contra-variant basis
        a1 = aab(1,1)*a_1 + aab(1,2)*a_2;
        a2 = aab(2,1)*a_1 + aab(2,2)*a_2;
        
        % determinants (surface stretch w.r.t. master configuration)
        Det_ab = det(A_ab) ;
        Gs = sqrt(Det_ab) ;
        det_ab = det(a_ab) ;
        gs = sqrt(det_ab) ;
        
        % surface stretch between current and reference configuration
        Js = gs/Gs;
        Jss = Js*Js ;
        
        % Deformation warning
        if abs(Js-1) < eps
            fprintf(2,'Negative area detected in (i,gp) %4i %3i',e,gp) ;
            term = 1 ;
        end
        
        % Calculate surface normal
        n_s = cross(a_1,a_2);
        n_s = n_s/norm(n_s);
        
        % contra-variant compontents of the 'Kirchhoff' Stress (incompressible NH)
        %         tau = mat.lamb/2 * (Jss - 1) * aab + mat.mu * (Aab - aab);
        tau = mat.mu * (Aab - aab/Jss);
        
        e_sigma = Js*tau;
        sigma = sigma + e_sigma;
        
        % auxilliary matrices
        M11 = dN1'*a_1 ;
        M12 = dN1'*a_2 ;
        M21 = dN2'*a_1 ;
        M22 = dN2'*a_2 ;
        Maa = dN1'*a1 + dN2'*a2 ;
        
        % Internal force vector and stiffness matrix
        rint = rint + Gs * quadr.w(gp) * (tau(1,1) * M11 + tau(2,1) * M21 + tau(1,2) * M12 + tau(2,2)* M22);
        
        kgeoh = kgeoh + (tau(1,1) * (dN1h'*dN1h) + tau(1,2) * (dN1h'*dN2h + dN2h'*dN1h) + tau(2,2) * (dN2h'*dN2h)) * Gs * quadr.w(gp);
        
        %defining the unit alternator
        e_alt = [0, 1; -1, 0];
        a_tau = [a_1, a_2];
        dN_tau{1,1} = M11;
        dN_tau{1,2} = M12;
        dN_tau{2,1} = M21;
        dN_tau{2,2} = M22;
        for alp = 1 : 2
            for bet = 1 : 2
                for gam = 1 : 2
                    for del = 1 : 2
                        %                         m_abgd = ((1/det_ab) * (e_alt(alp,gam) * e_alt(bet,del) + e_alt(alp,del) * e_alt(bet,gam)) - 2*aab(alp,bet)*aab(gam,del));
                        %                         c_abgd = (mat.lamb * Jss * aab(alp,bet) * aab(gam,del)) + ((mat.lamb/2)*(Jss - 1) - mat.mu) * m_abgd;
                        c_abgd = mat.mu * 1/Jss * (4 * aab(alp,bet) * aab(gam,del) - 1/det_ab * (e_alt(alp,gam) * e_alt(bet,del) + e_alt(alp,del) * e_alt(bet,gam)));
                        kmat = kmat + Gs * quadr.w(gp)* c_abgd * dN_tau{alp,bet} * dN_tau{gam,del}';
                    end
                end
            end
        end
        
        % External force vector for dead loading
        rextg = rextg + N'*Load.gext * Gs * quadr.w(gp);
        
        % hydrostatic pressure (reference level irrelevant)
        pexth = Load.w * (xgp(3) - 0.0);
        dpext = Load.w * [0 0 1];
        
        % combined pressure
        pext = pextv + pexth;
        
        % External force vector for pressure loading and corresponsind stiffness
        Nngw = N' * n_s * gs * quadr.w(gp);
        rextp = rextp + pext * Nngw;
        
        M1 = n_s*a1' - a1*n_s';
        M2 = n_s*a2' - a2*n_s';
        MNda = (M1 * dN1 + M2 * dN2) * gs * quadr.w(gp);
        kext = kext + N' * pext * MNda + Nngw * dpext * N;
        lext = lext + Nngw;
        
        % volume constraint contribution and corresponding gradient
        % note: boundary must contain the xy-plane for correctness
        gvel = gvel + n_s' * xgp * gs * quadr.w(gp)/3 ;
        kvel = kvel + n_s' * N * gs * quadr.w(gp)/3 + xgp' * MNda/3 ;
    end
    % Full Force vector and element stiffness
    rext = rextg + rextp;
    
    kgeo(1:3:el_dof,1:3:el_dof) = kgeoh;
    kgeo(2:3:el_dof,2:3:el_dof) = kgeoh;
    kgeo(3:3:el_dof,3:3:el_dof) = kgeoh;
    
    rel = rint - rext;
    kel = kmat + kgeo - kext;
    
    if volc == 1
        rel = [ rel; gvel];
        kel = [ kel  -lext; kvel 0];
        el_dof = el_dof + 1;
    end
    
    
    is = repmat(ie,1,el_dof);
    js = reshape(repmat(ie,el_dof,1),1,len);
    ris = ie;
    
    ks = reshape(kel,1,len);
    
    % Structure Stiffness and Stress Divergence Term
    R(ie,1)  = R(ie,1)  + rel;
    K(ie,ie) = K(ie,ie) + kel;
    
    %For plot_routine
    %stress_inv = trace(sigma);
    %vonMises(1,e) = stress_inv;
end