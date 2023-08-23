package gui;

import java.util.function.Function;

import ch.bailu.gtk.adw.Application;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.EventControllerKey;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Revealer;
import gui.oscilliscope.Oscilliscopes;

public class MainApplication extends Application{
    ApplicationWindow window;
    MainHeaderBar headerBar;
    StackContainer drawingArea;
    Oscilliscopes oscilliscopes;
    Box mainBox , leftBox;
    ParamtersEditor editor;

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

            window.setTitle("CircuitSimulator");
            window.setTitlebar(headerBar);
            window.setChild(mainBox);
            window.setDefaultSize(400, 300);

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
            keyboard.onKeyReleased((int keyValue , int keyCode , int z)->{
                drawingArea.keyReleased(keyValue, keyCode);
            });

            window.show();

        });

        
    }
    public void onStart(Function<Void,Void> function){
        this.onStart = function;
    }
}