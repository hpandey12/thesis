% Newton Loop fenewton.m
% Roger Sauer - 15.12.10

  
% Initialize Newton Loop
eN = 1 ; ns = 0 ; 

% Newton Loop
while eN > TOL   
    % Newton iteration counter
    ns = ns + 1 ;
    
    % Loop over volume elements
    felm
    
    % Terminate Computation
    if jterm == 1 ; break ; end
    
    % Global Residual Force
    f = R - Fext;
            
    % Eliminate Constraints in K and R due to BC
    Kr = K(ir,ir);
    fr = f(ir);
      
    % Solve
    Kr     = sparse(Kr);
    DUr    = -Kr\fr;
    DU     = zeros(2*node.n,1);
    DU(ir) = DUr;
    U      = U + DU;
        
    % Energy residual
    eN = abs(fr'*DUr)/nir;
    if jinfo ~= 0
      fprintf('Energy residual at Newton-step = %2i :  log10(eN) = %8.4f \n',ns,log10(eN)) 
    end
     
    % Current configuration
    Ux = [ U(i1) U(i2) ] ;
    node.x = node.X + Ux(:,1:2) ;

    % Terminate
    nlim = 50 ;
    if ns >= nlim 
      eN = 0 ; jterm = 1 ;
      fprintf('Newton iteration failed to converge within %3i steps \n',nlim)
    end
    
    if jlin == 1
        eN = 0 ;
    end
end

% Newton statistics
if jterm ~= 1
  fprintf('\n Load step %2i used %2i Newton-steps \n\n',l-1+e0,ns)
end


% Load under the displacement drive
Reac = R(id);
Reas = sum(Reac); 