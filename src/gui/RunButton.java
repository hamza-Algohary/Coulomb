package gui;

import ch.bailu.gtk.gtk.Button;

public class RunButton extends Button {
    boolean running = false;
    public boolean running(){
        return running;
    }
    public RunButton() {
        this.setIconName("player_start");

        this.onClicked(() -> {
            running = !running;
            if (running)
                this.setIconName("media-pause");
            else
                this.setIconName("player_start");
            
        });
    }
}
