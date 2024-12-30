t = tiledlayout(2,3);

nexttile;
view(-25,10)

%xlim([0 2.5])
%ylim([0 2.5])
zlim([-3.5 0.05]) 
grid on
grid minor
patch('Faces', face1, 'Vertices', vert1, 'FaceVertexCData', fv_cData1, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
%fv_cBar7 = colorbar;
%set(fv_cBar7, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(A)','Interpreter','latex', 'FontSize', 12);
alpha(0.8)

nexttile;
view(-25,10)

%xlim([0 2.5])
%ylim([0 2.5])
zlim([-3.5 0.1])
set(gca, 'zticklabel', [])
grid on
grid minor
patch('Faces', face2, 'Vertices', vert2, 'FaceVertexCData', fv_cData2, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar2 = colorbar;
set(fv_cBar2, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(B)','Interpreter','latex', 'FontSize', 12);

alpha(0.8)

nexttile;
view(-25,10)
%xlim([0 2.5])
%ylim([0 2.5])
zlim([-3.5 0.1])
set(gca, 'zticklabel', [])
grid on
grid minor
patch('Faces', face3, 'Vertices', vert3, 'FaceVertexCData', fv_cData3, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar3 = colorbar;
set(fv_cBar3, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(C)','Interpreter','latex', 'FontSize', 12);

alpha(0.8)


nexttile;
view(-25,10)

%xlim([0 2.5])
%ylim([0 2.5])
zlim([-3.5 0.05]) 
grid on
grid minor
patch('Faces', face4, 'Vertices', vert4, 'FaceVertexCData', fv_cData4, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar4 = colorbar;
set(fv_cBar4, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(D)','Interpreter','latex', 'FontSize', 12);
alpha(0.8)

nexttile;
view(-25,10)

%xlim([0 2.5])
%ylim([0 2.5])
zlim([-3.5 0.1])
set(gca, 'zticklabel', [])
grid on
grid minor
patch('Faces', face5, 'Vertices', vert5, 'FaceVertexCData', fv_cData5, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar5 = colorbar;
set(fv_cBar5, 'AxisLocation', 'out', 'TickDirection', 'out');
xlabel('(E)','Interpreter','latex', 'FontSize', 12);

alpha(0.8)

nexttile;
view(-25,10)

%xlim([0 2.5])
%ylim([0 2.5])
zlim([-3.5 0.1])
set(gca, 'zticklabel', [])
grid on
grid minor
patch('Faces', face6, 'Vertices', vert6, 'FaceVertexCData', fv_cData6, 'FaceColor', 'interp', 'EdgeColor', 'interp', 'LineStyle', 'none');
fv_cBar6 = colorbar;
set(fv_cBar6, 'AxisLocation', 'in', 'TickDirection', 'in'); %, 'Location', 'east'
xlabel('(F)','Interpreter','latex', 'FontSize', 12);

alpha(0.8)

t.Padding = 'compact';
t.TileSpacing = 'compact';