%% Startfile loaded membrane sheet
% Based on Roger Sauer - 24.3.14
% Christopher Zimmermann 17.6.15

clear
close all
tic


%% GEOMETRY
% ---------------------------------------------------------------------------------
jplot = 1; % 0 for no plots, 1 for plots
jload = 0; % 0 for gravitational loading, 1 for displacement, 2 for force driven
jmesh = 6; 

% geometry of the sheet
Lo = 1;
Xo = 0   ; Lx = 2*Lo ; 
Yo = 0   ; Ly = 2*Lo ; 

% create mesh
mBlock

% Reference configuration
node.X = NX;

% Connectivity
CON = CN;

% number of nodes and elements
node.n = mesh.nno ; nel = mesh.nel ; 

% number of dofs
dof.n  = 3*node.n ;

% current configurations (with prestraining)
% ====TODO====
 prestretch = 1.1;
 node.x = prestretch * node.X;

%% BCS
% -------------------------------------------------------------------------
% Fixed Dofs (Zero Displacement BC)
dof.ik = [ ] ;
dofTOL = 1.e-8;
if jload == 0
    for i = 1:node.n
        if abs(node.X(i,1) - Lx) < dofTOL
            % all direction fixed along x=Lx edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        elseif abs(node.X(i,2) - Ly) < dofTOL
            % all directions fixed along y=Ly edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        end
        if abs(node.X(i,1)) < dofTOL && abs(node.X(i,2) - Ly) > dofTOL
            % x direction fixed along x=0 edge
            dof.ik = [dof.ik 3*i-2] ;
        end
        if abs(node.X(i,2)) < dofTOL && abs(node.X(i,1) - Lx) > dofTOL
            % y direction fixed along y=0 edge
            dof.ik = [dof.ik 3*i-1] ;
        end
    end
elseif jload == 1
    % ====TODO====
    % dof.ik = ...
    for i = 1:node.n
        if abs(node.X(i,1) - Lx) < dofTOL
            % all direction fixed along x=Lx edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        elseif abs(node.X(i,2) - Ly) < dofTOL
            % all directions fixed along y=Ly edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        end
        if abs(node.X(i,1)) < dofTOL && abs(node.X(i,2) - Ly) > dofTOL
            % x direction fixed along x=0 edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        end
        if abs(node.X(i,2)) < dofTOL && abs(node.X(i,1) - Lx) > dofTOL
            % y direction fixed along y=0 edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        end
    end
elseif jload == 2
    for i = 1:node.n
        if abs(node.X(i,1) - Lx) < dofTOL
            % all direction fixed along x=Lx edge
            dof.ik = [dof.ik 3*i-2 3*i-1 3*i] ;
        elseif abs(node.X(i,2) - Ly) < dofTOL
            % all direction fixed along x=Lx edge
            dof.ik = [dof.ik 3*i-1] ;
        elseif abs(node.X(i,2)) < dofTOL
            % all direction fixed along x=Lx edge
             dof.ik = [dof.ik 3*i-1] ;
        end
    end
end
dof.nik = numel(dof.ik) ;

% displacement-prescribed dofs
dof.id  = [ ] ;
if jload == 0

elseif jload == 1
    for i = 1 : node.n
        if abs(node.X(i,1)) < Lx/2+dofTOL && abs(node.X(i,2)) < Ly/2+dofTOL...
                && abs(node.X(i,1)) > Lx/2-dofTOL && abs(node.X(i,2)) > Ly/2-dofTOL
%             % center node
            dof.id = [dof.id 3*i] ;
        end
    end
elseif jload == 2
    for i = 1 : node.n
        if abs(node.X(i,1)) < dofTOL %&& abs(node.X(i,2) - Ly) > dofTOL
            % x direction fixed along x=0 edge
            dof.id = [dof.id 3*i-2] ;
        end
    end
end

dof.nid = numel(dof.id) ;
% displacement-monitored dofs
if jload == 0
    dof.iu  = 3;
elseif jload == 1
    dof.iu = dof.id ;
elseif jload == 2
    dof.iu = 1;
end
dof.niu = numel(dof.iu) ;

% free Dofs
if jload == 2
    dof.ir  = setdiff(1:dof.n,[dof.ik]) ;
else
    dof.ir  = setdiff(1:dof.n,[dof.ik dof.id]) ;
end
    
dof.nir = numel(dof.ir) ;

% displacement rearragement arrays
dof.i1 = 1:3:3*node.n ;
dof.i2 = 2:3:3*node.n ;
dof.i3 = 3:3:3*node.n ;


%% INITAL CONFIGURATION
% ---------------------------------------------------------------------------------

