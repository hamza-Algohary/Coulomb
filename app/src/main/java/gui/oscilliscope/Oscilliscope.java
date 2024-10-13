package gui.oscilliscope;

import ch.bailu.gtk.cairo.Surface;
import ch.bailu.gtk.gtk.DrawingArea;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class Oscilliscope extends DrawingArea {
    Surface surface;
    Trace iTrace, vTrace;
    Component component = null;
    //public boolean showI = true, showV = true;
    //Color bgColor = Color.BACKGROUND_COLOR;
    public void sample(){
        iTrace.addSample();
        vTrace.addSample();
    }

    private double approximate(double x){
        return ((int)(x*100))/100d;
    }
    public Oscilliscope(Component component) {
        this.component = component;
        this.setSizeRequest(300, 200);
        iTrace = new Trace(component, Color.I_TRACE);
        vTrace = new Trace(component, Color.V_TRACE_COLOR);
        vTrace.traceV = true;

        this.setDrawFunc((cb, self, c, width, height, userData) -> {
            MyContext context = new MyContext(c);
            //context.setSource(Color.BACKGROUND_COLOR);
            context.setSource(new Color(0,0,0,0));
            context.paint();

            context.line(new Point(0, height / 2d), new Point(width, height / 2d), Color.FOREGROUND_COLOR); // X-axis
            //context.line(new Point(20, 0), new Point(20, height), Color.FOREGROUND_COLOR); // Y-axis
            iTrace.draw(context, width, height, component.showI);
            vTrace.draw(context, width, height , component.showV);

            context.setSource(Color.FOREGROUND_COLOR);
            context.moveTo(new Point(10 , 10));
            context.showText("Max: " + approximate(vTrace.getMax()) + "V , " + approximate(iTrace.getMax()) + "A");

            // To draw values on Y-axis, uncomment the following block
            /* 
            for(double i=-0.8 ; i<0.9 ; i+= 0.2){
                context.setSource(Color.FOREGROUND_COLOR);
                context.moveTo(new Point(20 , (height/2d*i) + height/2d));
                context.showText(""+ ((int)( (height/2d*-i)/vTrace.yScale * 100))/100d);
            }
            */
        }, null, (cb, data) -> {
            this.unregisterCallbacks();
        });

        this.onResize((width, height) -> {
            if (surface != null)
                surface.destroy();
            surface = this.getNative().getSurface().createSimilarSurface(0, width, height);
            this.queueDraw();
        });

        //var click = new GestureClick();
        //click.setButton(GdkConstants.BUTTON_PRIMARY);

        //EventControllerScroll scroll = new EventControllerScroll(1);
        //this.addController(scroll);
        //scroll.onScroll((double dx, double dy) -> {
        //    //System.out.println("scrolling....");
        //    vTrace.yScale += dy;
        //    iTrace.yScale += dy;
        //    return true;
        //});
    }

    public void reset(){
        iTrace.reset();
        vTrace.reset();
    }
}