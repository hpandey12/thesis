t = tiledlayout(3,2);


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
patch('Faces', face1, 'Vertices', vert1, 'FaceVertexCData', fv_cData1, 'FaceColor', 'interp');
fv_cBar1 = colorbar;
set(fv_cBar1, 'Ticks', sort([fv_cBar1.Limits, fv_cBar1.Ticks]))
set(fv_cBar1, 'AxisLocation', 'out', 'TickDirection', 'out');
ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
set(gca, 'yticklabel', [])
grid on
grid minor
patch('Faces', face4, 'Vertices', vert4, 'FaceVertexCData', fv_cData4, 'FaceColor', 'interp');
fv_cBar4 = colorbar;
set(fv_cBar4, 'AxisLocation', 'out', 'TickDirection', 'out');


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
patch('Faces', face2, 'Vertices', vert2, 'FaceVertexCData', fv_cData2, 'FaceColor', 'interp');
fv_cBar2 = colorbar;
set(fv_cBar2, 'Ticks', sort([fv_cBar2.Limits, fv_cBar2.Ticks]))
ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);



nexttile;
xlim ([0 16])
ylim ([-5 1.5])
set(gca, 'yticklabel', [])
grid on
grid minor
patch('Faces', face5, 'Vertices', vert5, 'FaceVertexCData', fv_cData5, 'FaceColor', 'interp');
fv_cBar5 = colorbar;
set(fv_cBar5, 'AxisLocation', 'out', 'TickDirection', 'out');


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
grid on
grid minor
patch('Faces', face3, 'Vertices', vert3, 'FaceVertexCData', fv_cData3, 'FaceColor', 'interp');
fv_cBar3 = colorbar;
set(fv_cBar3, 'Ticks', sort([fv_cBar3.Limits, fv_cBar3.Ticks]))
set(fv_cBar3, 'AxisLocation', 'out', 'TickDirection', 'out');
ylabel('Deflection [u/H$_{0}$]','Interpreter','latex', 'FontSize', 12);
xlabel({'Length of Beam [L/L$_{0}$]'; '(A) Displacement driven boundary conditions'}, 'Interpreter', 'latex', 'FontSize', 12);


nexttile;
xlim ([0 16])
ylim ([-5 1.5])
set(gca, 'yticklabel', [])
grid on
grid minor
patch('Faces', face6, 'Vertices', vert6, 'FaceVertexCData', fv_cData6, 'FaceColor', 'interp');
xlabel({'Length of Beam [L/L$_{0}$]'; '(B) Force driven boundary conditions'}, 'Interpreter', 'latex', 'FontSize', 12);
fv_cBar6 = colorbar;
set(fv_cBar6, 'Ticks', sort([fv_cBar6.Limits, fv_cBar6.Ticks]));
set(fv_cBar6, 'AxisLocation', 'out', 'TickDirection', 'out');


t.Padding = 'compact';
t.TileSpacing = 'compact';
%cb = colorbar;

