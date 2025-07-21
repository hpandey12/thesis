// Simcenter STAR-CCM+ macro: exportSummary.java
// Written by Simcenter STAR-CCM+ 18.06.006
package macro;

import java.util.*;

import star.common.*;

public class exportSummary extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    new star.common.SimulationSummaryReporter().report(getActiveSimulation(), resolvePath("/rwthfs/rz/cluster/hpcwork/yy310050/thesis/HARD_DATA/STEFAN/filename.html"));
  }
}
