// Simcenter STAR-CCM+ macro: actual_geom.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;

public class actual_geom extends StarMacro {

  public void execute() {
    //Export Creation of Cad Model
    execute0();
    //Exporting canonical feature : XY
    execute1();
    //Exporting canonical feature : YZ
    execute2();
    //Exporting canonical feature : ZX
    execute3();
    //Exporting canonical feature : Global Origin
    execute4();
    //Exporting canonical feature : Lab Coordinate System
    execute5();
    //Exporting Sketch 1
    execute6();
    //Exporting Primitives of Sketch :Sketch 1
    execute7();
    //Exporting Constraints of Sketch :Sketch 1
    execute8();
    //Exporting Constraints of Sketch :Sketch 1
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

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).createSolidModel();

    cadModel_0.setModelCreationVersion(33);
  }

  private void execute1() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("XY");
  }

  private void execute2() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_1 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("YZ");
  }

  private void execute3() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_2 = 
      (CanonicalSketchPlane) cadModel_0.getFeature("ZX");
  }

  private void execute4() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferencePoint canonicalReferencePoint_0 = 
      (CanonicalReferencePoint) cadModel_0.getFeature("Global Origin");
  }

  private void execute5() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferenceCoordinateSystem canonicalReferenceCoordinateSystem_0 = 
      (CanonicalReferenceCoordinateSystem) cadModel_0.getFeature("Lab Coordinate System");
  }

  private void execute6() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_0.getFeature("XY"));

    Sketch sketch_0 = 
      (Sketch) cadModel_0.getFeatureManager().createSketch(canonicalSketchPlane_0);

    sketch_0.setPresentationName("Sketch 1");

    PointSketchPrimitive pointSketchPrimitive_5 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(19, "Point 3", new DoubleVector(new double[] {0.07000000000000002, 0.12840000000000001}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_4 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(20, "Point 4", new DoubleVector(new double[] {0.0, 0.12840000000000001}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_0 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(26, "Point 5", new DoubleVector(new double[] {0.0615, 0.12840000000000004}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_1 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(28, "Point 6", new DoubleVector(new double[] {0.008500000000000006, 0.12840000000000001}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_2 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(30, "Point 7", new DoubleVector(new double[] {0.0, 0.1199}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_3 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(32, "Point 8", new DoubleVector(new double[] {0.07000000000000003, 0.1199}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_11 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(69, "Point 10", new DoubleVector(new double[] {0.0, 0.0085}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_8 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(70, "Point 11", new DoubleVector(new double[] {0.0, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_9 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(75, "Point 12", new DoubleVector(new double[] {0.0085, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_16 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(88, "Point 14", new DoubleVector(new double[] {0.061500000000000034, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_10 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(89, "Point 15", new DoubleVector(new double[] {0.07000000000000003, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_12 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(94, "Point 16", new DoubleVector(new double[] {0.07000000000000003, 0.0085}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_13 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(110, "Point 1", new DoubleVector(new double[] {0.012500000000000022, 0.12840000000000001}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_14 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(115, "Point 2", new DoubleVector(new double[] {0.060000000000000005, 0.12840000000000004}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_15 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(123, "Point 9", new DoubleVector(new double[] {0.0615, 0.1284}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_28 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(131, "Point 13", new DoubleVector(new double[] {0.012499999999999997, 0.25}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_17 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(152, "Point 19", new DoubleVector(new double[] {0.014667878888070592, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_18 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(160, "Point 20", new DoubleVector(new double[] {0.05533212111192944, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_21 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(190, "Point 25", new DoubleVector(new double[] {0.035, -4.3143296180150206E-5}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_22 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(199, "Point 26", new DoubleVector(new double[] {0.035, 0.00415685670381985}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_23 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(212, "Point 28", new DoubleVector(new double[] {0.022000029266659782, 0.004184441705138412}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_24 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(220, "Point 29", new DoubleVector(new double[] {0.047999970733340225, 0.0041292717025012875}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_26 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(228, "Point 30", new DoubleVector(new double[] {0.021991117209374307, -1.5539384118093413E-5}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_25 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(236, "Point 31", new DoubleVector(new double[] {0.047991175663097914, -1.557695736700143E-5}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_27 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(256, "Point 32", new DoubleVector(new double[] {0.0169618032106331, 0.15515843580184327}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_29 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(264, "Point 17", new DoubleVector(new double[] {0.03712100000000039, 0.25}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_30 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(272, "Point 18", new DoubleVector(new double[] {0.03712100000000039, 0.25}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_31 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(280, "Point 21", new DoubleVector(new double[] {0.012499999999999985, 0.15515843580184327}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_32 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(294, "Point 22", new DoubleVector(new double[] {0.026500019135892935, 0.004174893050835833}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_35 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(302, "Point 23", new DoubleVector(new double[] {0.04349998086410707, 0.004138820356803867}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_33 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(313, "Point 24", new DoubleVector(new double[] {0.026476253596295407, -0.007025081734811128}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_34 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(318, "Point 27", new DoubleVector(new double[] {0.04347621532450954, -0.007061154428843091}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_37 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(348, "Point 33", new DoubleVector(new double[] {0.008500000000000002, 0.0085}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_38 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(354, "Point 34", new DoubleVector(new double[] {0.014677051776352748, 0.008499995050476243}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_39 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(360, "Point 35", new DoubleVector(new double[] {0.05529021333697295, 0.008499896689866188}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_40 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(366, "Point 36", new DoubleVector(new double[] {0.061500000000000034, 0.008499999999999999}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_41 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(388, "Point 38", new DoubleVector(new double[] {0.06150000000000003, 0.11990000000000003}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_42 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(394, "Point 39", new DoubleVector(new double[] {0.008499999999999987, 0.1199}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_43 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(396, "Point 40", new DoubleVector(new double[] {0.019772000000000005, 0.1563550363687981}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_36 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(397, "Point 41", new DoubleVector(new double[] {0.01732663099816552, 0.15687481559584252}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_44 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(398, "Point 42", new DoubleVector(new double[] {0.018451988141966975, 0.15423193281629483}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_45 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(399, "Point 43", new DoubleVector(new double[] {0.0169618032106331, 0.15515843580184327}), true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_3 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(36, 20, 28, new DoubleVector(new double[] {1.0, 3.0616169978683866E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_3, "Line 5", 36, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(43, 26, 19, new DoubleVector(new double[] {1.0, 3.0616169978683826E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_4, "Line 6", 43, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_5 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(47, 20, 30, new DoubleVector(new double[] {1.2246467991473532E-16, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_5, "Line 7", 47, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_6 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(54, 19, 32, new DoubleVector(new double[] {1.2246467991473532E-16, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_6, "Line 8", 54, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(71, 69, 70, new DoubleVector(new double[] {1.2246467991473532E-16, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_9, "Line 10", 71, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_10 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(76, 70, 75, new DoubleVector(new double[] {1.0, 0.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_10, "Line 11", 76, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_12 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(90, 88, 89, new DoubleVector(new double[] {1.0, 0.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_12, "Line 13", 90, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_13 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(95, 89, 94, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_13, "Line 14", 95, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_14 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(102, 30, 69, new DoubleVector(new double[] {0.0, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_14, "Line 15", 102, true, false, false, true, true, false, 0.0);
  }

  private void execute7() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    Sketch sketch_0 = 
      (Sketch) cadModel_0.getFeature("Sketch 1");

    LineSketchPrimitive lineSketchPrimitive_15 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(106, 32, 94, new DoubleVector(new double[] {1.2246467991473532E-16, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_15, "Line 16", 106, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_16 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(111, 28, 110, new DoubleVector(new double[] {1.0000000000000002, 3.0616169978683866E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_16, "Line 1", 111, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_17 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(116, 110, 115, new DoubleVector(new double[] {1.0000000000000002, 3.0616169978683866E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_17, "Line 2", 116, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_18 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(124, 115, 123, new DoubleVector(new double[] {1.0, 3.0616169978683866E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_18, "Line 3", 124, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_19 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(132, 110, 131, new DoubleVector(new double[] {-1.0085601604516325E-16, 1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_19, "Line 4", 132, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_22 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(153, 75, 152, new DoubleVector(new double[] {1.0, 0.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_22, "Line 17", 153, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_23 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(161, 88, 160, new DoubleVector(new double[] {-1.0, -1.2246467991473532E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_23, "Line 9", 161, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_29 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(200, 190, 199, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_29, "Line 23", 200, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_31 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(213, 199, 212, new DoubleVector(new double[] {-0.9999977487184785, 0.0021219231783509984}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_31, "Line 25", 213, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_32 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(221, 199, 220, new DoubleVector(new double[] {0.9999977487184784, -0.0021219231783509707}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_32, "Line 26", 221, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_33 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(229, 212, 228, new DoubleVector(new double[] {-0.002121923178350804, -0.9999977487184785}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_33, "Line 27", 229, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_34 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(237, 220, 236, new DoubleVector(new double[] {-0.0021219231783509707, -0.9999977487184785}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_34, "Line 28", 237, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_27 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(191, 152, 190, new DoubleVector(new double[] {0.9999977487184787, -0.0021219231783510995}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_27, "Line 21", 191, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_28 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(195, 190, 160, new DoubleVector(new double[] {0.9999977487184785, 0.0021219231783510617}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_28, "Line 22", 195, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_35 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(248, 152, 228, new DoubleVector(new double[] {0.9999977487184785, -0.002121923178351337}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_35, "Line 29", 248, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_36 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(252, 236, 160, new DoubleVector(new double[] {0.9999977487184785, 0.002121923178350897}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_36, "Line 30", 252, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_37 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(257, 115, 398, new DoubleVector(new double[] {-0.8492414210013178, 0.528004743213224}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_37, "Line 31", 257, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_38 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(265, 397, 264, new DoubleVector(new double[] {0.20791169081776356, 0.9781476007338048}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_38, "Line 12", 265, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_39 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(273, 131, 272, new DoubleVector(new double[] {1.0, 0.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_39, "Line 18", 273, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_40 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(281, 256, 280, new DoubleVector(new double[] {-0.9999999999999998, -4.2862637970157336E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_40, "Line 19", 281, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_41 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(295, 212, 294, new DoubleVector(new double[] {0.9999977487184784, -0.0021219231783508427}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_41, "Line 20", 295, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_42 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(303, 220, 302, new DoubleVector(new double[] {-0.9999977487184784, 0.002121923178350921}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_42, "Line 24", 303, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_43 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(314, 294, 313, new DoubleVector(new double[] {-0.002121923178350693, -0.9999977487184787}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_43, "Line 32", 314, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_44 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(319, 313, 318, new DoubleVector(new double[] {0.9999977487184787, -0.0021219231783507994}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_44, "Line 33", 319, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_45 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(323, 318, 302, new DoubleVector(new double[] {0.002121923178350915, 0.9999977487184785}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_45, "Line 34", 323, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_0 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(349, 348, 69, 75);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_0, "CircularArc 1", 349, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_1 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(355, 354, 152, 212);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_1, "CircularArc 2", 355, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_2 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(361, 360, 220, 160);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_2, "CircularArc 3", 361, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_3 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(367, 366, 88, 94);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_3, "CircularArc 4", 367, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_6 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(389, 388, 32, 123);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_6, "CircularArc 6", 389, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_7 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(395, 394, 28, 30);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_7, "CircularArc 7", 395, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_8 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(400, 396, 397, 398);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_8, "CircularArc 8", 400, true, false, false, true, true, true, 10.0);
  }

  private void execute8() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    Sketch sketch_0 = 
      (Sketch) cadModel_0.getFeature("Sketch 1");

    LineSketchPrimitive lineSketchPrimitive_3 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 5"));

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_3);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 6"));

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_4);

    LineSketchPrimitive lineSketchPrimitive_5 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 7"));

    sketch_0.addVerticalConstraint(lineSketchPrimitive_5);

    LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 8"));

    sketch_0.addVerticalConstraint(lineSketchPrimitive_6);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_3, lineSketchPrimitive_5);

    sketch_0.addEqualLengthConstraint(lineSketchPrimitive_3, lineSketchPrimitive_5);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_4, lineSketchPrimitive_6);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 10"));

    sketch_0.addVerticalConstraint(lineSketchPrimitive_9);

    LineSketchPrimitive lineSketchPrimitive_10 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 11"));

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_10);

    LineSketchPrimitive lineSketchPrimitive_12 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 13"));

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_12);

    LineSketchPrimitive lineSketchPrimitive_13 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 14"));

    sketch_0.addVerticalConstraint(lineSketchPrimitive_13);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_6, lineSketchPrimitive_13);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_10, lineSketchPrimitive_12);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_3, lineSketchPrimitive_4);

    sketch_0.addEqualLengthConstraint(lineSketchPrimitive_13, lineSketchPrimitive_12);

    PointSketchPrimitive pointSketchPrimitive_8 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 11"));

    sketch_0.addFixationConstraint(pointSketchPrimitive_8);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    LengthDimension lengthDimension_1 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_9, 0.0085, units_0);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_5, lineSketchPrimitive_9);

    LengthDimension lengthDimension_2 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_5, 0.0085, units_0);

    LineSketchPrimitive lineSketchPrimitive_15 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 16"));

    LineSketchPrimitive lineSketchPrimitive_14 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 15"));

    sketch_0.addEqualLengthConstraint(lineSketchPrimitive_15, lineSketchPrimitive_14);

    LineSketchPrimitive lineSketchPrimitive_16 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));

    sketch_0.addCollinearConstraint(lineSketchPrimitive_16, lineSketchPrimitive_3);

    LineSketchPrimitive lineSketchPrimitive_17 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 2"));

    sketch_0.addCollinearConstraint(lineSketchPrimitive_17, lineSketchPrimitive_16);

    LineSketchPrimitive lineSketchPrimitive_18 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 3"));

    LengthDimension lengthDimension_3 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_18, 0.0015, units_0);

    LengthDimension lengthDimension_4 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_17, 0.0475, units_0);

    LengthDimension lengthDimension_5 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_16, 0.004, units_0);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_18, lineSketchPrimitive_17);

    PointSketchPrimitive pointSketchPrimitive_15 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 9"));

    PointSketchPrimitive pointSketchPrimitive_0 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 5"));

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_15, pointSketchPrimitive_0);

    LineSketchPrimitive lineSketchPrimitive_19 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 4"));

    LengthDimension lengthDimension_6 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_19, 0.1216, units_0);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_19, lineSketchPrimitive_17);

    LineSketchPrimitive lineSketchPrimitive_22 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 17"));

    sketch_0.addCollinearConstraint(lineSketchPrimitive_22, lineSketchPrimitive_10);

    LineSketchPrimitive lineSketchPrimitive_23 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 9"));

    sketch_0.addCollinearConstraint(lineSketchPrimitive_23, lineSketchPrimitive_12);

    LengthDimension lengthDimension_7 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_23, 0.006167878888070591, units_0);

    LengthDimension lengthDimension_8 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_22, 0.006167878888070591, units_0);

    LineSketchPrimitive lineSketchPrimitive_29 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 23"));

    sketch_0.addVerticalConstraint(lineSketchPrimitive_29);

    LengthDimension lengthDimension_9 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_29, 0.0042, units_0);

    LineSketchPrimitive lineSketchPrimitive_31 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 25"));

    LineSketchPrimitive lineSketchPrimitive_32 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 26"));

    sketch_0.addCollinearConstraint(lineSketchPrimitive_31, lineSketchPrimitive_32);

    sketch_0.addEqualLengthConstraint(lineSketchPrimitive_32, lineSketchPrimitive_31);

    LengthDimension lengthDimension_10 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_31, 0.013, units_0);

    LineSketchPrimitive lineSketchPrimitive_34 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 28"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_34, lineSketchPrimitive_32);

    LineSketchPrimitive lineSketchPrimitive_33 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 27"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_31, lineSketchPrimitive_33);

    LineSketchPrimitive lineSketchPrimitive_28 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 22"));

    LineSketchPrimitive lineSketchPrimitive_27 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 21"));

    sketch_0.addEqualLengthConstraint(lineSketchPrimitive_28, lineSketchPrimitive_27);

    sketch_0.addParallelConstraint(lineSketchPrimitive_31, lineSketchPrimitive_27);

    PointSketchPrimitive pointSketchPrimitive_25 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 31"));

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_25, lineSketchPrimitive_28);

    PointSketchPrimitive pointSketchPrimitive_26 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 30"));

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_26, lineSketchPrimitive_27);

    LengthDimension lengthDimension_12 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_14, 0.1114, units_0);

    LineSketchPrimitive lineSketchPrimitive_39 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 18"));

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_39);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_39, lineSketchPrimitive_19);

    PointSketchPrimitive pointSketchPrimitive_29 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 17"));

    PointSketchPrimitive pointSketchPrimitive_30 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 18"));

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_29, pointSketchPrimitive_30);

    PointSketchPrimitive pointSketchPrimitive_31 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 21"));

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_31, lineSketchPrimitive_19);

    LineSketchPrimitive lineSketchPrimitive_40 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 19"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_40, lineSketchPrimitive_19);

    LengthDimension lengthDimension_14 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_40, 0.004461803210633112, units_0);
  }

  private void execute9() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_0 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    Sketch sketch_0 = 
      (Sketch) cadModel_0.getFeature("Sketch 1");

    LineSketchPrimitive lineSketchPrimitive_41 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 20"));

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    LengthDimension lengthDimension_15 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_41, 0.0045000000000000005, units_0);

    LineSketchPrimitive lineSketchPrimitive_42 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 24"));

    sketch_0.addEqualLengthConstraint(lineSketchPrimitive_41, lineSketchPrimitive_42);

    LineSketchPrimitive lineSketchPrimitive_45 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 34"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_45, lineSketchPrimitive_42);

    LineSketchPrimitive lineSketchPrimitive_44 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 33"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_44, lineSketchPrimitive_45);

    LineSketchPrimitive lineSketchPrimitive_43 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 32"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_43, lineSketchPrimitive_44);

    LengthDimension lengthDimension_16 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_45, 0.0112, units_0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_1 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 2"));

    RadiusDimension radiusDimension_0 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_1, 0.0085, units_0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_0 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 1"));

    RadiusDimension radiusDimension_1 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_0, 0.0085, units_0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_3 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 4"));

    RadiusDimension radiusDimension_2 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_3, 0.0085, units_0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_2 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 3"));

    RadiusDimension radiusDimension_3 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_2, 0.0085, units_0);

    LineSketchPrimitive lineSketchPrimitive_32 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 26"));

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_45, lineSketchPrimitive_32);

    LineSketchPrimitive lineSketchPrimitive_31 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 25"));

    sketch_0.addCollinearConstraint(lineSketchPrimitive_41, lineSketchPrimitive_31);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_42, lineSketchPrimitive_32);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 10"));

    sketch_0.addTangentConstraint(circularArcSketchPrimitive_0, lineSketchPrimitive_9);

    LineSketchPrimitive lineSketchPrimitive_13 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 14"));

    sketch_0.addTangentConstraint(circularArcSketchPrimitive_3, lineSketchPrimitive_13);

    CircularArcSketchPrimitive circularArcSketchPrimitive_6 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 6"));

    RadiusDimension radiusDimension_5 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_6, 0.0085, units_0);

    LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 8"));

    sketch_0.addTangentConstraint(circularArcSketchPrimitive_6, lineSketchPrimitive_6);

    CircularArcSketchPrimitive circularArcSketchPrimitive_7 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 7"));

    RadiusDimension radiusDimension_8 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_7, 0.0085, units_0);

    PointSketchPrimitive pointSketchPrimitive_29 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 17"));

    PointSketchPrimitive pointSketchPrimitive_27 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 32"));

    DistanceDimension distanceDimension_1 = 
      (DistanceDimension) sketch_0.addDistanceDimension(pointSketchPrimitive_29, pointSketchPrimitive_27, 0.09696038116027351, units_0);

    PointSketchPrimitive pointSketchPrimitive_14 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 2"));

    DistanceDimension distanceDimension_2 = 
      (DistanceDimension) sketch_0.addDistanceDimension(pointSketchPrimitive_14, pointSketchPrimitive_27, 0.05067840042307609, units_0);

    LineSketchPrimitive lineSketchPrimitive_38 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 12"));

    PointSketchPrimitive pointSketchPrimitive_45 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 43"));

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_38, pointSketchPrimitive_45);

    LineSketchPrimitive lineSketchPrimitive_37 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 31"));

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_37, pointSketchPrimitive_45);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_27, pointSketchPrimitive_45);

    CircularArcSketchPrimitive circularArcSketchPrimitive_8 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 8"));

    sketch_0.addTangentConstraint(lineSketchPrimitive_38, circularArcSketchPrimitive_8);

    sketch_0.addTangentConstraint(lineSketchPrimitive_37, circularArcSketchPrimitive_8);

    RadiusDimension radiusDimension_9 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_8, 0.0025000000000000066, units_0);

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

    extrusionMerge_0.getDistance().setValue(0.01);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    extrusionMerge_0.getDistance().setUnits(units_0);

    extrusionMerge_0.getDraftAngle().setValue(10.0);

    Units units_2 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    extrusionMerge_0.getDraftAngle().setUnits(units_2);

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

    LineSketchPrimitive lineSketchPrimitive_44 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 33"));

    Face face_2 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_44,"True"));

    nameAttributeFeature_0.renameTopology2(face_2, "inlet", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_39 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 18"));

    Face face_3 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_39,"True"));

    nameAttributeFeature_0.renameTopology2(face_3, "outlet", 135, 206, 250, 255, true, 1.0, false);

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
