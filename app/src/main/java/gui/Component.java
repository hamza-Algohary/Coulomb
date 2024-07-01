package gui;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

import algebra.Utils;
import ch.bailu.gtk.cairo.Context;
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
import gui.oscilliscope.OscilliscopeFrame;

public class Component {
    protected Point lastStart = new Point();
    protected Point lastEnd = new Point();
    protected Point dragStartPoint = new Point();
    protected Point lastMouse = new Point();
    protected boolean selected = false;
    protected boolean reverseVoltage = false;
    public boolean showI = false , showV = false;
    protected boolean hoverBlocked = false;
    //public boolean vMonitored = false , iMonitored = false;

    public OscilliscopeFrame oscilliscope = new OscilliscopeFrame(this);
    public boolean showOscilliscope = false;

    protected String type = "";
    public String name = "";

    public static ArrayList<String> split(String s , char delim){
        ArrayList<String> list = new ArrayList<>();
        s += delim;
        String str = "";
        for(char c : s.toCharArray()){
            if(c!=delim){
                str += c;
            }else if(str != ""){
                list.add(str);
                str = "";
            }
        }
        return list;
    }

    public static Component parse(String line){
        Component component;

        Scanner scanner = new Scanner(line);
        ArrayList<String> tokennVec = new ArrayList<>();
        while(scanner.hasNext()) tokennVec.add(scanner.next());
        scanner.close();

        var tokens = tokennVec.toArray(new String[0]);

        var t = split(tokens[0] , '.');
        //var type = scanner2.useDelimiter(".").next();
        //var name = scanner2.next();

        //System.out.println("token[0] = " + tokens[0]);
        //for(var text : type_and_name)
        //    System.out.println(text);

        try{component = Component.newFromName(t.get(0).trim());}catch(Exception e){e.printStackTrace(); return null;}        
        try{component.name = t.get(1) != null ? t.get(1) : "";}catch(Exception e){}
        

        try{component.setStart(new Point(tokens[1]));}catch(Exception e){e.printStackTrace();return null;}
        try{component.setEnd(new Point(tokens[2]));}catch(Exception e){e.printStackTrace();return null;}
        
        int nargs = component.getFields().length;

        for (int i=0 ; i<nargs ; i++){
            try{component.setValue(i , Double.parseDouble(tokens[i+3]));}catch(Exception e){e.printStackTrace();return null;}         
        }

        component.showV = false;
        component.showI = false;

        try{component.showV |= tokens[3+nargs].equals("v");}catch(Exception e){}
        try{component.showV |= tokens[4+nargs].equals("v");}catch(Exception e){}

        try{component.showI |= tokens[3+nargs].equals("i");}catch(Exception e){}
        try{component.showI |= tokens[4+nargs].equals("i");}catch(Exception e){}

        if(component.showI || component.showV)
            component.showOscilliscope = true;

        return component;
    }

    public String getType(){ return type; }
    public String getName(){ return name; }
        
    private CurrentDrawer currentDrawer = new CurrentDrawer();
    boolean isselected(){
        return selected;
    }
    protected String label = "";

    protected double originalDistanceBetweenElectrons = 19;
    protected double distanceBetweenElectrons = originalDistanceBetweenElectrons;
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
        return reverseVoltage ? vEnd() - vStart() : vStart() - vEnd() ;
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

