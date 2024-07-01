package gui.components;

import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.Diode;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class LedComponent extends Component {
    Diode diode = new Diode("" ,"" , 0.7);
    public Point start(){return new Point(diode.start);}
    public Point end(){return new Point(diode.end);}

    public LedComponent(){
        type = "led";
        name = "LED";
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(diode.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(diode.end), 0d);
    }

    @Override 
    public void setStart(Point start){
        diode.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        diode.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return diode;
    }

    @Override
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        context.line(new Point(0,0) , new Point(-maxLength/2 , 0), gColor() , Color.LINE_WIDTH);
        context.line(new Point(0,0) , new Point(maxLength/2 , 0), gColor());
        
        context.line( new Point(-0.5 * length , 0) , new Point(0.5 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);

        if(hover()){
            context.line( new Point(-0.5 * length , -0.5 * length) , new Point(-0.5 * length , 0.5 * length) , gColor() , Color.LINE_WIDTH);
            context.line( new Point(-0.5 * length , -0.5 * length) , new Point(0.5 * length , 0)           , gColor());
            context.line( new Point(-0.5 * length , 0.5 * length)  , new Point(0.5 * length , 0)           , gColor());        
            context.line( new Point(0.5 * length , -0.5 * length)  , new Point(0.5 * length , 0.5 * length),gColor());
        }else{
            context.setLineWidth(Color.LINE_WIDTH);
            context.setSource(gColor());
            context.circle(Point.O(), length/2);
            context.stroke();

            context.setSource(I()>0 ? Color.RED : Color.BACKGROUND_COLOR);
            context.circle(Point.O(), length/2);
            context.fill();
        }
        context.unadjust(center, angle);
    }
}
