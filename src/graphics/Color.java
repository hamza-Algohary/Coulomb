package graphics;

public class Color{
    public double red , green , blue , alpha;
    public Color(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public static double LINE_WIDTH = 5;
    public static double CIRCLE_RADIUS = 10;
    public static double COMPONENT_MAX_SIZE = 50;

    public static Color GROUND_POTENTIAL = new Color(0.2, 0.2, 0.2, 1); // Gray
    public static Color POSITIVE_POTENTIAL = new Color(0, 0.8, 0.1, 1); // Greenish
    public static Color NEGATIVE_POTENTIAL = new Color(0.8, 0, 0.1, 1); // Reddish

    public static Color BACKGROUND_COLOR = new Color(0.98, 0.98, 0.98, 1); // White
    public static Color FOREGROUND_COLOR = GROUND_POTENTIAL;
    public static Color SWITCH_COLOR = new Color(1, 1, 1, 1); // White
    public static Color HOVER_COLOR = new Color(0.9 , 0.1 , 0.8 , 1);
    public static Color ELECTRON_COLOR = new Color(0 , 0.478 , 0.8 , 1); //#007acc
    public static Color RED = new Color(0.9 , 0.1 , 0.1 , 1);

    public static Color V_TRACE_COLOR = RED;
    public static Color I_TRACE = ELECTRON_COLOR;


    public static int MOUSE_RADIUS = 5;

} 


