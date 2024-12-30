t = tiledlayout(3,1);
nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
set(gca, 'yticklabel', [])
%patch('Faces', face1, 'Vertices', vert1, 'FaceVertexCData', fv_cData1, 'FaceColor', 'interp');
patch('Faces', face4, 'Vertices', vert4, 'FaceVertexCData', fv_cData4, 'FaceColor', 'interp');
fv_cBar4 = colorbar;
set(fv_cBar4, 'AxisLocation', 'out', 'TickDirection', 'out', 'Location', 'east');
%fv_cBar4.Label.String = 'Normal Stress $\sigma_{yy}$';
%fv_cBar4.Label.Interpreter = 'latex';
%fv_cBar4.Label.FontSize = 12;
%ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
%xlabel('Length of Beam [L/L$_{0}$]', 'Interpreter', 'latex', 'FontSize', 12);


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
set(gca, 'yticklabel', [])
patch('Faces', face5, 'Vertices', vert5, 'FaceVertexCData', fv_cData5, 'FaceColor', 'interp');
fv_cBar5 = colorbar;
set(fv_cBar5, 'AxisLocation', 'out', 'TickDirection', 'out', 'Location', 'east');
%fv_cBar5.Label.String = 'Normal Stress $\sigma_{yy}$';
%fv_cBar5.Label.Interpreter = 'latex';
%fv_cBar5.Label.FontSize = 12;
%ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
%xlabel('Length of Beam [L/L$_{0}$]', 'Interpreter', 'latex', 'FontSize', 12);


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
set(gca, 'yticklabel', [])
patch('Faces', face6, 'Vertices', vert6, 'FaceVertexCData', fv_cData6, 'FaceColor', 'interp');
fv_cBar6 = colorbar;
set(fv_cBar6, 'Ticks', sort([fv_cBar6.Limits, fv_cBar6.Ticks]));
set(fv_cBar6, 'AxisLocation', 'out', 'TickDirection', 'out', 'Location', 'east');
%ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
xlabel('Length of Beam [L/L$_{0}$]', 'Interpreter', 'latex', 'FontSize', 12);

t.Padding = 'compact';
t.TileSpacing = 'compact';
