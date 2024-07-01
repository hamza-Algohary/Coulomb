package gui;

import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.Popover;

public class MenuButton extends Button{
    boolean hidePopover = true;
    public MenuButton(String text , int align , Runnable handler , Popover popover){
        Label label = new Label(text);
        //label.setUseMarkup(true);
        //label.setMarkup("<span style=\"normal\" >"+text+"</span>");
        label.setHalign(align);

        this.setChild(label);
        this.onClicked(()->{
            if(hidePopover) popover.hide();
            handler.run();
        });
        this.addCssClass("flat");
        label.addCssClass("my-menu-button");
    }

    public MenuButton(String text , int align , Runnable handler){
        this(text, align, handler, null);
        hidePopover = false;
    }
}
