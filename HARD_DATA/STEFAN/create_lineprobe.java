// Simcenter STAR-CCM+ macro: create_lineprobe.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class create_lineprobe extends StarMacro {

  public void execute() {
    execute0();
    // /rwthfs/rz/cluster/hpcwork/yy310050/thesis/HARD_DATA/STEFAN/stefan.sim
    execute1();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(Dimensions.Builder().length(1).build());

    Scene scene_8 = 
      simulation_0.getSceneManager().getScene("Mesh Scene 2");

    scene_8.setTransparencyOverrideMode(SceneTransparencyOverride.MAKE_SCENE_TRANSPARENT);

    scene_8.getCreatorGroup().setQuery(null);

    Region region_1 = 
      simulation_0.getRegionManager().getRegion("Body 1");

    scene_8.getCreatorGroup().setObjects(region_1);

    scene_8.getCreatorGroup().setQuery(null);

    scene_8.getCreatorGroup().setObjects(region_1);

    PartDisplayer partDisplayer_5 = 
      scene_8.getDisplayerManager().createPartDisplayer("Probe Surface", -1, 1);

    scene_8.setTransparencyOverrideMode(SceneTransparencyOverride.MAKE_SCENE_TRANSPARENT);

    LinePart linePart_1 = 
      simulation_0.getPartManager().createLinePart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), new DoubleVector(new double[] {1.0, 0.0, 0.0}), 20, null);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    linePart_1.getPoint1Coordinate().setCoordinateSystem(labCoordinateSystem_0);

    linePart_1.getPoint1Coordinate().setUnits0(units_0);

    linePart_1.getPoint1Coordinate().setUnits1(units_0);

    linePart_1.getPoint1Coordinate().setUnits2(units_0);

    linePart_1.getPoint1Coordinate().setDefinition("");

    linePart_1.getPoint1Coordinate().setValue(new DoubleVector(new double[] {0.0, 0.002, 0.0}));

    linePart_1.getPoint1Coordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.002, 0.0}));

    linePart_1.getPoint2Coordinate().setCoordinateSystem(labCoordinateSystem_0);

    linePart_1.getPoint2Coordinate().setUnits0(units_0);

    linePart_1.getPoint2Coordinate().setUnits1(units_0);

    linePart_1.getPoint2Coordinate().setUnits2(units_0);

    linePart_1.getPoint2Coordinate().setDefinition("");

    linePart_1.getPoint2Coordinate().setValue(new DoubleVector(new double[] {0.5, 0.002, 0.0}));

    linePart_1.getPoint2Coordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.5, 0.002, 0.0}));

    linePart_1.setCoordinateSystem(labCoordinateSystem_0);

    linePart_1.getInputParts().setQuery(null);

    linePart_1.getInputParts().setObjects(region_1);

    linePart_1.setResolution(100);

    partDisplayer_5.getVisibleParts().addParts(linePart_1);

    partDisplayer_5.getHiddenParts().addParts();

    scene_8.setTransparencyOverrideMode(SceneTransparencyOverride.USE_DISPLAYER_PROPERTY);

    XyzInternalTable xyzInternalTable_0 = 
      simulation_0.getTableManager().create("star.common.XyzInternalTable");

    xyzInternalTable_0.setPresentationName("line_probe");

    xyzInternalTable_0.getParts().setQuery(null);

    xyzInternalTable_0.getParts().setObjects(linePart_1);

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Temperature"));

    PrimitiveFieldFunction primitiveFieldFunction_2 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("RelativeSolidVolumeFractionh2o"));

    PrimitiveFieldFunction primitiveFieldFunction_3 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Volume"));

    xyzInternalTable_0.setFieldFunctions(new NeoObjectVector(new Object[] {primitiveFieldFunction_0, primitiveFieldFunction_2, primitiveFieldFunction_3}));

    CurrentView currentView_3 = 
      scene_8.getCurrentView();

    currentView_3.setInput(new DoubleVector(new double[] {0.02139413993119419, 0.0027222022782518096, 1.4914097734575193E-11}), new DoubleVector(new double[] {0.02139413993119419, 0.0027222022782518096, 0.051312382563580915}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 0.2500079998720041, 0, 30.0);

    TableUpdate tableUpdate_0 = 
      xyzInternalTable_0.getTableUpdate();

    tableUpdate_0.setAutoExtract(true);

    tableUpdate_0.setSaveToFile(true);

    tableUpdate_0.getUpdateModeOption().setSelected(StarUpdateModeOption.Type.DELTATIME);

    DeltaTimeUpdateFrequency deltaTimeUpdateFrequency_2 = 
      tableUpdate_0.getDeltaTimeUpdateFrequency();

    Units units_7 = 
      ((Units) simulation_0.getUnitsManager().getObject("s"));

    deltaTimeUpdateFrequency_2.setDeltaTime("20.0", units_7);

    simulation_0.saveState("/rwthfs/rz/cluster/hpcwork/yy310050/thesis/HARD_DATA/STEFAN/stefan.sim");
  }

  private void execute1() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Solution solution_0 = 
      simulation_0.getSolution();

    solution_0.clearSolution();

    solution_0.initializeSolution();
  }
}
