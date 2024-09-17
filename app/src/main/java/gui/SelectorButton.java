package gui;

import ch.bailu.gtk.gtk.Image;
//import ch.bailu.gtk.lib.bridge.Image;
import ch.bailu.gtk.gtk.ToggleButton;
import graphics.Color;

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
            Image image = Color.newImage(name);//Image.newFromPixbufImage(Color.pixbufFromResource(name,32));
            this.setChild(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public SelectorButton(String name){
        this();
        this.setDevice(name);
    }
}
