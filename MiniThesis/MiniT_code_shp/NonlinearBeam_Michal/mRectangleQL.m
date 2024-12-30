function [node, elem, epro] = mRectangleQL(XB, nx, ny, p, q)
% -------------------------------------------------------------------------
%
% -------------------------------------------------------------------------
nm = [nx*p+1, ny*q+1];
x1 = linspace(0, 1, nm(1));
x2 = linspace(0, 1, nm(2));
ln1 = (XB(1,:)' + x1.*(XB(2,:)'-XB(1,:)'))';
ln2 = (XB(1,:)' + x2.*(XB(4,:)'-XB(1,:)'))';
% -------------------------------------------------------------------------
node.X = zeros(prod(nm),2);
node.n = prod(nm);
for i = 1 : nm(2)
    tM = uVectorTranslation(ln2(i,:)' - ln1(1,:)');
    node.X((i-1)*nm(1) + 1 : i*nm(1),:) = uExtrudePoints(tM,ln1);
end
% -------------------------------------------------------------------------
con = zeros(1,(p+1)*(q+1));
for i = 1 : q+1
    ind = (i-1)*(p+1)+1 ;
    con(ind:ind+p) = (1:p+1) + (i-1)*nm(1) ;
end
% -------------------------------------------------------------------------
elem.con = cell(nx*ny,1);
epro = cell(nx*ny,1);
cnt = 0;
for j = 1 : ny
    indJ = (j-1)*nm(1);
    for i = 1 : nx
        indI = (i-1);
        cnt = cnt+1;
        epro{cnt}.p = p;
        epro{cnt}.q = q;
        elem.con{cnt} = con + indJ*q + indI*p;
    end
end
elem.n = size(elem.con,1);
% -------------------------------------------------------------------------
end