package gui.components;

import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.IdealBattery;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class BatteryComponent extends Component {
    IdealBattery battery = new IdealBattery("" ,"" , 5);
    public Point start(){return new Point(battery.start);}
    public Point end(){return new Point(battery.end);}

    public BatteryComponent(){
        type = "v";
        name = "Battery";
        reverseVoltage = true;
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(battery.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(battery.end), battery.voltage);
    }

    @Override 
    public void setStart(Point start){
        battery.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        battery.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return battery;
    }

    @Override
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        context.line(new Point(0,0) , new Point(-maxLength/2 , 0), gColor() , Color.LINE_WIDTH);
        context.line(new Point(0,0) , new Point(maxLength/2 , 0), pColor());
        
        context.line( new Point(-0.3 * length , 0) , new Point(0.3 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);

        context.line( new Point(-0.3 * length , -0.3 * length) , new Point(-0.3 * length , 0.3 * length),gColor() , Color.LINE_WIDTH);
        context.line( new Point(0.3 * length , -0.5 * length)  , new Point(0.3 * length , 0.5 * length),pColor());
        
        this.label = Utils.doubleToString(battery.voltage) + " V";
        drawLabel(context, length);
        
        context.unadjust(center, angle);
    }

    @Override
    public void setValue(int index , Double value){
        this.battery.voltage = value;
    }

    @Override
    public Double getValue(int index){
        return this.battery.voltage;
    }

    @Override
    public String[] getFields(){
        return new String[]{"V"};
    }
}
