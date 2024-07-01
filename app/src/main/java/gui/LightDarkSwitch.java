package gui;

import ch.bailu.gtk.adw.ColorScheme;
import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.GestureClick;
import ch.bailu.gtk.gtk.Orientation;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;

public class LightDarkSwitch extends Box{
    public class Selector extends DrawingArea{
        int scheme = 0;
        Selector(Color color1 , Color color2 , int colorScheme){
            this.scheme = colorScheme;
            this.setDrawFunc( (cb, self, c, width, height, userData) -> {
                width = height;
                MyContext context = new MyContext(c);

                int linewidth = 3;

                boolean selected = (this.scheme == Main.app.getStyleManager().getColorScheme());
                //context.circle(new Point(width/2d , height/2d), width/2d);
                //context.clip();

                //context.setLineWidth(0);
                context.setSource(color1);
                //context.moveTo(0, 0); context.lineTo(width, 0); context.lineTo(0 , height); context.lineTo(0 , 0);
                context.arc(width/2d , height/2d , width/2d - linewidth , -4/3d * Math.PI , -1/3d * Math.PI);
                context.closePath();
                context.fillPreserve();
                context.stroke();

                context.newPath();
        
                context.setSource(color2);
                //context.moveTo(width , height); context.lineTo(width,0); context.lineTo(0 , height); context.lineTo(width , height);
                context.arc(width/2d , height/2d , width/2d - linewidth , -1/3d * Math.PI , -4/3d * Math.PI);

                context.closePath();
                context.fillPreserve();
                context.stroke();

                //if(color1 == color2){
                //    context.setSource(color1);
                //    context.paint();
                //}

                if(selected)
                    context.circle(new Point(width/2d , height/2d), width/2d - linewidth , new Color(212/255d, 90/255d, 33/255d , 1) , linewidth);
                else if(color1 != color2 && !Main.app.getStyleManager().getDark())
                    context.circle(new Point(width/2d , height/2d), width/2d - linewidth, Color.DARK , 1);
                
                //context.circle(new Point(width/2d , height/2d), width/2d - linewidth , selected ? new Color(212/255d, 90/255d, 33/255d , 1) : Color.FOREGROUND_COLOR, selected?linewidth:1);


            } , null , (cb, data) -> {
                this.unregisterCallbacks();
            });

            var click = new GestureClick();
            click.setButton(GdkConstants.BUTTON_PRIMARY);
            click.onPressed((int n_press, double x, double y)->{
                Main.app.getStyleManager().setColorScheme(colorScheme);
                System.out.println("COLOR SCHEME = " + colorScheme);
                update();
            });

            this.addController(click);

            int dimensions = 50;
            this.setSizeRequest(dimensions, dimensions);

            int margin = 5;
            this.setMarginBottom(margin);
            this.setMarginTop(margin);
            this.setMarginStart(margin);
            this.setMarginEnd(margin);

        }
    }

    Selector lightSelector = new Selector(Color.LIGHT , Color.LIGHT , ColorScheme.FORCE_LIGHT);
    Selector darkSelector = new Selector(Color.LIGHT , Color.DARK , ColorScheme.PREFER_LIGHT);
    Selector systemSelector = new Selector(Color.DARK , Color.DARK , ColorScheme.FORCE_DARK);

    //Button lightSelector = Button.newWithLabelButton("L");
    //Button darkSelector = Button.newWithLabelButton("D");
    //Button systemSelector = Button.newWithLabelButton("S");

    void update(){
        lightSelector.queueDraw();
        darkSelector.queueDraw();
        systemSelector.queueDraw();
    }

    public LightDarkSwitch(){
        super(Orientation.HORIZONTAL, 0);

        this.addCssClass("auto");

        this.append(darkSelector);
        this.append(lightSelector);
        this.append(systemSelector);
    }
}
