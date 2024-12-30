clear; close all;
% -------------------------------------------------------------------------
a = [0 0]'; % Start point of line geometry
b = [1 1]'; % End point of line geometry
nx = [2 1]; % Number of elements
pp = 4; % Order of approximation polynomial
ngp = 5; % Number of quarature points
[node, elem, epro] = mRectLL(a, b, nx, pp);

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

s = @(x, y) 2*exp(x-y);

K = zeros(node.n, node.n);
F = zeros(node.n, 1);
for e = 1 : elem.n
    message = sprintf("calculating element number %i", e);
    disp(message);
    xIs = node.X(elem.con{e},:);
    
    dim = (pp+1)*(pp+1);
    K_e = zeros(dim, dim);
    f_e = zeros(dim, 1);
    
    
    for gp = 1 : quadr.ngp
        [N, dpN] = shapesQL(quadr.gps(gp,1), quadr.gps(gp,2), pp, pp);
        J = dpN*xIs;
        if det(J) < 0 
            error('Sth wrong');
        end
        dN_dx = zeros(2, dim);
        for i = 1 : dim
            tempdN = J\dpN(:,i);
            dN_dx(1, i) = tempdN(1, 1);
            dN_dx(2, i) = tempdN(2, 1);
        end
        r = s(quadr.gps(gp,1),quadr.gps(gp,2));
        N_ij = dN_dx(1,:)' * dN_dx(1,:) + dN_dx(2,:)' * dN_dx(2,:);
        K_e = K_e + det(J) * N_ij * quadr.w(gp);
        f_e = f_e + det(J) * N * r * quadr.w(gp);
    end
    K(elem.con{e}, elem.con{e}) =  K(elem.con{e}, elem.con{e}) - K_e;
    F(elem.con{e}, 1) = F(elem.con{e}, 1) + f_e;
end

bc = @(x,y) exp(x-y) + exp(x).*cos(y);

n_bc = find(node.Boundary);
for i = 1 : length(n_bc)
    ii = n_bc(i);
    K(ii,:) = 0;
    K(ii,ii) = 1;
    F(ii) = bc(node.X(ii,1),node.X(ii,2));
end

x = K\F;


%--------------------------------------------------------------------------
%---------Graphing Solution------------------------------------------------
nodePoints = nx.*pp+1;
x_nodes = linspace(0,1,nodePoints(1));
y_nodes = linspace(0,1,nodePoints(2));

[X, Y] = meshgrid(x_nodes, y_nodes);
surf(X,Y,reshape(x,[size(X)])); hold on;
% surf(X,Y,reshape(bc(node.X(:,1),node.X(:,2)),size(X))); % Analytic
% solution for comparison