// Simcenter STAR-CCM+ macro: star.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;

public class star extends StarMacro {

  public void execute() {
    //Exporting  creation of Global parameters of Simulation
    execute0();
    //Exporting values and units of Global parameters of Simulation
    execute1();
    //Exporting  creation of Tags of Simulation
    execute2();
    //Export Creation of Cad Model
    execute3();
    //Exporting canonical feature : XY
    execute4();
    //Exporting canonical feature : YZ
    execute5();
    //Exporting canonical feature : ZX
    execute6();
    //Exporting canonical feature : Global Origin
    execute7();
    //Exporting canonical feature : Lab Coordinate System
    execute8();
    //Exporting Sketch 1
    execute9();
    //Exporting Extrude 1
    execute10();
    //Exporting Rename 1
    execute11();
    //Exporting Cad Display options of model
    execute12();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "r_0");

    ScalarGlobalParameter scalarGlobalParameter_11 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("r_0"));

    scalarGlobalParameter_11.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "x_0");

    ScalarGlobalParameter scalarGlobalParameter_12 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("x_0"));

    scalarGlobalParameter_12.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "y_0");

    ScalarGlobalParameter scalarGlobalParameter_13 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("y_0"));

    scalarGlobalParameter_13.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "mf_inert");

    ScalarGlobalParameter scalarGlobalParameter_14 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("mf_inert"));

    scalarGlobalParameter_14.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "mf_gas");

    ScalarGlobalParameter scalarGlobalParameter_15 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("mf_gas"));

    scalarGlobalParameter_15.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "T_sat");

    ScalarGlobalParameter scalarGlobalParameter_16 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("T_sat"));

    scalarGlobalParameter_16.setDimensions(Dimensions.Builder().temperature(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "rho_v");

    ScalarGlobalParameter scalarGlobalParameter_5 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("rho_v"));

    scalarGlobalParameter_5.setDimensions(Dimensions.Builder().mass(1).volume(-1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "Cp_l");

    ScalarGlobalParameter scalarGlobalParameter_0 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("Cp_l"));

    scalarGlobalParameter_0.setDimensions(Dimensions.Builder().mass(-1).temperature(-1).energy(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "h_lv");

    ScalarGlobalParameter scalarGlobalParameter_2 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("h_lv"));

    scalarGlobalParameter_2.setDimensions(Dimensions.Builder().mass(-1).energy(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "rho_l");

    ScalarGlobalParameter scalarGlobalParameter_4 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("rho_l"));

    scalarGlobalParameter_4.setDimensions(Dimensions.Builder().mass(1).volume(-1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "Cp_v");

    ScalarGlobalParameter scalarGlobalParameter_6 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("Cp_v"));

    scalarGlobalParameter_6.setDimensions(Dimensions.Builder().mass(-1).temperature(-1).energy(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "visc_l");

    ScalarGlobalParameter scalarGlobalParameter_7 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("visc_l"));

    scalarGlobalParameter_7.setDimensions(Dimensions.Builder().time(1).pressure(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "visc_v");

    ScalarGlobalParameter scalarGlobalParameter_8 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("visc_v"));

    scalarGlobalParameter_8.setDimensions(Dimensions.Builder().time(1).pressure(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "k_l");

    ScalarGlobalParameter scalarGlobalParameter_9 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("k_l"));

    scalarGlobalParameter_9.setDimensions(Dimensions.Builder().length(-1).temperature(-1).power(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "k_v");

    ScalarGlobalParameter scalarGlobalParameter_10 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("k_v"));

    scalarGlobalParameter_10.setDimensions(Dimensions.Builder().length(-1).temperature(-1).power(1).build());
  }

  private void execute1() {

    Simulation simulation_0 = 
      getActiveSimulation();

    ScalarGlobalParameter scalarGlobalParameter_11 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("r_0"));

    scalarGlobalParameter_11.getQuantity().setValue(0.008);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject(""));

    scalarGlobalParameter_11.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_12 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("x_0"));

    scalarGlobalParameter_12.getQuantity().setValue(0.0);

    scalarGlobalParameter_12.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_13 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("y_0"));

    scalarGlobalParameter_13.getQuantity().setValue(0.0);

    scalarGlobalParameter_13.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_14 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("mf_inert"));

    scalarGlobalParameter_14.getQuantity().setValue(1.0E-8);

    scalarGlobalParameter_14.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_15 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("mf_gas"));

    scalarGlobalParameter_15.getQuantity().setDefinition("1-${mf_inert}");

    ScalarGlobalParameter scalarGlobalParameter_16 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("T_sat"));

    scalarGlobalParameter_16.getQuantity().setValue(0.0);

    Units units_4 = 
      ((Units) simulation_0.getUnitsManager().getObject("K"));

    scalarGlobalParameter_16.getQuantity().setUnits(units_4);

    ScalarGlobalParameter scalarGlobalParameter_5 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("rho_v"));

    scalarGlobalParameter_5.getQuantity().setValue(0.0);

    Units units_5 = 
      ((Units) simulation_0.getUnitsManager().getObject("kg/m^3"));

    scalarGlobalParameter_5.getQuantity().setUnits(units_5);

    ScalarGlobalParameter scalarGlobalParameter_0 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("Cp_l"));

    scalarGlobalParameter_0.getQuantity().setValue(0.0);

    Units units_6 = 
      ((Units) simulation_0.getUnitsManager().getObject("J/kg-K"));

    scalarGlobalParameter_0.getQuantity().setUnits(units_6);

    ScalarGlobalParameter scalarGlobalParameter_2 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("h_lv"));

    scalarGlobalParameter_2.getQuantity().setValue(0.0);

    Units units_7 = 
      ((Units) simulation_0.getUnitsManager().getObject("J/kg"));

    scalarGlobalParameter_2.getQuantity().setUnits(units_7);

    ScalarGlobalParameter scalarGlobalParameter_4 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("rho_l"));

    scalarGlobalParameter_4.getQuantity().setValue(0.0);

    scalarGlobalParameter_4.getQuantity().setUnits(units_5);

    ScalarGlobalParameter scalarGlobalParameter_6 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("Cp_v"));

    scalarGlobalParameter_6.getQuantity().setValue(0.0);

    scalarGlobalParameter_6.getQuantity().setUnits(units_6);

    ScalarGlobalParameter scalarGlobalParameter_7 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("visc_l"));

    scalarGlobalParameter_7.getQuantity().setValue(0.0);

    Units units_8 = 
      ((Units) simulation_0.getUnitsManager().getObject("Pa-s"));

    scalarGlobalParameter_7.getQuantity().setUnits(units_8);

    ScalarGlobalParameter scalarGlobalParameter_8 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("visc_v"));

    scalarGlobalParameter_8.getQuantity().setValue(0.0);

    scalarGlobalParameter_8.getQuantity().setUnits(units_8);

    ScalarGlobalParameter scalarGlobalParameter_9 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("k_l"));

    scalarGlobalParameter_9.getQuantity().setValue(0.0);

    Units units_2 = 
      ((Units) simulation_0.getUnitsManager().getObject("W/m-K"));

    scalarGlobalParameter_9.getQuantity().setUnits(units_2);

    ScalarGlobalParameter scalarGlobalParameter_10 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("k_v"));

    scalarGlobalParameter_10.getQuantity().setValue(0.0);

    scalarGlobalParameter_10.getQuantity().setUnits(units_2);
  }

  private void execute2() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.get(TagManager.class).createNewUserTag("Region (2D Mesh)");

    simulation_0.get(TagManager.class).createNewUserTag("Boundary (2D Mesh)");

    simulation_0.get(TagManager.class).createNewUserTag("Region (2D Mesh) 2");

    simulation_0.get(TagManager.class).createNewUserTag("Boundary (2D Mesh) 2");
  }

  private void execute3() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).createSolidModel();

    cadModel_0.setModelCreationVersion(33);
  }

  private void execute4() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("XY");
  }

  private void execute5() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_1 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("YZ");
  }

  private void execute6() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_2 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("ZX");
  }

  private void execute7() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferencePoint canonicalReferencePoint_0 = 
      (CanonicalReferencePoint) cadModel_0.getFeature("Global Origin");
  }

  private void execute8() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferenceCoordinateSystem canonicalReferenceCoordinateSystem_0 = 
      (CanonicalReferenceCoordinateSystem) cadModel_0.getFeature("Lab Coordinate System");
  }

  private void execute9() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_0.getFeature("XY"));

    Sketch sketch_0 = 
      (Sketch) cadModel_0.getFeatureManager().createSketch(canonicalSketchPlane_0);

    sketch_0.setPresentationName("Sketch 1");

    PointSketchPrimitive pointSketchPrimitive_0 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(9, "Point 1", new DoubleVector(new double[] {-0.025, -0.025}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_1 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(10, "Point 2", new DoubleVector(new double[] {0.025, -0.025}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_2 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(11, "Point 3", new DoubleVector(new double[] {0.025, 0.025}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_3 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(12, "Point 4", new DoubleVector(new double[] {-0.025, 0.025}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_4 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(20, "Point 5", new DoubleVector(new double[] {0.0, 0.025}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_5 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(21, "Point 6", new DoubleVector(new double[] {0.0, -0.025}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_6 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(29, "Point 7", new DoubleVector(new double[] {-0.025, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_7 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(30, "Point 8", new DoubleVector(new double[] {0.025, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_8 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(36, "Point 9", new DoubleVector(new double[] {0.0, 0.0}), true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_0 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(13, 9, 10, new DoubleVector(new double[] {1.0000000000000002, 0.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_0, "Line 1", 13, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(14, 10, 11, new DoubleVector(new double[] {-1.8369701987210302E-16, 1.0000000000000002}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_1, "Line 2", 14, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_2 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(15, 11, 12, new DoubleVector(new double[] {-1.0000000000000002, -1.2246467991473535E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_2, "Line 3", 15, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_3 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(16, 12, 9, new DoubleVector(new double[] {6.123233995736767E-17, -1.0000000000000002}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_3, "Line 4", 16, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(22, 20, 21, new DoubleVector(new double[] {5.551115123125783E-17, -1.0000000000000002}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_4, "Line 5", 22, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_5 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(31, 29, 30, new DoubleVector(new double[] {1.0000000000000002, 2.449293598294707E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_5, "Line 6", 31, true, true, false, true, true, false, 0.0);

    sketch_0.addParallelConstraint(lineSketchPrimitive_0, lineSketchPrimitive_2);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_0, lineSketchPrimitive_1);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_0, lineSketchPrimitive_3);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_4, lineSketchPrimitive_2);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_5, lineSketchPrimitive_0);

    sketch_0.addMidPointConstraint(pointSketchPrimitive_5, lineSketchPrimitive_0);

    sketch_0.addMidPointConstraint(pointSketchPrimitive_4, lineSketchPrimitive_2);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_6, lineSketchPrimitive_3);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_7, lineSketchPrimitive_1);

    sketch_0.addMidPointConstraint(pointSketchPrimitive_7, lineSketchPrimitive_1);

    sketch_0.addMidPointConstraint(pointSketchPrimitive_6, lineSketchPrimitive_3);

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_0);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_8, lineSketchPrimitive_4);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_8, lineSketchPrimitive_5);

    sketch_0.addFixationConstraint(pointSketchPrimitive_8);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    LengthDimension lengthDimension_0 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_1, 0.05, units_0);

    LengthDimension lengthDimension_1 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_2, 0.05, units_0);

    sketch_0.setPresentationName("Sketch 1");

    sketch_0.setFeatureBaseCreationVersion(19);

    sketch_0.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(sketch_0);
  }

  private void execute10() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    //Exporting Extrude merge feature :Extrude 1

    ExtrusionMerge extrusionMerge_0 = 
      (ExtrusionMerge) cadModel_0.getFeatureManager().createExtrusionMerge();

    extrusionMerge_0.setExtrusionMergeOption(0);

    extrusionMerge_0.setInteractingBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_0.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));

    extrusionMerge_0.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));

    extrusionMerge_0.setPostOption(1);

    extrusionMerge_0.setInteractingSelectedBodies(false);

    extrusionMerge_0.setExtrudedBodyTypeOption(0);

    extrusionMerge_0.setExtrusionOption(0);

    extrusionMerge_0.setDistanceOption(0);

    extrusionMerge_0.setDirectionOption(0);

    extrusionMerge_0.setCoordinateSystemOption(0);

    extrusionMerge_0.setDraftOption(0);

    CanonicalReferenceCoordinateSystem canonicalReferenceCoordinateSystem_0 = 
      ((CanonicalReferenceCoordinateSystem) cadModel_0.getFeature("Lab Coordinate System"));

    extrusionMerge_0.setReferenceCoordinateSystem(canonicalReferenceCoordinateSystem_0);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    extrusionMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.setFeatureInputType(0);

    extrusionMerge_0.getDistance().setValue(0.001);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    extrusionMerge_0.getDistance().setUnits(units_0);

    extrusionMerge_0.getDraftAngle().setValue(10.0);

    Units units_9 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    extrusionMerge_0.getDraftAngle().setUnits(units_9);

    extrusionMerge_0.getDistanceAsymmetric().setValue(0.1);

    extrusionMerge_0.getDistanceAsymmetric().setUnits(units_0);

    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_0.getOffsetDistance().setValue(0.1);

    extrusionMerge_0.getOffsetDistance().setUnits(units_0);

    Sketch sketch_0 = 
      ((Sketch) cadModel_0.getFeature("Sketch 1"));

    extrusionMerge_0.setSketch(sketch_0);

    extrusionMerge_0.setPresentationName("Extrude 1");

    extrusionMerge_0.setFeatureBaseCreationVersion(19);

    extrusionMerge_0.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(extrusionMerge_0);
  }

  private void execute11() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    NameAttributeFeature nameAttributeFeature_0 = 
      (NameAttributeFeature) cadModel_0.getFeatureManager().createNameAttributeFeature(true);

    nameAttributeFeature_0.setPresentationName("Rename 1");

    nameAttributeFeature_0.setIsIncrementalRenaming(false);

    ExtrusionMerge extrusionMerge_0 = 
      ((ExtrusionMerge) cadModel_0.getFeature("Extrude 1"));

    Sketch sketch_0 = 
      ((Sketch) cadModel_0.getFeature("Sketch 1"));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));

    Face face_0 = 
      ((Face) extrusionMerge_0.getEndCapFace(lineSketchPrimitive_0));

    nameAttributeFeature_0.renameTopology2(face_0, "max_z", 135, 206, 250, 255, true, 1.0, false);

    Face face_1 = 
      ((Face) extrusionMerge_0.getStartCapFace(lineSketchPrimitive_0));

    nameAttributeFeature_0.renameTopology2(face_1, "min_z", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 2"));

    Face face_2 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_1,"True"));

    nameAttributeFeature_0.renameTopology2(face_2, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_2 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 3"));

    Face face_3 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_2,"True"));

    nameAttributeFeature_0.renameTopology2(face_3, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_3 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 4"));

    Face face_4 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_3,"True"));

    nameAttributeFeature_0.renameTopology2(face_4, "walls", 135, 206, 250, 255, true, 1.0, false);

    Face face_5 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_0,"True"));

    nameAttributeFeature_0.renameTopology2(face_5, "bottom", 135, 206, 250, 255, true, 1.0, false);

    nameAttributeFeature_0.setPresentationName("Rename 1");

    nameAttributeFeature_0.setFeatureBaseCreationVersion(19);

    nameAttributeFeature_0.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(nameAttributeFeature_0);
  }

  private void execute12() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CadModelDisplayOptions cadModelDisplayOptions_0 = 
      cadModel_0.getDisplayOptions();

    cadModelDisplayOptions_0.setSolidsVisibility(true);

    cadModelDisplayOptions_0.setSheetsVisibility(true);

    cadModelDisplayOptions_0.setFacetedSolidsVisibility(true);

    cadModelDisplayOptions_0.setFacetedSheetsVisibility(true);

    cadModelDisplayOptions_0.setEdgesVisibility(false);

    cadModelDisplayOptions_0.setFreeEdgesVisibility(true);

    cadModelDisplayOptions_0.setVerticesVisibility(false);

    cadModelDisplayOptions_0.setNamedEdgesVisibility(false);

    cadModelDisplayOptions_0.setNamedFacesVisibility(false);

    cadModelDisplayOptions_0.setColorOption(0);

    cadModelDisplayOptions_0.setSketchVisibility(true);

    cadModelDisplayOptions_0.setReferenceAxisVisibility(true);

    cadModelDisplayOptions_0.setReferenceCsysVisibility(true);

    cadModelDisplayOptions_0.setReferencePlaneVisibility(true);

    cadModelDisplayOptions_0.setReferencePointVisibility(true);
  }
}
