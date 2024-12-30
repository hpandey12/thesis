% Initialize Newton Loop
eN = 1 ; ns = 0; 
% Newton Loop
while eN > TOL   
    % Newton iteration counter
    ns = ns + 1;
    
    % Loop over volume elements
    felm_mem;
    
    % Terminate Computation
    if jterm == 1; break; end
    
    % Global Residual Force
    f = R - fext;
    
    if volc == 1
        f(dof.n) = R(dof.n) - V_0;
    end
    
    % Eliminate Constraints in K and R due to BC
    Kr = K(dof.ir,dof.ir);
    fr = f(dof.ir);
      
    % Solve
    Kr = sparse(Kr);
    DUr = -Kr\fr;
    DU = zeros(3*node.n,1);
    DU(dof.ir) = DUr;
    U = U + DU;
        
    % Energy residual
    eN = abs(fr'*DUr)/dof.nir;
    jinfo = 1;
    if jinfo ~= 0
      fprintf('Energy residual at Newton-step = %2i :  log10(eN) = %8.4f \n',ns,log10(eN));
    end
     
    % Current configuration
    Ux = [U(dof.i1) U(dof.i2) U(dof.i3)];
    node.x = node.X + Ux(:,1:3);

    if volc == 1
        Load.p = U(dof.n);
    end
    
    % Terminate
    nlim = 90;
    if ns >= nlim 
      eN = 0; jterm = 1;
      fprintf('Newton iteration failed to converge within %3i steps \n',nlim);
    end
    jlin = 0;
    if jlin == 1
        eN = 0;
    end

end
% Newton statistics
if jterm ~= 1
  fprintf('\n Load step %2i used %2i Newton-steps \n\n',l-1,ns);
end
% Load under the displacement drive
Reac = R(dof.id);
Reas = sum(Reac); 