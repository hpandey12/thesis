clear; close all;
% -------------------------------------------------------------------------
H = 1; L = 20;
XB = [0 0; L 0; L H; 0 H];
nx = 10; ny = 2;
p = 2; q = p;
ngp = 4;
% -------------------------------------------------------------------------
jload = 0; % 0 for displacement, 1 for force-driven
jlin  = 0; % 0 for nonlinear
jplot = 0; % 0 for no plots, 1 for plots
% Computational options
jinfo = 1;
jcomp = 1;
% -------------------------------------------------------------------------
[node, elem, epro] = mRectangleQL(XB, nx, ny, p, q);
% -------------------------------------------------------------------------
quadr = cQuad_classic(ngp);
% -------------------------------------------------------------------------
% Fixed Dofs (Zero Displacement BC)
ik = zeros(1, ny*p+2) ;
cnt = 1 ;
fix_x = [];
fix_y = [];
for i = 1 : node.n
    if abs(node.X(i,1)) < eps
        ik(cnt) = 2*i-1; % x direction fixed along x=0 edge
        fix_x = [fix_x i];
        cnt = cnt + 1 ;
        if abs(node.X(i,2)-H/2) < eps
            ik(cnt) = 2*i; % also y direction fixed at y=H/2
            fix_y = [fix_y i];
            cnt = cnt + 1 ;
        end
    end
end

% Prescribed (nonzero) Displacement BC
id = 0;
for i = 1 : node.n
    if abs(node.X(i,1) - L) < eps && abs(node.X(i,2)-H/2) < eps
        id = 2*i;
    end
end
sid = length(id) ;

% Free Dofs
if jload == 0
    ir  = setdiff(1:2*node.n,[ik id]);
else
    ir  = setdiff(1:2*node.n, ik);
end
nir = length(ir) ;

% Initial Displacement
U = zeros(2*node.n,1);

% Current configuration
i1 = 1:2:2*node.n;
i2 = 2:2:2*node.n;

Ux = [ U(i1) U(i2) ];
node.x = node.X + Ux(:,1:2);

% External Force Vector
Fext = zeros(2*node.n,1);

% body force
g = [0 ; 0];

% for initial guess
i2d  = setdiff(i2,[id ik]) ;
ni2d = length(i2d) ;
% -------------------------------------------------------------------------
% MATERIAL (Normalized by E)
% -------------------------------------------------------------------------
% Bulk Material
E  = 1          ; nu  = 0.2              ;
mu = E/2/(1+nu) ; lam = 2*mu*nu/(1-2*nu) ; kappa = lam + 2*mu/3 ;

% Linear Material Tangent
Ip4 = [ones(3,3) zeros(3,1) ; zeros(1,3) 0 ];
Is4 = [ 2*eye(3) zeros(3,1) ; zeros(1,3) 1 ];
D   = lam*Ip4 + mu*Is4;
% -------------------------------------------------------------------------
% COMPUTATIONAL PARAMETERS
% -------------------------------------------------------------------------

% Displacement/Force Increments
if jload == 0
        e0 = 1 ; ds = -1.6e-2 ; es = 100 ; ub = es*ds ; % with initial guess
    %     e0 = 1 ; ds = -0.16 ; es = 100 ; ub = es*ds ; % dense output
    %     e0 = 1 ; ds = -0.09 ; es = 11 ; ub = es*ds ; % with no initial guess
else
    e0 = 1 ; ds = -0.0002 ; es = 8 ; ub = es*ds ;
    %e0 = 1 ; ds = -0.00002 ; es = 100 ; ub = es*ds ; % denses output
end

% Newton-Raphson tolerance
TOL = 1.e-20;
% -------------------------------------------------------------------------
% History variable
Uh = zeros(es,4);

% -------------------------------------------------------------------------
% FE COMPUTATION
% ---------------------------------------------------------------------------------
if jcomp == 1
    % Initialize
    
    l = 0; jterm = 0;
    % Load stepping
    for ub = e0*ds:ds:es*ds
        % Load step counter
        l = l + 1;
        % Imposed current BC
        if jload == 0
            U(id) = ub * ones(sid,1);
            % Current configuration
            Ux = [ U(i1) U(i2) ];
            node.x = node.X + Ux(:,1:2);
        else
            Fext(id) = ub * ones(sid,1);
        end
        
        % Newton's Method
        fenewton
        
        % Terminate Computation
        if jterm == 1
            jcomp = 0;
            fprintf('\n Terminating computation \n')
            break
        end    
        % Load History
        Uh(l,:) = [ub Reas node.x(id/2,1)-node.X(id/2,1) node.x(id/2,2)-node.X(id/2,2)] ;
    end
end