package circuitsimulator.devices;

import algebra.Equation;
import algebra.Equations;
import circuitsimulator.Device;

public class IdealBattery extends Device{
    public String start = "" , end = "";
    public double voltage = 0;
    public double voltage(){
        return voltage;
    }
    public IdealBattery(String start , String end , double voltage){
        this.id = newId();
        this.start = start;
        this.end = end;
        this.voltage = voltage;

    }
    /*public Battery(String start , String end , double voltage){
        this(start, end, voltage, newId());
    }*/
    @Override
    public Equations getActiveEquations(){
        if(start.compareTo(end)==0)
            return new Equations();

        circuitSimulator.circuit.addCurrentFrom(I(), start);
        circuitSimulator.circuit.addCurrentTo(I() , end);        
        
        Equation equation = new Equation();
        equation.vars.add(V(end , 1));
        equation.vars.add(V(start , -1)); 
        equation.constant = voltage();

        return new Equations(equation);
    }
    @Override
    public Equations getTestingEquations(){
        return getActiveEquations();
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
    public void draw(Context context){
        Transformation transformation = new Transformation(Double.parseDouble(start), Double.parseDouble(end));
        Point t1 = new Point(0 , 0.5).transform(transformation);
        Point t2 = new Point(1 , 0.5).transform(transformation);

        Point t11 = new Point(0.2 , 0.5).transform(transformation);
        Point t22 = new Point(0.8 , 0.5).transform(transformation);

        Point n_up = new Point(0.2 , 0.2).transform(transformation);
        Point n_down = new Point(0.2 , 0.8).transform(transformation);
        Point p_up = new Point(0.8 , 0.2).transform(transformation);
        Point p_down = new Point(0.8 , 1).transform(transformation);

        DrawingUtils.setSource(context, Color.FOREGROUND_COLOR);

        DrawingUtils.line(context, p_up, p_down);
        DrawingUtils.line(context, n_up, n_down);
        DrawingUtils.line(context, t1, t11);
        DrawingUtils.line(context, t2, t22);

        context.setLineWidth(Color.LINE_WIDTH);
        context.stroke();
    }
    */
    
}
