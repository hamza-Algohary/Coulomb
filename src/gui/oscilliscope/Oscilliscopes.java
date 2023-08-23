package gui.oscilliscope;

import java.util.Vector;

import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Revealer;
import gui.Component;

public class Oscilliscopes extends Revealer{
    private Vector<OscilliscopeFrame> oscilliscopes = new Vector<>();
    private Box box = new Box(Orientation.VERTICAL, 5);
    public Oscilliscopes(){
        this.setChild(box);
        OscilliscopeFrame.oscilliscopes = this;
        //box.append(Button.newWithLabelButton("GOOGLE"));
    }
    public void refresh(){
        for(var o : oscilliscopes)
            o.oscilliscope.queueDraw();
    }
    public void addComponent(Component component){
        var oscilliscopeFrame = new OscilliscopeFrame(component);
        oscilliscopes.add(oscilliscopeFrame);
        oscilliscopeFrame.setVexpand(false);
        oscilliscopeFrame.setValign(Align.START);
        this.addCssClass("background");
        box.addCssClass("background");
        box.append(oscilliscopeFrame);
        setRevealChild(true);
    }
    public void removeOscilliscope(OscilliscopeFrame oscilliscope){
        box.remove(oscilliscope);
        oscilliscopes.remove(oscilliscope);
        if(oscilliscopes.size() == 0){
            setRevealChild(false);
        }
    }
}
