package gui.oscilliscope;

import java.util.Arrays;
import java.util.Vector;

import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class Trace {
    //String varName = "";
    Color color = new Color(0, 0, 0, 1);
    boolean reverse = false;
    //double yScale = 1;
    //int pointsMax = 10;
//
    //boolean differnce = false;
    //String endVarName = "";
    private double max = -1e-300 , min = Double.MAX_VALUE;
    Component component;
    private Vector<Point> points = new Vector<>();

    public Trace(Component component , Color color){
        this.component = component;
        this.color = color;
    }
    //private double maxY = -1e9;
    //private double minY = 1e9;
    private int maxPoints = 5000;
    double yScale = 2;
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
    public void addSample(){
        if(!traceV){
            addPoint( new Point(circuitsimulator.Device.circuitSimulator.clock.current_time_micro_second , component.I()));
        }else{
            addPoint( new Point(circuitsimulator.Device.circuitSimulator.clock.current_time_micro_second , component.voltage()));
        }
        if(points.size() > (maxPoints*2)) {
            cutVector();
        }
    }

    double limitMax(double m){
        return (m > 1e-3 || m < -1e-3) ? m : m >= 0 ? 1e-3 : -1e-3;
    }

    public void draw(MyContext context , int width , int height , boolean show){
        if(!show)
            return;
        //context.setSource(color);
        int length = Math.min(maxPoints , points.size());
        double xScale =  (double) width / (double) maxPoints;
         //(double) height / (double) (maxY - minY) ;
        //System.out.println("Drawing");


        //yScale = (int) Math.abs((1/3d)*height/max);
        //yScale = Math.min(yScale, 1000);

        //if(reverse)
        //    yScale *= -1;


        //if(points.size() <= points.size()-length) max = 0;
        //else max = points.get(points.size()-length).y;

        max = -1e-300;
        min = Double.MAX_VALUE;
        int step = maxPoints / width;

        for(int i=points.size()-length+step ; i<points.size() ; i+= step){
            max = Math.max(max , points.get(i).y);   
            min = Math.min(min , points.get(i).y);         
        }

        yScale = (int) Math.abs((1/3d)*height/ Math.max(Math.abs(max),Math.abs(min)) );
        yScale = Math.min(yScale, 1000);

        for(int i=points.size()-length+step ,x=0 ; i<points.size() ; i+= step , x+= step){

            //System.out.println("Looping");
            Point p = points.get(i);
            Point p0 = points.get(i-step);
            //max = Math.max(max, p.y);
            context.line(new Point( (x-1)*xScale , -p0.y*yScale + height/2d), new Point( x*xScale , -p.y*yScale + height/2d), color);
            //System.out.println(p.toString());
            //context.circle(new Point( x*xScale , -p.y*yScale + height/2d) , 2);
            //context.fill();
        }
        
    }

    double getMax(){
        return (points.size()<5) ? 0d : max; 
    }
    double getMin(){
        return (points.size()<5) ? 0d : min; 
    }
    void cutVector(){
        if(points.size() <= maxPoints)
            return;

        int length = Math.min(maxPoints , points.size());
        Point newPoints[] = new Point[length];
        for(int i=points.size()-length , x=0 ; i<points.size() && x<newPoints.length; i++ ,x++){
            newPoints[x] = points.get(i);
        }
        points = new Vector<Point>(Arrays.asList(newPoints));
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

    public void reset(){
        points.clear();
    }

}
