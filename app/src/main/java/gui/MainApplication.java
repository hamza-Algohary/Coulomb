package gui;

import java.util.function.Function;
import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.adw.ColorScheme;
import ch.bailu.gtk.gtk.MessageDialog;
import ch.bailu.gtk.gtk.MessageType;
import ch.bailu.gtk.gdk.Gdk;
import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gdk.RGBA;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.ButtonsType;
import ch.bailu.gtk.gtk.CssProvider;
import ch.bailu.gtk.gtk.DialogFlags;
import ch.bailu.gtk.gtk.EventControllerKey;
import ch.bailu.gtk.gtk.GtkConstants;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.StyleContext;
import ch.bailu.gtk.gtk.Window;
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
    CssProvider customThemeProvider;

    public boolean ctrlIsPressed = false;

    Function<Void,Void> onStart = (Void v)->{return null;};
    MainApplication(String args[]){
        super("io.github.hamza_algohary.coulomb", ApplicationFlags.DEFAULT_FLAGS);
        this.onActivate(()->{
            window = new ApplicationWindow(this);
            headerBar = new MainHeaderBar();
            
            leftBox = new Box(Orientation.VERTICAL , 0);
            oscilliscopes = new Oscilliscopes();
            drawingArea = new StackContainer(headerBar.devicesSelector , oscilliscopes);

            mainBox = new Box(Orientation.HORIZONTAL , 0);
            editor = new ParamtersEditor(drawingArea);

            cssProvider = new CssProvider();
            customThemeProvider = new CssProvider();

            loadStyle();

            window.setTitle("Coulomb");
            window.setTitlebar(headerBar);
            window.setChild(mainBox);
            window.setDefaultSize(950, 600);
            //window.setIconName("electron");
            window.setIconName("coulomb");
            
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
                    //System.out.println("Ctrl is pressed.");
                    ctrlIsPressed = true;
                }
                drawingArea.keyPressed(keyValue, keyCode);
                drawingArea.queueDraw();
                //System.out.println("WIDTH = " + window.getWidth() + "HEIGHT = " + window.getHeight());
                return true;
            });
            keyboard.onKeyReleased((int keyValue , int keyCode , int z)->{
                if(keyValue == GdkConstants.KEY_Control_L || keyValue == GdkConstants.KEY_Control_R){
                    //System.out.println("Ctrl is released");
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

            StyleContext.addProviderForDisplay(window.getDisplay(), customThemeProvider.asStyleProvider(), 0);
            StyleContext.addProviderForDisplay(window.getDisplay(), cssProvider.asStyleProvider(), 1000);
            this.getStyleManager().setColorScheme(ColorScheme.PREFER_LIGHT);
            updateSettings();

            window.show();

            new MyMessageDialog().run(window); 
//             new MessageDialog(window, DialogFlags.MODAL , MessageType.INFO , ButtonsType.OK , """ 
// 1. Circuits containing non-linear devices will most probably not be solvable, because the current backend is not good at dealing with non-linear systems, that's going to change though.
// 2. Due to the way inductors and capacitors are modeled you can't put two inductors in series, or two capacitors in parallel, that's also going to be fixed.""").show();
         });

    }

    private void loadStyle() {
        System.out.println("Loading Style");
        LightDarkSwitch.Selector.updateColorScheme();

        // if(customThemeProvider != null) {
        //     var file = ch.bailu.gtk.gio.File.newForPath(new Str(Resource.PREFIX+Resources.css_gtk_0_gtk_4_Fluent_round_Dark_themes_));
        //     System.out.println("Attempting");
        //     customThemeProvider.loadFromFile(file);
        //     System.out.println("Finished the attempt.");
        // }

        if(cssProvider != null)
            cssProvider.loadFromData(style(), style().length());
        System.out.println("Loaded Style");
    }

    // private List<String> systemStyle() {
    //     if(true) {
    //         /*return Color.stringFromResource(
    //             this.getStyleManager().getDark()?
    //             Resources.css_gtk_0_gtk_4_Fluent_round_Dark_themes_ :
    //             Resources.css_gtk_0_gtk_4_Fluent_round_Light_themes_
    //         );*/
    //         return Color.stringFromResource("themes/Fluent-round-Dark/gtk-4.0/gtk.css");
    //     } else if (Platform.isMac()) {
    //         return Color.longStringFromResource(
    //             this.getStyleManager().getDark()?
    //             Resources.css_gtk_0_gtk_4_WhiteSur_Dark_themes_:
    //             Resources.css_gtk_0_gtk_4_WhiteSur_Light_themes_
    //         );
    //     } else {
    //         return "";
    //     }
    // }

    public synchronized void updateSettings(){
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
        Color.BACKGROUND_COLOR = new Color(0,0,0,0);//new Color(background);
        //Color.FOREGROUND_COLOR = new Color(foreground);
        //Color.SHARPER_FOREGROUND_COLOR = new Color(foreground);
        
        if(this.getStyleManager().getDark()){
            Color.FOREGROUND_COLOR = new Color(129.0/255,126.0/255,126.0/255 , 1);
        }else{
            Color.FOREGROUND_COLOR = new Color(0.196,0.196,0.196);//new Color(foreground);
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
        headerBar.autoCenterButton.updateSettings();
        headerBar.deleteButton.updateSettings();
        Icon.updateAll();
    }

    public void onStart(Function<Void,Void> function){
        this.onStart = function;
    }

    public final String style(){
        return /*systemStyle()+*/"""
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
        .app-version {
            border-radius: 100px;
            padding-left: 18px;
            padding-right: 18px;
            padding-top: 3px;
            padding-bottom: 3px;
        }
        .app-version label {
            margin: 0px;
        }
            """+(this.getStyleManager().getDark()?"":"""
        .myshortcut{
            background: #ffffff;
        }
        """
        )+platformStyle();
    }

    public final String platformStyle() {
        if(Platform.isWindows()) {
            return """
                    windowhandle{
                        box-shadow:none;
                    }
                    windowcontrols button , windowcontrols button image{
                        margin:-3px ;
                        padding-left: 10px;
                        padding-right: 10px;
                        border-radius:0px;
                        background:transparent;
                    } 
                    windowcontrols {
                        margin-top:-29px;
                        margin-bottom:-10px;
                        margin-left:-5px;
                        margin-right: -5px
                    }    
                    windowcontrols button:hover {
                        background: """+ (this.getStyleManager().getDark()?"#373737;":" #e9e9e9;")  +""" 
                    }
                    windowcontrols button.close:hover {
                        background: #e81123;
                    }    
                    windowcontrols button.close:hover image{
                        color: #fcfcfc;
                    }
                    """;
        } else {
            return "";
        }
    }

    public void reset(){
        drawingArea.reset();
    }

    public Window getWindow() {
        return window;
    }
}