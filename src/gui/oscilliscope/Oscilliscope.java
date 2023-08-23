package gui.oscilliscope;

import ch.bailu.gtk.cairo.Surface;
import ch.bailu.gtk.gobject.FlagsValue;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.EventControllerScroll;
import circuitsimulator.Device;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.Component;

public class Oscilliscope extends DrawingArea {
    Surface surface;
    Trace iTrace, vTrace;
    boolean showI = true, showV = true;

    public Oscilliscope(Component component) {
        this.setSizeRequest(300, 200);
        iTrace = new Trace(component, Color.I_TRACE);
        vTrace = new Trace(component, Color.V_TRACE_COLOR);
        vTrace.traceV = true;
        this.setDrawFunc((cb, self, c, width, height, userData) -> {
            MyContext context = new MyContext(c);
            context.setSource(Color.BACKGROUND_COLOR);
            context.paint();

            context.line(new Point(0, height / 2d), new Point(width, height / 2d), Color.FOREGROUND_COLOR);
            context.line(new Point(20, 0), new Point(20, height), Color.FOREGROUND_COLOR);
            iTrace.draw(context, width, height, showI);
            vTrace.draw(context, width, height , showV);

        }, null, (cb, data) -> {
            this.unregisterCallbacks();
        });

        this.onResize((width, height) -> {
            if (surface != null)
                surface.destroy();
            surface = this.getNative().getSurface().createSimilarSurface(0, width, height);
            this.queueDraw();
        });

        EventControllerScroll scroll = new EventControllerScroll(1);
        this.addController(scroll);
        scroll.onScroll((double dx, double dy) -> {
            //System.out.println("scrolling....");
            vTrace.yScale += dy;
            iTrace.yScale += dy;
            return true;
        });

    }
}