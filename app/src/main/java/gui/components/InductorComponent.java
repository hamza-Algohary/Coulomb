package gui.components;

import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.Inductor;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class InductorComponent extends Component{
    Inductor inductor = new Inductor("" ,"" , 1);
    public Point start(){return new Point(inductor.start);}
    public Point end(){return new Point(inductor.end);}

    public InductorComponent(){
        type = "l";
        name = "Inductor";
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Device.circuitSimulator.data, Device.V(inductor.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Device.circuitSimulator.data, Device.V(inductor.end), 0d);
    }

    @Override 
    public void setStart(Point start){
        inductor.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        inductor.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return inductor;
    }

    @Override
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){
        if(maxLength == 0) return;
        double length = Math.min(maxLength, Color.COMPONENT_MAX_SIZE);

        context.adjust(center, angle);
        // wire
        context.line(new Point(0,0) , new Point(-maxLength/2 , 0), gColor() , Color.LINE_WIDTH);
        context.line(new Point(0,0) , new Point(maxLength/2 , 0), gColor());
        // emptying wire from middle
        context.line( new Point(-0.5 * length , 0) , new Point(0.5 * length , 0)  , Color.BACKGROUND_COLOR , Color.LINE_WIDTH+1);
        // drawing device
        length = length/2;
        context.setSource(gColor());
        context.setLineWidth(Color.LINE_WIDTH);
        context.arc(Point.O().offsetX(-2d/3*length) , length/3, Math.PI , 0);
        context.arc(Point.O()                       , length/3, Math.PI , 0);
        context.arc(Point.O().offsetX(2d/3*length)  , length/3, Math.PI , 0);
        context.stroke();

        this.label = Utils.doubleToString(inductor.inductance) + " H";
        drawLabel(context, length);
       
        context.unadjust(center, angle);
    }

    @Override
    public void setValue(int index , Double value){
        this.inductor.inductance = value;
    }

    @Override
    public Double getValue(int index){
        return this.inductor.inductance;
    }

    @Override
    public String[] getFields(){
        return new String[]{"L"};
    }
}
