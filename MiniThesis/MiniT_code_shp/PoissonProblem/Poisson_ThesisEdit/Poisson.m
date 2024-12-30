%function [L2_Data] = Poisson(nx, pp, ngp)
% -------------------------------------------------------------------------
format longG
a = [0 0]'; % Start point of line geometry
b = [1 1]'; % End point of line geometry
nx = [10 10]; % Number of elements
pp = 2; % Order of approximation polynomial
ngp = 3; % Number of quadrature points
[node, elem, epro] = mRectLL(a, b, nx, pp);
L2_Data = strings(1, 3);
L2_Data(1) = num2str(pp);
L2_Data(2) = num2str(nx(1)*nx(2));
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
    %message = sprintf("calculating element number %i", e);
    %disp(message);
    xIs = node.X(elem.con{e},:);
    
    dim = (pp+1)*(pp+1);
    K_e = zeros(dim, dim);
    f_e = zeros(dim, 1);
    
    for gp = 1 : quadr.ngp
        [N, dpN] = shapesQL(quadr.gps(gp,1), quadr.gps(gp,2), pp, pp);
        
        x_gp = N' * xIs;
        J = dpN*xIs;
        dN_dx = J\dpN; %inv(J)*dpN
        
        %r = s(quadr.gps(gp,1),quadr.gps(gp,2));
        r = s(x_gp(1), x_gp(2));
        K_e = K_e + (det(J)) * (dN_dx' * dN_dx) * quadr.w(gp); %detJ multiplied by -1 because nodes aren't clockwise or counter clockwise
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
test = 1;
x = K\F;
%--------------------------------------------------------------------------
% Analytic Solution

an_soln = bc(node.X(:, 1), node.X(:, 2));

%--------------------------------------------------------------------------
L2error = 0;
for e = 1:elem.n
    xIs = node.X(elem.con{e},:);
    
    dim = (pp+1)*(pp+1);
    f_num_el = x(elem.con{e});
    f_analytical_el = an_soln(elem.con{e});
    
    L2_el = 0;
    for gp = 1:quadr.ngp
        [N, dpN] = shapesQL(quadr.gps(gp,1), quadr.gps(gp,2), pp, pp);
        J = dpN*xIs;
        dN_dx = J\dpN; %inv(J)*dpN
        
        err = abs((N'*f_analytical_el) - (N'*f_num_el));
        L2_el = L2_el + (err^2)*(det(J))*quadr.w(gp); %detJ multiplied by -1 because nodes aren't clockwise or counter clockwise
    end
    L2error = L2error + L2_el;
end
L2error = sqrt(L2error);

fprintf('\nL2-error for %d elements of order %d with %d quadrature points is: %0.16f \n', elem.n, pp, ngp, L2error);
L2_Data(3) = sprintf('%0.16f', L2error);
%L2_Data(3) = L2error;
%---------Graphing Solution------------------------------------------------
nodePoints = nx.*pp+1;
x_nodes = linspace(0,1,nodePoints(1));
y_nodes = linspace(0,1,nodePoints(2));


[X, Y] = meshgrid(x_nodes, y_nodes);
Z = reshape(x', (size(X)));
Z_an = reshape(bc(node.X(:,1),node.X(:,2)),size(X));

tiledlayout(1,2)
fem = nexttile;
surf(X,Y,reshape(x',[size(X)]));
set(gca, 'xtick', [0:0.20:1])
set(gca, 'ytick', [0:0.20:1])
%title(fem, '(a.)', 'Interpreter', 'latex');
set(gca, 'ticklabelinterpreter','latex');
%xlabel(x_label1,'Interpreter','latex');
ylabel('(a.)','Interpreter','latex');
%    title(fig_name_left, 'Interpreter', 'latex');
xlim([0 1])
ylim([0 1])
zlim auto
grid on

er = nexttile;
%relative error
contourf(X, Y, (Z-Z_an))
%axis([0 1 0 1 -0.0000005 0.0000005])
set(gca, 'xtick', [0:0.1:1])
set(gca, 'ytick', [0:0.1:1])
set(gca, 'ticklabelinterpreter','latex');
 xlabel('(b.)','Interpreter','latex');
%title(er, '(b.)', 'Interpreter', 'latex');
grid on
grid minor
colorbar('Direction', 'reverse', 'AxisLocation', 'out')
zlabel('relative error')

%surf(X,Y,reshape(x',[size(X)])); %hold on;
%surf(X,Y,reshape(bc(node.X(:,1),node.X(:,2)),size(X))); % Analytic
% solution for comparison