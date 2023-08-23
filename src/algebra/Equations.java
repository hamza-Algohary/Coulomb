package algebra;
import org.ejml.data.DMatrixIterator;
import org.ejml.simple.SimpleMatrix;
import java.util.HashMap;
import java.util.Vector;

public class Equations extends Vector<Equation>{
    String varNames[] = new String[0];
    SimpleMatrix coeff;
    SimpleMatrix constants;
    SimpleMatrix solution;
    public static class EquationsUnsolvableException extends Exception{

    }
    public boolean isSolvable(){
        return (varNames.length == coeff.toArray2().length) && (coeff.toArray2().length == constants.toArray2().length);
    }
    public HashMap<String , Double> solve()throws Exception{
        prepare();
        

        //System.out.println(coeff);
        //System.out.println(constants);

        SimpleMatrix X = coeff.solve(constants);
        DMatrixIterator element = X.iterator(true, 0, 0, X.numRows()-1, 0);

        HashMap<String , Double> varsMap = new HashMap<String , Double>();      
        int i = 0;
        while(element.hasNext()){
            varsMap.put(varNames[i],(Math.ceil(element.next()*1000000)/1000000));
            i++;
        }
        return varsMap;
    }
    public Equations(){
        
    }
    public Equations(Equation equation){
        this.add(equation);
    }
    private void prepare()throws Exception{
        /*
        for(var eqn : this){
            System.out.println(eqn.toString());
        }
        //*/
        this.varNames = Utils.getAllVarNamesFromEquations(this);
        this.constants = new SimpleMatrix(Utils.getAllConstantsFromEquations(this));
        /*
        System.out.println("=========================");
        for(var v : this.varNames){
            System.out.println(v);
        } 
        //*/
        if(!(varNames.length == constants.toArray2().length)) throw new EquationsUnsolvableException();
        double[][] coeffArray = new double[this.size()][this.size()];
        for(int i=0 ; i<this.size() ; i++){
            for(Variable var : this.get(i).vars){
                int j = Utils.indexOf(this.varNames, var.name);
                coeffArray[i][j] = var.coeffecient;
            }
        }
        this.coeff = new SimpleMatrix(coeffArray);
    }
    public boolean hasNanValue(){
        for(var equation : this){
            for(var v : equation.vars){
                if(Double.isNaN(v.coeffecient)){
                    return true;
                }
            }
        }
        return false;
    }
}
