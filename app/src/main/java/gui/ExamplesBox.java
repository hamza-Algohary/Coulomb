package gui;

import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.ScrolledWindow;

public class ExamplesBox extends Box{
    HamburgerMenu menu;
    ScrolledWindow scrolledWindow = new ScrolledWindow();
    Box scrolledBox = new Box(Orientation.VERTICAL, 1);
    BackButton backButton;
    ExamplesBox(HamburgerMenu menu){
        super(Orientation.VERTICAL, 0);
        this.menu = menu;

        backButton = new BackButton("Examples", menu);

        append(backButton);
        append(scrolledWindow);
        scrolledWindow.setChild(scrolledBox);
        scrolledBox.setVexpand(true);

        addExample("Inductor"           , "examples/inductor");
        addExample("Capacitor"          , "examples/capacitor");
        addExample("Oscillating Circuit", "examples/rlc");
        addExample("LED"                , "examples/led");
        addExample("Zener Diode"        , "examples/zener-diode");
        addExample("Positive Clipper"   , "examples/positive-clipper");
        addExample("Negative Clipper"   , "examples/negative-clipper");
        addExample("Biased Clipper"     , "examples/biased-clipper");
        addExample("Double Clipper"     , "examples/double-clipper");
    }
    public void addExample(String name , String path){
        MenuButton button = new MenuButton(name, Align.START, ()->{
            menu.stack.setVisibleChild(menu.box);
            menu.hide();
            Main.app.drawingArea.loadExample(path);
            Main.app.drawingArea.path = null;
            //System.out.println("I GOT PRESSED");
        }, menu);
        scrolledBox.append(button);
    }

    public final static String OHM = "";
    public final static String THEVENIN = "";
    public final static String NORTON = "";
    public final static String INDUCTOR = "";
    public final static String CAPACITOR = "";
    public final static String AC = "";
    public final static String DIODE = "";
    public final static String ZENER_DIODE = "";
    public final static String CLIPPER = "";
    public final static String CLAMPER = "";
    public final static String RESONANT_RLC = "";
}