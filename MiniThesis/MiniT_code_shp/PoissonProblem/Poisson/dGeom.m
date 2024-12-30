function h = dGeom(X, elem, popt, epro, etab)
%DGEOM Draws the mesh
% 
% h = dGeom(X, elem, popt, epro, etab)
%   Draws the mesh defined by the points X, the connectivity elem.con and
%   possibly information in the structure epro{e}. Ploting options can be
%   passed in the structure popt. The most important option is popt.fig
%   which defines the figure to plot into. popt.fig may be a string (figure
%   name), an integer (figure number) or a figure handle.
%
%   The figure handle h is returned.

warning('off','MATLAB:mir_warning_maybe_uninitialized_temporary') ;

% make 2D data 3D for now
if size(X,2) == 2
  X = [ X zeros(size(X,1),1) ] ;
end

% 
% open new figure of draw into existing one
if ischar( popt.fig ) % figure name
  h = figure('Name',popt.fig) ;
elseif ishandle( popt.fig ) % figure handle
  h = figure(popt.fig) ;
  hold on ;
elseif isinteger( popt.fig ) % figure number
  h = figure(popt.fig) ;
  hold on ;
end


% pre-compute number of vertices and faces
verts = cell(elem.n,1) ;
faces = cell(elem.n,1) ;
edges = cell(elem.n,1) ;
if popt.num
    center = zeros(elem.n,3);
end


% pre-compute offsets
vo = cumsum([0 ; (popt.elsmo+1).^2]) ;

% TODO: pre-compute shape function values

% loop over elements
% par

for e = 1:elem.n
  
  % data points for smoothing
  esmo = popt.elsmo(e)+1 ; %#ok<PFBNS>
  dat = linspace(-1,1,esmo) ;
  
  % nodal positions
  
  Xe = X(elem.con{e},:) ; %#ok<PFBNS>
  vc = 0 ;
  verts{e} = zeros(esmo^2,3) ;
  % compute sub-grid
  for eta = 1:esmo
    for xi= 1:esmo
      [NR] = cShpF([dat(xi) dat(eta)], elem.type(e), epro{e}, etab,[1 0 0]) ;
      vc = vc+1 ;
      verts{e}(vc,:) = NR' * Xe ;
    end
  end
  
  if popt.num %plot element numbering
      [NR] = cShpF([0 0], elem.type(e), epro{e}, etab,[1 0 0]) ; %at midpoint
      center(e,:) = NR' * Xe ;
  end
  
  fc = 0 ;
  faces{e} = zeros(popt.elsmo(e)^2,4) ;
  % create sub-grid face connectivity
  for j=1:popt.elsmo(e)
    for i=1:popt.elsmo(e)
      fc = fc+1 ;
      faces{e}(fc,:) = [1 2 esmo+2 esmo+1] + (i-1) + (j-1)*esmo + vo(e) ;
    end
  end
  
  edges{e} = [1:esmo 2*esmo:esmo:vc vc-1:-1:vc-esmo+1 vc-2*esmo+1:-esmo:esmo+1] + vo(e) ;
  
end

% concatenate vertices, faces, and edges
faces = cell2mat(faces) ;
verts = cell2mat(verts) ;
edges = cell2mat(edges) ;

% plot faces
p = patch('Faces',faces,'Vertices',verts) ;

% assigen colors (currently z-data)
% cdata = verts(:,3) ;
% set(p,'FaceColor','interp',...
%       'FaceVertexCData',cdata,...
%       'EdgeColor','none',...
%       'LineWidth',1,...
%       'CDataMapping','scaled')

set(p,'FaceColor',popt.col,...
    'FaceAlpha',popt.alpha,...
    'SpecularStrength',0.5,...
    'SpecularColorReflectance',0.5,...
    'EdgeColor','none',...
    'LineWidth',1)

if popt.mesh==1
    % plot edges
    q = patch('Faces',edges,'Vertices',verts) ;
    set(q,'FaceColor','none',...
        'EdgeColor','k',...
        'LineWidth',popt.lw)
end
if popt.num
    n_el_str=strtrim(cellstr(num2str((1:elem.n)'))');
    text(center(:,1),center(:,2),center(:,3),n_el_str,'fontsize',popt.FS,'interpreter','latex');
end

end
