package circuitsimulator.devices;
 

import algebra.Equations;
import circuitsimulator.Device;

public class Diode extends Device{
    public String start = "";
    public String end = "";
    String middle = "";
    //double forwardVoltage = 0;
    double saturationCurrent = 1e-9;
    private int lastTimePoint = -1;
    private double activeResistance = 1;
    //boolean focusOnSaturationCurrent = false;
    public static final double VT = 26e-3;
    public static final double standardForwardCurrent = 1e-3; 
    //private static final int maxIterations = 50;
    //private int iterations = 0;
    //private boolean lastWasBattery = false;
    private double backupForwardVoltage = 0.7;
    //Resistance resistance;
    public Diode(String start , String end , double forwardVoltage){
        this.id = newId();
        this.start = start;
        this.end = end;
        this.setForwardVoltage(forwardVoltage);
        System.out.println("ID = " + id);
        //this.middle = "inner"+start+end;
        /*
        this.forwardVoltage = forwardVoltage;
        this.saturationCurrent = saturationCurrent;
        this.focusOnSaturationCurrent = focusOnSaturationCurrent;
        */
        //resistance = new Resistance(start, middle, 0.000001);
        /*circuitSimulator.circuit.addCurrentFrom(I(), start);
        circuitSimulator.circuit.addCurrentTo(I() , end);*/
    }
    /*public Diode(String start , String end , double forwardVoltage , double saturationCurrent){
        this(start, end, forwardVoltage, saturationCurrent, idPrefix);
    }*/
    public static double saturationCurrent(double forwardVoltage){
        return standardForwardCurrent / ( Math.exp(forwardVoltage/VT) - 1);
    }
    public static double current(final double saturationCurrent , double voltage){
        return saturationCurrent*(Math.exp(voltage/VT) - 1);
    }

    //This function is evil..
    public static double voltage(final double saturationCurrent , double current){
        return VT*Math.log(current/saturationCurrent + 1);
    }   

    // Maximum Forward voltage is 18.
    public void setForwardVoltage(double forwardVoltage){
        //this.forwardVoltage = forwardVoltage;
        forwardVoltage = Math.min(forwardVoltage, 18);
        this.saturationCurrent = saturationCurrent(forwardVoltage);
        this.backupForwardVoltage = forwardVoltage;
    } 
    public void setSaturationCurrent(double saturationCurrent){
        //this.forwardVoltage = voltage(saturationCurrent , standardForwardCurrent);
        this.saturationCurrent = saturationCurrent;
    }

    public double saturationCurrent(){
        return saturationCurrent;
    }

    public double forwardVoltage(){
        if(saturationCurrent() < Double.MIN_NORMAL)
            return backupForwardVoltage;
        return voltage(saturationCurrent , standardForwardCurrent);
    }
    
    public double voltage(double current){
        return voltage(saturationCurrent(), current);
    }
    public double current(double voltage){
        return current(saturationCurrent(), voltage);
    }
    public double resistanceFromVoltage(double voltage){
        /*if(Math.abs(voltage) < Double.MIN_NORMAL){
            System.out.println("Zero Voltage");
            return 1;
        } */// voltage == 0
        return voltage / current(voltage);
    }

    // The domain of this function is ]Isaturation , infinity[.
    // It's advisable to not use this function.
    public double resistanceFromCurrent(double current){
        /*if(current <=0 )
            return voltage(saturationCurrent())/saturationCurrent();
        if(Double.isInfinite(current))
            return voltage(standardForwardCurrent)/standardForwardCurrent;
        */
        return voltage(current)/current;
    }
    public double resistanceFromDeriviative(double voltage){
        return 1d/(saturationCurrent()/VT)*Math.exp(voltage/VT);
    }
    /* 
    private double getIeq(double voltage){
        return current(voltage)-voltage/resistanceFromDeriviative(voltage);
    }
    */
    /*
    public static double forwardVoltage(double saturationCurrent){
        return Math.log(forwardCurrent/saturationCurrent + 1)*(VT);
    }
    */
    /* 
    private Equations getAidBattery(){
        Battery battery = new Battery(start, end, -forwardVoltage());
        battery.id = this.id;
        return battery.getActiveEquations();
    }

    private boolean useAidBattery(){
        return Double.isNaN(activeResistance) || Double.isNaN(1d/activeResistance); //|| (lastWasBattery && getTestValue(I()) > 0);
    }*/

