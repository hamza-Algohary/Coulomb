package gui.components;


import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.Switch;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class SwitchComponent extends Component{
    Switch key = new Switch("" ,"");
    @Override public Point start(){return new Point(key.start);}
    @Override public Point end(){return new Point(key.end);}

    public SwitchComponent(){
        type = "s";
        name = "Switch";
        key.on = false;
    }

    @Override public void setStart(Point start){
        key.start = start.toString();
    }
    @Override public void setEnd(Point end){
        key.end = end.toString();
    }
    @Override protected double vStart(){
        return Utils.getKey(Switch.circuitSimulator.data, Switch.V(key.start), 0d);
    }
    @Override protected double vEnd(){
        return Utils.getKey(Switch.circuitSimulator.data, Switch.V(key.end), 0d);
    }
    @Override public Device getDevice(){
        return key;
    }
    @Override public void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
 
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        context.line(new Point(-maxLength/2 , 0), new Point(maxLength/2 , 0), gColor(), Color.LINE_WIDTH);
        if(key.off()){
            context.line( new Point(-0.5 * length , 0) , new Point(0.5 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);
            context.line( new Point(-0.5 * length , 0) , new Point(0.5 * length , -0.3*length) , gColor() , Color.LINE_WIDTH);
        }
        

        context.unadjust(center, angle);
    }
    @Override protected void onClick(){
        System.out.println("pressed");
        key.toggle();
    }
}
