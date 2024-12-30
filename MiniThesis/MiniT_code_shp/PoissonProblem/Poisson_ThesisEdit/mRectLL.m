function [node, elem, epro] = mRectLL(a, b, nel, p)
% -------------------------------------------------------------------------
% 
% -------------------------------------------------------------------------
% Inputs:
%   - a (dim x 1) - start point
%   - b (dim x 1) - end point
%   - nel (n x m) - number of elements
%   - p (1 x 1) - order of the polynomial
% -------------------------------------------------------------------------
% Outputs:
%   - node - struct - contains data about the nodes:
%       * X (nn x dim) - coordinates of the nodes
%       * n (n+1 x m+1) - number of nodes
%   - elem - struct - contains data about the elements:
%       * con{nel} - connectivity of the element
%       * n (1 x 1) - number of elements
%   - epro {nel} - struct - contains data of element properties:
%       * p (1 x 1) - order of the elements
% -------------------------------------------------------------------------
nn = nel.*p+1;
dim = nn(1)*nn(2);
% -------------------------------------------------------------------------

x = linspace(0, 1, nn(1));
y = linspace(0, 1, nn(2));

X = (a(1) + x.*(b(1)-a(1)))';
Y = (a(2) + y.*(b(2)-a(2)))';

node.X = zeros(dim,2);
xn = nn(1);
yn = nn(2);
node.n = xn*yn;
node.Boundary = zeros(xn*yn, 1);
node.BoundaryCount = 0;
n_c = 1;
for j = 1:yn
    for i = 1: xn
        node.X(n_c, 1) = X(i, 1);
        node.X(n_c, 2) = Y(j, 1);
        n_c = n_c + 1;
    end
end
for i = 1:node.n
    if node.X(i,1) == 0 || node.X(i,2) == 0 || node.X(i,1) == 1 || node.X(i,2) == 1
        node.Boundary(i, 1) = 1;
        node.BoundaryCount = node.BoundaryCount + 1;
    end
end
% -------------------------------------------------------------------------

el_row = 1;

nel_x = nel(1);
nel_y = nel(2);
elem.n = nel(1)*nel(2);
elem.con = cell(elem.n, 1);
epro = cell(elem.n, 1);

row = 1;
check = 1;

while row <= yn
    row_nodes = [(row-1)*xn+1 : (row)*(xn)];
    elem_numbers = [(el_row-1)*nel_x + 1 : (el_row)*(nel_x)];
    
    count = 1;
    for i = elem_numbers
        nodes = row_nodes(count:count+p);
        elem.con{i} = [elem.con{i} nodes];
        epro{i}.p = p;
        count = count + p;
    end
    
    
    if check == p+1 && row_nodes(end) ~= node.n
        check = 1;
        el_row = el_row + 1;
    else
        row = row + 1;
        check = check + 1; 
    end
end


end