// Simcenter STAR-CCM+ macro: newgeom.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;

public class newgeom extends StarMacro {

  public void execute() {
    //Exporting  creation of Global parameters of Simulation
    execute0();
    //Exporting values and units of Global parameters of Simulation
    execute1();
    //Exporting  creation of Tags of Simulation
    execute2();
    //Export Creation of Cad Model
    execute3();
    //Exporting design parameters of model
    execute4();
    //Exporting canonical feature : XY
    execute5();
    //Exporting canonical feature : YZ
    execute6();
    //Exporting canonical feature : ZX
    execute7();
    //Exporting canonical feature : Global Origin
    execute8();
    //Exporting canonical feature : Lab Coordinate System
    execute9();
    //Exporting Sketch 1
    execute10();
    //Exporting Extrude 1
    execute11();
    //Exporting Rename 1
    execute12();
    //Exporting Cad Display options of model
    execute13();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "des_c2c");

    ScalarGlobalParameter scalarGlobalParameter_0 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_c2c"));

    scalarGlobalParameter_0.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "des_dia_inlet");

    ScalarGlobalParameter scalarGlobalParameter_1 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_dia_inlet"));

    scalarGlobalParameter_1.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "des_height_chamber");

    ScalarGlobalParameter scalarGlobalParameter_2 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_height_chamber"));

    scalarGlobalParameter_2.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "des_dia_chamber");

    ScalarGlobalParameter scalarGlobalParameter_3 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_dia_chamber"));

    scalarGlobalParameter_3.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "des_height_inlet");

    ScalarGlobalParameter scalarGlobalParameter_4 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_height_inlet"));

    scalarGlobalParameter_4.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "delta_h");

    ScalarGlobalParameter scalarGlobalParameter_5 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("delta_h"));

    scalarGlobalParameter_5.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "amplitude");

    ScalarGlobalParameter scalarGlobalParameter_6 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("amplitude"));

    scalarGlobalParameter_6.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "high_rho");

    ScalarGlobalParameter scalarGlobalParameter_7 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("high_rho"));

    scalarGlobalParameter_7.setDimensions(Dimensions.Builder().mass(1).volume(-1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "low_rho");

    ScalarGlobalParameter scalarGlobalParameter_8 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("low_rho"));

    scalarGlobalParameter_8.setDimensions(Dimensions.Builder().mass(1).volume(-1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "surface_tension");

    ScalarGlobalParameter scalarGlobalParameter_9 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("surface_tension"));

    scalarGlobalParameter_9.setDimensions(Dimensions.Builder().length(-1).force(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "time_end");

    ScalarGlobalParameter scalarGlobalParameter_10 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("time_end"));

    scalarGlobalParameter_10.setDimensions(Dimensions.Builder().time(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "timestep");

    ScalarGlobalParameter scalarGlobalParameter_11 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("timestep"));

    scalarGlobalParameter_11.setDimensions(Dimensions.Builder().time(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "timestep_end");

    ScalarGlobalParameter scalarGlobalParameter_12 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("timestep_end"));

    scalarGlobalParameter_12.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "timestep_freq");

    ScalarGlobalParameter scalarGlobalParameter_13 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("timestep_freq"));

    scalarGlobalParameter_13.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "viscosity_high");

    ScalarGlobalParameter scalarGlobalParameter_14 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("viscosity_high"));

    scalarGlobalParameter_14.setDimensions(Dimensions.Builder().time(1).pressure(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "viscosity_low");

    ScalarGlobalParameter scalarGlobalParameter_15 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("viscosity_low"));

    scalarGlobalParameter_15.setDimensions(Dimensions.Builder().time(1).pressure(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "initial_pressure");

    ScalarGlobalParameter scalarGlobalParameter_16 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("initial_pressure"));

    scalarGlobalParameter_16.setDimensions(Dimensions.Builder().pressure(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "final_pressure");

    ScalarGlobalParameter scalarGlobalParameter_17 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("final_pressure"));

    scalarGlobalParameter_17.setDimensions(Dimensions.Builder().pressure(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "probe_x");

    ScalarGlobalParameter scalarGlobalParameter_18 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("probe_x"));

    scalarGlobalParameter_18.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "probe_y");

    ScalarGlobalParameter scalarGlobalParameter_19 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("probe_y"));

    scalarGlobalParameter_19.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "inlet_v_avg");

    ScalarGlobalParameter scalarGlobalParameter_20 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("inlet_v_avg"));

    scalarGlobalParameter_20.setDimensions(Dimensions.Builder().velocity(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "delta_h_ref");

    ScalarGlobalParameter scalarGlobalParameter_21 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("delta_h_ref"));

    scalarGlobalParameter_21.setDimensions(Dimensions.Builder().length(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "grev");

    ScalarGlobalParameter scalarGlobalParameter_22 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("grev"));

    scalarGlobalParameter_22.setDimensions(Dimensions.Builder().length(1).time(-2).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "inlet_friction_factor");

    ScalarGlobalParameter scalarGlobalParameter_23 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("inlet_friction_factor"));

    scalarGlobalParameter_23.setDimensions(Dimensions.Builder().build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "initial_static_temperature_high_rho");

    ScalarGlobalParameter scalarGlobalParameter_24 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("initial_static_temperature_high_rho"));

    scalarGlobalParameter_24.setDimensions(Dimensions.Builder().temperature(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "initial_static_temperature_low_rho");

    ScalarGlobalParameter scalarGlobalParameter_25 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("initial_static_temperature_low_rho"));

    scalarGlobalParameter_25.setDimensions(Dimensions.Builder().temperature(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "inlet_T");

    ScalarGlobalParameter scalarGlobalParameter_26 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("inlet_T"));

    scalarGlobalParameter_26.setDimensions(Dimensions.Builder().temperature(1).build());

    simulation_0.get(GlobalParameterManager.class).createGlobalParameter(ScalarGlobalParameter.class, "des_1stAngle Value");

    ScalarGlobalParameter scalarGlobalParameter_27 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_1stAngle Value"));

    scalarGlobalParameter_27.setDimensions(Dimensions.Builder().angle(1).build());
  }

  private void execute1() {

    Simulation simulation_0 = 
      getActiveSimulation();

    ScalarGlobalParameter scalarGlobalParameter_0 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_c2c"));

    scalarGlobalParameter_0.getQuantity().setValue(90.0);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    scalarGlobalParameter_0.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_1 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_dia_inlet"));

    scalarGlobalParameter_1.getQuantity().setValue(7.0);

    scalarGlobalParameter_1.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_2 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_height_chamber"));

    scalarGlobalParameter_2.getQuantity().setValue(100.0);

    scalarGlobalParameter_2.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_3 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_dia_chamber"));

    scalarGlobalParameter_3.getQuantity().setValue(120.0);

    scalarGlobalParameter_3.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_4 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_height_inlet"));

    scalarGlobalParameter_4.getQuantity().setValue(14.0);

    scalarGlobalParameter_4.getQuantity().setUnits(units_1);

    ScalarGlobalParameter scalarGlobalParameter_5 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("delta_h"));

    scalarGlobalParameter_5.getQuantity().setValue(0.01);

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    scalarGlobalParameter_5.getQuantity().setUnits(units_0);

    ScalarGlobalParameter scalarGlobalParameter_6 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("amplitude"));

    scalarGlobalParameter_6.getQuantity().setValue(0.1);

    Units units_2 = 
      ((Units) simulation_0.getUnitsManager().getObject(""));

    scalarGlobalParameter_6.getQuantity().setUnits(units_2);

    ScalarGlobalParameter scalarGlobalParameter_7 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("high_rho"));

    scalarGlobalParameter_7.getQuantity().setValue(3.0);

    Units units_3 = 
      ((Units) simulation_0.getUnitsManager().getObject("kg/m^3"));

    scalarGlobalParameter_7.getQuantity().setUnits(units_3);

    ScalarGlobalParameter scalarGlobalParameter_8 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("low_rho"));

    scalarGlobalParameter_8.getQuantity().setValue(1.0);

    scalarGlobalParameter_8.getQuantity().setUnits(units_3);

    ScalarGlobalParameter scalarGlobalParameter_9 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("surface_tension"));

    scalarGlobalParameter_9.getQuantity().setValue(1.0E-5);

    Units units_4 = 
      ((Units) simulation_0.getUnitsManager().getObject("N/m"));

    scalarGlobalParameter_9.getQuantity().setUnits(units_4);

    ScalarGlobalParameter scalarGlobalParameter_10 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("time_end"));

    scalarGlobalParameter_10.getQuantity().setValue(6.5);

    Units units_5 = 
      ((Units) simulation_0.getUnitsManager().getObject("s"));

    scalarGlobalParameter_10.getQuantity().setUnits(units_5);

    ScalarGlobalParameter scalarGlobalParameter_11 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("timestep"));

    scalarGlobalParameter_11.getQuantity().setValue(5.0E-5);

    scalarGlobalParameter_11.getQuantity().setUnits(units_5);

    ScalarGlobalParameter scalarGlobalParameter_12 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("timestep_end"));

    scalarGlobalParameter_12.getQuantity().setDefinition("${time_end}/${timestep}+1000");

    ScalarGlobalParameter scalarGlobalParameter_13 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("timestep_freq"));

    scalarGlobalParameter_13.getQuantity().setDefinition("1/(15*${timestep})");

    ScalarGlobalParameter scalarGlobalParameter_14 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("viscosity_high"));

    scalarGlobalParameter_14.getQuantity().setValue(0.001);

    Units units_6 = 
      ((Units) simulation_0.getUnitsManager().getObject("Pa-s"));

    scalarGlobalParameter_14.getQuantity().setUnits(units_6);

    ScalarGlobalParameter scalarGlobalParameter_15 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("viscosity_low"));

    scalarGlobalParameter_15.getQuantity().setValue(0.001);

    scalarGlobalParameter_15.getQuantity().setUnits(units_6);

    ScalarGlobalParameter scalarGlobalParameter_16 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("initial_pressure"));

    scalarGlobalParameter_16.getQuantity().setValue(0.3);

    Units units_7 = 
      ((Units) simulation_0.getUnitsManager().getObject("bar"));

    scalarGlobalParameter_16.getQuantity().setUnits(units_7);

    ScalarGlobalParameter scalarGlobalParameter_17 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("final_pressure"));

    scalarGlobalParameter_17.getQuantity().setValue(50000.0);

    Units units_8 = 
      ((Units) simulation_0.getUnitsManager().getObject("Pa"));

    scalarGlobalParameter_17.getQuantity().setUnits(units_8);

    ScalarGlobalParameter scalarGlobalParameter_18 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("probe_x"));

    scalarGlobalParameter_18.getQuantity().setValue(0.12);

    scalarGlobalParameter_18.getQuantity().setUnits(units_2);

    ScalarGlobalParameter scalarGlobalParameter_19 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("probe_y"));

    scalarGlobalParameter_19.getQuantity().setValue(0.05);

    scalarGlobalParameter_19.getQuantity().setUnits(units_2);

    ScalarGlobalParameter scalarGlobalParameter_20 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("inlet_v_avg"));

    scalarGlobalParameter_20.getQuantity().setValue(6.2);

    Units units_9 = 
      ((Units) simulation_0.getUnitsManager().getObject("m/s"));

    scalarGlobalParameter_20.getQuantity().setUnits(units_9);

    ScalarGlobalParameter scalarGlobalParameter_21 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("delta_h_ref"));

    scalarGlobalParameter_21.getQuantity().setValue(0.001);

    scalarGlobalParameter_21.getQuantity().setUnits(units_0);

    ScalarGlobalParameter scalarGlobalParameter_22 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("grev"));

    scalarGlobalParameter_22.getQuantity().setValue(1.0);

    Units units_10 = 
      ((Units) simulation_0.getUnitsManager().getObject("m/s^2"));

    scalarGlobalParameter_22.getQuantity().setUnits(units_10);

    ScalarGlobalParameter scalarGlobalParameter_23 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("inlet_friction_factor"));

    scalarGlobalParameter_23.getQuantity().setValue(0.01959);

    scalarGlobalParameter_23.getQuantity().setUnits(units_2);

    ScalarGlobalParameter scalarGlobalParameter_24 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("initial_static_temperature_high_rho"));

    scalarGlobalParameter_24.getQuantity().setDefinition("80+273.15");

    ScalarGlobalParameter scalarGlobalParameter_25 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("initial_static_temperature_low_rho"));

    scalarGlobalParameter_25.getQuantity().setDefinition("80+273.15");

    ScalarGlobalParameter scalarGlobalParameter_26 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("inlet_T"));

    scalarGlobalParameter_26.getQuantity().setDefinition("80+273.15");

    ScalarGlobalParameter scalarGlobalParameter_27 = 
      ((ScalarGlobalParameter) simulation_0.get(GlobalParameterManager.class).getObject("des_1stAngle Value"));

    scalarGlobalParameter_27.getQuantity().setValue(25.0);

    Units units_11 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    scalarGlobalParameter_27.getQuantity().setUnits(units_11);
  }

  private void execute2() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.get(TagManager.class).createNewUserTag("Region (2D Mesh)");

    simulation_0.get(TagManager.class).createNewUserTag("Boundary (2D Mesh)");
  }

  private void execute3() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).createSolidModel();

    cadModel_1.setModelCreationVersion(33);
  }

  private void execute4() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    UserDesignParameter scalarQuantityDesignParameter_1 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_rad");

    UserDesignParameter scalarQuantityDesignParameter_2 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().angle(1).build(), "des_1stAngle");

    UserDesignParameter scalarQuantityDesignParameter_3 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().angle(1).build(), "des_2ndAngle");

    UserDesignParameter scalarQuantityDesignParameter_4 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_width_chamber");

    UserDesignParameter scalarQuantityDesignParameter_0 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_lateral_shift");

    UserDesignParameter scalarQuantityDesignParameter_5 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_inlet_width");

    UserDesignParameter scalarQuantityDesignParameter_6 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_height_inlet");

    UserDesignParameter scalarQuantityDesignParameter_7 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_height_chamber");

    UserDesignParameter scalarQuantityDesignParameter_8 = 
      (UserDesignParameter) cadModel_1.getDesignParameterManager().createDesignParameter(Dimensions.Builder().length(1).build(), "des_height_total");

    scalarQuantityDesignParameter_1.getQuantity().setValue(8.0);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    scalarQuantityDesignParameter_1.getQuantity().setUnits(units_1);

    scalarQuantityDesignParameter_2.getQuantity().setValue(58.00000000000001);

    Units units_11 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    scalarQuantityDesignParameter_2.getQuantity().setUnits(units_11);

    scalarQuantityDesignParameter_3.getQuantity().setValue(12.0);

    scalarQuantityDesignParameter_3.getQuantity().setUnits(units_11);

    scalarQuantityDesignParameter_4.getQuantity().setValue(90.0);

    scalarQuantityDesignParameter_4.getQuantity().setUnits(units_1);

    scalarQuantityDesignParameter_0.getQuantity().setValue(8.5);

    scalarQuantityDesignParameter_0.getQuantity().setUnits(units_1);

    scalarQuantityDesignParameter_5.getQuantity().setValue(20.0);

    scalarQuantityDesignParameter_5.getQuantity().setUnits(units_1);

    scalarQuantityDesignParameter_6.getQuantity().setValue(10.0);

    scalarQuantityDesignParameter_6.getQuantity().setUnits(units_1);

    scalarQuantityDesignParameter_7.getQuantity().setValue(128.4);

    scalarQuantityDesignParameter_7.getQuantity().setUnits(units_1);

    scalarQuantityDesignParameter_8.getQuantity().setValue(260.0);

    scalarQuantityDesignParameter_8.getQuantity().setUnits(units_1);
  }

  private void execute5() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      (CanonicalSketchPlane) cadModel_1.getFeature("XY");
  }

  private void execute6() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_1 = 
      (CanonicalSketchPlane) cadModel_1.getFeature("YZ");
  }

  private void execute7() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_2 = 
      (CanonicalSketchPlane) cadModel_1.getFeature("ZX");
  }

  private void execute8() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferencePoint canonicalReferencePoint_0 = 
      (CanonicalReferencePoint) cadModel_1.getFeature("Global Origin");
  }

  private void execute9() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalReferenceCoordinateSystem canonicalReferenceCoordinateSystem_0 = 
      (CanonicalReferenceCoordinateSystem) cadModel_1.getFeature("Lab Coordinate System");
  }

  private void execute10() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_1.getFeature("XY"));

    Sketch sketch_0 = 
      (Sketch) cadModel_1.getFeatureManager().createSketch(canonicalSketchPlane_0);

    sketch_0.setPresentationName("Sketch 1");

    PointSketchPrimitive pointSketchPrimitive_0 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(200, "Point 15", new DoubleVector(new double[] {0.0, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_1 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(201, "Point 16", new DoubleVector(new double[] {0.09, 0.1285}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_2 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(202, "Point 17", new DoubleVector(new double[] {0.0, 0.1285}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_3 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(203, "Point 18", new DoubleVector(new double[] {0.09000000000000001, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_4 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(208, "Point 22", new DoubleVector(new double[] {0.08200000000000011, -0.01661249575486482}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_5 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(209, "Point 23", new DoubleVector(new double[] {0.08164602752999019, -0.02460466088644682}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_6 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(210, "Point 24", new DoubleVector(new double[] {0.09000000000000011, -0.016612495754864814}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_7 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(211, "Point 25", new DoubleVector(new double[] {0.09000000000000001, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_8 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(213, "Point 26", new DoubleVector(new double[] {0.082, 0.0957953391135532}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_9 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(214, "Point 27", new DoubleVector(new double[] {0.09000000000000004, 0.0957953391135532}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_10 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(215, "Point 28", new DoubleVector(new double[] {0.08199999999999999, 0.1037953391135532}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_11 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(216, "Point 29", new DoubleVector(new double[] {0.09, 0.1285}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_12 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(218, "Point 30", new DoubleVector(new double[] {0.008, 0.09579533911355315}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_13 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(219, "Point 31", new DoubleVector(new double[] {0.008000000000000005, 0.10379533911355315}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_14 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(220, "Point 32", new DoubleVector(new double[] {0.0, 0.09579533911355315}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_15 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(221, "Point 33", new DoubleVector(new double[] {0.0, 0.1285}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_16 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(223, "Point 34", new DoubleVector(new double[] {0.008, -0.016612495754864842}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_17 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(224, "Point 35", new DoubleVector(new double[] {0.0, -0.016612495754864842}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_18 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(225, "Point 36", new DoubleVector(new double[] {0.007646027529990192, -0.024604660886446847}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_19 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(226, "Point 37", new DoubleVector(new double[] {0.0, 0.0}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_20 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(231, "Point 1", new DoubleVector(new double[] {0.0085, 0.10379533911355315}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_21 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(232, "Point 2", new DoubleVector(new double[] {0.008499999999999912, 0.23539533911355318}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_22 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(237, "Point 3", new DoubleVector(new double[] {0.04000157245314898, 0.2353953391135532}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_23 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(242, "Point 4", new DoubleVector(new double[] {0.019999999999999987, 0.14129533911355313}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_24 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(247, "Point 5", new DoubleVector(new double[] {0.0800125448390394, 0.1037953391135532}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_25 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(257, "Point 6", new DoubleVector(new double[] {0.04500000000000001, 0.0}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_26 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(267, "Point 7", new DoubleVector(new double[] {0.04500000000000002, -0.03460466088644684}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_27 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(268, "Point 8", new DoubleVector(new double[] {0.045000000000000005, 0.03624266594694288}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_28 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(289, "Point 9", new DoubleVector(new double[] {0.0350000000000001, -0.02460466088644684}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_29 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(290, "Point 10", new DoubleVector(new double[] {0.055000000000000104, -0.024604660886446826}), true, false, true, false, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_30 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(291, "Point 11", new DoubleVector(new double[] {0.0550000000000001, -0.03460466088644684}), true, false, true, true, false, 0.0);

    PointSketchPrimitive pointSketchPrimitive_31 = 
      (PointSketchPrimitive) sketch_0.addPointToSketch(292, "Point 12", new DoubleVector(new double[] {0.0350000000000001, -0.03460466088644684}), true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_5 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(206, 214, 210, new DoubleVector(new double[] {1.2246467991473532E-16, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_5, "Line 5", 206, true, false, false, true, true, false, 0.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_1 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(212, 208, 209, 210);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_1, "CircularArc 3", 212, true, false, false, true, true, true, 10.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_3 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(217, 213, 214, 215);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_3, "CircularArc 4", 217, true, false, false, true, true, true, 10.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_2 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(222, 218, 219, 220);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_2, "CircularArc 5", 222, true, false, false, true, true, true, 10.0);

    CircularArcSketchPrimitive circularArcSketchPrimitive_0 = 
      (CircularArcSketchPrimitive) sketch_0.addCircularArcToSketch(227, 223, 224, 225);

    sketch_0.addWirePrimitveProperties(circularArcSketchPrimitive_0, "CircularArc 6", 227, true, false, false, true, true, true, 10.0);

    LineSketchPrimitive lineSketchPrimitive_7 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(233, 231, 232, new DoubleVector(new double[] {-6.735557395310445E-16, 1.0000000000000002}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_7, "Line 3", 233, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_12 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(238, 232, 237, new DoubleVector(new double[] {1.0, 4.898587196589413E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_12, "Line 4", 238, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_11 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(243, 237, 242, new DoubleVector(new double[] {-0.20791169081775845, -0.978147600733806}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_11, "Line 7", 243, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_10 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(248, 242, 247, new DoubleVector(new double[] {0.8480480961564265, -0.5299192642332042}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_10, "Line 8", 248, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_8 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(252, 219, 231, new DoubleVector(new double[] {1.0, 4.898587196589413E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_8, "Line 2 1", 252, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(254, 247, 215, new DoubleVector(new double[] {1.0, 4.898587196589413E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_9, "Line 2 3", 254, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_6 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(204, 224, 220, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_6, "Line 1", 204, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_2 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(294, 290, 291, new DoubleVector(new double[] {3.061616997868383E-16, -1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_2, "Line 10", 294, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(296, 292, 289, new DoubleVector(new double[] {0.0, 1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_1, "Line 12", 296, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(297, 209, 290, new DoubleVector(new double[] {-1.0, -3.6739403974420594E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_4, "Line 6 1", 297, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_3 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(300, 289, 225, new DoubleVector(new double[] {-1.0, -3.6739403974420594E-16}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_3, "Line 6 4", 300, true, false, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_13 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(269, 267, 268, new DoubleVector(new double[] {-1.8369701987210297E-16, 1.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_13, "Line 2", 269, true, true, false, true, true, false, 0.0);

    LineSketchPrimitive lineSketchPrimitive_0 = 
      (LineSketchPrimitive) sketch_0.addLineToSketch(295, 291, 292, new DoubleVector(new double[] {-1.0, 0.0}));

    sketch_0.addWirePrimitveProperties(lineSketchPrimitive_0, "Line 11", 295, true, false, false, true, true, false, 0.0);

    sketch_0.addVerticalConstraint(lineSketchPrimitive_5);

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_5, pointSketchPrimitive_7);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_3, pointSketchPrimitive_7);

    sketch_0.addTangentConstraint(lineSketchPrimitive_5, circularArcSketchPrimitive_1);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    RadiusDimension radiusDimension_0 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_1, 0.008, units_1);

    radiusDimension_0.getRadius().linkQuantityToExportedUserDesignParameter("des_rad");

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_5, pointSketchPrimitive_11);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_1, pointSketchPrimitive_11);

    sketch_0.addTangentConstraint(lineSketchPrimitive_5, circularArcSketchPrimitive_3);

    RadiusDimension radiusDimension_1 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_3, "${des_rad}");

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_2, pointSketchPrimitive_15);

    RadiusDimension radiusDimension_2 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_2, "${des_rad}");

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_0, pointSketchPrimitive_19);

    RadiusDimension radiusDimension_3 = 
      (RadiusDimension) sketch_0.addRadiusDimension(circularArcSketchPrimitive_0, "${des_rad}");

    sketch_0.addFixationConstraint(pointSketchPrimitive_0);

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_12);

    Units units_11 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    AngleDimension angleDimension_0 = 
      (AngleDimension) sketch_0.addAngleDimension(lineSketchPrimitive_10, lineSketchPrimitive_7, 1.0122909661567114, units_11, false, false, true);

    angleDimension_0.getAngle().linkQuantityToExportedUserDesignParameter("des_1stAngle");

    AngleDimension angleDimension_1 = 
      (AngleDimension) sketch_0.addAngleDimension(lineSketchPrimitive_11, lineSketchPrimitive_7, 0.2094395102393196, units_11, false, false, false);

    angleDimension_1.getAngle().linkQuantityToExportedUserDesignParameter("des_2ndAngle");

    sketch_0.addVerticalConstraint(lineSketchPrimitive_6);

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_6, pointSketchPrimitive_15);

    sketch_0.addTangentConstraint(lineSketchPrimitive_6, circularArcSketchPrimitive_2);

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_6, pointSketchPrimitive_19);

    sketch_0.addTangentConstraint(lineSketchPrimitive_6, circularArcSketchPrimitive_0);

    DistanceDimension distanceDimension_0 = 
      (DistanceDimension) sketch_0.addDistanceDimension(lineSketchPrimitive_6, lineSketchPrimitive_5, 0.09, units_1);

    distanceDimension_0.getDistance().linkQuantityToExportedUserDesignParameter("des_width_chamber");

    sketch_0.addCollinearConstraint(lineSketchPrimitive_8, lineSketchPrimitive_9);

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_8);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_7, lineSketchPrimitive_8);

    DistanceDimension distanceDimension_1 = 
      (DistanceDimension) sketch_0.addDistanceDimension(lineSketchPrimitive_6, lineSketchPrimitive_7, 0.0085, units_1);

    distanceDimension_1.getDistance().linkQuantityToExportedUserDesignParameter("des_lateral_shift");

    DistanceDimension distanceDimension_2 = 
      (DistanceDimension) sketch_0.addDistanceDimension(pointSketchPrimitive_25, lineSketchPrimitive_5, "${des_width_chamber}/2");

    sketch_0.addIncidenceConstraint(lineSketchPrimitive_13, pointSketchPrimitive_25);

    sketch_0.addCollinearConstraint(lineSketchPrimitive_4, lineSketchPrimitive_3);

    sketch_0.addHorizontalConstraint(lineSketchPrimitive_4);

    sketch_0.addPerpendicularConstraint(lineSketchPrimitive_13, lineSketchPrimitive_4);

    sketch_0.addIncidenceConstraint(pointSketchPrimitive_26, lineSketchPrimitive_0);

    sketch_0.addMidPointConstraint(pointSketchPrimitive_26, lineSketchPrimitive_0);

    LengthDimension lengthDimension_0 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_0, 0.02, units_1);

    lengthDimension_0.getLength().linkQuantityToExportedUserDesignParameter("des_inlet_width");

    LengthDimension lengthDimension_1 = 
      (LengthDimension) sketch_0.addLengthDimension(lineSketchPrimitive_1, 0.01, units_1);

    lengthDimension_1.getLength().linkQuantityToExportedUserDesignParameter("des_height_inlet");

    DistanceDimension distanceDimension_3 = 
      (DistanceDimension) sketch_0.addDistanceDimension(lineSketchPrimitive_4, lineSketchPrimitive_9, 0.12840000000000001, units_1);

    distanceDimension_3.getDistance().linkQuantityToExportedUserDesignParameter("des_height_chamber");

    DistanceDimension distanceDimension_4 = 
      (DistanceDimension) sketch_0.addDistanceDimension(lineSketchPrimitive_12, lineSketchPrimitive_3, 0.26, units_1);

    distanceDimension_4.getDistance().linkQuantityToExportedUserDesignParameter("des_height_total");

    sketch_0.setPresentationName("Sketch 1");

    sketch_0.setFeatureBaseCreationVersion(19);

    sketch_0.markFeatureForEdit();

    cadModel_1.getFeatureManager().execute(sketch_0);
  }

  private void execute11() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    //Exporting Extrude merge feature :Extrude 1

    ExtrusionMerge extrusionMerge_0 = 
      (ExtrusionMerge) cadModel_1.getFeatureManager().createExtrusionMerge();

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
      ((CanonicalReferenceCoordinateSystem) cadModel_1.getFeature("Lab Coordinate System"));

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

    Units units_11 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

    extrusionMerge_0.getDraftAngle().setUnits(units_11);

    extrusionMerge_0.getDistanceAsymmetric().setValue(0.1);

    extrusionMerge_0.getDistanceAsymmetric().setUnits(units_0);

    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_0.getOffsetDistance().setValue(0.1);

    extrusionMerge_0.getOffsetDistance().setUnits(units_0);

    Sketch sketch_0 = 
      ((Sketch) cadModel_1.getFeature("Sketch 1"));

    extrusionMerge_0.setSketch(sketch_0);

    extrusionMerge_0.setPresentationName("Extrude 1");

    extrusionMerge_0.setFeatureBaseCreationVersion(19);

    extrusionMerge_0.markFeatureForEdit();

    cadModel_1.getFeatureManager().execute(extrusionMerge_0);
  }

  private void execute12() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    NameAttributeFeature nameAttributeFeature_0 = 
      (NameAttributeFeature) cadModel_1.getFeatureManager().createNameAttributeFeature(true);

    nameAttributeFeature_0.setPresentationName("Rename 1");

    nameAttributeFeature_0.setIsIncrementalRenaming(false);

    ExtrusionMerge extrusionMerge_0 = 
      ((ExtrusionMerge) cadModel_1.getFeature("Extrude 1"));

    Sketch sketch_0 = 
      ((Sketch) cadModel_1.getFeature("Sketch 1"));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 11"));

    Face face_17 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_0,"True"));

    nameAttributeFeature_0.renameTopology2(face_17, "inlet", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 12"));

    Face face_18 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_1,"True"));

    nameAttributeFeature_0.renameTopology2(face_18, "inlet_walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_2 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 10"));

    Face face_19 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_2,"True"));

    nameAttributeFeature_0.renameTopology2(face_19, "inlet_walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_3 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 6 4"));

    Face face_20 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_3,"True"));

    nameAttributeFeature_0.renameTopology2(face_20, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_4 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 6 1"));

    Face face_21 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_4,"True"));

    nameAttributeFeature_0.renameTopology2(face_21, "walls", 135, 206, 250, 255, true, 1.0, false);

    CircularArcSketchPrimitive circularArcSketchPrimitive_0 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 6"));

    Face face_22 = 
      ((Face) extrusionMerge_0.getSideFace(circularArcSketchPrimitive_0,"True"));

    nameAttributeFeature_0.renameTopology2(face_22, "walls", 135, 206, 250, 255, true, 1.0, false);

    CircularArcSketchPrimitive circularArcSketchPrimitive_1 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 3"));

    Face face_23 = 
      ((Face) extrusionMerge_0.getSideFace(circularArcSketchPrimitive_1,"True"));

    nameAttributeFeature_0.renameTopology2(face_23, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_5 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 5"));

    Face face_24 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_5,"True"));

    nameAttributeFeature_0.renameTopology2(face_24, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));

    Face face_25 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_6,"True"));

    nameAttributeFeature_0.renameTopology2(face_25, "walls", 135, 206, 250, 255, true, 1.0, false);

    CircularArcSketchPrimitive circularArcSketchPrimitive_2 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 5"));

    Face face_26 = 
      ((Face) extrusionMerge_0.getSideFace(circularArcSketchPrimitive_2,"True"));

    nameAttributeFeature_0.renameTopology2(face_26, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_7 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 3"));

    Face face_27 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_7,"True"));

    nameAttributeFeature_0.renameTopology2(face_27, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_8 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 2 1"));

    Face face_28 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_8,"True"));

    nameAttributeFeature_0.renameTopology2(face_28, "walls", 135, 206, 250, 255, true, 1.0, false);

    CircularArcSketchPrimitive circularArcSketchPrimitive_3 = 
      ((CircularArcSketchPrimitive) sketch_0.getSketchPrimitive("CircularArc 4"));

    Face face_29 = 
      ((Face) extrusionMerge_0.getSideFace(circularArcSketchPrimitive_3,"True"));

    nameAttributeFeature_0.renameTopology2(face_29, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_9 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 2 3"));

    Face face_30 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_9,"True"));

    nameAttributeFeature_0.renameTopology2(face_30, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_10 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 8"));

    Face face_31 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_10,"True"));

    nameAttributeFeature_0.renameTopology2(face_31, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_11 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 7"));

    Face face_32 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_11,"True"));

    nameAttributeFeature_0.renameTopology2(face_32, "walls", 135, 206, 250, 255, true, 1.0, false);

    LineSketchPrimitive lineSketchPrimitive_12 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 4"));

    Face face_33 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_12,"True"));

    nameAttributeFeature_0.renameTopology2(face_33, "top", 135, 206, 250, 255, true, 1.0, false);

    nameAttributeFeature_0.setPresentationName("Rename 1");

    nameAttributeFeature_0.setFeatureBaseCreationVersion(19);

    nameAttributeFeature_0.markFeatureForEdit();

    cadModel_1.getFeatureManager().execute(nameAttributeFeature_0);
  }

  private void execute13() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_1 = 
      (CadModel) simulation_0.get(SolidModelManager.class).getActiveModel();

    CadModelDisplayOptions cadModelDisplayOptions_0 = 
      cadModel_1.getDisplayOptions();

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
