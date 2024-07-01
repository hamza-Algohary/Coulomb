package algebra;
public class Variable{
    public String name = "";
    public double coeffecient;    

    public Variable(String name , double coeffecient){
        this.name = name;
        this.coeffecient = coeffecient;
    }
    @Override
    public String toString(){
        return ""+coeffecient+name;
    }

}
