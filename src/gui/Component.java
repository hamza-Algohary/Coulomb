package gui;


import java.util.HashMap;
import java.util.Vector;
import java.util.function.Function;

import algebra.Utils;
import ch.bailu.gtk.cairo.Context;
import ch.bailu.gtk.gtk.Widget;
import ch.bailu.gtk.type.Dbl;
import circuitsimulator.Device;
import circuitsimulator.devices.Constants;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.components.ACVoltageSourceComponent;
import gui.components.BatteryComponent;
import gui.components.CapacitorComponent;
import gui.components.CurrentSourceComponent;
import gui.components.DiodeComponent;
import gui.components.InductorComponent;
import gui.components.LedComponent;
import gui.components.ResistorComponent;
import gui.components.SwitchComponent;
import gui.components.WireComponent;
import gui.components.ZenerDiodeComponent;

public class Component {
    protected Point lastStart = new Point();
    protected Point lastEnd = new Point();
    protected Point dragStartPoint = new Point();
    protected Point lastMouse = new Point();
    private boolean selected = false;
    boolean isselected(){
        return selected;
    }
    protected String label = "";

    protected double distanceBetweenElectrons = 10;
    protected double position = 0;
    public static double currentSpeedFactor = 40;
    public static final int FrameDurationMilliSeconds = 1;
    public static boolean running = false;
    
    public static final double frameDurationSeconds(){
        return FrameDurationMilliSeconds/1000d;
    }

    public static Function<Void , Void> onSelectedChange;
    public static void onSelectedChange(Function<Void , Void> callback){
        onSelectedChange = callback;
    }

    public Point start() {
        return null;
    }

    public Point end() {
        return null;
    }

    protected double vStart() {
        return Utils.getKey(Device.circuitSimulator.data ,  Device.V(start().toString()) , 0d);
    }

    protected double vEnd() {
        return Utils.getKey(Device.circuitSimulator.data ,  Device.V(end().toString()) , 0d);
    }

    public double voltage(){
        return vStart() - vEnd();
    }

    protected boolean nearStart(Point mouse) {
        return mouse.distanceFrom(start()) < Color.MOUSE_RADIUS;
    }

    protected boolean nearEnd(Point mouse) {
        return mouse.distanceFrom(end()) < Color.MOUSE_RADIUS;
    }

    protected boolean nearWire(Point mouse) {
        return mouse.distanceFromLine(start(), end()) < Color.MOUSE_RADIUS;
    }

    protected boolean nearDevice(Point mouse) {
        return (mouse.distanceFrom(Point.mid(start(), end())) < Color.COMPONENT_MAX_SIZE) || nearWire(mouse);
    }

    protected void move() {
        Point shift = lastMouse.relativeTo(dragStartPoint);
        setStart(lastStart.add(shift));
        setEnd(lastEnd.add(shift));
    }

    public double I(){
        //System.out.println("Contains = " + Device.circuitSimulator.data.containsKey(getDevice().I()));
        if(Device.circuitSimulator.data.containsKey(getDevice().I())){
            return Device.circuitSimulator.data.get(getDevice().I());
        }else{
            return 0d;
        }
    }

    public static Component newFromName(String name) {
        if (name.compareTo(Constants.W) == 0) {
            return new WireComponent();
        } else if (name.compareTo(Constants.V) == 0) {
            return new BatteryComponent();
        } else if (name.compareTo(Constants.R) == 0){
            return new ResistorComponent();
        } else if (name.compareTo(Constants.L) == 0){
            return new InductorComponent();
        } else if (name.compareTo(Constants.C) == 0){
            return new CapacitorComponent();
        } else if(name.compareTo(Constants.D) == 0){
            return new DiodeComponent();
        }else if(name.compareTo(Constants.ZD) == 0){
            return new ZenerDiodeComponent();
        }else if(name.compareTo(Constants.LED) == 0){
            return new LedComponent();
        }else if(name.compareTo(Constants.I) == 0){
            return new CurrentSourceComponent();
        }else if(name.compareTo(Constants.AC_V) == 0){
            return new ACVoltageSourceComponent();
        }else if(name.compareTo(Constants.SWITCH) == 0){
            return new SwitchComponent();
        }
        return new WireComponent();
    }
    protected boolean hover(){
        return nearDevice(lastMouse);
    }
    protected boolean highlight(){
        return hover() || selected;
    }
    protected Color pColor(){
        return highlight()?Color.HOVER_COLOR:Color.POSITIVE_POTENTIAL;
    }
    protected Color nColor(){
        return highlight()?Color.HOVER_COLOR:Color.NEGATIVE_POTENTIAL;
    }
    protected Color gColor(){
        return highlight()?Color.HOVER_COLOR:Color.GROUND_POTENTIAL;
    }
    public Device getDevice() {
        return null;
    }

