package gui;

import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Image;
import graphics.Color;

public class DeleteButton extends Button{
    
    public DeleteButton() {
        this.getStyleContext().addClass("flat");
        this.setMarginBottom(MainHeaderBar.headerbarChildrenMargin);
        this.setMarginTop(MainHeaderBar.headerbarChildrenMargin);   

        this.onClicked(() -> {
            Main.app.drawingArea.deleteSelectedComponent();
        });
        this.updateSettings();
    }

    public void updateSettings(){
        try{
            Image image = Image.newFromPixbufImage(Color.pixbufFromResource("delete",32));
            this.setChild(image);
        }catch(Exception E){}
    }
}
