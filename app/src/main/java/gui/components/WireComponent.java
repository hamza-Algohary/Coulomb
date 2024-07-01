package gui.components;

import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.Wire;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class WireComponent extends Component{
    Wire wire = new Wire("" ,"");
    @Override public Point start(){return new Point(wire.start);}
    @Override public Point end(){return new Point(wire.end);}
    @Override public void setStart(Point start){
        wire.start = start.toString();
    }
    @Override public void setEnd(Point end){
        wire.end = end.toString();
    }
    @Override public Device getDevice(){
        return wire;
    }
    @Override protected double vStart(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(wire.start), 0d);
    }
    @Override protected double vEnd(){
        return Utils.getKey(Wire.circuitSimulator.data, Wire.V(wire.end), 0d);
    }
    @Override public void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        context.adjust(center, angle);
        context.line(new Point(-maxLength/2 , 0), new Point(maxLength/2 , 0), gColor(), Color.LINE_WIDTH);
        context.unadjust(center, angle);
    }

    public WireComponent(){
        type = "w";
        name = "Wire";
    }
}
