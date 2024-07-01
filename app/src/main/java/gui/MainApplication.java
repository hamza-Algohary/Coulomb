package gui;

import java.util.function.Function;

import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.adw.ColorScheme;
import ch.bailu.gtk.gdk.Gdk;
import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gdk.RGBA;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.CssProvider;
import ch.bailu.gtk.gtk.EventControllerKey;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.StyleContext;
import graphics.Color;
import gui.oscilliscope.Oscilliscopes;

public class MainApplication extends Application{
    ApplicationWindow window;
    MainHeaderBar headerBar;
    StackContainer drawingArea;
    Oscilliscopes oscilliscopes;
    Box mainBox , leftBox;
    ParamtersEditor editor;
    CssProvider cssProvider;

    public boolean ctrlIsPressed = false;

    Function<Void,Void> onStart = (Void v)->{return null;};
    MainApplication(String args[]){
        super("com.example.hello", ApplicationFlags.FLAGS_NONE);
        this.onActivate(()->{
            window = new ApplicationWindow(this);
            headerBar = new MainHeaderBar();
            leftBox = new Box(Orientation.VERTICAL , 0);
            oscilliscopes = new Oscilliscopes();
            drawingArea = new StackContainer(headerBar.devicesSelector , oscilliscopes);

            mainBox = new Box(Orientation.HORIZONTAL , 0);
            editor = new ParamtersEditor(drawingArea);

            cssProvider = new CssProvider();
            loadStyle();
            window.setTitle("CircuitSimulator");
            window.setTitlebar(headerBar);
            window.setChild(mainBox);
            window.setDefaultSize(950, 600);
            window.setIconName("electron");
            
            mainBox.append(leftBox);
            mainBox.append(oscilliscopes);

            leftBox.append(drawingArea);
            leftBox.append(editor);

            drawingArea.setSizeRequest(400, 300);

            drawingArea.setHexpand(true);
            drawingArea.setVexpand(true);
            editor.setVexpand(false);
            editor.setValign(Align.END);
            leftBox.setHexpand(true);
            oscilliscopes.setHexpand(false);

            onStart.apply(null);

            EventControllerKey keyboard = new EventControllerKey();
            window.addController(keyboard);
            keyboard.onKeyPressed((int keyValue , int keyCode , int z)->{
                if(keyValue == GdkConstants.KEY_Control_L || keyValue == GdkConstants.KEY_Control_R){
                    System.out.println("Ctrl is pressed.");
                    ctrlIsPressed = true;
                }
                drawingArea.keyPressed(keyValue, keyCode);
                drawingArea.queueDraw();
                System.out.println("WIDTH = " + window.getWidth() + "HEIGHT = " + window.getHeight());
                return true;
            });
            keyboard.onKeyReleased((int keyValue , int keyCode , int z)->{
                if(keyValue == GdkConstants.KEY_Control_L || keyValue == GdkConstants.KEY_Control_R){
                    System.out.println("Ctrl is released");
                    ctrlIsPressed = false;
                }
                drawingArea.keyReleased(keyValue, keyCode);

                String letter = Gdk.keyvalName(keyValue).toString();

                if(keyValue == GdkConstants.KEY_Escape) headerBar.devicesSelector.buttons[0].setActive(true);
                else for(int i=0 ; i<headerBar.devicesSelector.keys.length ; i++){
                    if(letter.equals(headerBar.devicesSelector.keys[i])){
                        headerBar.devicesSelector.buttons[i].setActive(true);
                    }
                }

                drawingArea.queueDraw();
            });

            StyleContext.addProviderForDisplay(window.getDisplay(), cssProvider.asStyleProvider(), 1000);
            this.getStyleManager().setColorScheme(ColorScheme.PREFER_LIGHT);
            updateSettings();

            window.show();

        });

    }

    private void loadStyle() {
        cssProvider.loadFromData(style(), style().length());
    }

    public void updateSettings(){
        loadStyle();
        RGBA background = new RGBA();
        RGBA foreground = new RGBA();
        RGBA sharperForeground = new RGBA();
        RGBA electron_color = new RGBA();
        RGBA positive = new RGBA();
        RGBA negative = new RGBA();
        RGBA hover = new RGBA();
        RGBA red = new RGBA();

        window.getStyleContext().lookupColor("window_bg_color", background);
        window.getStyleContext().lookupColor("window_fg_color", foreground);
        window.getStyleContext().lookupColor("headerbar_fg_color", sharperForeground);

        String n = "5" , blue = "blue_1";
        if(this.getStyleManager().getDark() ){
            n = "1";
            blue = "blue_5";
        }
        window.getStyleContext().lookupColor(blue, electron_color);
        window.getStyleContext().lookupColor("red_3", negative);
        window.getStyleContext().lookupColor("red_3", red);
        window.getStyleContext().lookupColor("green_"+n, positive);
        window.getStyleContext().lookupColor("purple_"+n, hover);

        //Color.RED = new Color(red);
        Color.BACKGROUND_COLOR = new Color(background);
        //Color.FOREGROUND_COLOR = new Color(foreground);
        Color.SHARPER_FOREGROUND_COLOR = new Color(foreground);
        
        if(this.getStyleManager().getDark()){
            Color.FOREGROUND_COLOR = new Color(129.0/255,126.0/255,126.0/255 , 1);
        }else{
            Color.FOREGROUND_COLOR = new Color(foreground);
        }

        //Color.SHARPER_FOREGROUND_COLOR = 
        //Color.ELECTRON_COLOR = new Color(electron_color);
        //Color.I_TRACE = new Color(electron_color);
        //Color.V_TRACE_COLOR = new Color(negative);

        //Color.POSITIVE_POTENTIAL = new Color(positive);
        //Color.NEGATIVE_POTENTIAL = new Color(negative);

        //Color.HOVER_COLOR = new Color(hover);

        Color.dark = this.getStyleManager().getDark();
        //if(debug){
            Color.BACKGROUND_COLOR.print("bg");
            Color.FOREGROUND_COLOR.print("fg");
        //}
        
        headerBar.devicesSelector.updateSettings();
        headerBar.runButton.updateSettings();
        headerBar.hamburgerMenuButton.menu.examplesBox.backButton.updateSettings();
        headerBar.autoCenterButton.updateSettings();
    }

    public void onStart(Function<Void,Void> function){
        this.onStart = function;
    }

    public final String style(){
        return """
        .mycard{
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            border-radius: 10px;
        }
        .myshortcut{
            background: #363636;
            box-shadow: 0 0 0 1px rgba(0,0,0,0.03) , 0 1px 3px 1px rgba(0,0,0,0.07) , 0 2px 6px 2px rgba(0,0,0,0.03);
            padding: 7px;
            border-radius: 7px;
        }
        .my-menu-button{
            font-style: normal;
            font-weight: normal;
        }

        .my-entry{
            outline-width: 0px;
        }
            """+(this.getStyleManager().getDark()?"":"""
        .myshortcut{
            background: #ffffff;
        }
                """);
    }

    public void reset(){
        drawingArea.reset();
    }
}