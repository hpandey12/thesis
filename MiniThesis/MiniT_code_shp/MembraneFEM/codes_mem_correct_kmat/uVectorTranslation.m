function TransformationMatrix = uVectorTranslation(vec)
% -------------------------------------------------------------------------
% Routine creates the tranlation matrix.
% Input:
%   - vec - the point(s) tranlated by the vector
% Output:
%   - TransformationMatrix - translation matrix
% -------------------------------------------------------------------------
v = [vec(:); 0; 0]; 
TransformationMatrix = [1 0 v(1); 0 1 v(2); 0 0 1]; 
end