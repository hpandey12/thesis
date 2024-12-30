%% FE Inputfile 1 : feinp1.m
% Bending Beam
% based on Roger Sauer - 15.12.10
% Callum Corbett - 04.06.12

clear;
clc;
close all;
tic %toc on the clock

%% GEOMETRY & MESH GENERATION
% ---------------------------------------------------------------------------------
jload = 0; % 0 for displacement, 1 for force-driven
jlin  = 0; % 0 for nonlinear
jplot = 0; % 0 for no plots, 1 for plots

% Computational options
jinfo = 1;
jcomp = 1;

% Mesh Generation Data
L = 20; % Length of Beam
H = 1; % Height of Beam

num_el = [10 2]; % Number of Elements along [Length x Height]
pp = 2; % Order of Polynomial Approximation
if mod(num_el(2),2)~=0 && mod(pp,2)~=0
    error("Odd Elements over height require even order of approximation polynomial");
end

MeshGeneration(L, H, num_el, pp);

file1 = 'NonLinearMesh/NX.dat';
file2 = 'NonLinearMesh/CONN.dat';

% Reference configuration
Xn = load(file1);

% Connectivity
CON  = load(file2);

% number of nodes and elements
nno = size(Xn,1);
nel = size(CON,1);

% Fixed Dofs (Zero Displacement BC)
ik = zeros(1, num_el(2)*pp+2) ;
cnt = 1 ;
for i = 1 : nno
    if abs(Xn(i,1)) < eps
        ik(cnt) = 2*i-1; % x direction fixed along x=0 edge
        cnt = cnt + 1 ;
        if abs(Xn(i,2)-H/2) < eps
            ik(cnt) = 2*i; % also y direction fixed at y=H/2
            cnt = cnt + 1 ;
        end
    end
end

% Prescribed (nonzero) Displacement BC
id = 0;
for i = 1 : nno
    if abs(Xn(i,1) - L) < eps && abs(Xn(i,2)-H/2) < eps
        id = 2*i;
    end
end
sid = length(id) ;

% Free Dofs
if jload == 0
    ir  = setdiff(1:2*nno,[ik id]);
else
    ir  = setdiff(1:2*nno, ik);
end
nir = length(ir) ;

% Initial Displacement
U = zeros(2*nno,1);

% Current configuration
i1 = 1:2:2*nno;
i2 = 2:2:2*nno;

Ux = [ U(i1) U(i2) ];
xn = Xn + Ux(:,1:2);

% External Force Vector
Fext = zeros(2*nno,1);

% body force
g = [0 ; 0];

% for initial guess
i2d  = setdiff(i2,[id ik]) ;
ni2d = length(i2d) ;

%% MATERIAL (Normalized by E)
% ---------------------------------------------------------------------------------
% Bulk Material
E  = 1          ; nu  = 0.2              ;
mu = E/2/(1+nu) ; lam = 2*mu*nu/(1-2*nu) ; kappa = lam + 2*mu/3 ;

% Linear Material Tangent
Ip4 = [ones(3,3) zeros(3,1) ; zeros(1,3) 0 ];
Is4 = [ 2*eye(3) zeros(3,1) ; zeros(1,3) 1 ];
D   = lam*Ip4 + mu*Is4;

%% PLOT INITAL CONFIGURATION
% ---------------------------------------------------------------------------------
% Initial Mesh
if jplot == 1
   % Plot beam 
   pstrs = 0 ; fig = 1 ; col = 'g' ;
   axe = [-.5 20.5 -16 2] ;
   PX = Xn ; fedraw ; fenum ;
   
   % plot support
   plot([0 0],[-2 3],'k')
   
   % Figure Properties
   xlabel('X / L')
   ylabel('Y / L')
end

%% COMPUTATIONAL PARAMETERS
% ---------------------------------------------------------------------------------

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


% History variable
Uh = zeros(es,4);

% Preprocession time
pretime = toc

%% FE COMPUTATION
% ---------------------------------------------------------------------------------
if jcomp == 1
    
    % Initialize
    l = 0 ; jterm = 0 ;
    
    % Load stepping
    for ub = e0*ds:ds:es*ds
        
        % Load step counter
        l = l + 1 ;
        
        % Imposed current BC
        if jload == 0
            U(id) = ub * ones(sid,1) ;
            % Initial guess
                       %for i = 1 : ni2d
                        %   x=Xn(i2d(i)/2,1);
                           %U(i2d(i))=x^2*(3*L-x);
                           %U(i2d(i))=eps*(-x^3+3*(L^2)*x+2*L^3) ;
                           %U(i2d(i))=L-x^3
                           %U(i2d(i))=eps*(L-x);
                       %end
            
            % Current configuration
            Ux = [ U(i1) U(i2) ] ;
            xn = Xn + Ux(:,1:2) ;
        else
            Fext(id) = ub * ones(sid,1) ;
        end
        
        % Newton's Method
        fenewton
        
        % Terminate Computation
        if jterm == 1
            jcomp = 0 ;
            fprintf('\n Terminating computation \n')
            break
        end
        
        % Load History
        Uh(l,:) = [ub Reas xn(id/2,1)-Xn(id/2,1) xn(id/2,2)-Xn(id/2,2)] ;
        
        % Current configuration plot
        if jinfo == 2
            festrs11
            sig = sig';
            fig = 1; figure(fig);
            clf
            PX = xn;
            axe = [-2 22 -16 2] ;
            pstrs = 2 ; col = 'g' ; fedraw ;
            pstrs = 0 ; col = 'o' ; fedraw ;
            colorbar
            
            % plot support
            plot([0 0],[-2 2],'k')
            
            % Figure Properties
            xlabel('x / L')
            ylabel('y / L')
            axis(axe)
            colormap('jet');
        end
    end
  
  % Stress Computation
  if jterm ~= 1 
      festrs11
  end
 
  % Computation time
  comptime = toc - pretime
end

%% PLOT RESULTS
% ---------------------------------------------------------------------------------
if jcomp == 1

% Deformed configuration, stresses
if jplot == 1
   PX = xn ; axe = [-.5 20.5 -16 2] ; fig = 2 ; 
   pstrs = 2 ; col = 'g' ; fedraw ;
   pstrs = 0 ; col = 'o' ; fedraw ;
   
   % plot support
   plot([0 0],[-2 3],'k')
   
   % Figure Properties
   xlabel('x / L')
   ylabel('y / L')
   colorbar
   axis(axe)   
end

% Load-displacement curve
figure(3)
plot([0 ; -Uh(:,4)],[0 ; -Uh(:,2)],'-bo','markerfacecolor','b') ; hold on ; grid on

% Total Time
end
totaltime = toc