% initial mesh
if jplot > 0
    
    col = 'g';
    % Plot Elements
    figure
    for i = 1:nel
        
        X = [node.X(CON(i,1),1) node.X(CON(i,2),1) node.X(CON(i,3),1) node.X(CON(i,4),1) node.X(CON(i,1),1)] ;
        Y = [node.X(CON(i,1),2) node.X(CON(i,2),2) node.X(CON(i,3),2) node.X(CON(i,4),2) node.X(CON(i,1),2)] ;
        Z = [node.X(CON(i,1),3) node.X(CON(i,2),3) node.X(CON(i,3),3) node.X(CON(i,4),3) node.X(CON(i,1),3)] ;
        
        if col == 'o'
            plot3(X,Y,Z,'k','linewidth',3) ; hold on
        else
            fill(X,Y,col) ; hold on
        end
        
    end
    
    % Figure Properties
    xlabel('X / L_0') ;
    ylabel('Y / L_0') ;
    zlabel('Z / L_0') ;
    grid on ;
    view(60,30) ;
end

%% COMPUTATIONAL PARAMETERS
% ---------------------------------------------------------------------------------

% initial displacement
U = zeros(dof.n,1) ; DU = zeros(dof.n,1) ;
Ux = node.x - node.X ;
U(dof.i1) = Ux(:,1) ;
U(dof.i2) = Ux(:,2) ;
U(dof.i3) = Ux(:,3) ;
clear Ux

% external force vector
fext = zeros(dof.n,1) ;

% Newton-Raphson tolerance
TOL = 1.e-30 ;

% Quadrature parameters
jgaus = 2 ; fequad ; ngp = jgaus^2 ; xigv = xig2 ;

% preprocession time
rtime.prep = toc

%% FE COMPUTATION
% ---------------------------------------------------------------------------------
% load level, number of steps
dyn.LL = 1 ;
dyn.ne = 100 ;

% load increments
% ==== TODO ====
 dyn.ds = 0.1;

% load list
% ==== TODO ====
TI = 0:dyn.ds:dyn.ds*dyn.ne;

% membrane material parameters
mat.mu   = 1 ;  % membrane stiffness
mat.rho  = 1 ;  % membrane density for gravitational loading

% initial loading parameters
if jload == 0
    Load.g    = [0 ; 0 ; -1] ; % gravity vector
elseif jload > 0
    Load.g    = [0 ; 0 ; 0] ; % gravity vector
end

Load.gext = 0 * Load.g ;   % gravity loading
Load.vol  = 0 ;
Load.w    = 0 ;
Load.pex0 = 0 ;

% initialize
nsm = 0 ;
l = 0 ; jterm = 0 ;

% loading loop
for t = TI
    
    % load step counter
    l = l + 1 ;
    
    if jload == 1
        U(dof.id) = t * 0.5 * ones(dof.nid,1) ;
        Ux = [ U(dof.i1) U(dof.i2) U(dof.i3)] ;
        node.x = node.X + Ux(:,1:3); 
    elseif jload == 2
        fext(dof.id) = t * 0.5 * ones(dof.nid,1) ;
    end
    
    % current load level
    Load.gext = t * mat.rho * Load.g ;
    Load.pext = t * Load.pex0 ;
    
    % Newton's method
    fenewton
    
    % terminate
    if jterm == 1 ; break ; end
    
    
    % approximate pressure for gravity loading
    if jload == 0
        pres = -Load.pext - Load.gext(3) / prestretch ;
        Th(l,:) = [-R(end) pres U(dof.iu) t 0] ;
    elseif jload == 1
        pres = -Load.pext - R(end) / prestretch ;
        Th(l,:) = [-R(end) pres U(dof.iu) t 0] ;
    end    
end



%% DEFORMED CONFIGURATION
% ---------------------------------------------------------------------------------

% compute stress invariant (I_1 = sig_a^b(1,1)+sig_a^b(2,2))
mem_str

% deformed mesh
if jplot > 0 

    % Plot Elements
    figure
    cdata = SL;
    dStrs
    axis([0 2.5 0 2.5 -1.5 0])
    axis equal
    axis tight
    xlabel('x / L_0') ;
    ylabel('y / L_0') ;
    zlabel('z / L_0') ;
    title('Stress invariant I_1')
    grid on ;
    view(60,30) ;
    colormap(jet(512)) ;
    colorbar
%     print('-r400','-djpeg',['res_' num2str(mat.mu)])
end

if jload == 0
    
    % Load-displacement curve
    figure
    plot([0 ; Th(:,4)],[0 ; Th(:,3)],'-bo','markerfacecolor','b') ; hold on ; grid on
    set(gca,'fontsize',18)
    xlabel('gravitational loading   [g]')
    ylabel('displacement normalized by L_0')
    
end
% print('-r400','-djpeg',['res_curve_' num2str(mat.mu)])
% total time
rtime.total = toc ;
rtime
 