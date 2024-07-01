package circuitsimulator.devices;

import algebra.Equations;
import circuitsimulator.Device;

public class Switch extends Device{
    public String start = "" , end = "";
    public boolean on = true;
    public Switch(String start , String end){
        this.id = newId();
        this.start = start;
        this.end = end;
    }

    public void toggle(){
        //System.out.println("toggling");
        System.out.println(on);
        on = !on;
    }

    public boolean on(){
        return on;
    }
    public boolean off(){
        return !on;
    }
    @Override
    public Equations getTestingEquations(){
        return getActiveEquations();
    }

    @Override
    public Equations getActiveEquations(){
        circuitSimulator.circuit.addCurrentFrom(I(), start);
        circuitSimulator.circuit.addCurrentTo(I() , end);

        if(on()){
            Wire wire = new Wire(start, end);
            wire.id = this.id;
            return wire.getActiveEquations();
        }else{
            var source = new CurrentSource(start , end , 0);
            source.id = this.id;
            return source.getActiveEquations();
        }
    }
}
