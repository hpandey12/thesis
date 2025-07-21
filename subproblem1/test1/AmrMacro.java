package macro;

import star.common.*;
import star.base.neo.*;
import star.meshing.*;

public class AmrMacro extends StarMacro {

    @Override
    public void execute() {
        Simulation sim = getActiveSimulation();

        // ----------------------------------------------------------------------------------
        // 1) Retrieve Physical Time stopping criterion
        // ----------------------------------------------------------------------------------
        PhysicalTimeStoppingCriterion timeStopCriterion =
            (PhysicalTimeStoppingCriterion) sim.getSolverStoppingCriterionManager()
                                               .getSolverStoppingCriterion("Maximum Physical Time");
        if (timeStopCriterion == null) {
            sim.println("Stopping criterion 'Maximum Physical Time' not found.");
            return;
        }
        
        // The 'maximum time' is stored as a ScalarPhysicalQuantity. 
        // Use .getValue() or .getInternalValue() depending on how you want to interpret units.
        double maxTime = timeStopCriterion.getMaximumTime().getValue();
        sim.println("Maximum physical time (according to stopping criterion): " + maxTime);

        // ----------------------------------------------------------------------------------
        // 2) Retrieve the AMR mesh operation
        // ----------------------------------------------------------------------------------
        MeshOperationManager meshOpManager = 
            (MeshOperationManager) sim.get(MeshOperationManager.class);
        
        AutoMeshOperation amrOperation =
            (AutoMeshOperation) meshOpManager.getObject("amr");
        
        if (amrOperation == null) {
            sim.println("AMR operation 'amr' not found. Aborting.");
            return;
        }

        // ----------------------------------------------------------------------------------
        // 3) Execute AMR once before starting the time loop
        // ----------------------------------------------------------------------------------
        sim.println("Executing AMR operation once before starting the time loop...");
        amrOperation.execute();

        // ----------------------------------------------------------------------------------
        // 4) Initialize the solution if needed
        // ----------------------------------------------------------------------------------
        sim.initializeSolution();

        // ----------------------------------------------------------------------------------
        // 5) Step through the time loop, re-executing AMR each iteration
        // ----------------------------------------------------------------------------------
        while (sim.getSolution().getPhysicalTime() < maxTime) {
            double currentTime = sim.getSolution().getPhysicalTime();
            sim.println("Current physical time: " + currentTime);

            // Advance the solver by 1 iteration (or more if desired)
            sim.getSimulationIterator().step(1);

            // After each step, re-run the AMR operation to refine the mesh
            sim.println("Re-executing AMR operation...");
            amrOperation.execute();
        }

        // ----------------------------------------------------------------------------------
        // 6) (Optional) Save the simulation or handle any post-processing steps
        // ----------------------------------------------------------------------------------
        sim.println("Time has reached the stopping criterion limit. AMR macro finished.");
        // e.g. sim.saveState("/path/to/updated_sim.sim");
    }
}