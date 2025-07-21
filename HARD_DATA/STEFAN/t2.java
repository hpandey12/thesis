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
    // /rwthfs/rz/cluster/hpcwork/yy310050/thesis/HARD_DATA/STEFAN/stefan.sim
    execute1();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));

    cadModel_0.update();

    SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Body 1"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_0}));

    PrepareFor2dOperation prepareFor2dOperation_0 = 
      ((PrepareFor2dOperation) simulation_0.get(MeshOperationManager.class).getObject("Badge for 2D Meshing"));

    prepareFor2dOperation_0.execute();

    AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("mesh_op"));

    autoMeshOperation_0.execute();

    simulation_0.saveState("/rwthfs/rz/cluster/hpcwork/yy310050/thesis/HARD_DATA/STEFAN/stefan.sim");
  }

  private void execute1() {
  }
}
