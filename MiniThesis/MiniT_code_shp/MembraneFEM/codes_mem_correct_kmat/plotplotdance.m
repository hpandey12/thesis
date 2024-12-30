nne = (epro{e}.p+1)*(epro{e}.q+1);
% dofs per element
el_dof = 3*nne;

quad_pts = 10;
plt_quadr = cQuad_plot(quad_pts);

face = [];
Xv = [];
xv = [];
c = [];
X_el_verts = [];
x_el_verts = [];

coords = [0 0 0];
f_counter = 1;
f = [1 2 3 4];

for e = 1:elem.n
    con = 3*elem.con{e};
    ie = zeros(1,el_dof);
    ie(1:3:el_dof) = con-2;
    ie(2:3:el_dof) = con-1;
    ie(3:3:el_dof) = con;
    
    % Initial and Current Nodal Coordinates
    plt_Xn = node.X(elem.con{e},:);
    plt_xn = node.x(elem.con{e},:);
    % Vector representation
    plt_XIs = reshape(plt_Xn',el_dof,1);
    plt_xIs = reshape(plt_xn',el_dof,1);
    
    %Initialise array for quadrature coordinates
    gp_xIs = zeros(plt_quadr.ngp, 3);
    vonMises = zeros(plt_quadr.ngp, 1);
    for gp = 1:plt_quadr.ngp
        [plt_NR, plt_dN , ~] = shapesQL(plt_quadr.gps(gp,:), epro{e}.p, epro{e}.q, [1 0]);
        plt_N(1,1:3:el_dof) = plt_NR;
        plt_N(2,2:3:el_dof) = plt_NR;
        plt_N(3,3:3:el_dof) = plt_NR;
        
        plt_dN1(1,1:3:el_dof) = plt_dN(:,1);
        plt_dN1(2,2:3:el_dof) = plt_dN(:,1);
        plt_dN1(3,3:3:el_dof) = plt_dN(:,1);
        plt_dN2(1,1:3:el_dof) = plt_dN(:,2);
        plt_dN2(2,2:3:el_dof) = plt_dN(:,2);
        plt_dN2(3,3:3:el_dof) = plt_dN(:,2);
        
        plt_dN1h = plt_dN(:,1)';
        plt_dN2h = plt_dN(:,2)';
        
        plt_xgp  = plt_N * plt_xIs ;
        check_gps = plt_N * plt_XIs;
        
         % co-variant basis
        plt_A_1 = plt_dN1 * plt_XIs;
        plt_A_2 = plt_dN2 * plt_XIs;
        
        plt_a_1 = plt_dN1 * plt_xIs;
        plt_a_2 = plt_dN2 * plt_xIs;
        
        % co-variant surface metric
        plt_A_ab = [plt_A_1'*plt_A_1 plt_A_1'*plt_A_2; plt_A_2'*plt_A_1 plt_A_2'*plt_A_2];
        plt_a_ab = [plt_a_1'*plt_a_1 plt_a_1'*plt_a_2; plt_a_2'*plt_a_1 plt_a_2'*plt_a_2];
        
        % contra-variant surface metric
        plt_Aab  = inv(plt_A_ab);
        plt_aab = inv(plt_a_ab);
        
         % determinants (surface stretch w.r.t. master configuration)
        plt_Det_ab = det(plt_A_ab) ;
        plt_Gs = sqrt(plt_Det_ab) ;
        plt_det_ab = det(plt_a_ab) ;
        plt_gs = sqrt(plt_det_ab) ;
        
        % surface stretch between current and reference configuration
        plt_Js = plt_gs/plt_Gs;
        plt_Jss = plt_Js*plt_Js ;
        plt_tau = mat.mu * (plt_Aab - plt_aab/plt_Jss);
        
        e_sigma = plt_Js*plt_tau;
        
        vonMises(gp,:) = trace(e_sigma);
        gp_xIs(gp, :) = plt_xgp';
        gp_check(gp,:) = check_gps';
    end
    
    plt_lambda = quad_pts+1;
    
    plt.n = plt_quadr.ngp;
    plt.con = cell((plt_lambda)^2, 1);
    ii = 1;
    check_col = 1;
    for quad_row = 1:plt_lambda
        for quad_col = 1:plt_lambda
            plt.con{ii} = [(quad_row-1)*(plt_lambda+1) + quad_col, (quad_row-1)*(plt_lambda+1)+(quad_col+1), (quad_row)*(plt_lambda+1)+(quad_col+1), (quad_row)*(plt_lambda+1)+quad_col];
            ii = ii + 1;
        end 
    end
    
    for ii = 1:plt_lambda^2
        X_el_verts = [X_el_verts; gp_check(plt.con{ii},:)];
        x_el_verts = [x_el_verts; gp_xIs(plt.con{ii},:)];
        el_faces = f + (f_counter-1)*4;
        f_counter = f_counter+1;
        face = [face; el_faces];
        el_stress = [vonMises(plt.con{ii},1)];
        c = [c; el_stress]; 
    end
    
end
xv = x_el_verts;
c_xx = c;


figure
view(-25,10)
%axis([-1*Lx 2*Lx -1*Lx 2*Lx -5 0])
grid on
patch('Faces', face, 'Vertices', x_el_verts, 'FaceVertexCData',  c, 'FaceColor','interp', 'LineWidth', 0.1);
colorbar('AxisLocation', 'out')
alpha(0.9)