// Simcenter STAR-CCM+ macro: geometry.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;

public class geometry extends StarMacro {

  public void execute() {
    //Exporting  creation of Tags of Simulation
    execute0();
    //Export Creation of Cad Model
    execute1();
    //Exporting design parameters of model
    execute2();
    //Exporting canonical feature : XY
    execute3();
    //Exporting canonical feature : YZ
    execute4();
    //Exporting canonical feature : ZX
    execute5();
    //Exporting canonical feature : Global Origin
    execute6();
    //Exporting canonical feature : Lab Coordinate System
    execute7();
    //Exporting Sketch 1
    execute8();
    //Exporting Extrude 1
    execute9();
    //Exporting Rename 1
    execute10();
    //Exporting Cad Display options of model
    execute11();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.get(TagManager.class).createNewUserTag("Region (2D Mesh)");

    simulation_0.get(TagManager.class).createNewUserTag("Boundary (2D Mesh)");
  }

  private void execute1() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).createSolidModel();

    cadModel_0.setModelCreationVersion(33);
  }

  private void execute2() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    UserDesignParameter scalarQuantityDesignParameter_0 = 
      (UserDesignParameter) cadModel_0.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "dia_chamber");

    UserDesignParameter scalarQuantityDesignParameter_3 = 
      (UserDesignParameter) cadModel_0.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "dia_inlet");

    UserDesignParameter scalarQuantityDesignParameter_4 = 
      (UserDesignParameter) cadModel_0.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "height_chamber");

    UserDesignParameter scalarQuantityDesignParameter_5 = 
      (UserDesignParameter) cadModel_0.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "dist2center_inlet");

    scalarQuantityDesignParameter_0.getQuantity().setValue(0.1);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    scalarQuantityDesignParameter_0.getQuantity().setUnits(units_0);

    scalarQuantityDesignParameter_3.getQuantity().setValue(0.01);

    scalarQuantityDesignParameter_3.getQuantity().setUnits(units_0);

    scalarQuantityDesignParameter_4.getQuantity().setValue(0.1);

    scalarQuantityDesignParameter_4.getQuantity().setUnits(units_0);

    scalarQuantityDesignParameter_5.getQuantity().setValue(0.07);

    scalarQuantityDesignParameter_5.getQuantity().setUnits(units_0);
  }

  private void execute3() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_1 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("XY");
  }

  private void execute4() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_2 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("YZ");
  }

  private void execute5() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("ZX");
  }

  private void execute6() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferencePoint canonicalReferencePoint_0 = 
      (CanonicalReferencePoint) cadModel_0.getFeature("Global Origin");
  }

  private void execute7() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferenceCoordinateSystem canonicalReferenceCoordinateSystem_0 = 
      (CanonicalReferenceCoordinateSystem) cadModel_0.getFeature("Lab Coordinate System");
  }

  private void execute8() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_0.getFeature("ZX"));

    Sketch sketch_1 = 
      (Sketch) cadModel_0.getFeatureManager().createSketch(canonicalSketchPlane_0);

    sketch_1.setPresentationName("Sketch 1");

    PointSketchPrimitive pointSketchPrimitive_4 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(9, "Point 1", new DoubleVector(new double[] {0.0, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_7 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(10, "Point 2", new DoubleVector(new double[] {0.0, 0.1}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_1 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(11, "Point 3", new DoubleVector(new double[] {0.1, 0.1}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_0 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(12, "Point 4", new DoubleVector(new double[] {0.1, 0.0}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_2 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(20, "Point 5", new DoubleVector(new double[] {0.1, 0.06000000000000001}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_3 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(28, "Point 6", new DoubleVector(new double[] {0.1, 0.08}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_6 = 
      (PointSketchPrimitive) sketch_1.addPointToSketch(57, "Point 7", new DoubleVector(new double[] {0.1, 0.07}), true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(13, 9, 10, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_4, "Line 1", 13, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_11 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(14, 10, 11, new DoubleVector(new double[] {1.0, 0.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_11, "Line 2", 14, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_12 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(16, 12, 9, new DoubleVector(new double[] {-1.0, 0.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_12, "Line 4", 16, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(21, 12, 20, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_1, "Line 5", 21, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_2 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(29, 28, 11, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_2, "Line 6", 29, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(61, 20, 57, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_9, "Line 3", 61, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_10 = 
      (LineSketchPrimitive) sketch_1.addLineToSketch(65, 57, 28, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_1.addWirePrimitveProperties(lineSketchPrimitive_10, "Line 7", 65, true, false, false, true, true, false, 0.0);

    sketch_1.addPerpendicularConstraint(lineSketchPrimitive_4, lineSketchPrimitive_11);

    sketch_1.addPerpendicularConstraint(lineSketchPrimitive_4, lineSketchPrimitive_12);

    sketch_1.addVerticalConstraint(lineSketchPrimitive_1);

    sketch_1.addVerticalConstraint(lineSketchPrimitive_2);

    sketch_1.addFixationConstraint(pointSketchPrimitive_4);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    LengthDimension lengthDimension_0 = 
      (LengthDimension) sketch_1.addLengthDimension(lineSketchPrimitive_4, 0.1, units_0);

    lengthDimension_0.getLength().linkQuantityToExportedUserDesignParameter("dia_chamber");

    sketch_1.addVerticalConstraint(lineSketchPrimitive_9);

    sketch_1.addVerticalConstraint(lineSketchPrimitive_10);

    sketch_1.addEqualLengthConstraint(lineSketchPrimitive_9, lineSketchPrimitive_10);

    LengthDimension lengthDimension_2 = 
      (LengthDimension) sketch_1.addLengthDimension(lineSketchPrimitive_10, 0.01, units_0);

    lengthDimension_2.getLength().linkQuantityToExportedUserDesignParameter("dia_inlet");

    LengthDimension lengthDimension_3 = 
      (LengthDimension) sketch_1.addLengthDimension(lineSketchPrimitive_11, 0.1, units_0);

    lengthDimension_3.getLength().linkQuantityToExportedUserDesignParameter("height_chamber");

    DistanceDimension distanceDimension_1 = 
      (DistanceDimension) sketch_1.addDistanceDimension(pointSketchPrimitive_6, pointSketchPrimitive_0, 0.07, units_0);

    distanceDimension_1.getDistance().linkQuantityToExportedUserDesignParameter("dist2center_inlet");

    sketch_1.setPresentationName("Sketch 1");

    sketch_1.setFeatureBaseCreationVersion(19);

    sketch_1.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(sketch_1);
  }

  private void execute9() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    //Exporting Extrude merge feature :Extrude 1

    ExtrusionMerge extrusionMerge_1 = 
      (ExtrusionMerge) cadModel_0.getFeatureManager().createExtrusionMerge();

    extrusionMerge_1.setExtrusionMergeOption(0);

    extrusionMerge_1.setInteractingBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_1.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));

    extrusionMerge_1.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));

    extrusionMerge_1.setPostOption(0);

    extrusionMerge_1.setInteractingSelectedBodies(false);

    extrusionMerge_1.setExtrudedBodyTypeOption(0);

    extrusionMerge_1.setExtrusionOption(0);

    extrusionMerge_1.setDistanceOption(0);

    extrusionMerge_1.setDirectionOption(0);

    extrusionMerge_1.setCoordinateSystemOption(0);

    extrusionMerge_1.setDraftOption(0);

    CanonicalReferenceCoordinateSystem canonicalReferenceCoordinateSystem_0 = 
      ((CanonicalReferenceCoordinateSystem) cadModel_0.getFeature("Lab Coordinate System"));

    extrusionMerge_1.setReferenceCoordinateSystem(canonicalReferenceCoordinateSystem_0);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    extrusionMerge_1.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_1.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_1.setFeatureInputType(0);

    extrusionMerge_1.getDistance().setValue(0.01);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    extrusionMerge_1.getDistance().setUnits(units_0);

    extrusionMerge_1.getDraftAngle().setValue(10.0);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    extrusionMerge_1.getDraftAngle().setUnits(units_1);

    extrusionMerge_1.getDistanceAsymmetric().setValue(0.1);

    extrusionMerge_1.getDistanceAsymmetric().setUnits(units_0);

    extrusionMerge_1.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_1.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_1.getOffsetDistance().setValue(0.1);

    extrusionMerge_1.getOffsetDistance().setUnits(units_0);

    Sketch sketch_1 = 
      ((Sketch) cadModel_0.getFeature("Sketch 1"));

    extrusionMerge_1.setSketch(sketch_1);

    extrusionMerge_1.setPresentationName("Extrude 1");

    extrusionMerge_1.setFeatureBaseCreationVersion(19);

    extrusionMerge_1.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(extrusionMerge_1);
  }

  private void execute10() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    NameAttributeFeature nameAttributeFeature_0 = 
      (NameAttributeFeature) cadModel_0.getFeatureManager().createNameAttributeFeature(true);

    nameAttributeFeature_0.setPresentationName("Rename 1");

    nameAttributeFeature_0.setIsIncrementalRenaming(false);

    ExtrusionMerge extrusionMerge_1 = 
      ((ExtrusionMerge) cadModel_0.getFeature("Extrude 1"));

    Sketch sketch_1 = 
      ((Sketch) cadModel_0.getFeature("Sketch 1"));

    LineSketchPrimitive lineSketchPrimitive_12 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 4"));

    Face face_7 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_12,"True"));

    nameAttributeFeature_0.renameTopology2(face_7, "min_x", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 1"));

    Face face_8 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_4,"True"));

    nameAttributeFeature_0.renameTopology2(face_8, "min_y", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_11 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 2"));

    Face face_9 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_11,"True"));

    nameAttributeFeature_0.renameTopology2(face_9, "max_x", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 5"));

    Face face_10 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_1,"True"));

    nameAttributeFeature_0.renameTopology2(face_10, "max_y", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_2 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 6"));

    Face face_11 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_2,"True"));

    nameAttributeFeature_0.renameTopology2(face_11, "max_y", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 3"));

    Face face_12 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_9,"True"));

    nameAttributeFeature_0.renameTopology2(face_12, "inlet", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_10 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 7"));

    Face face_13 = 
      ((Face) extrusionMerge_1.getSideFace(lineSketchPrimitive_10,"True"));

    nameAttributeFeature_0.renameTopology2(face_13, "inlet", 135, 206, 250, 255, true, 1.0, false);

    nameAttributeFeature_0.setPresentationName("Rename 1");

    nameAttributeFeature_0.setFeatureBaseCreationVersion(19);

    nameAttributeFeature_0.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(nameAttributeFeature_0);
  }

  private void execute11() {

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
