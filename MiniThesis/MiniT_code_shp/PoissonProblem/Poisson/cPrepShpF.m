function [quad] = cPrepShpF(elty, quad, epro, etab)
%CPREPSHPF Pre-process shape functions, for instance for IGA or Hermite
%elem is only needed fot T-Splines. may change to varigin
% fprintf('cPrepShpF\n')
switch elty
    case etab.LI
        for gp=1:quad.ngp            
            Nx = epro.Cx *  quad.N{gp}(1:epro.p+1) ;
            R=Nx.*epro.We;
            w=sum(R);
            quad.N{gp}=R./w;
            dNx= epro.Cx*quad.dN{gp};
            dR= dNx.*epro.We;
            dw=sum(dR);
            quad.dN{gp}=(dR-R*dw)/w;
        end
    case etab.H1I
        % NECE
        for gp=1:quad.ngp
            
            Nx  = epro.Cx * quad.N{gp}(1:epro.p1) ;
            Ny  = epro.Cy * quad.N{gp}((1:epro.q1)+epro.p1) ;
            dNx = epro.Cx * quad.dN{gp}(1:epro.p1,1) ;
            dNy = epro.Cy * quad.dN{gp}((1:epro.q1)+epro.p1,1) ;
            ddNx = epro.Cx * quad.ddN{gp}(1:epro.p1,1) ;
            ddNy = epro.Cy * quad.ddN{gp}((1:epro.q1)+epro.p1,1) ;
            
            % calculate shape functions and derivatives
            dR       = zeros(epro.p1q1,2) ;
            
            R       = reshape(  Nx *  Ny' ,epro.p1q1,1) .*epro.We ;
            dR(:,1) = reshape( dNx *  Ny' ,epro.p1q1,1) .*epro.We ;
            dR(:,2) = reshape(  Nx * dNy' ,epro.p1q1,1) .*epro.We ;
            
            ddR(:,1) = reshape(ddNx *   Ny' ,epro.p1q1,1) .*epro.We ;
            ddR(:,2) = reshape(  Nx * ddNy' ,epro.p1q1,1) .*epro.We ;
            ddR(:,3) = reshape( dNx *  dNy' ,epro.p1q1,1) .*epro.We ;
            
            % apply weights
            w       = sum(  R)     ;
            dw_dxi  = sum( dR) ; % sums column-wise
            ddw_dxi = sum(ddR) ;
            R = R./w ;
            for j=1:2
                dR(:,j)  = ( dR(:,j) - R.* dw_dxi(j)) ./ w ;
                ddR(:,j) = (ddR(:,j) - R.*ddw_dxi(j) - 2*dR(:,j).*dw_dxi(j) ) ./ w ; %#ok<AGROW>
            end
            ddR(:,3) = (ddR(:,3) - R.*ddw_dxi(3) - dR(:,1)*dw_dxi(2) - dR(:,2)*dw_dxi(1) ) ./ w ;
            
            % combine with linear part
            quad.N{gp}   = [  R*(1-quad.gps(gp,3))/2       ; quad.N{gp}(epro.p1+epro.q1+(1:4)) ] ;
            quad.dN{gp}  = [ dR*(1-quad.gps(gp,3))/2  -R/2 ; quad.dN{gp}(epro.p1+epro.q1+(1:4),:) ] ;
            quad.ddN{gp} = [ddR(:,1:2)*(1-quad.gps(gp,3))/2  zeros(epro.p1q1,1) -dR(:,2)/2 -dR(:,1)/2 ddR(:,3)*(1-quad.gps(gp,3))/2 ; quad.ddN{gp}(epro.p1+epro.q1+(1:4),:) ] ;
            
        end
        
    case etab.H2I
        %% % NECE
        for gp=1:quad.ngp
            
            Nx  = epro.Cx * quad.N{gp}(1:epro.p1) ;
            Ny  = epro.Cy * quad.N{gp}((1:epro.q1)+epro.p1) ;
            dNx = epro.Cx * quad.dN{gp}(1:epro.p1,1) ;
            dNy = epro.Cy * quad.dN{gp}((1:epro.q1)+epro.p1,1) ;
            
            % calculate shape functions and derivatives
            dR       = zeros(epro.p1q1,2) ;
            
            R       = reshape(  Nx *  Ny' ,epro.p1q1,1) .*epro.We ;
            dR(:,1) = reshape( dNx *  Ny' ,epro.p1q1,1) .*epro.We ;
            dR(:,2) = reshape(  Nx * dNy' ,epro.p1q1,1) .*epro.We ;
            
            % apply weights
            w       = sum(  R)     ;
            dw_dxi  = sum( dR) ; % sums column-wise
            R = R./w ;
            for j=1:2
                dR(:,j)  = ( dR(:,j) - R.* dw_dxi(j)) ./ w ;
            end
            
            % combine with linear part
            quad.N{gp}  = [ R*(quad.gps(gp,3)^2-quad.gps(gp,3))/2                           ; quad.N{gp}(epro.p1+epro.q1+(1:18)) ] ;
            quad.dN{gp} = [dR*(quad.gps(gp,3)^2-quad.gps(gp,3))/2  R*(2*quad.gps(gp,3)-1)/2 ; quad.dN{gp}(epro.p1+epro.q1+(1:18),:) ] ;
        end
        
    case {etab.H1T,etab.QT}
        for gp=1:quad.ngp
            %             N = epro.C * quad.B{gp}(1:epro.p1q1);
            %             dN(:,1) = epro.C * quad.dB{gp}(1:epro.p1q1,1);
            %             dN(:,2) = epro.C * quad.dB{gp}(1:epro.p1q1,2);
            %             ddN(:,1)= epro.C * quad.ddB{gp}(1:epro.p1q1,1) ;
            %             ddN(:,2)= epro.C * quad.ddB{gp}(1:epro.p1q1,2) ;
            %             ddN(:,3)= epro.C * quad.ddB{gp}(1:epro.p1q1,3) ;
            % calculate shape functions and derivatives
            R=epro.C * quad.B{gp}(1:epro.p1q1).*epro.We;
            dR(:,1)=epro.C * quad.dB{gp}(1:epro.p1q1,1).*epro.We;
            dR(:,2)=epro.C * quad.dB{gp}(1:epro.p1q1,2).*epro.We;
            ddR(:,1)=epro.C * quad.ddB{gp}(1:epro.p1q1,1) .*epro.We;
            ddR(:,2)=epro.C * quad.ddB{gp}(1:epro.p1q1,2) .*epro.We;
            ddR(:,3)=epro.C * quad.ddB{gp}(1:epro.p1q1,3) .*epro.We;
            nn=size(epro.We,1);
            
            % apply weights
            w       = sum(  R);
            dw_dxi  = sum( dR) ; % sums column-wise
            %TODO weights for 2nd deriv
            
            R = R./w ;
            for j=1:2
                dR(:,j)  = ( dR(:,j) - R.* dw_dxi(j)) ./ w ;
            end
            
            if elty==etab.QT
                quad.N{gp}=R;
                quad.dN{gp}=dR;
                
            elseif elty==etab.H1T
                % combine with linear part
                quad.N{gp}   = [  R*(1-quad.gps(gp,3))/2       ; quad.B{gp}(epro.p1q1+(1:4)) ] ;
                quad.dN{gp}  = [ dR*(1-quad.gps(gp,3))/2  -R/2 ; quad.dB{gp}(epro.p1q1+(1:4),:) ] ;
                quad.ddN{gp} = [ddR(:,1:2)*(1-quad.gps(gp,3))/2  zeros(nn,1) -dR(:,2)/2 -dR(:,1)/2 ddR(:,3)*(1-quad.gps(gp,3))/2 ; quad.ddB{gp}(epro.p1q1+(1:4),:) ] ;
            end
        end
    case etab.HI
        %% full IGA quad
        for gp=1:quad.ngp
            Nx  = epro.Cx * quad.N{gp}(1:epro.p1) ;
            Ny  = epro.Cy * quad.N{gp}((1:epro.q1)+epro.p1) ;
            Nz  = epro.Cz * quad.N{gp}((1:epro.r1)+epro.p1+epro.q1) ;
            dNx = epro.Cx * quad.dN{gp}(1:epro.p1,1) ;
            dNy = epro.Cy * quad.dN{gp}((1:epro.q1)+epro.p1,1) ;
            dNz = epro.Cz * quad.dN{gp}((1:epro.r1)+epro.p1+epro.q1,1) ;
            
            % calculate shape functions and derivatives
            dR       = zeros(epro.p1q1r1,3) ;
            
            Rt = reshape(  Nx *  Ny' ,epro.p1q1  ,1) ;
            R  = reshape(  Rt *  Nz' ,epro.p1q1r1,1) .*epro.We ;
            
            dRt(:,1) = reshape( dNx *  Ny' ,epro.p1q1,1) ;
            dRt(:,2) = reshape(  Nx * dNy' ,epro.p1q1,1) ;
            %dRt(:,3) = Rt ;
            dR(:,1) = reshape( dRt(:,1) *  Nz' ,epro.p1q1r1,1) .*epro.We ;
            dR(:,2) = reshape( dRt(:,2) *  Nz' ,epro.p1q1r1,1) .*epro.We ;
            dR(:,3) = reshape(  Rt      * dNz' ,epro.p1q1r1,1) .*epro.We ;
            
            % apply weights
            w       = sum(  R)     ;
            dw_dxi  = sum( dR) ; % sums column-wise
            R = R./w ;
            for j=1:3
                dR(:,j)  = ( dR(:,j) - R.* dw_dxi(j)) ./ w ;
            end
            
            % combine with linear part
            quad.N{gp}  =  R ;
            quad.dN{gp} = dR ;
        end
    case etab.QI %correct version (correced appication of weights)
        for gp=1:quad.ngp
            % B-spline shape functions and derivatives
            Nx = epro.Cx *  quad.N{gp}(1:epro.p+1) ;
            Ny = epro.Cy *  quad.N{gp}((1:epro.q+1)+epro.p+1) ;
            dNx = epro.Cx * quad.dN{gp}(1:epro.p+1) ;
            dNy = epro.Cy * quad.dN{gp}((1:epro.q+1)+epro.p+1) ;
            ddNx = epro.Cx * quad.ddN{gp}(1:epro.p+1) ;
            ddNy = epro.Cy * quad.ddN{gp}((1:epro.q+1)+epro.p+1) ;
            
            % NURBS shape functions and derivatives
            dR   = zeros((epro.p+1)*(epro.q+1),2) ;
            ddR  = zeros((epro.p+1)*(epro.q+1),3) ;
            
            R  = reshape( Nx*Ny',(epro.p+1)*(epro.q+1),1).*epro.We ;
            dR(:,1) = reshape( dNx *  Ny' ,(epro.p+1)*(epro.q+1),1).*epro.We ;
            dR(:,2) = reshape( Nx  *  dNy',(epro.p+1)*(epro.q+1),1).*epro.We ;
            %
            ddR(:,1) = reshape(ddNx* Ny',(epro.p+1)*(epro.q+1),1).*epro.We ;
            ddR(:,2) = reshape( Nx*ddNy',(epro.p+1)*(epro.q+1),1).*epro.We ;
            ddR(:,3) = reshape( dNx *  dNy' ,(epro.p+1)*(epro.q+1),1).*epro.We ;
            
            w   = sum(R);
            dw  = sum(dR) ; % sums column-wise
            ddw = sum(ddR) ; % sums column-wise
            
            R = R./w ;
            for i=1:2
                dR(:,i)  = ( dR(:,i) -  R * dw(i)) ./ w ;
                ddR(:,i) = (ddR(:,1)  -  R *ddw(i) - 2*dR(:,i) *dw(i))./w;
            end
            ddR(:,3) = (ddR(:,3) - R*ddw(3) - dR(:,1)*dw(2) - dR(:,2)*dw(1)) ./ w ;
            quad.N{gp}=R;
            quad.dN{gp}=dR;
            quad.ddN{gp}=ddR;
        end
    case etab.HT
        %% full T-spline quad
        for gp=1:quad.ngp
            p1=epro.p+1;q1=epro.q+1;r1=epro.r+1;
            Cxy=epro.Cxy;Cz=epro.Cz;We=epro.We;
            p1q1=p1*q1;
            p1q1r1=p1q1*r1;
            Bt=quad.N{gp};
            dBt=quad.dN{gp};
            
            
            % shape function values
            Bxy = reshape(  Bt(1:p1,1) *   Bt(1:q1,2)' ,p1q1,1) ;
            Nxy = Cxy' * Bxy;%B(1:nbf);
            Nz=Cz*Bt(1:r1,3);
            % R=reshape(Nxy*Nz',p1q1r1,1).*We;
            Rt=Nxy*Nz';
            nrt=numel(Rt);
            R=reshape(Rt,nrt,1).*We;
            
            % first derivatives
            dB  = zeros(p1q1,3);
            dB(:,1)  = reshape( dBt(1:p1,1) *   Bt(1:q1,2)' ,p1q1,1) ;
            dB(:,2)  = reshape(  Bt(1:p1,1) *  dBt(1:q1,2)' ,p1q1,1) ;
            dNx= Cxy' * dB(1:p1q1,1);
            dNy= Cxy' * dB(1:p1q1,2);
            dNz= Cz* dBt(1:r1,3);
            
            % dR(:,1)=reshape(dNx*Nz',p1q1r1,1).*We;
            % dR(:,2)=reshape(dNy*Nz',p1q1r1,1).*We;
            
            dRt=dNx*Nz';
            ndRt=numel(dRt);
            dR(:,1)=reshape(dRt,ndRt,1).*We;
            dRt=dNy*Nz';
            ndRt=numel(dRt);
            dR(:,2)=reshape(dRt,ndRt,1).*We;
            dRt=Nxy*dNz';
            ndRt=numel(dRt);
            dR(:,3)=reshape(dRt,ndRt,1).*We;
            
            % apply weights
            w       = sum(  R);
            dw  = sum( dR) ; % sums column-wise
            quad.N{gp} = R./w ;
            for j=1:3
                dR(:,j)  = ( dR(:,j) - R.* dw(j)) ./ w ;
            end
            quad.dN{gp}=dR;
        end
        
end