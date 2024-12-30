dx = Lx/nx;
dy = Lx/ny;
dx_e = Lx/(nx*p);
dy_e = Ly/(ny*p);

%f = [1 2 3 4];
face = [];
Xv = [];
xv = [];
c = [];
X_el_verts = [];
x_el_verts = [];

coords = [0 0 0];
f_counter = 1;
f = [1 2 3 4];

for i = 1:elem.n
    con = elem.con{i};
    initial_coords = node.X(con(1), :, :); 
    
    for column_it = 1:p
        for row_it = 1:p
            coords = initial_coords' + [dx_e*(row_it-1); 0 ; 0;];
            coords = coords';
        
            [X_points, x_points, check] = ret_points(coords, dx_e, dy_e, con, node);
            if check == 1
                fprintf("Element Number: %d, X, Y: %d, %d\n", i, row_it, column_it);  
                break;
            end
            X_el_verts = [X_el_verts; coords; X_points];
            x_el_verts = [x_el_verts; x_points];
        
            el_faces = f + (f_counter-1)*4;
            f_counter = f_counter+1;
            face = [face; el_faces];
            
            el_stress = [vonMises(1, i); vonMises(1, i); vonMises(1, i); vonMises(1, i)];
            c = [c; el_stress]; 
        end
        initial_coords = initial_coords' + [0; dy_e; 0];
        initial_coords = initial_coords';
    end

end


figure
view(-25,10)
%axis([-1*Lx 2*Lx -1*Lx 2*Lx -5 0])
grid on
patch('Faces', face, 'Vertices', x_el_verts, 'FaceVertexCData',  c, 'FaceColor','interp');
colorbar('Direction', 'reverse', 'AxisLocation', 'in')




function [X_ar, x_ar, check] = ret_points(coords, dx_e, dy_e, con, node)
    point1 = coords' + [dx_e; 0; 0];
    point2 = point1 + [0; dy_e; 0];
    point3 = point2 + [-dx_e; 0; 0];
    
    id_f = find(ismembertol(node.X(con, :, :), coords, 'ByRows', true)); %index_first
    id_s = find(ismembertol(node.X(con, :, :), point1', 'ByRows', true));
    id_t = find(ismembertol(node.X(con, :, :), point2', 'ByRows', true));
    id_l = find(ismembertol(node.X(con, :, :), point3', 'ByRows', true));%index_last
    
    x_ar = [node.x(con(id_f), :, :);  node.x(con(id_s), :, :);  node.x(con(id_t), :, :);  node.x(con(id_l), :, :)];
    X_ar = [point1' ; point2' ; point3';];
    
    if(length(x_ar) ~= 4)
        check = 1;
    else
        check = 0;
    end
end
