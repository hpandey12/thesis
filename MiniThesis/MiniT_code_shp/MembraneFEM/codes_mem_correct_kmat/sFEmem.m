%clear; 
close all; clc
% -------------------------------------------------------------------------
% Loading type: 0 - gravitational loading, 1 - volume constraint
jload = 1;
if jload == 0
    volc = 0;
elseif jload == 1
    volc = 1;
end
% -------------------------------------------------------------------------
Lo = 1;
Xo = 0; Lx = 2*Lo;
Yo = 0; Ly = 2*Lo;
initial_Vol = 4*((2*Lo)^3);
% -------------------------------------------------------------------------
nx = 4; ny = nx;
p = 5; q = p;
XB = [0 0; Lx 0; Lx Ly; 0 Ly];
ngp = 9;
% -------------------------------------------------------------------------
[node, elem, epro] = mRectangleQL(XB, nx, ny, p, q);
node.X = [node.X zeros(node.n,1)];
% -------------------------------------------------------------------------
% number of dofs
dof.n  = 3*node.n ;

%plot_routine

% -------------------------------------------------------------------------
% for stablity reason add a prestreach
prestretch = 1.10;
node.x = prestretch * node.X;
% -------------------------------------------------------------------------
% Fixed Dofs (Zero Displacement BC)
dof.ik = [];
dofTOL = 1.e-8;
ind_x1 = find(node.X(:,1) < dofTOL);
ind_x2 = find(abs(node.X(:,1) - Lx) < dofTOL);
ind_y1 = find(node.X(:,2) < dofTOL);
ind_y2 = find(abs(node.X(:,2) - Ly) < dofTOL);
tmp = [ind_x1; ind_x2; ind_y1; ind_y2]';
dof.ik = unique([3*tmp-2 3*tmp-1 3*tmp]);
dof.nik = numel(dof.ik);
% -------------------------------------------------------------------------
% displacement-prescribed dofs
dof.id = [];
dof.nid = numel(dof.id);
% -------------------------------------------------------------------------
% displacement-monitored dofs
dof.iu = 3;
dof.niu = numel(dof.iu);
% -------------------------------------------------------------------------
if volc == 1
    dof.n = dof.n + 1;
end
% -------------------------------------------------------------------------
% free dofs
dof.ir = setdiff(1:dof.n,[dof.ik dof.id]);
dof.nir = numel(dof.ir);
% -------------------------------------------------------------------------
% displacement rearragement arrays
dof.i1 = 1:3:3*node.n;
dof.i2 = 2:3:3*node.n;
dof.i3 = 3:3:3*node.n;
% -------------------------------------------------------------------------
% initial displacement
U = zeros(dof.n,1); DU = zeros(dof.n,1);
Ux = node.x - node.X;
U(dof.i1) = Ux(:,1);
U(dof.i2) = Ux(:,2);
U(dof.i3) = Ux(:,3);
clear Ux
% -------------------------------------------------------------------------
% external force vector
fext = zeros(dof.n,1);
% -------------------------------------------------------------------------
% Newton-Raphson tolerance
TOL = 1.e-25;
% -------------------------------------------------------------------------
% Quadrature parameters
quadr = cQuad_classic(ngp);
% -------------------------------------------------------------------------
% load level, number of steps
dyn.ne = 2000;
% -------------------------------------------------------------------------
% load increments
dyn.ds = 0.2;
% -------------------------------------------------------------------------
% load list
TI = 0:dyn.ds:dyn.ds*dyn.ne;
% -------------------------------------------------------------------------
% membrane material parameters
mat.mu = 1;  % membrane stiffness
mat.rho = 1;  % membrane density for gravitational loading
mat.lamb = 1;
% -------------------------------------------------------------------------
% initial loading parameters
if jload == 0
    Load.g = [0; 0; -1]; % gravity vector
elseif jload == 1
   Load.g = [0; 0; 0]; 
end
Load.gext = 0 * Load.g;   % gravity loading
Load.vol = 0;
Load.w = 0;
Load.pex0 = 0;
Load.p = 0;

%variables to save pressure and volume
int_P = [];
int_Vol = [];
rec_Vol = [];
% -------------------------------------------------------------------------
% initialize
nsm = 0; l = 0; jterm = 0;
% -------------------------------------------------------------------------
% loading loop
for t = TI
    % ---------------------------------------------------------------------
    % load step counter
    l = l + 1;
    % ---------------------------------------------------------------------
    % current load level
    Load.gext = t * mat.rho * Load.g;
    Load.pext = t * Load.pex0;
    if jload == 1
        V_0 = -t;
        int_Vol = [int_Vol; abs(V_0)];
        int_P = [int_P; abs(Load.p)];
    end
    % ---------------------------------------------------------------------
    % Newton's method
    fenewton
    % ---------------------------------------------------------------------
    % Volume
%     if jload == 1
%             if l == 1
%                 plotplotdance
%                 face1 = face;
%                 vert1 = xv;
%                 fv_cData1 = c_xx;
%                 rec_Vol = [rec_Vol; abs(V_0)];
%             end
%             
%             if l == 10
%                 plotplotdance
%                 face2 = face;
%                 vert2 = xv;
%                 fv_cData2 = c_xx;
%                 rec_Vol = [rec_Vol; abs(V_0)];
%             end
%         
%             if l == 25
%                 plotplotdance
%                 face3 = face;
%                 vert3 = xv;
%                 fv_cData3 = c_xx;
%                 rec_Vol = [rec_Vol; abs(V_0)];
%             end
%             
%             if l == 50
%                 plotplotdance
%                 face4 = face;
%                 vert4 = xv;
%                 fv_cData4 = c_xx;
%                 rec_Vol = [rec_Vol; abs(V_0)];
%             end
%             
%             if l == 75
%                 plotplotdance
%                 face5 = face;
%                 vert5 = xv;
%                 fv_cData5 = c_xx;
%                 rec_Vol = [rec_Vol; abs(V_0)];
%             end
%             
%             if l == 100
%                 plotplotdance
%                 face6 = face;
%                 vert6 = xv;
%                 fv_cData6 = c_xx;
%                 rec_Vol = [rec_Vol; abs(V_0)];
%             end
%             
%     else
%             
%             if l == 1
%                 plotplotdance
%                 face7 = face;
%                 vert7 = xv;
%                 fv_cData7 = c_xx;
%                 rec_Vol = [rec_Vol; abs(t)];
%             end
%             if l == 4
%                 plotplotdance
%                 face8 = face;
%                 vert8 = xv;
%                 fv_cData8 = c_xx;
%                 rec_Vol = [rec_Vol; abs(t)];
%             end
%             if t == dyn.ds*dyn.ne
%                 plotplotdance
%                 face9 = face;
%                 vert9 = xv;
%                 fv_cData9 = c_xx;
%                 rec_Vol = [rec_Vol; abs(t)];
%             end
%     end
        
    % terminate
    if jterm == 1 ; break ; end
end

%plot_routine
