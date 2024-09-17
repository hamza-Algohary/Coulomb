package gui;

import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Image;
import graphics.Color;

public class RunButton extends Button {
    boolean running = true;
    String name = "pause";
    public boolean running(){
        return running;
    }

    public RunButton() {
        //this.setIconName(Color.icon("play"));  
        updateSettings();
        this.onClicked(() -> {
            running = !running;
            this.updateSettings();
        });
    }

    public void updateSettings(){
        if (running)
            name = "pause";
        else
            name = "play";
        try{
            //Image image = Image.newFromPixbufImage(Color.pixbufFromResource(name,32));
            this.setChild(Color.newImage(name));
        }catch(Exception E){}
    }
}
