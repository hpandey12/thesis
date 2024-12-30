tiledlayout(1,3)

nexttile;
view(-25,10)
zlim([-2 0.05]) 
grid on
grid minor
patch('Faces', face7, 'Vertices', vert7, 'FaceVertexCData', fv_cData7, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
%fv_cBar7 = colorbar;
%set(fv_cBar7, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(A)','Interpreter','latex', 'FontSize', 12);
alpha(0.8)

nexttile;
view(-25,10)
zlim([-2 0.05]) 
grid on
grid minor
patch('Faces', face8, 'Vertices', vert8, 'FaceVertexCData', fv_cData8, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar8 = colorbar;
set(fv_cBar8, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(B)','Interpreter','latex', 'FontSize', 12);

alpha(0.8)

nexttile;
view(-25,10)
zlim([-2 0.05]) 
grid on
grid minor
patch('Faces', face9, 'Vertices', vert9, 'FaceVertexCData', fv_cData9, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar9 = colorbar;
set(fv_cBar9, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(C)','Interpreter','latex', 'FontSize', 12);

alpha(0.8)