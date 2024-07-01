package gui;

import graphics.Color;
import graphics.MyContext;
import graphics.Point;

public class CurrentDrawer {
    public static int speed_controller = 4;
    //public static double speedFactor = 40*speed_controller*100; 
    public static double speedFactor(){
        // Moderate speedFactor = 4000 , max = 10000 , min=1000;
        //return 40*speed_controller*100;
        return (speed_controller)*1000;
    }
    private static int distanceBetweenElectrons = Point.approximation;

    double position = 0;
    double step = 0;

    private void limitPosition(){
        if(position > 1) position = -1;
        else if(position < -1) position = 1;
    }
    //private void limitStep(){
    //    if (step > 1) step = 1;
    //    else if (step < -1) step = -1;
    //}

    void draw(MyContext context , Point start , Point end , double I){
        int numberOfParticles = (int) (start.distanceFrom(end)/Point.approximation + 1);

        step = Math.max(Math.min(I * speedFactor() , 253) , -253) * Component.frameDurationSeconds(); // The number 253 is just by experimentation.
        //limitStep();

        position += step;
        limitPosition();

        double angle = end.relativeTo(start).angle();

        for(int i=0 ; i<numberOfParticles ; i++){
            double magntiude = (i + position)*distanceBetweenElectrons;
            Point point = start.offsetPolar(magntiude , angle);
            
            if(point.distanceFrom(start) < end.distanceFrom(start) && point.distanceFrom(end) < start.distanceFrom(end)){
                context.circle(point, 1, Color.ELECTRON_COLOR , 4);
            }
        }
    }
}