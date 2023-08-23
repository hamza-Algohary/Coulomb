package gui.oscilliscope;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

import ch.bailu.gtk.cairo.Device;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class Trace {
    //String varName = "";
    Color color = new Color(0, 0, 0, 1);
    //double yScale = 1;
    //int pointsMax = 10;
//
    //boolean differnce = false;
    //String endVarName = "";

    Component component;
    private Vector<Point> points = new Vector<>();

    public Trace(Component component , Color color){
        this.component = component;
        this.color = color;
    }
    //private double maxY = -1e9;
    //private double minY = 1e9;
    private int maxPoints = 100;
    double yScale = 10;
    private void addPoint(Point point){
        //maxY = Math.max(point.y, maxY);
        //minY = Math.min(point.y, minY);
        points.add(point);
    }

    boolean traceV = false;
    //public Trace(String varName, Color color) {
    //    this.varName = varName;
    //    this.color = color;
    //}
//
    //public Trace(String varName , String endVarName, Color color) {
    //    this.varName = varName;
    //    this.color = color;
    //    this.endVarName = endVarName;
    //    this.differnce = true;
    //}

    public void draw(MyContext context , int width , int height , boolean show){
        if(!traceV){
            addPoint( new Point(circuitsimulator.Device.circuitSimulator.clock.current_time_micro_second , component.I()));
        }else{
            addPoint( new Point(circuitsimulator.Device.circuitSimulator.clock.current_time_micro_second , component.voltage()));
        }
        if(!show)
            return;
        //context.setSource(color);
        int length = Math.min(maxPoints , points.size());
        double xScale =  (double) width / (double) length;
         //(double) height / (double) (maxY - minY) ;
        //System.out.println("Drawing");
        for(int i=points.size()-length+1 ,x=0 ; i<points.size() ; i++,x++){

            //System.out.println("Looping");
            Point p = points.get(i);
            Point p0 = points.get(i-1);
            context.line(new Point( (x-1)*xScale , -p0.y*yScale + height/2d), new Point( x*xScale , -p.y*yScale + height/2d), color);
            //System.out.println(p.toString());
            //context.circle(new Point( x*xScale , -p.y*yScale + height/2d) , 2);
            //context.fill();
        }
        
    }
        /*int i=0;
        context.moveTo(new Point(0 , 0));
        double xStep = (double)width/pointsMax;
        System.out.println("SIZE =" + storage.size());
        for(var vars : storage){
            Double y = vars.get(varName);
            System.out.println(y);
            //System.out.println(varName);
            Double y2 = vars.get(endVarName);
            if(y==null) 
                y=5d;
            if(differnce){
                if(y2 == null)
                    y2=5d;
                y = y-y2;
            }
            Point point = new Point(y*yScale + (height/2d) , i*xStep);
            //Point point = new Point(height + , i*10);
            System.out.println("POINT = " + point.toString());
            context.lineTo(point);
            context.stroke();
            i++;*/
        //}
    //}

}
