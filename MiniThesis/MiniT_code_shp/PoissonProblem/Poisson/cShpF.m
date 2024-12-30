function varargout = cShpF( xi, eltyp, eproi, etab, comp)
% compute shape function value at xi for element type eltyp
% the flag comp specifies which values and derivatives are computed.
% It is a boolean vector of length 3, where the entries refer to the
% computation of the shape function value, the first derivative, and the
% second derivative, respectively, so:
%
% comp = [ value , 1st deriv, 2nd deriv ]
%

% dimensionality
dim = numel(xi) ;

switch dim
    case 1
        switch eltyp
            case etab.LI
                [N, dN, ddN] = shp_LIdd_pre(xi, eproi.p) ;
            case etab.L1
                [N, dN, ddN] = shp_L1(xi, [1 1 1]) ;
            otherwise
                fprintf(2,'Element type %i not defined (yet) in cShpF for 1D!\n', eltyp) ;
        end
    case 2
        % 2D elements
        switch eltyp
            case etab.D1
                [N, dN, ddN] = ddD1_shapes2D(xi(1), xi(2)) ;
            case etab.Q1
                [N, dN, ddN] = shp_Q1(xi(1), xi(2), comp) ;
            case etab.Q2
                [N, dN, ddN] = shp_Q2(xi(1), xi(2), comp) ;
            case etab.QI
                [N, dN, ddN] = shp_QI(xi, eproi.We, eproi.Cx, eproi.Cy, [eproi.p eproi.q], comp) ;
            case etab.Q1I
                [N, dN, ddN] = shp_Q1I(xi(1), xi(2), eproi.We, eproi.C, eproi.p, comp) ;
            case etab.QT
                [N, dN] = shp_QT(xi, eproi.We, eproi.C, [eproi.p eproi.q]);
            case 777                
                [ N, dN, ddN] = shp_Q1II( xi, [eproi.p eproi.q], eproi.We, eproi.Cx, eproi.Cy, comp );
            case 778                
                [ N, dN, ddN] = shp_Q1IIb( xi, [eproi.p eproi.q], eproi.We, eproi.Cx, eproi.Cy, comp );
            otherwise
                fprintf(2,'Element type %i not defined (yet) in cShpF for 2D!\n', eltyp) ;
        end
        
        
    case 3
        % 3D elements
        switch eltyp
            case etab.H1
                [N, dN, ddN] = shp_H1(xi(1), xi(2), xi(3), comp) ;
            case etab.H2
                [N, dN, ddN] = shp_H2(xi(1), xi(2), xi(3), comp) ;
            case etab.H1I
                [N, dN, ddN] = shp_H1I(xi, [eproi.p eproi.q], eproi.We, eproi.Cx, eproi.Cy, comp ) ;
            case etab.H1T
                [N, dN] = shp_H1T(xi, eproi.p, eproi.We, eproi.C) ;
            case etab.P1
                [N,dN ,ddN] = ddP1_shapes3D( xi(1), xi(2), xi(3) );
            case etab.T1
                [N,dN ,ddN] = ddT1_shapes3D( xi(1), xi(2), xi(3) );
            case etab.HI
                [N,dN ,ddN] = shp_HI(xi, eproi.We,eproi.Cx, eproi.Cy, eproi.Cz, [eproi.p eproi.q eproi.r],  comp );
            case etab.HT
                [N,dN] = shp_HT(xi,[eproi.p eproi.q eproi.r], eproi.We,eproi.Cxy,eproi.Cz);
                ddN=0;
            otherwise
                fprintf(2,'Element type %i not defined (yet) in cShpF for 3D!\n', eltyp) ;
        end
        
        % not defined
    otherwise
        fprintf(2,'Only 1D, 2D, and 3D elements are supported in cShpF!\n') ;
        fprintf(2,'Your xi was %i-dimensional!\n',dim) ;
        
end

% set output components
k = 0 ;
if comp(1)
    k=k+1 ;
    varargout{k} = N ;
end
if comp(2)
    k=k+1 ;
    varargout{k} = dN ;
end
if comp(3)
    k=k+1 ;
    varargout{k} = ddN ;
end
