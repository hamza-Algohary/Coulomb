package circuitsimulator.devices;


import algebra.*;
import circuitsimulator.Device;

public class Resistance extends Device{
    public String start = "" , end = "";
    public double resistance = 0;
    
    public Resistance(String start , String end , double resistance){
        this.id = newId();
        this.start = start;
        this.end = end;
        this.resistance = resistance;
    }

    /*public Resistance(String start , String end , double resistance){
        this(start, end, resistance, newId());
    }*/

    @Override
    public Equations getTestingEquations(){
        //return new Resistance(start, end, 0.000001).getActiveEquations();
        return getActiveEquations();
    }

    @Override
    public Equations getActiveEquations(){
        if(start.compareTo(end)==0)
            return new Equations();

        circuitSimulator.circuit.addCurrentFrom(I(), start);
        circuitSimulator.circuit.addCurrentTo(I() , end);

        /*if(this.resistance < Double.MIN_NORMAL){
            System.out.println("Insisting on the zero thing.");
            resistance = Double.MIN_NORMAL;
        }
        if(Double.isInfinite(resistance)){
            System.out.println("INFINITE RESISTANCE");
            resistance = Double.MAX_VALUE;
        }*/
        resistance = Utils.limitDoubleBounds(resistance);
        
        double conductance = 1d/resistance;
        conductance = Utils.limitDoubleBounds(conductance);


        Equation equation = new Equation();
        equation.vars.add(I(1));
        equation.vars.add(V(start , -1d/resistance)); 
        equation.vars.add(V(end , 1d/resistance));
        equation.constant = 0;

        return new Equations(equation);
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
