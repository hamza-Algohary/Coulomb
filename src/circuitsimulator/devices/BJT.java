package circuitsimulator.devices;

import algebra.Equations;
import circuitsimulator.Device;

public class BJT extends Device{
    public String emitterTerminal = "" , baseTerminal = "" , collectorTerminal = "";
    double alpha = 0.99;
    Diode BE_junction;
    Diode BC_junction;
    public static enum BJT_TYPE{
        NPN,
        PNP
    }
    BJT_TYPE type = BJT_TYPE.NPN;
    public BJT(String emitterTerminal, String baseTerminal, String collectorTerminal, double alpha , BJT_TYPE type) {
        this.id = newId();
        this.emitterTerminal = emitterTerminal;
        this.baseTerminal = baseTerminal;
        this.collectorTerminal = collectorTerminal;
        this.alpha = Math.min(Math.min(alpha, 0.95) , 1);
        //this.centerTerminal = "BJT" + this.id + "center";
        switch(type){
            case NPN:
                this.BE_junction = new Diode(baseTerminal, emitterTerminal, 0.7);
                this.BC_junction = new Diode(baseTerminal, collectorTerminal, 0.7);
                break;
            case PNP:
                this.BE_junction = new Diode(emitterTerminal, baseTerminal, 0.7);
                this.BC_junction = new Diode(collectorTerminal, baseTerminal, 0.7);
                break;
            default:
                break;
            
        }
        this.BE_junction.id =  this.id + "BE";
        this.BE_junction.id =  this.id + "BC";
        
    }

    @Override 
    public boolean isLinear(){
        return false;
    }

    @Override
    public Equations getActiveEquations(){
        Equations equations = new Equations();
        equations.addAll(BE_junction.getActiveEquations());
        equations.addAll(BC_junction.getActiveEquations());
        return equations;
    };

    @Override
    public Equations getTestingEquations(){
        Double saturationCurrent = getTestValue(BE_junction.I());
        if(saturationCurrent != null)
            BC_junction.setSaturationCurrent(alpha*saturationCurrent);
        
        Equations equations = new Equations();
        equations.addAll(BE_junction.getTestingEquations());
        equations.addAll(BC_junction.getTestingEquations());
        return equations;
    };

    @Override 
    public Integer getTerminalNumber(String name){
        return name.compareTo(emitterTerminal)==0 ? 0 : name.compareTo(baseTerminal)==0? 1 : name.compareTo(collectorTerminal)==0? 1 : null;
    }

    @Override 
    public void setTerminalByNumber(int number , String newValue){
        if(number == 0){
            emitterTerminal = newValue;
        }else if(number == 1){
            baseTerminal = newValue;
        }else if(number == 2){
            collectorTerminal = newValue;
        }else{
            System.out.println("OutOfBoundsTerminal");
        }
    }

    
    
}
