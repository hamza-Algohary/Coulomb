package gui.components;

import gui.Component;
import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.CurrentSource;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;

public class CurrentSourceComponent extends Component {
    CurrentSource currentSource = new CurrentSource("" ,"" , 5);
    public Point start(){return new Point(currentSource.start);}
    public Point end(){return new Point(currentSource.end);}

    public CurrentSourceComponent(){
        type = "i";
        name = "Current Source";
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(currentSource.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(currentSource.end), 0d);
    }

    @Override 
    public void setStart(Point start){
        currentSource.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        currentSource.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return currentSource;
    }

    @Override
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        context.line(new Point(0,0) , new Point(-maxLength/2 , 0), gColor() , Color.LINE_WIDTH);
        context.line(new Point(0,0) , new Point(maxLength/2 , 0), gColor());
        
        context.line( new Point(-0.3 * length , 0) , new Point(0.3 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);

        context.circle(Point.O(), length/2 , gColor() , Color.LINE_WIDTH);
        context.line(Point.O().offsetX(-0.4*length) , Point.O().offsetX(0.4*length) , gColor());
        context.line(Point.O().offsetX(0.4*length)  , new Point(0.2*length , 0.2*length) , gColor());
        context.line(Point.O().offsetX(0.4*length)  , new Point(0.2*length , -0.2*length) , gColor());
       
        this.label = Utils.doubleToString(currentSource.current) + " A";
        drawLabel(context, length);
       
        context.unadjust(center, angle);
    }

    @Override
    public void setValue(int index , Double value){
        this.currentSource.current = value;
    }

    @Override
    public Double getValue(int index){
        return this.currentSource.current;
    }

    @Override
    public String[] getFields(){
        return new String[]{"I"};
    }
}