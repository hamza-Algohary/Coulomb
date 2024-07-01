package circuitsimulator.devices;

import algebra.Equation;
import algebra.Equations;
import algebra.Variable;

public class DependentVoltageSource extends IdealBattery{
    Variable independentVariable;
    DependentVoltageSource(String start , String end , Variable independentVariable){
        super(start, end, 0);
        this.independentVariable = independentVariable;
        this.independentVariable.coeffecient *= -1;
    }
    @Override
    public Equations getActiveEquations(){
        circuitSimulator.circuit.addCurrentFrom(I(), start);
        circuitSimulator.circuit.addCurrentTo(I() , end);        
 
        Equation equation = new Equation();
        equation.vars.add(V(end , 1));
        equation.vars.add(V(start , -1));
        equation.vars.add(independentVariable); 
        equation.constant = 0;
        return new Equations(equation);
    }
}