    protected void move(Point mouse) {
        Point shift = mouse.relativeTo(dragStartPoint);
        System.out.println("MOVING = " + shift);
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
        System.out.println("NAME = " + name);
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
        return nearDevice(lastMouse) && !hoverBlocked && !nearStart(lastMouse) && !nearEnd(lastMouse);
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
        return highlight()?Color.HOVER_COLOR:Color.FOREGROUND_COLOR;
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
        //lastMouse = mouse;
        // System.out.println(mouse.toString());
        switch (mode) {
            case MOVE_START:
                setStart(mouse);
                break;
            case MOVE_END:
                setEnd(mouse);
                break;
            case MOVE:
                move(mouse);
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

    public boolean hover(Point mouse , boolean blocked) {
        System.out.println("MOUSE = " + mouse);
        this.hoverBlocked = blocked;
        lastMouse = mouse;
        return nearDevice(mouse);
    }

    public boolean click(Point mouse){
        boolean responded = false;
        if(nearDevice(mouse)){
            selected = !selected;
            responded = true;
            onClick();
        }else{
            selected = false;
        }
        //System.out.println("Selected = "+selected);
        onSelectedChange.apply(null);
        return responded;   
    }
    public void unclick(){
        selected = false;
        onSelectedChange.apply(null);
        //onSelectChange();
    }
    protected void onClick(){

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

    }
    protected void drawDevice(MyContext context , Point center , double maxLength , double angle , boolean hover){

    }
    protected void drawLabel(MyContext context , double componentSize){
        context.moveTo(new Point(-componentSize/2 , -componentSize/1.25 - 16));
        context.showText(name);
        context.moveTo(new Point(-componentSize/2 , -componentSize/2 - 16));
        context.showText(label);
    }
    protected void drawInfo(MyContext context){
        if(nearDevice(lastMouse)){
            context.moveTo(lastMouse);
            context.setSource(Color.FOREGROUND_COLOR);
            context.showText("  "+ Utils.doubleToString(Utils.unsigned(I()))+" Amps  "+ Utils.doubleToString(Utils.unsigned(voltage()))+"Volts");
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
        context.newPath();
        if(running)
            currentDrawer.draw(new MyContext(context) , start(), end(), I());
    }

    public void drawAdditionalInfo(Context c){
        MyContext context = new MyContext(c);
        drawCurrent(context);
        drawNodes(context);
        drawInfo(context);
    }

    //private boolean lastIwasPositive = false;
    //protected void drawCurrent(MyContext context){
        //Point Istart , Iend;
        //if(/*I()!=0 &&*/ running){
        //    if(I() > 0) {
        //        Istart = start();
        //        Iend = end();
        //    }else if ( I() < 0 ) {
        //        Istart = end();
        //        Iend = start();
        //    } else if (lastIwasPositive){
        //        Istart = start();
        //        Iend = end();
        //    } else {
        //        Istart = end();
        //        Iend = start();               
        //    }
        //
        //    double angle = end().relativeTo(start()).angle();
        //    Point currentStart = Istart/*.offsetPolar((distanceBetweenElectrons), angle)*/.offsetPolar((distanceBetweenElectrons)*position, angle);   
        //    context.setDash(new Dbl(new double[]{1 , distanceBetweenElectrons}), 2, 0);   
        //    context.line(currentStart, Iend , Color.ELECTRON_COLOR);
        //    context.setDash(new Dbl(new double[]{1}), 1, 0);
        //    stepCurrent();              
        //}
    //}
    //protected void stepCurrent(){
    //    double step = Math.max(Math.min(I() , 7.5), -7.5)*currentSpeedFactor*frameDurationSeconds();
    //    //if(step > 1 || step < -1){
    //    //    distanceBetweenElectrons -= step*5;
    //    //    if(distanceBetweenElectrons < 0) distanceBetweenElectrons = 0;
    //    //  if(step > 0.50) step = 0.50; else if(step < -0.5) step = -0.50;
    //    //}
    //    //distanceBetweenElectrons = originalDistanceBetweenElectrons;
//
    //    position += step;
    //    position -= (int)position;
    //}
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

    @Override public String toString(){
        String savedName = name.trim().replace(" ", "");
        String line = getType() + "." + savedName + " " + start() + " " + end();
        for(int i=0 ; i<getFields().length ; i++)
            line += (" " + getValue(i));
        
        if(oscilliscope!=null && showOscilliscope){
            line += showV?" v":"";
            line += showI?" i":"";
        }
        return line;
    }

    @Override 
    public boolean equals(Object point){
        if(point == null || point.getClass() != this.getClass())
            return false;

        return this == point || point.toString().equals(this.toString());
    }
    

}   
