package circuitsimulator.devices;

import algebra.Equations;
import algebra.Utils;

public class Capacitor extends IdealBattery{
    public double capacitance = 0;
    double Q = 0;
    //private static final double capacitorInternalResistance = 0;
    //public static double maxCapacitance(){
    //    return (circuitSimulator.clock.timestep_micro_second/1e-6d ) * capacitorInternalResistance;
    //}
    public Capacitor(String start , String end , double capacitance){
        super(start, end, 0);
        this.capacitance = capacitance;
        //this.internalResistance = capacitorInternalResistance;
        //this.capacitance = Math.min(1e-5 , capacitance);
    }
    @Override
    public double voltage(){
        return -Q/capacitance;
    }
    @Override
    public Equations getActiveEquations(){
        Q += Utils.getKey(circuitSimulator.data, I(), 0d)*circuitSimulator.clock.timestep_micro_second/1000000d;
        return super.getActiveEquations();
    }
    @Override 
    public Equations getTestingEquations(){
        return super.getActiveEquations();
    }
    @Override
    public void reset() {
        Q = 0;
    }
    
}