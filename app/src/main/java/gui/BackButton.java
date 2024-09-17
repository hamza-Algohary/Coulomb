package gui;

import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.CenterBox;
import ch.bailu.gtk.gtk.Image;
import ch.bailu.gtk.gtk.Label;
import graphics.Color;

public class BackButton extends Button{
    Image image = new Image();// = Image.newFromFileImage(Color.icon("back"));

    public BackButton(String text , HamburgerMenu popover){
        Label label = new Label(text);
        label.setUseMarkup(true);
        label.setMarkup("<b>"+text+"</b>");
        label.setHalign(Align.CENTER);
        label.setHexpand(true);

        this.onClicked(()->{
            popover.stack.setVisibleChild(popover.box);
        });
        this.addCssClass("flat");
        label.addCssClass("my-menu-button");

        CenterBox box = new CenterBox();
        this.setChild(box);

        updateSettings();

        box.setStartWidget(image);
        box.setCenterWidget(label);

        

        image.setHexpand(false);

        //Box hBox = new Box(Orientation.HORIZONTAL, 1);

        //box.append(image);
        //box.append(label);
        //hBox.append(image2);
    }
    void updateSettings(){
        try{
            Color.initImage(image, "back");
            //image.setFromPixbuf(Color.pixbufFromResource("back",32));    
        }catch(Exception E){}
    }


}
