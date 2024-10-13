package graphics;

import java.io.IOException;
import java.util.Scanner;

import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gdk.Paintable;
import ch.bailu.gtk.gdk.RGBA;
import ch.bailu.gtk.gdkpixbuf.Pixbuf;
import ch.bailu.gtk.glib.Uri;
import ch.bailu.gtk.gtk.Gtk;
import ch.bailu.gtk.gtk.Image;
import ch.bailu.gtk.lib.util.JavaResource;
import ch.bailu.gtk.type.Str;
import ch.bailu.gtk.type.exception.AllocationError;
import gui.Main;
import gui.Platform;
import resources.Resource;

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

    // static {
    //     if(Platform.isWindows()) {
    //         new File(Platform.TEMP_DIR+"/icons/light").mkdirs();
    //         new File(Platform.TEMP_DIR+"/icons/dark").mkdirs();
    //     }
    // }

    public static double LINE_WIDTH = 5;
    public static double CIRCLE_RADIUS = 10;
    public static double COMPONENT_MAX_SIZE = 50;
    public static int MOUSE_RADIUS = 5;

    //public static Color GROUND_POTENTIAL = new Color(0.2, 0.2, 0.2, 1); // Gray
    public static Color POSITIVE_POTENTIAL = new Color(0, 0.8, 0.1, 1); // Greenish
    public static Color NEGATIVE_POTENTIAL = new Color(0.8, 0, 0.1, 1); // Reddish
    public static Color ELECTRON_COLOR = new Color(1.0 , 1.0 , 0 , 1);//new Color(0 , 0.478 , 0.8 , 1); //#007acc

    public static volatile Color BACKGROUND_COLOR = new Color(0.98, 0.98, 0.98, 1); // White
    public static volatile Color FOREGROUND_COLOR = new Color(0.2, 0.2, 0.2, 1);
    //public static volatile Color SHARPER_FOREGROUND_COLOR = new Color(0.2, 0.2, 0.2, 1);
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

    public static Color background() {
        return BACKGROUND_COLOR;
    }

    public static Color foreground() {
        return FOREGROUND_COLOR;
    }

    public static String colorScheme() {
        return (dark?"dark":"light");
    }

    public static String scaleFactor() {
        return Main.app.getWindow().getScaleFactor()==2?"scale_2":"scale_1";
    }

    public static String icon_scalable(String name){
        //System.out.println("DARK = " + dark);
        return "/icons/vector/"+colorScheme()+"/"+name+".svg";
    }

    public static String icon_raster(String name){
        //System.out.println("DARK = " + dark);
        return "/icons/raster/"+scaleFactor()+"/"+colorScheme()+"/"+name+".png";
    }

    public static Pixbuf pixbufFromResource(String name , int size) throws IOException , AllocationError {

        try (var inputStream = new JavaResource(Color.icon_scalable(name)).asStream()) {
            // if (Platform.isWindows()) {
            //     Platform.extractResource(Color.icon(name), Platform.TEMP_DIR +"/" +name+".svg");
            //     return Pixbuf.newFromFileAtSizePixbuf(Platform.TEMP_DIR + Color.icon(name), size, size);
            // }else {
                return ch.bailu.gtk.lib.bridge.Image.load(inputStream,size,size);
            //}
        }
    }

    public static Pixbuf pixbufFromResourceRaster(String path) throws IOException , AllocationError  {
        try (var inputStream = new JavaResource(Color.icon_raster(path)).asStream()) {
            return ch.bailu.gtk.lib.bridge.Image.load(inputStream);
        }
    }

    public static void initImage(Image image , String name) throws Exception {
        // if(Platform.isWindows()) {
        //     String target = Resource.PREFIX + Color.icon_raster(name);
        //     //Platform.extractResource("/win"+Color.icon_png(name), target);
        //     image.setFromFile(target);
        // } else {
        //     image.setFromPixbuf(Color.pixbufFromResource(name,32));
        // }
        initImage(image, name, 32);
    }

    public static void initImage(Image image , String name , int size) throws Exception {
        if(Platform.isWindows()) {
            String target = Resource.PREFIX + Color.icon_raster(name);
            //Platform.extractResource("/win"+Color.icon_png(name), target);
            image.setFromFile(target);
            //image.setFromPixbuf(Color.pixbufFromResourceRaster(name));
        } else {
            image.setFromPixbuf(Color.pixbufFromResource(name,size));
        }
    }

    public static Image newImage(String name , int size) throws Exception {
        Image image = new Image();
        initImage(image, name , size);
        return image;
    }

    public static Image newImage(String name) throws Exception {
        // Image image = new Image();
        // initImage(image, name);
        // return image;
        return newImage(name, 32);
    }

    public static Paintable logo() {
        try {
            return newImage("coulomb" , 256).getPaintable();
        }catch (Exception e) {
            System.out.println("Couldn't load logo.");
            return new Image().getPaintable();
        }
    }

    public static Image logoImage() {
        try {
            return newImage("coulomb" , 256);
        }catch (Exception e) {
            System.out.println("Couldn't load logo.");
            return new Image();
        }
    }

    public static String stringFromResource(String path) {
        var contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            var stream = contextClassLoader.getResourceAsStream(path);
            if(stream == null)
                System.out.println("!!!!!!!!!!!!!!!!!PANIC!!!!!!!!!!!!!!!!!!!!!!"); 
            var scanner = new Scanner(stream, "UTF-8");
            var text = scanner.useDelimiter("\\A").next();
            //System.out.println("TEXT = \n"+text);
            scanner.close();
            return text;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void launch_uri(String uri) {
        Gtk.showUri(Main.app.getWindow(), new Str(uri), GdkConstants.CURRENT_TIME);
    }

    public static void copyToClipboard(String text) {
        Main.app.getWindow().getClipboard().setText(text);
    }

} 