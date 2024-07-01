package circuitsimulator;

import algebra.Equations;
import algebra.Variable;

public class Device {
    public String id = "";
    public static int counter = -1;
    public static CircuitSimulator circuitSimulator = new CircuitSimulator();

    public String terminals[] = {"",""};

    public Equations getActiveEquations(){return new Equations();};
    public Equations getTestingEquations(){
        return getActiveEquations();
    };
    public String name(){
        return "Device";
    }
    //public void putResult(Variable variable){};
    //public void putTestResult(Variable variable){};

    /*protected Device(String id){
        this.id = id;
    }*/
    protected static String newId(){
        counter++;
        return Integer.toString(counter);
    }

    protected Variable V(String point , double coeff){
        return new Variable(V(point), coeff);
    }
    protected Variable I(double coeff){
        return new Variable(I(), coeff);
    }
    public boolean isLinear(){
        return true;
    }
    public String I(){
        return "I"+id;
    }
    public static String V(String point){
        return "V"+point;
    } 
    protected static Double getTestValue(String name){
        //return Utils.getKey(circuitSimulator.testingData, name, 0d);
        return circuitSimulator.testingData.get(name);
    } 
    protected static Double getActiveValue(String name){
        //return Utils.getKey(circuitSimulator.data, name, 0d);
        return circuitSimulator.data.get(name);
    } 

    public Integer getTerminalNumber(String name){
        return 0;
    }
    public void setTerminalByNumber(int number , String newValue){

    }
    public int getNumberOfTerminals(){
        return 2;
    }
    public boolean hasTwoTerminals(){
        return false;
    }

    public void reset(){
        
    }
}