package macro;

import star.common.*;
import star.base.neo.*;
import star.meshing.*;
import star.cadmodeler.*;

public class init extends StarMacro {

    @Override
    public void execute() {
        // 1. Get the active simulation
        Simulation sim = getActiveSimulation();
        sim.getSolution().clearSolution();
        sim.initializeSolution();

        // At this point, the macro’s job is done; STAR-CCM+ will continue to run
        // any subsequent steps in your batch script as normal.
    }
}