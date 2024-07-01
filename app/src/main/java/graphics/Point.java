package graphics;


public class Point{
    public double x = 0;
    public double y = 0;
    public static int approximation = 15;
  
    public Point(){
        this.x = 0;
        this.y = 0;
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public static Point polar(double magnitude , double angle){
        return new Point(magnitude*Math.cos(angle) , magnitude*Math.sin(angle));
    }
    public Point(String point){
        var array = point.split("," , 2);
        try {x = Double.parseDouble(array[0]);} catch(Exception e){}
        try {y = Double.parseDouble(array[1]);} catch(Exception e){}

    }
    public Point offsetPolar(double magnitude , double angle){
        return this.add(Point.polar(magnitude, angle));
    }
    public static Point O(){
        return new Point(0,0);
    }
    /*public Point rotate(double angle){
        SimpleMatrix rotationMatrix = new SimpleMatrix( new double[][]{{Math.cos(angle), Math.sin(angle)},
                                     {-Math.sin(angle) , Math.cos(angle)}});
        SimpleMatrix point =  new SimpleMatrix( new double[]{this.x , this.y} );

        var rotatedPoint = rotationMatrix.mult(point);
        return new Point(rotatedPoint.toArray2()[0][0] , rotatedPoint.toArray2()[1][0]);
    }*/
    public Point scale(double scalar){
        return new Point(scalar*x , scalar*y);
    }
    public Point add(Point point){
        return new Point(x+point.x , y+point.y);
    }
    public Point offsetX(double x){return this.add(new Point(x , 0));}
    public Point offsetY(double x){return this.add(new Point(0 , y));}
    
    public double length(){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
    public double angle(){
        //return Math.atan(y/x);
        if(x == 0 && y == 0) return 0;
        double theta = Math.atan(y/x);
        if(x < 0){
            return Math.PI + theta;
        }else
        return theta;
    }
    
    public Point relativeTo(Point ref){
        return new Point(x-ref.x , y-ref.y);
    }
    public double slope(){
        return y/x;
    }

    //From StackOverflow.
    public double distanceFromLine(Point p1 , Point p2){
        double distance = Math.pow(p2.distanceFrom(p1) , 2);
        if(distance == 0) 
            return this.distanceFrom(p1);
        double t = ((x-p1.x)*(p2.x - p1.x) + (y-p1.y)*(p2.y-p1.y))/ distance;
        t = Math.min(Math.max(t , 0) , 1);
        return Math.pow( this.distanceFrom(new Point(p1.x + t*(p2.x - p1.x) , p1.y + t*(p2.y - p1.y))) , 2);
        /*Point tan = p2.relativeTo(p1);
        if(tan.x == 0)
            return Math.abs(this.x - p1.x);

        double m = -tan.slope();
        double c = -(p1.y - m*p1.x);

        return Math.abs(m*x + y + c)/Math.sqrt(Math.pow(m,2)+1);*/
        //double l1 = distanceFrom(p1) , l2 = distanceFrom(p2) , d = p1.distanceFrom(p2);
        //double distance = (Math.pow(l1, 2) - Math.pow(l2, 2) - Math.pow(d, 2)) / (-2*d);
        //return distance;
    }
    public double distanceFrom(Point point){
        return this.relativeTo(point).length();
    }
    public static Point mid(Point start , Point end){
        return start.add(end).scale(0.5);
    }
    private static int approximate(double num){
        int x = (int)num;
        if(x%approximation > approximation/2d) {
            return x + approximation - x%approximation; 
        }else{
            return x - x%approximation;
        }         
    }

    @Override
    public String toString(){
        return approximate(x)+","+approximate(y);

    }

    public static Point average(Point points[]){
        Point average = new Point();
        for(var point : points){
            average = average.add(point);
        }
        return average.scale(1d/points.length);
    }

    public static Point[] getBounds(Point points[]){
        Point start = points[0] , end = points[0];
        for(var point : points){
            start.x = Math.min(point.x, start.x);
            start.y = Math.min(point.y, start.y);

            end.x = Math.max(end.x , point.x);
            end.y = Math.max(end.y , point.y);
        }    

        System.out.println("Start = "+start);
        System.out.println("End = "+end);
        
        return new Point[]{start , end};
    }

}
