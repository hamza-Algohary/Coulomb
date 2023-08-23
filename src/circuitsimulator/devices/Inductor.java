package circuitsimulator.devices;

import algebra.Equations;
import algebra.Utils;

public class Inductor extends Battery{
    public double inductance = 0;
    private double previousCurrent = 0;
    private double beforePreviousCurrent = 0;

    private static final double inductorInternalResistance = 1;
    public static double maxInductance(){
        return (circuitSimulator.clock.timestep_micro_second/1e-6d ) / inductorInternalResistance;
    }
    public Inductor(String start , String end , double inductance){
        super(start , end , 0);
        this.connection = ResistorConnection.PARALLEL;
        this.internalResistance = inductorInternalResistance;
        this.inductance = Math.min(maxInductance() , inductance);
    }
    @Override
    public double voltage(){
        return -inductance * (previousCurrent - beforePreviousCurrent) / (circuitSimulator.clock.timestep_micro_second/1000000d);
    }
    @Override
    public Equations getActiveEquations(){
        previousCurrent = Utils.getKey(circuitSimulator.data, I(), 0d);
        return super.getActiveEquations();
    }
    @Override 
    public Equations getTestingEquations(){
        return super.getActiveEquations();
    }

}
