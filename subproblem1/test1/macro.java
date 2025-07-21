// Simcenter STAR-CCM+ macro: macro.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;
import star.meshing.*;

public class macro extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));

    ExtrusionMerge extrusionMerge_0 = 
      ((ExtrusionMerge) cadModel_0.getFeature("Extrude 1"));

    Sketch sketch_0 = 
      ((Sketch) cadModel_0.getFeature("Sketch 1"));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));

    star.cadmodeler.Body cadmodelerBody_0 = 
      ((star.cadmodeler.Body) extrusionMerge_0.getBody(lineSketchPrimitive_0));

    cadModel_0.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_0}), new NeoObjectVector(new Object[] {}), true, false, 1, false, false, 3, "SharpEdges", 30.0, 2, true, 1.0E-5, false);

    SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Body 1"));

    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {solidModelPart_0}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, RegionManager.CreateInterfaceMode.BOUNDARY, "OneEdgeBoundaryPerPart", null);

    PrepareFor2dOperation prepareFor2dOperation_0 = 
      (PrepareFor2dOperation) simulation_0.get(MeshOperationManager.class).createPrepareFor2dOperation(new NeoObjectVector(new Object[] {}));

    prepareFor2dOperation_0.execute();
  }
}
