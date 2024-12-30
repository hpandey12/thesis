function [quadr] = cQuad_classic(ngp)
% -------------------------------------------------------------------------
%
% -------------------------------------------------------------------------
[gps1d, w1d] = cGaussLegendreQuadrature(ngp);
w1 = (repmat(w1d', 1, ngp))';
w2 = (reshape(repmat(w1d', ngp,1), 1, ngp^2))';
quadr.w = w1.*w2;
% -------------------------------------------------------------------------
quadr.gps = zeros(ngp^2,2);
quadr.gps(:,1) = (repmat(gps1d', 1, ngp))';
quadr.gps(:,2) = (reshape(repmat(gps1d', ngp, 1), 1, ngp^2))';
quadr.ngp = size(quadr.gps,1);
% -------------------------------------------------------------------------
end