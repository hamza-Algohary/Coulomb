package algebra;
import java.util.Vector;

public class Equation {
    public Vector<Variable> vars = new Vector<>();
    public double constant;

    // Unnecessary
    public Equation() {

    }
    public Equation(Vector<Variable> vars, double constant) {
        this.vars = vars;
        this.constant = constant;
    }
    public String toString(){
        String s = new String();
        for(Variable var : vars){
            s = s.concat(var.toString() + " ");
        }
        s = s.concat(" = " + this.constant);
        return s;
    }
}