    @Override
    public boolean isLinear(){
        return false;
    }
    @Override
    public Equations getTestingEquations(){
        
        //return new Resistance(start, end, forwardVoltage , idPrefix).getActiveEquations();
        /*var battery = new Battery(middle, end, -forwardVoltage);
        battery.id = id;
        Equations equations = battery.getActiveEquations();
        equations.addAll(resistance.getActiveEquations());
        return equations;*/
        //System.out.println("==============================");
        //Resistance resistance;// = new Resistance(start, end, resistanceFromDeriviative(voltage));
        //CurrentSource currentSource ;//= new CurrentSource(start, end, getIeq(voltage));

        Double current = getTestValue(I());
        Double voltageStart = getTestValue(V(start));
        Double voltageEnd = getTestValue(V(end));
        if(circuitSimulator.clock.current_time_micro_second != lastTimePoint || current == null || voltageStart == null || voltageEnd == null){
            //System.out.println("first iteration");
            lastTimePoint = circuitSimulator.clock.current_time_micro_second;
            //if(Double.isNaN(activeResistance)){
            //    activeResistance = 1;
            //}
            //iterations = 0;

            /*currentSource = new CurrentSource(start, end, 0);
            */
            //activeResistance = resistance.resistance;
            //resistance.id = this.id;
            //return resistance.getActiveEquations();
        }else{
            double voltage = voltageStart - voltageEnd;
            //System.out.println("iterating....");

            /* 
            resistance = new Resistance(start, end, resistanceFromDeriviative(voltage));
            currentSource = new CurrentSource(start, end, getIeq(voltage));
            */
            //if(current > -saturationCurrent()){
                //activeResistance = resistanceFromCurrent(current);
            //}else{
            activeResistance = resistanceFromVoltage(voltage);
            if(voltage > forwardVoltage()){
                activeResistance = resistanceFromCurrent(current);
                //System.out.println("Switching to current mode.");
                //if(Double.isNaN(activeResistance)){
                //    activeResistance = resistanceFromVoltage(voltage);
                //}
            }

            // It's probably forward biased.

            //}
            /* 
            if(iterations > maxIterations && current >= 0){
                System.out.println("Doing It...............................");
                activeResistance = resistanceFromCurrent(current);
            }*/
            //activeResistance = resistanceFromCurrent(current);

            
            //System.out.println("Voltage = " + voltage + "  |  Current = " + current);
            //System.out.println("Voltage is infinity : " + Double.isInfinite(voltage(current)));
            /*if(Double.isNaN(activeResistance)){
                //if(Double.isInfinite(activeResistance)){
                activeResistance = Double.MAX_VALUE;
                /*}else{
                    activeResistance = Double.MIN_NORMAL;
                } */     
            //}
            //iterations++;

            //Resistance resistance = new Resistance(start, end, activeResistance);
            //System.out.println("Active Resistance = " + activeResistance);
            //resistance.id = this.id;
            //return resistance.getActiveEquations();
            
        }
        Resistance resistance = new Resistance(start, end, activeResistance);
        resistance.id = this.id;
        Equations equations = new Equations();
        equations.addAll(resistance.getActiveEquations());
        return equations;

        /* 
        if(useAidBattery()){
            System.out.println("R= NAN");
            lastWasBattery = true;
            return getAidBattery();
            //activeResistance = resistanceFromDeriviative(voltage);
        }else{
            lastWasBattery = false;
        }*/
        //resistance.id = this.id + "sub";
        //currentSource.id = this.id + "sub2";


        //equations.addAll(currentSource.getActiveEquations());
        
    }
    @Override
    public Equations getActiveEquations(){
        /* 
        if(useAidBattery()){
            return getAidBattery();
        }
        */

        var resistance = new Resistance(start, end, activeResistance);
        resistance.id = this.id;
        return resistance.getActiveEquations();
        //double voltage = getTestValue(V(start)) - getTestValue(V(end));
        //double current = getTestValue(I());
        //System.out.println("Current = " + current);
        /*if(voltage >= forwardVoltage){
            return new Battery(start, end, -1*forwardVoltage , idPrefix).getActiveEquations();
        }else{
            return new CurrentSource(start, end , -1*saturationCurrent , idPrefix).getActiveEquations();  // This is to allow dependent current sources which might become useful in transistor.
        }*/
        /* 
        if(current<=0){
            /*var i = new CurrentSource(middle, end, 0);
            i.id = id;
            Equations equations = i.getActiveEquations();
            equations.addAll(resistance.getActiveEquations());
            return equations;*//* 
            var r = new Resistance(start, end, 10000000);
            return r.getActiveEquations();
        }else{
            return getTestingEquations();//new Battery(start, end, -1*forwardVoltage , idPrefix).getActiveEquations();
        }*/

    }

    @Override 
    public Integer getTerminalNumber(String name){
        return name.compareTo(start)==0 ? 0 : name.compareTo(end)==0? 1 : null;
    }

    @Override 
    public void setTerminalByNumber(int number , String newValue){
        if(number == 0){
            start = newValue;
        }else if(number == 1){
            end = newValue;
        }else{
            System.out.println("OutOfBoundsTerminal");
        }
    }
}