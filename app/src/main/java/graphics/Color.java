package graphics;

import java.io.IOException;
import java.util.Scanner;

import org.checkerframework.common.reflection.qual.GetClass;

import ch.bailu.gtk.gdk.RGBA;
import ch.bailu.gtk.gdkpixbuf.Pixbuf;
import ch.bailu.gtk.lib.util.JavaResource;

public class Color{
    public double red , green , blue , alpha;
    public static boolean dark = false;
    public Color(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    public Color(double red, double green, double blue) {
        this(red, green, blue, 1);
    }
    public Color(RGBA color){
        red = color.getFieldRed();
        green = color.getFieldGreen();
        blue = color.getFieldBlue();
        alpha = color.getFieldAlpha();
    }

    public static double LINE_WIDTH = 5;
    public static double CIRCLE_RADIUS = 10;
    public static double COMPONENT_MAX_SIZE = 50;
    public static int MOUSE_RADIUS = 5;

    //public static Color GROUND_POTENTIAL = new Color(0.2, 0.2, 0.2, 1); // Gray
    public static Color POSITIVE_POTENTIAL = new Color(0, 0.8, 0.1, 1); // Greenish
    public static Color NEGATIVE_POTENTIAL = new Color(0.8, 0, 0.1, 1); // Reddish
    public static Color ELECTRON_COLOR = new Color(1.0 , 1.0 , 0 , 1);//new Color(0 , 0.478 , 0.8 , 1); //#007acc

    public static Color BACKGROUND_COLOR = new Color(0.98, 0.98, 0.98, 1); // White
    public static Color FOREGROUND_COLOR = new Color(0.2, 0.2, 0.2, 1);
    public static Color SHARPER_FOREGROUND_COLOR = new Color(0.2, 0.2, 0.2, 1);
    //public static Color SWITCH_COLOR = new Color(1, 1, 1, 1); // White
    public static Color HOVER_COLOR = new Color(0.9 , 0.1 , 0.8 , 1);
    public static Color RED = new Color(0.9 , 0.1 , 0.1 , 1);

    public static Color V_TRACE_COLOR = NEGATIVE_POTENTIAL;
    public static Color I_TRACE = ELECTRON_COLOR;

    public static Color LIGHT = new Color(1,1,1);
    public static Color DARK = new Color(32/255d, 32/255d, 32/255d , 1);

    public void print(String name){
        System.out.println(name + " = (" + red + " , " + green + " , " + blue + ")");
    }

    public static String icon(String name){
        //System.out.println("DARK = " + dark);
        return "/icons/"+(dark?"dark/":"light/")+name+".svg";
    }
    public static Pixbuf pixbufFromResource(String name) throws IOException {
        try (var inputStream = new JavaResource(Color.icon(name)).asStream()) {
            return ch.bailu.gtk.lib.bridge.Image.load(inputStream);
        }
    }

    public static String stringFromResource(String path) {
        var contextClassLoader = Thread.currentThread().getContextClassLoader();
        var stream = contextClassLoader.getResourceAsStream(path);
        if(stream == null)
            System.out.println("!!!!!!!!!!!!!!!!!PANIC!!!!!!!!!!!!!!!!!!!!!!"); 
        var scanner = new Scanner(stream, "UTF-8");
        var text = scanner.useDelimiter("\\A").next();
        System.out.println("TEXT = \n"+text);
        scanner.close();
        return text;
    }
} 