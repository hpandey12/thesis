function [node, elem, epro] = mRectQL(L, H, nel, p)
%--------------------------------------------------------------------------
%Mesh Generation for Nonlinear Beam
%
%% Inputs: 
%   - L - Length of Beam
%   - H - Height of Beam
%   - nel (n x m) - Number of Elements along [Length x Height]
%   - p - Order of Polynomial Approximantion
% -------------------------------------------------------------------------
%% Outputs:
%   - node - struct - contains data about the nodes:
%       * X (nn x dim) - coordinates of the nodes
%       * n (n+1 x m+1) - number of nodes
%   - elem - struct - contains data about the elements:
%       * con{nel} - connectivity of the element
%       * n (1 x 1) - number of elements
%   - epro {nel} - struct - contains data of element properties:
%       * p (1 x 1) - order of the elements
%% ------------------------------------------------------------------------
if (mod(nel(2), 2) ~= 0) && mod(p,2) ~= 0
    error('Odd Number Elements Over Height Cannot be Meshed with Odd Order Polynomial Approximation')
end
    

a = [0 0];
b = [L H];

nn = nel.*p+1;
dim = nn(1)*nn(2);

x = linspace(0, 1, nn(1));
y = linspace(0, 1, nn(2));

X = (0 + x.*(L))';
Y = (0 + y.*(H))';

node.X = zeros(dim,2);
xn = nn(1);
yn = nn(2);
node.n = xn*yn;

n_c = 1;
for j = 1:yn
    for i = 1: xn
        node.X(n_c, 1) = X(i, 1);
        node.X(n_c, 2) = Y(j, 1);
        n_c = n_c + 1;
    end
end
%% ------------------------------------------------------------------------
nel_x = nel(1);
nel_y = nel(2);
elem.n = nel(1)*nel(2);
elem.con = cell(elem.n, 1);
epro = cell(elem.n, 1);

el_row = 1;
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
