function etab = cEtab(varargin)
%CETAB Create element ID lookup table
% 
% etab = cEtab()
%   Creates the element ID lookup table etab. Elements IDs are increasing
%   numbers starting from 1 and may change in future updates as more
%   elements are added. For this reason, element types must alway be set
%   and checked using etab.* for example
%
%   if elem.type(i) == etab.Q1 % check if element is a linear Lagrange quad
%
%   Calling the function with an element ID or a list/vector of element IDs
%   prints the corresponding name to the screen. Valid calls are
%
%   etab(1) ; etab(3:5) ; etab(4,7,1) ; etab([5 2 3]) ; cEtab(unique(elem.type));
%
%   New elements may be added at any time, the name should identify the
%   element shape, interpolation type, and order, and possibly enrichment
%   information, using the following abbreviations:
%   
%   Element Shapes 1D:
%   L - line element
%   
%   Element Shapes 2D:
%   D - triangular element (Dreieck)
%   Q - quadrilateral element
%   
%   Element Shapes 3D:
%   T - tetrahedral element
%   P - pyramid element (quad base + tip)
%   H - hexahedral element
%   
%   Interpolation:
%     - Lagrange is standard and has no letter
%   H - Hermite
%   I - isogeometric (NURBS, T-Splines)
%   B - B-Spline (possibly in future --> cheaper than NURBS with const. weight)
%   S - serendipity(?)
%   
%   Enrichments:
%   Interpolation type and order as above + possibly additional
%   information, like edge or surface (transitional elements), or left or
%   right side (2D transitional Hermite elements). This can be added when
%   needed.

c = 0 ; % counter to ensure no ID is used twice and simplify adding new
        % element types at the appropriate position in this file.

%% Line elements
c = c+1 ; etab.L1 = c ; % linear Lagrange
c = c+1 ; etab.L2 = c ; % quadratic Lagrange
c = c+1 ; etab.LH = c ; % cubic Hermite
c = c+1 ; etab.LI = c ; % IGA

%% Triangular elements
c = c+1 ; etab.D1 = c ; % linear Lagrange
c = c+1 ; etab.D2 = c ; % quadratic Lagrange

%% Quadrilateral elements
c = c+1 ; etab.Q1  = c ; % linear Lagrange
c = c+1 ; etab.Q2  = c ; % quadratic Lagrange
c = c+1 ; etab.QI2 = c ; % quadratic IGA element
c = c+1 ; etab.QI3 = c ; % cubic IGA element
c = c+1 ; etab.QI  = c ; % general IGA element
c = c+1 ; etab.QT = c ; % quadratic or cubic T-Spline element
                         % serendipity element - etab.QS?

% enrichments of linear Lagrange quad
c = c+1 ; etab.Q1L2 = c ; % with quadratic Lagrange
c = c+1 ; etab.Q1L4 = c ; % with quartic Lagrange
c = c+1 ; etab.Q1H  = c ; % with Hermite
c = c+1 ; etab.Q1I2 = c ; % with quadratic IGA
c = c+1 ; etab.Q1I3 = c ; % with cubic IGA
c = c+1 ; etab.Q1I  = c ; % with general IGA
c = c+1 ; etab.Q1T  = c ; % with quadr. or cubic T-spline

%% Tetrahedral elements
c = c+1 ; etab.T1  = c ; % linear Lagrange

%% Pyramids
c = c+1 ; etab.P1  = c ; % linear Lagrange

%% Hexahedral elements
c = c+1 ; etab.H1  = c ; % linear Lagrange
c = c+1 ; etab.H2  = c ; % quadratic Lagrange
c = c+1 ; etab.HI2 = c ; % quadratic IGA
c = c+1 ; etab.HI3 = c ; % cubic IGA
c = c+1 ; etab.HI  = c ; % general IGA
c = c+1 ; etab.HT  = c ; % T-Hex

% enrichments of linear Lagrange hex
c = c+1 ; etab.H1L2 = c ; % with quadratic Lagrange surface
c = c+1 ; etab.H1L4 = c ; % with quartic Lagrange surface
c = c+1 ; etab.H1I2 = c ; % with quadratic IGA surface
c = c+1 ; etab.H1I3 = c ; % with cubic IGA surface
c = c+1 ; etab.H1I  = c ; % with general IGA surface
c = c+1 ; etab.H1T  = c ; % with quadratic or cubic T-spline

% enrichments of quadratic Lagrange hex
c = c+1 ; etab.H2I  = c ; % with general IGA surface

%% constant  elements
% enrichments of quadratic Lagrange hex
c = c+1 ; etab.C1  = c ; % 1D line elements


%% If input arguments provided, print element type to screen
if nargin > 0
  
  % get the field names
  eNames = fieldnames(etab);
  
  % loop over inputs
  for i=1:nargin
    if isnumeric(varargin{i})
      if isvector(varargin{i})
        for j=1:numel(varargin{i})
          el = varargin{i}(j) ;
          if el == floor(el) && el > 0 && el <= c
            fprintf(1,'Element type %2u is %s\n',el,eNames{el}) ;
          else
            fprintf(2,'Input must be integers between 1 and %u. You entered %g\n', c,el) ;
            %break ;
          end
        end
      else
        el = varargin{i} ;
        if el == floor(el) && el > 0 && el <= c
          fprintf(1,'Element type %2u is %s\n',el,eNames{el}) ;
        else
          fprintf(2,'Input must be integers between 1 and %u. You entered %g\n', c,el) ;
          %break ;
        end
      end
    else
      fprintf(2,'Input must be integers or an integer array.\n') ;
    end
  end
end

end