// Simcenter STAR-CCM+ macro: t2.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;
import star.meshing.*;

public class t2 extends StarMacro {

  public void execute() {
    execute0();
    // /rwthfs/rz/cluster/hpcwork/yy310050/thesis/subproblem1/test1/subsub_Copy.sim
    execute1();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Solution solution_0 = 
      simulation_0.getSolution();

    solution_0.clearSolution();

    MeshPipelineController meshPipelineController_0 = 
      simulation_0.get(MeshPipelineController.class);

    meshPipelineController_0.clearGeneratedMeshes();

    ScalarGlobalParameter scalarGlobalParameter_0 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_dia_chamber"));

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    scalarGlobalParameter_0.getQuantity().setValueAndUnits(130.0, units_0);

    CadModel cadModel_0 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));

    cadModel_0.update();

    SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Body 1"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_0}));

    AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("amr"));

    autoMeshOperation_0.execute();

    PhysicalTimeStoppingCriterion physicalTimeStoppingCriterion_0 = 
      ((PhysicalTimeStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Physical Time"));

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("s"));

    physicalTimeStoppingCriterion_0.getMaximumTime().setValueAndUnits(1.5, units_1);

    solution_0.initializeSolution();

    simulation_0.getSimulationIterator().step(1);

    ResidualPlot residualPlot_0 = 
      ((ResidualPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    residualPlot_0.openInteractive();

    PlotUpdate plotUpdate_0 = 
      residualPlot_0.getPlotUpdate();

    HardcopyProperties hardcopyProperties_0 = 
      plotUpdate_0.getHardcopyProperties();

    hardcopyProperties_0.setCurrentResolutionWidth(1343);

    hardcopyProperties_0.setCurrentResolutionHeight(566);

    simulation_0.saveState("/rwthfs/rz/cluster/hpcwork/yy310050/thesis/subproblem1/test1/subsub_Copy.sim");
  }

  private void execute1() {
  }
}
