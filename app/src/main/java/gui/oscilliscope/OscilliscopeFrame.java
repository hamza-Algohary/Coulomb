package gui.oscilliscope;

import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Entry;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.ToggleButton;
import ch.bailu.gtk.type.Str;
import gui.Component;

public class OscilliscopeFrame extends Box{
    public static Oscilliscopes oscilliscopes = new Oscilliscopes();
    public Oscilliscope oscilliscope;
    Box controlsBox = new Box(Orientation.HORIZONTAL, 2);
    ToggleButton i , v;
    Button close;
    Entry componentNameEntry = new Entry();
    public OscilliscopeFrame(Component component){
        super(Orientation.VERTICAL, 0);

        component.showOscilliscope = true;

        oscilliscope = new Oscilliscope(component);

        i = ToggleButton.newWithLabelToggleButton("I");
        v = ToggleButton.newWithLabelToggleButton("V");
        close = Button.newFromIconNameButton("window-close-symbolic");
        controlsBox.setHalign(Align.FILL);
        i.setHalign(Align.START);
        v.setHalign(Align.START);
        close.setHalign(Align.START);
        controlsBox.setHalign(Align.END);

        componentNameEntry.setAlignment(Align.FILL);

        initComponentNameLabel();

        controlsBox.append(componentNameEntry);
        controlsBox.append(i);
        controlsBox.append(v);
        controlsBox.append(close);
        //controlsBox.addCssClass("linked");
        close.addCssClass("flat");
        v.addCssClass("circular");
        i.addCssClass("circular");
        this.addCssClass("mycard");
        controlsBox.addCssClass("background");
        this.addCssClass("background");

        this.setOverflow(1);

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

        i.setActive(oscilliscope.component.showI);
        v.setActive(oscilliscope.component.showV);
        
        i.onToggled(()->{
            oscilliscope.component.showI = i.getActive();
        });
        v.onToggled(()->{
            oscilliscope.component.showV = v.getActive();
        });
        close.onClicked(()->{
            oscilliscopes.removeOscilliscope(this);
            oscilliscope.vTrace.component.showOscilliscope = false;
        });
        
    }

    public void reset(){
        oscilliscope.reset();
    }

    private void initComponentNameLabel(){
        componentNameEntry.getBuffer().setText(oscilliscope.component.name == null ? "" : oscilliscope.component.name , oscilliscope.component.name == null ? 0 : oscilliscope.component.name.length());
        componentNameEntry.getBuffer().onInsertedText((int position, Str chars, int n_chars)->{
            oscilliscope.component.name = componentNameEntry.getBuffer().getText().toString();
        });
        componentNameEntry.getBuffer().onDeletedText((int position, int n_chars)->{
            oscilliscope.component.name = componentNameEntry.getBuffer().getText().toString();
        });

        componentNameEntry.addCssClass("flat");
        componentNameEntry.addCssClass("my-entry");
    }
}