package gui.components;

import gui.Component;
import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.ACVoltageSource;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;

public class ACVoltageSourceComponent extends Component {
    ACVoltageSource source = new ACVoltageSource("" ,"" , 24 , 50);
    public Point start(){return new Point(source.start);}
    public Point end(){return new Point(source.end);}

    public ACVoltageSourceComponent(){
        type = "acv";
        name = "AC Voltage Source";
        reverseVoltage = true;
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(source.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(source.end), 0d);
    }

    @Override 
    public void setStart(Point start){
        source.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        source.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return source;
    }

    @Override
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        context.line(new Point(0,0) , new Point(-maxLength/2 , 0), gColor() , Color.LINE_WIDTH);
        context.line(new Point(0,0) , new Point(maxLength/2 , 0), gColor());
        
        context.line( new Point(-0.5 * length , 0) , new Point(0.5 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);

        context.circle(Point.O(), length/2 , gColor() , Color.LINE_WIDTH);
        context.arc(new Point(0.15*length , 0) , 0.15*length, Math.PI , 0);
        context.stroke();
        context.arc(new Point(-0.15*length , 0) , 0.15*length, 0 , Math.PI);
        context.stroke();

        this.label = Utils.doubleToString(source.amplitude) + " V " + Utils.doubleToString(source.frequency) + "Hz";
        drawLabel(context, length);

        context.unadjust(center, angle);
    }

    @Override
    public void setValue(int index , Double value){
        if(index == 0) source.amplitude = value;
        else source.frequency = value; 
        
    }

    @Override
    public Double getValue(int index){
        if(index == 0) return source.amplitude;
        return source.frequency;
    }

    @Override
    public String[] getFields(){
        return new String[]{"Amplitude" , "Frequency"};
    }
}