package gui;

import ch.bailu.gtk.gtk.Window;
import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.HeaderBar;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Separator;
import ch.bailu.gtk.gtk.SizeGroup;
import ch.bailu.gtk.gtk.SizeGroupMode;
import ch.bailu.gtk.gtk.Widget;

public class MyShortuctsWindow extends Window{
    Box hbox = new Box(Orientation.HORIZONTAL, 0);
    Box generalShortcuts = new Box(Orientation.VERTICAL  , 0);
    Box devciesShortcuts = new Box(Orientation.VERTICAL, 0);

    Box generalKeys = new Box(Orientation.VERTICAL, 0), generalActions = new Box(Orientation.VERTICAL, 0);
    Box devicesKeys = new Box(Orientation.VERTICAL, 0), devicesActions = new Box(Orientation.VERTICAL, 0);

    Separator separator = new Separator(Orientation.VERTICAL);

    HeaderBar headerBar = new HeaderBar();

    MyShortuctsWindow(ApplicationWindow parent){
        this.setResizable(false);
        this.setTransientFor(parent);
        this.setTitle("Shortcuts");
        this.setTitlebar(headerBar);

        headerBar.setShowTitleButtons(true);
        headerBar.addCssClass("flat");

        this.setChild(hbox);

        hbox.append(devciesShortcuts);
        hbox.append(separator);
        hbox.append(generalShortcuts);

        Box box1 = new Box(Orientation.HORIZONTAL, Orientation.HORIZONTAL);
        Box box2 = new Box(Orientation.HORIZONTAL, Orientation.HORIZONTAL);

        generalShortcuts.append(title("Commands"));
        generalShortcuts.append(box1);

        devciesShortcuts.append(title("Devices"));
        devciesShortcuts.append(box2);

        box1.append(generalKeys);
        box1.append(generalActions);

        box2.append(devicesKeys);
        box2.append(devicesActions);
/* 
        generalShortcuts.append(makeShortcut("Esc", "Hand Tool"));
        generalShortcuts.append(makeShortcut("Ctrl", "A", "Select All"));
        generalShortcuts.append(makeShortcut("Delete", "Delete"));

        devciesShortcuts.append(makeShortcut("B", "Battery"));
        devciesShortcuts.append(makeShortcut("I", "Current Source"));
        devciesShortcuts.append(makeShortcut("R", "Resistor"));
        devciesShortcuts.append(makeShortcut("C", "Capacitor"));
        devciesShortcuts.append(makeShortcut("L", "Inductor"));
        devciesShortcuts.append(makeShortcut("S", "Switch"));
        devciesShortcuts.append(makeShortcut("W", "Wire"));
        devciesShortcuts.append(makeShortcut("V", "AC Voltage Source"));
        devciesShortcuts.append(makeShortcut("D", "Diode"));
        devciesShortcuts.append(makeShortcut("Z", "Zener Diode"));
*/  
        addShortcuts();

        marginalize(hbox , 10);
        marginalize(generalShortcuts , 10);
        marginalize(devciesShortcuts , 10);
        marginalize(separator , 20 , 5);

    } 

    void addDevice(Widget keys[] , Widget action){
        Box box = new Box(Orientation.HORIZONTAL, 0);
        for(int i=0 ; i<keys.length-1 ; i++){
            box.append(keys[i]);
            box.append(label("+"));
        }
        box.append(keys[keys.length-1]);

        SizeGroup group = new SizeGroup(SizeGroupMode.VERTICAL);
        group.addWidget(box);
        group.addWidget(action);

        box.setHalign(Align.FILL);
        devicesKeys.append(box);

        action.setHalign(Align.START);
        devicesActions.append(action);
    }

    void addCommand(Widget keys[] , Widget action){
        Box box = new Box(Orientation.HORIZONTAL, 0);
        for(int i=0 ; i<keys.length-1 ; i++){
            box.append(keys[i]);
            box.append(label("+"));
        }
        box.append(keys[keys.length-1]);

        SizeGroup group = new SizeGroup(SizeGroupMode.VERTICAL);
        group.addWidget(box);
        group.addWidget(action);

        box.setHalign(Align.FILL);
        generalKeys.append(box);      

        action.setHalign(Align.START);
        generalActions.append(action);
    }



    public void addShortcuts(){
        addCommand(new Widget[]{cardedLabel("Esc")}, label("Hand Tool"));
        addCommand(new Widget[]{cardedLabel("Ctrl") , cardedLabel("A")}, label("Select All"));
        addCommand(new Widget[]{cardedLabel("Delete")}, label("Delete"));
        addCommand(new Widget[]{cardedLabel("Ctrl") , cardedLabel("Drag")}, label("Move All"));
        addCommand(new Widget[]{cardedLabel("Scroll")}, label("Zoom In/Out"));
        addCommand(new Widget[]{cardedLabel("Double Click")}, label("Open Ocsilloscope"));

        addDevice(new Widget[]{cardedLabel("B")}, label("Battery"));
        addDevice(new Widget[]{cardedLabel("I")}, label("Current Source"));
        addDevice(new Widget[]{cardedLabel("R")}, label("Resistor"));
        addDevice(new Widget[]{cardedLabel("C")}, label("Capacitor"));
        addDevice(new Widget[]{cardedLabel("L")}, label("Inductor"));
        addDevice(new Widget[]{cardedLabel("S")}, label("Switch"));
        addDevice(new Widget[]{cardedLabel("W")}, label("Wire"));
        addDevice(new Widget[]{cardedLabel("V")}, label("AC Voltage Source"));
        addDevice(new Widget[]{cardedLabel("D")}, label("Diode"));
        addDevice(new Widget[]{cardedLabel("Z")}, label("Zener Diode"));
    }

    Label cardedLabel(String str){
        Label label = label(str);
        label.addCssClass("myshortcut");
        label.setSizeRequest(25, 25);
        return label;
    }

    Label label(String label){
        Label l = new Label(label);
        marginalize(l);
        return l;
    }

/* 
    Box makeShortcut(String key1 , String key2 , String action){
        Box box = new Box(Orientation.HORIZONTAL, 0);
        box.append(cardedLabel(key1));
        box.append(label("+"));
        box.append(cardedLabel(key2));

        var l = label(action);       
        l.setHalign(Align.END);
        box.append(l);
        return box;        
    }

    Box makeShortcut(String shortcut , String action){
        Box box = new Box(Orientation.HORIZONTAL, 0);
        box.append(cardedLabel(shortcut));
        var l = label(action);
        l.setHalign(Align.END);
        box.append(l);
        return box;
    }
*/
    void marginalize(Widget widget , int x){
        marginalize(widget, x, x);
    }

    void marginalize(Widget widget , int top_bottom , int left_right){
        widget.setMarginBottom(top_bottom);
        widget.setMarginTop(top_bottom);
        widget.setMarginStart(left_right);
        widget.setMarginEnd(left_right);
    }

    void marginalize(Widget widget){
        marginalize(widget,5);
    }

    Label title(String title){
        Label label = label("");
        label.setMarkup("<big><b>" + title + "</b></big>");
        return label;
    }
    
}
