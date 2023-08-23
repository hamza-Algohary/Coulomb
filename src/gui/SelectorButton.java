package gui;

import ch.bailu.gtk.gtk.Image;
import ch.bailu.gtk.gtk.ToggleButton;

public class SelectorButton extends ToggleButton {
    public static StackContainer container;
    public String name = "";

    public void setDevice(String name){
        this.name = name;
        Image image = Image.newFromFileImage("build/graphics/icons/"+name+".svg");
        this.setChild(image);
    }
    SelectorButton(){
        this.getStyleContext().addClass("flat");
        this.setMarginBottom(MainHeaderBar.headerbarChildrenMargin);
        this.setMarginTop(MainHeaderBar.headerbarChildrenMargin);    
    }
}
