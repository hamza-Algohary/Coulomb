package gui.oscilliscope;

import java.util.Vector;


import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.PolicyType;
import ch.bailu.gtk.gtk.Revealer;
import ch.bailu.gtk.gtk.ScrolledWindow;
import gui.Component;

public class Oscilliscopes extends Revealer{
    private Vector<OscilliscopeFrame> oscilliscopes = new Vector<>();
    private ScrolledWindow scrolled = new ScrolledWindow();
    private Box box = new Box(Orientation.VERTICAL, 5);
    public Oscilliscopes(){
        this.setChild(scrolled);
        scrolled.setChild(box);
        scrolled.setSizeRequest(getAllocatedWidth(), getAllocatedHeight());
        scrolled.setPolicy(PolicyType.NEVER, PolicyType.ALWAYS);
        OscilliscopeFrame.oscilliscopes = this;
        //box.append(Button.newWithLabelButton("GOOGLE"));
    }
    public void clear(){
        oscilliscopes.clear();
    }
    public void remove(Component component){
        removeOscilliscope(component.oscilliscope);
    }
    public void draw(){
        for(var o : oscilliscopes){
            o.oscilliscope.queueDraw();
        }
    }

    public void sample(){
        for(var o : oscilliscopes){
            o.oscilliscope.sample();
        }
    }
    public void addComponent(Component component){
        component.oscilliscope = new OscilliscopeFrame(component);
        oscilliscopes.add(component.oscilliscope);
        component.oscilliscope.setVexpand(false);
        component.oscilliscope.setValign(Align.START);
        this.addCssClass("background");
        box.addCssClass("background");
        box.append(component.oscilliscope);
        setRevealChild(true);
    }
    public void removeOscilliscope(OscilliscopeFrame oscilliscope){
        box.remove(oscilliscope);
        oscilliscopes.remove(oscilliscope);
        if(oscilliscopes.size() == 0){
            setRevealChild(false);
        }
    }
    public void reset(){
        for(var oscilliscope : oscilliscopes){
            oscilliscope.reset();
        }
    }
}
