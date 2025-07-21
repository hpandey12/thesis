package macro;

import star.common.*;
import star.base.neo.*;
import star.meshing.*;
import star.cadmodeler.*;

public class initialise extends StarMacro {

    @Override
    public void execute() {
        // 1. Get the active simulation
        Simulation sim = getActiveSimulation();

        // 2. Retrieve the design parameters from the GlobalParameterManager
        ScalarGlobalParameter param1 =
            (ScalarGlobalParameter) sim.get(GlobalParameterManager.class).getObject("des_param1");
        ScalarGlobalParameter param2 =
            (ScalarGlobalParameter) sim.get(GlobalParameterManager.class).getObject("des_param2");
        ScalarGlobalParameter param3 =
            (ScalarGlobalParameter) sim.get(GlobalParameterManager.class).getObject("des_param3");
        ScalarGlobalParameter param4 =
            (ScalarGlobalParameter) sim.get(GlobalParameterManager.class).getObject("des_param4");

        // Optional: Print out the parameter values for debugging.
        // double value1 = param1.getQuantity().getValue();
        // sim.println("Parameter des_param1 = " + value1);

        // 3. Clear the existing solution
        sim.getSolution().clearSolution();

        // 4. Clear the existing mesh
        MeshPipelineController meshPipelineController = 
            sim.get(MeshPipelineController.class);
        meshPipelineController.clearGeneratedMeshes();

        // 5. Update the CAD model (assuming it’s named "3D-CAD Model 1" in your .sim)
        CadModel cadModel = 
            (CadModel) sim.get(SolidModelManager.class).getObject("3D-CAD Model 1");
        if (cadModel != null) {
            cadModel.update();
        } else {
            sim.println("Warning: Could not find CAD Model named '3D-CAD Model 1'.");
        }

        // 6. Update the relevant part (assuming it's named "Body 1")
        SolidModelPart bodyPart = 
            (SolidModelPart) sim.get(SimulationPartManager.class).getPart("Body 1");
        if (bodyPart != null) {
            sim.get(SimulationPartManager.class).updateParts(
                new NeoObjectVector(new Object[] { bodyPart }));
        } else {
            sim.println("Warning: Could not find Part named 'Body 1'.");
        }

        // 7. Execute the mesh operation named "mesh_op"
        AutoMeshOperation meshOp = 
            (AutoMeshOperation) sim.get(MeshOperationManager.class).getObject("mesh_op");
        if (meshOp != null) {
            meshOp.execute();
        } else {
            sim.println("Warning: Could not find mesh operation named 'mesh_op'.");
        }

        // 8. Finally, initialize the solution
        sim.initializeSolution();

        // At this point, the macro’s job is done; STAR-CCM+ will continue to run
        // any subsequent steps in your batch script as normal.
    }
}