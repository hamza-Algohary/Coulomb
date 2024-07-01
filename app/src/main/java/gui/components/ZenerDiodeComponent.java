package gui.components;

import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.ZenerDiode;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class ZenerDiodeComponent extends Component {
    ZenerDiode diode = new ZenerDiode("" ,"" , 0.7 , 10);
    public Point start(){return new Point(diode.start());}
    public Point end(){return new Point(diode.end());}

    @Override 
    protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(diode.start()), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(diode.end()), 0d);
    }

    @Override 
    public void setStart(Point start){
        diode.setStart(start.toString());
    }
    @Override 
    public void setEnd(Point end){
        diode.setEnd(end.toString());
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

        context.line( new Point(-0.5 * length , -0.5 * length) , new Point(-0.5 * length , 0.5 * length), gColor() , Color.LINE_WIDTH);
        context.line( new Point(-0.5 * length , -0.5 * length) , new Point(0.5 * length , 0)          , gColor() , Color.LINE_WIDTH);
        context.line( new Point(-0.5 * length , 0.5 * length)  , new Point(0.5 * length , 0)          , gColor() , Color.LINE_WIDTH);
        context.line(new Point(0.5 * length , 0.5*length), new Point(0.6 * length , 0.6*length));
        context.line(new Point(0.5 * length , -0.5*length), new Point(0.4 * length , -0.6*length));
        context.line( new Point(0.5 * length , -0.5 * length)  , new Point(0.5 * length , 0.5 * length),gColor());
        
        this.label = "Vz = " + Utils.doubleToString(diode.reverse.forwardVoltage());
        drawLabel(context, length);
       
        
        context.unadjust(center, angle);
    }

    @Override
    public void setValue(int index , Double value){
        this.diode.reverse.setForwardVoltage(value);
    }

    @Override
    public Double getValue(int index){
        return this.diode.reverse.forwardVoltage();
    }

    @Override
    public String[] getFields(){
        return new String[]{"Vz"};
    }

    public ZenerDiodeComponent(){
        type = "dz";
        name = "Zener Diode";
    }
}
