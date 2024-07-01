package circuitsimulator.devices;

import algebra.Equation;
import algebra.Equations;
import algebra.Variable;
import circuitsimulator.Device;

public class ZenerDiode extends Device{
    /*double forwardVoltage = 0 , zenerVoltage = 0;*/
    private String start = "" , end = "";
    public String start(){return start;}
    public String end(){return end;}
    public Diode forward , reverse;
    public ZenerDiode(String start , String  end , double forwardVoltage , double zenerVoltage){
        this.id = newId();
        this.start = start;
        this.end = end;
        /*this.forwardVoltage = Double.max(forwardVoltage, 0);
        this.zenerVoltage = Double.max(zenerVoltage, 0);*/
        this.forward = new Diode(start, end, forwardVoltage);
        this.reverse = new Diode(end, start, zenerVoltage);
        
    }
    public void setStart(String start){
        this.start = start;
        this.forward.start = start;
        this.reverse.end = start;
    }
    public void setEnd(String end){
        this.end = end;
        this.forward.end = end;
        this.reverse.start = end;
    }
    @Override
    public boolean isLinear(){
        return false;
    }
    @Override
    public Equations getActiveEquations(){
        Equations equations = new Equations();
        equations.addAll(reverse.getActiveEquations());
        equations.addAll(forward.getActiveEquations());
        equations.add(getCurrentEquation());

        return equations;
    }

    @Override
    public Equations getTestingEquations(){
        Equations equations = new Equations();
        equations.addAll(forward.getTestingEquations());
        equations.addAll(reverse.getTestingEquations());
        return equations;
    }

    @Override 
    public Integer getTerminalNumber(String name){
        return name.compareTo(forward.start)==0 ? 0 : name.compareTo(forward.end)==0? 1 : null;
    }

    @Override 
    public void setTerminalByNumber(int number , String newValue){
        if(number == 0){
            forward.start = newValue;
        }else if(number == 1){
            forward.end = newValue;
        }else{
            System.out.println("OutOfBoundsTerminal");
        }
    }
    private Equation getCurrentEquation() {
        Equation currentEquation = new Equation();
        currentEquation.vars.add(new Variable(forward.I(), -1));
        currentEquation.vars.add(new Variable(reverse.I(), 1));
        currentEquation.vars.add(new Variable(I(), 1));
        currentEquation.constant = 0;
        return currentEquation;
    } 
}
