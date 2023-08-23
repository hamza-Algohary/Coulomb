package gui.oscilliscope;

import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.ToggleButton;
import gui.Component;

public class OscilliscopeFrame extends Box{
    public static Oscilliscopes oscilliscopes = new Oscilliscopes();
    Oscilliscope oscilliscope;
    Box controlsBox = new Box(Orientation.HORIZONTAL, 2);
    ToggleButton i , v;
    Button close;
    public OscilliscopeFrame(Component component){
        super(Orientation.VERTICAL, 3);
        oscilliscope = new Oscilliscope(component);

        i = ToggleButton.newWithLabelToggleButton("I");
        v = ToggleButton.newWithLabelToggleButton("V");
        close = Button.newFromIconNameButton("window-close-symbolic");
        i.setHalign(Align.FILL);
        v.setHalign(Align.FILL);
        close.setHalign(Align.FILL);
        controlsBox.setHalign(Align.END);

        controlsBox.append(i);
        controlsBox.append(v);
        controlsBox.append(close);
        //controlsBox.addCssClass("linked");
        close.addCssClass("flat");
        v.addCssClass("circular");
        i.addCssClass("circular");
        controlsBox.addCssClass("background");
        this.addCssClass("background");
        this.addCssClass("card");

        this.setMarginStart(10);
        this.setMarginEnd(10);
        this.setMarginTop(10);
        this.setMarginBottom(10);

        controlsBox.setMarginStart(5);
        controlsBox.setMarginEnd(5);
        controlsBox.setMarginTop(5);
        controlsBox.setMarginBottom(0);
        controlsBox.setVexpand(false);
        controlsBox.setValign(Align.START);
        this.append(controlsBox);
        this.append(oscilliscope);

        i.setActive(true);
        v.setActive(true);
        i.onToggled(()->{
            oscilliscope.showI = i.getActive();
        });
        v.onToggled(()->{
            oscilliscope.showV = v.getActive();
        });
        close.onClicked(()->{
            oscilliscopes.removeOscilliscope(this);
        });
        
    }
}
