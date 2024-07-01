package gui.components;

import gui.Component;
import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.Capacitor;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;

public class CapacitorComponent extends Component {
    Capacitor capacitor = new Capacitor("" ,"" , 15e-6);
    public Point start(){return new Point(capacitor.start);}
    public Point end(){return new Point(capacitor.end);}

    public CapacitorComponent(){
        type = "c";
        name = "Capacitor";
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(capacitor.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(capacitor.end), 0d);
    }

    @Override 
    public void setStart(Point start){
        capacitor.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        capacitor.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return capacitor;
    }

    @Override
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        context.line(new Point(0,0) , new Point(-maxLength/2 , 0), gColor() , Color.LINE_WIDTH);
        context.line(new Point(0,0) , new Point(maxLength/2 , 0), gColor());
        
        context.line( new Point(-0.3 * length , 0) , new Point(0.3 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);

        context.line( new Point(-0.3 * length , -0.5 * length) , new Point(-0.3 * length , 0.5 * length),gColor() , Color.LINE_WIDTH);
        context.line( new Point(0.3 * length , -0.5 * length)  , new Point(0.3 * length , 0.5 * length),gColor());
        
        this.label = Utils.doubleToString(capacitor.capacitance) + " F";
        drawLabel(context, length);
        
        context.unadjust(center, angle);
    }

    @Override
    public void setValue(int index , Double value){
        this.capacitor.capacitance = value;
    }

    @Override
    public Double getValue(int index){
        return this.capacitor.capacitance;
    }

    @Override
    public String[] getFields(){
        return new String[]{"C"};
    }
}
