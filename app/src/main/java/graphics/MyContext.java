package graphics;

import ch.bailu.gtk.cairo.Context;
import ch.bailu.gtk.cairo.LineCap;
import ch.bailu.gtk.cairo.Pattern;
import ch.bailu.gtk.type.PointerContainer;

public class MyContext extends Context{
    private MyContext(PointerContainer pointer) {
        super(pointer);
    }
    public MyContext(Context context){
        this(new PointerContainer(context.asCPointer()));
        this.setLineCap(LineCap.ROUND);
    }
    public void setSource(Color color){
        this.setSourceRgba(color.red, color.green , color.blue , color.alpha);
    }
    public void line(Point start , Point end){
        this.moveTo(start);
        this.lineTo(end);
    }
    public void moveTo(Point point){
        this.moveTo(point.x, point.y);
    }
    public void lineTo(Point end){
        this.lineTo(end.x, end.y);
    }
    public void line(Point start , Point end , Color color){
        this.setSource(color);
        this.line(start, end);
        this.stroke();
    }
    public void curve(Point p1 , Point p2 , Point p3){
        this.curveTo(p1.x , p1.y , p2.x , p2.y , p3.x , p3.y);
    }
    public void circle(Point center, double radius) {
        this.arc(center.x, center.y, radius, 0, 2 * Math.PI);
    }
    public void wave(Point start , Point end){
        double angle = end.relativeTo(start).angle();
        var mid = Point.mid(start, end);

        var c1 = Point.mid(start, mid);
        var c2 = Point.mid(mid, end);

        double radius = mid.relativeTo(start).length();

        this.arc(c1.x, c1.y, radius, angle, angle+Math.PI);
        this.arc(c2.x, c2.y, radius, angle+Math.PI, angle + 2 * Math.PI);
    }
    public void translate(Point point){
        this.translate(point.x, point.y);
    }
    public void linearGradient(Point start , Color startColor , Point end , Color endColor){
        var pattern = new Pattern(start.x, start.y, end.x, end.y);
        pattern.addColorStopRgb(0,startColor.red , startColor.green , startColor.blue);
        pattern.addColorStopRgb(0,endColor.red , endColor.green , endColor.blue);
        this.setSource(pattern);
    }
    public void adjust(Point center , double angle){
        this.translate(center);
        this.rotate(angle);
    }
    public void unadjust(Point center , double angle){
        this.rotate(-angle);
        this.translate(center.scale(-1));
    }
    public void line(Point start , Point end , Color color, double linewidth){
        this.setLineWidth(linewidth);
        line(start, end, color);
    }
    public void join(Point points[] , Color color , double linewidth){
        this.setSource(color);
        this.setLineWidth(linewidth);
        for(int i=1 ; i<points.length ; i++){
            line(points[i-1], points[i]);
        }
        this.stroke();
    }
    public void joinCurve(Point points[] , Color color , double linewidth){
        this.setSource(color);
        this.setLineWidth(linewidth);
        for(int i=2 ; i<points.length ; i+=2){
            curve(points[i-2], points[i-1], points[i]);
        }
        this.stroke();
    }
    public void arc(Point center , double radius , double angleStart , double angleEnd){
        this.arc(center.x , center.y, radius, angleStart, angleEnd);
    }
    public void arc(Point center , double radius , double angle){
        this.arc(center, radius, 0 , angle);
    }
    public void circle(Point center , double radius , Color color , double linewidth){
        this.setSource(color);
        this.setLineWidth(linewidth);
        this.circle(center, radius);
        this.stroke();
    }
    public void rotateAboutCenter(double angle){
        
    }
}