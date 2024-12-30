
dx = X_Length/nxh;
dy = Y_Length/nyh;

f = [1 2 3 4];
face = [];
Xv = [];
xv = [];
c_xx = [];
c_yy = [];
c_xy = [];
c_vm = [];
for i = 1:elem.n
    con = elem.con{i};
    Xfirst = node.X(con(1), :);
    Xlast = node.X(con(end), :);
    Xsecond = (Xfirst' + [dx; 0])';
    Xthird = (Xlast' - [dx; 0])';
    
    X_el_verts = [Xfirst; Xsecond; Xlast; Xthird];
    Xv = [Xv; X_el_verts];
    
    el_faces = f + (i-1)*4;
    face = [face; el_faces];
    
    for indice = con
        if (norm((node.X(indice, :)'- Xsecond')) < eps)
            s_id = indice;
        end
        if (norm((node.X(indice, :)' - Xthird')) < eps)
            t_id = indice;
        end
    end 
    
    xfirst = node.x(con(1), :);
    xlast = node.x(con(end), :);
    xsecond = node.x(s_id, :);
    xthird = node.x(t_id, :);
    
    x_el_verts = [xfirst; xsecond; xlast; xthird];
    xv = [xv; x_el_verts];
    
    el_stressxx = [sigma_(1,i); sigma_(1,i); sigma_(1,i); sigma_(1,i)];
    el_stressyy = [sigma_(2,i); sigma_(2,i); sigma_(2,i); sigma_(2,i)];
    el_stressxy = [sigma_(4,i); sigma_(4,i); sigma_(4,i); sigma_(4,i)];
    el_stressvm = [vonMises(1, i); vonMises(1, i); vonMises(1, i); vonMises(1, i)];
    c_xx = [c_xx; el_stressxx];
    c_yy = [c_yy; el_stressyy];
    c_xy = [c_xy; el_stressxy];
    c_vm = [c_vm; el_stressvm];
end

%figxx = figure('visible', 'on');
%axis([0 1.5*X_Length])
% xlim ([0 1.5*X_Length])
% ylim ([-1 1.5*X_Length])
% grid on
% patch('Faces', face, 'Vertices', xv, 'FaceVertexCData', c_xx, 'FaceColor', 'interp');
% cbar_xx = colorbar;
% t_xx = get(cbar_xx, 'Limits');
% set(cbar_xx, 'Ticks', round(linspace(t_xx(1), t_xx(2), 10), 3, "significant"), 'AxisLocation', 'out', 'TickDirection', 'in')
% ylabel('(a.)','Interpreter','latex');
% xlabel('(a.)', 'Interpreter', 'latex');


%figyy = figure('visible', 'on');
%axis([0 1.5*X_Length])
%xlim ([0 1.5*X_Length])
%ylim auto
%grid on
%patch('Faces', face, 'Vertices', xv, 'FaceVertexCData', c_yy, 'FaceColor', 'interp');
%cbar_yy = colorbar;
%t_yy = get(cbar_yy, 'Limits')
%set(cbar_yy, 'Ticks', round(linspace(t_yy(1), t_yy(2), 10), 3, "significant"), 'AxisLocation', 'out', 'TickDirection', 'in')

%figxy = figure('visible', 'on');
%axis([0 1.5*X_Length])
%xlim ([0 1.5*X_Length])
%ylim auto
%grid on
%patch('Faces', face, 'Vertices', xv, 'FaceVertexCData', c_xy, 'FaceColor', 'interp');
%cbar_xy = colorbar;
%t_xy = get(cbar_xy, 'Limits')
%set(cbar_xy, 'Ticks', round(linspace(t_xy(1), t_xy(2), 10), 3, "significant"), 'AxisLocation', 'out', 'TickDirection', 'in')

%figvm = figure('visible', 'on');
%axis([0 1.5*X_Length])
%xlim ([0 1.5*X_Length])
%ylim auto
%grid on
%patch('Faces', face, 'Vertices', xv, 'FaceVertexCData', c_vm, 'FaceColor', 'interp');
%cbar_vm = colorbar;
%t_vm = get(cbar_vm, 'Limits')
%set(cbar_vm, 'Ticks', round(linspace(t_vm(1), t_vm(2), 10), 3, "significant"), 'AxisLocation', 'out', 'TickDirection', 'in')

%image_folder = "C:\Users\dell\OneDrive\Desktop\MiniThesis\Thesis template";
%img_name = sprintf('%dBeam%d.png', bc_num, img_number);
%full_img_name = fullfile(image_folder, img_name);
%saveas(fig, full_img_name, 'png')