    protected enum Mode {
        IGNORE, MOVE_START, MOVE_END, MOVE
    }

    protected Mode mode = Mode.IGNORE;

    public boolean dragStart(Point mouse) {
        dragStartPoint = mouse;
        lastStart = start();
        lastEnd = end();
        if (nearStart(mouse))
            mode = Mode.MOVE_START;
        else if (nearEnd(mouse))
            mode = Mode.MOVE_END;
        else if (nearDevice(mouse))
            mode = Mode.MOVE;
        else {
            mode = Mode.IGNORE;
            return false;
        }
        return true;
    }

    public boolean dragUpdate(Point mouse) {
        // lastMouse = mouse;
        // System.out.println(mouse.toString());
        switch (mode) {
            case MOVE_START:
                setStart(mouse);
                break;
            case MOVE_END:
                setEnd(mouse);
                break;
            case MOVE:
                move();
                break;
            case IGNORE:
                return false;
        }
        return true;
    }

    public boolean dragEnd(Point mouse) {
        mode = Mode.IGNORE;
        return dragUpdate(mouse);
    }

    public boolean hover(Point mouse) {
        lastMouse = mouse;
        return nearDevice(mouse);
    }

    public boolean click(Point mouse){
        boolean responded = false;
        if(nearDevice(mouse)){
            selected = !selected;
            responded = true;
        }else{
            selected = false;
        }
        onSelectedChange.apply(null);
        //System.out.println("Selected = "+selected);
        return responded;   
    }
    public void unclick(){
        selected = false;
        onSelectedChange.apply(null);
    }
    protected void onSelectChange(){

    }
    public boolean doubleClick(Point mouse){
        return nearDevice(mouse);
    }

    public void setStart(Point start){

    }

    public void setEnd(Point end){

    }

    public void onDraw(Context c){
        MyContext context = new MyContext(c);

        Point mid = Point.mid(start(), end());
        double length = start().relativeTo(end()).length();
        double angle = end().relativeTo(start()).angle();

        drawDevice(context, mid, length , angle , false);   
        drawCurrent(context);
        drawNodes(context);
        drawInfo(context);
    }
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){

    }
    protected void drawLabel(MyContext context , double componentSize){
        context.moveTo(new Point(-componentSize/2 , -componentSize/2 - 16));
        context.showText(label);
    }
    protected void drawInfo(MyContext context){
        if(nearDevice(lastMouse)){
            context.moveTo(lastMouse);
            context.setSource(Color.FOREGROUND_COLOR);
            context.showText("  "+ Utils.unsigned(I())+" Amps  "+Utils.unsigned(voltage())+"Volts");
        }
    }
    protected void drawNodes(MyContext context){
        context.setSource(Color.HOVER_COLOR);
        if(nearStart(lastMouse)){
            context.circle(start(), 7);
            context.fill();
        }else if(nearEnd(lastMouse)){
            context.circle(end(), 7);
            context.fill();
        }
    }
    protected void drawCurrent(MyContext context){
        if(I()!=0 && running){
            Point start , end;
            if(I()>0){
               start = start(); 
               end = end();
            } else {
                start = end();
                end = start();
            } 

            double angle = end().relativeTo(start()).angle();
            Point currentStart = start.offsetPolar(distanceBetweenElectrons*position, angle);   
            context.setDash(new Dbl(new double[]{1 , 9}), 2, 0);   
            context.line(currentStart, end , Color.ELECTRON_COLOR);
            context.setDash(new Dbl(new double[]{1}), 1, 0);
            stepCurrent();              
        }
    }
    protected void stepCurrent(){
        position += I()*currentSpeedFactor*frameDurationSeconds();
        position -= (int)position;
    }
    //public Widget getEditWidget(){
    //    return null;
    //}
    public boolean keyReleased(int value , int code){
        return false;
    }
    public boolean deletePressed(){
        return selected;
    }

    public void setValue(int index , Double value){
        
    }

    public Double getValue(int index){
        return null;
    }

    public String[] getFields(){
        return new String[0];
    }

}
