t = tiledlayout(3,1);
nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
%patch('Faces', face1, 'Vertices', vert1, 'FaceVertexCData', fv_cData1, 'FaceColor', 'interp');
patch('Faces', face1, 'Vertices', vert1, 'FaceVertexCData', fv_cData1, 'FaceColor', 'interp');
fv_cBar1 = colorbar;
set(fv_cBar1, 'Ticks', sort([fv_cBar1.Limits, fv_cBar1.Ticks]))
set(fv_cBar1, 'AxisLocation', 'out', 'TickDirection', 'out', 'Location', 'east');
%fv_cBar1.Label.String = 'Normal Stress $\sigma_{yy}$';
%fv_cBar1.Label.Interpreter = 'latex';
%fv_cBar1.Label.FontSize = 12;
ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
%xlabel('Length of Beam [L/L$_{0}$]', 'Interpreter', 'latex', 'FontSize', 12);


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
patch('Faces', face2, 'Vertices', vert2, 'FaceVertexCData', fv_cData2, 'FaceColor', 'interp');
fv_cBar2 = colorbar;
set(fv_cBar2, 'Ticks', sort([fv_cBar2.Limits, fv_cBar2.Ticks]))

set(fv_cBar2, 'AxisLocation', 'out', 'TickDirection', 'out', 'Location', 'east');
%fv_cBar2.Label.String = 'Normal Stress $\sigma_{yy}$';
%fv_cBar2.Label.Interpreter = 'latex';
%fv_cBar2.Label.FontSize = 12;
%title(fv_cBar2,'Normal Stress $\sigma_{yy}$', 'Interpreter', 'latex')
ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
%xlabel('Length of Beam [L/L$_{0}$]', 'Interpreter', 'latex', 'FontSize', 12);



nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
patch('Faces', face3, 'Vertices', vert3, 'FaceVertexCData', fv_cData3, 'FaceColor', 'interp');
fv_cBar3 = colorbar;
set(fv_cBar3, 'Ticks', sort([fv_cBar3.Limits, fv_cBar3.Ticks]))

set(fv_cBar3, 'AxisLocation', 'out', 'TickDirection', 'out', 'Location', 'east');
%fv_cBar3.Label.String = 'Normal Stress $\sigma_{yy}$';
%fv_cBar3.Label.Interpreter = 'latex';
%fv_cBar3.Label.FontSize = 12;
ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
xlabel('Length of Beam [L/L$_{0}$]', 'Interpreter', 'latex', 'FontSize', 12);


t.Padding = 'compact';
t.TileSpacing = 'compact';
