package circuitsimulator.devices;

import algebra.Equation;
import algebra.Equations;
import circuitsimulator.Device;

public class CurrentSource extends Device{
    public String start = "" , end = "";
    public double current = 0;
    
    public double current(){
        return current;
    }
    public CurrentSource(String start , String end , double current){
        this.id = newId();
        this.start = start;
        this.end = end;
        this.current = current;

    }
    /*
    public CurrentSource(String start , String end , double current){
        this(start, end, current, newId());
    }
    */
    @Override
    public Equations getActiveEquations(){
        if(start.compareTo(end)==0)
            return new Equations();

        circuitSimulator.circuit.addCurrentFrom(I(), start);
        circuitSimulator.circuit.addCurrentTo(I() , end);        
        if(Double.isInfinite(current)){
            current = Double.MAX_VALUE;
        }
        Equation equation = new Equation();
        equation.vars.add(I(1));
        equation.constant = current();
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


    /*
    @Override
    public Equation[] getTestingEquations(){
        return getActiveEquations();
    }
    */
}
