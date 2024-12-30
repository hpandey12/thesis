function ExtrudedPoints = uExtrudePoints(TransformationMatrix,Points)
% -------------------------------------------------------------------------
%
% -------------------------------------------------------------------------
ExtrudedPoints = (TransformationMatrix * [Points ones(size(Points,1),1)]')';
ExtrudedPoints = ExtrudedPoints(:,1:2);
% -------------------------------------------------------------------------
end