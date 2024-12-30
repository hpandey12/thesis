clear; close all;

% Plotting parameters 
popt.smo = 1;
popt.mesh = 1;
popt.lw = 1;
popt.fs = 10;
popt.num = 0;
popt.col = 'b';
popt.alpha = 1;

etab  = cEtab();
etype = etab.Q1;

% Define mesh parameters
L = 1;
H = 1;
nL = 5;
nH = 5;
ngp = 4;

% Define mesh nodes
[X,Y] = meshgrid(linspace(0,L,nL));
node.X = [reshape(X',[numel(X),1]) reshape(Y',[numel(X),1])];
node.n = size(node.X,1);

% Define connectivity
CON = [(1:nL-1)' (2:nL)' (nL+2:2*nL)' (nL+1:2*nL-1)'];
con = cell(nH-2,1);
for i = 1 : nH-2
    con{i} = CON + i*nL;
end

elem.con = num2cell([CON; cell2mat(con)],2);
elem.n = size(elem.con,1);
elem.type = etab.Q1*ones(elem.n,1);
elem.quad = ones(elem.n,1);
epro = cell(elem.n,1);

% -------------------------------------------------------------------------
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

sf = @(x,y) 2*exp(x-y);

func = @(x,y) x + y + 2;

doff.S = unique([find(node.X(:,1) == 0); find(node.X(:,1) == 1); find(node.X(:,2) == 0); find(node.X(:,2) == 1)]);
doff.n = node.n;
doff.ir = setdiff(1:doff.n,doff.S);

K = zeros(node.n);
s = zeros(node.n,1);
g = 0;
for e = 1 : elem.n
    nne = size(node.X(elem.con{e},:),1);
    dof = 2*nne ;
    
    xIs = reshape(node.X(elem.con{e},:)',dof,1) ;
    
    N      = zeros(2,dof);
    dN1    = zeros(2,dof);
    dN2    = zeros(2,dof);
    
    x_e = node.X(elem.con{e},:);
    f_e = sf(x_e(:,1),x_e(:,2));
    Ke = zeros(nne);
    se = zeros(nne,1);
    ge = 0;
    for gp=1:quadr.ngp
        wg = quadr.w(gp) ;
        [NR, dpN] = shp_Q1(quadr.gps(gp,1), quadr.gps(gp,2), [1 1 1]);
        
        % shape function arrays
        N(1,1:2:dof) = NR ;
        N(2,2:2:dof) = NR ;
        
        dN1(1,1:2:dof) = dpN(:,1) ;
        dN1(2,2:2:dof) = dpN(:,1) ;
        
        dN2(1,1:2:dof) = dpN(:,2) ;
        dN2(2,2:2:dof) = dpN(:,2) ;
        
        % quadrature point
        x_gp  = N * xIs;
       
        J = [dN2 * xIs  dN1 * xIs];
        
        detJ = det(J);
        
        dNdxy = J\dpN';
        
        Jac = node.X(elem.con{e},:)' * dpN;
        detJac = det(Jac);
        
        dN = dpN/Jac;
        B = dN';
        
        Ke = Ke - B'*B*detJac*wg;
        se = se + NR * sf(x_gp(1),x_gp(2)) * detJac * wg;
        ge = ge + sum(NR * func(x_gp(1),x_gp(2))) * detJac * wg;
    end
    K(elem.con{e},elem.con{e}) = K(elem.con{e},elem.con{e}) + Ke;
    s(elem.con{e}) = s(elem.con{e}) + se;
    g = g + ge;
end

chk = abs(g - integral2(func,0,1,0,1));

bc = @(x,y) exp(x-y) + exp(x).*cos(y);

for i = 1 : length(doff.S)
    ii = doff.S(i);
    K(ii,:) = 0;
    K(ii,ii) = 1;
    s(ii) = bc(node.X(ii,1),node.X(ii,2));
end

x = K\s;

surf(X,Y,reshape(x,size(X))); hold on;
surf(X,Y,reshape(bc(node.X(:,1),node.X(:,2)),size(X)));

f_ana = bc(node.X(:,1),node.X(:,2));

L2error = 0;
for e = 1 : elem.n
    nne = size(node.X(elem.con{e},:),1);
    dof = 2*nne ;
    
    xIs = reshape(node.X(elem.con{e},:)',dof,1) ;
    
    N      = zeros(2,dof);
    dN1    = zeros(2,dof);
    dN2    = zeros(2,dof);
    
    x_e = node.X(elem.con{e},:);
    f_ana_e = f_ana(elem.con{e});
    f_num_e = x(elem.con{e});
    
    L2e = 0;
    for gp = 1 : quadr.ngp
        wg = quadr.w(gp) ;
        [NR, dpN] = shp_Q1(quadr.gps(gp,1), quadr.gps(gp,2), [1 1 1]);
        
        % shape function arrays
        N(1,1:2:dof) = NR ;
        N(2,2:2:dof) = NR ;
        
        dN1(1,1:2:dof) = dpN(:,1) ;
        dN1(2,2:2:dof) = dpN(:,1) ;
        
        dN2(1,1:2:dof) = dpN(:,2) ;
        dN2(2,2:2:dof) = dpN(:,2) ;
        
        % quadrature point
        x_gp  = N * xIs;
       
        J = [dN2 * xIs  dN1 * xIs];
        
        detJ = det(J);
        
        dNdxy = J\dpN';
        
        Jac = node.X(elem.con{e},:)' * dpN;
        detJac = det(Jac);
        
        dN = dpN/Jac;
        B = dN';
        
        err = abs((NR'*f_ana_e) - (NR'*f_num_e));
        
        L2e = L2e + err^2*detJac*wg;
    end
    L2error = L2error + L2e;
end
L2error = sqrt(L2error);

fprintf('L2-error for no. elements: %d and no. quadrature points %d is: %e \n', nL*nH, quadr.ngp, L2error);