package gui.components;

import algebra.Utils;
import circuitsimulator.Device;
import circuitsimulator.devices.Resistance;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class ResistorComponent extends Component {
    Resistance resistance = new Resistance("" ,"" , 100);
    public Point start(){return new Point(resistance.start);}
    public Point end(){return new Point(resistance.end);}
    //Box box = new Box(Orientation.HORIZONTAL, 5);

    //public ResistorComponent(){
    //    box.append(new Field("R =" , (Double number)->{
    //        this.resistance.resistance = number;
    //        return null;
    //    }));
    //}


    public ResistorComponent(){
        type = "r";
        name = "Resistor";
    }

    @Override 
    protected double vStart(){
        return Utils.getKey(Device.circuitSimulator.data, Device.V(resistance.start), 0d);
    }
    @Override 
    protected double vEnd(){
        return Utils.getKey(Device.circuitSimulator.data, Device.V(resistance.end), 0d);
    }

    @Override 
    public void setStart(Point start){
        resistance.start = start.toString();
    }
    @Override 
    public void setEnd(Point end){
        resistance.end = end.toString();
    }
    @Override 
    public Device getDevice(){
        return resistance;
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
        Point points[] = {
            new Point(  -1*length      ,0) ,         
            new Point(  -(5d/6d) * length ,  0.3*length),
            new Point(  -(3d/6d) * length , -0.3*length),
            new Point(  -(1d/6d) * length ,  0.3*length),
            new Point(   (1d/6d) *  length , -0.3*length),
            new Point(   (3d/6d) *  length ,  0.3*length),
            new Point(   (5d/6d) *  length , -0.3*length),
            new Point(  1*length , 0)              
        };
        context.join(points , gColor() , Color.LINE_WIDTH);

        this.label = Utils.doubleToString(resistance.resistance) + " Ω";
        drawLabel(context, length*2);

        context.unadjust(center, angle);
    }

    //@Override
    //public Widget getEditWidget(){
    //    return box;
    //}

    @Override
    public void setValue(int index , Double value){
        this.resistance.resistance = value;
    }

    @Override
    public Double getValue(int index){
        return this.resistance.resistance;
    }

    @Override
    public String[] getFields(){
        return new String[]{"R"};
    }

}
