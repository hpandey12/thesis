// Simcenter STAR-CCM+ macro: turn_autosave.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class turn_autosave extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    AutoSave autoSave_0 = 
      simulation_0.getSimulationIterator().getAutoSave();

    autoSave_0.setMaxAutosavedFiles(50);

    StarUpdate starUpdate_0 = 
      autoSave_0.getStarUpdate();

    starUpdate_0.setEnabled(true);

    starUpdate_0.getUpdateModeOption().setSelected(StarUpdateModeOption.Type.DELTATIME);

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(Dimensions.Builder().time(1).build());

    DeltaTimeUpdateFrequency deltaTimeUpdateFrequency_0 = 
      starUpdate_0.getDeltaTimeUpdateFrequency();

    deltaTimeUpdateFrequency_0.setDeltaTime("${max_time}", units_0);
  }
}
