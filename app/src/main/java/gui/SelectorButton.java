package gui;

import ch.bailu.gtk.gtk.Gtk;
import ch.bailu.gtk.gtk.Image;
//import ch.bailu.gtk.lib.bridge.Image;
import ch.bailu.gtk.gtk.ToggleButton;
import graphics.Color;
import ch.bailu.gtk.lib.util.JavaResource;

public class SelectorButton extends ToggleButton {
    public static StackContainer container;
    public String name = "";

    public void setDevice(String name){
        this.name = name;
        updateSettings();
    }
    SelectorButton(){
        this.getStyleContext().addClass("flat");
        this.setMarginBottom(MainHeaderBar.headerbarChildrenMargin);
        this.setMarginTop(MainHeaderBar.headerbarChildrenMargin);   
    }

    void updateSettings(){
        try{
            Image image = Image.newFromPixbufImage(Color.pixbufFromResource(name));
            this.setChild(image);
        }catch(Exception E){}
    }
    public SelectorButton(String name){
        this();
        this.setDevice(name);
    }
}
