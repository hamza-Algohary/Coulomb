package gui;

import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Gtk;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Popover;

public class HamburgerMenu extends Popover {
    Box box = new Box(Orientation.VERTICAL , 0);
    Button preferencesButton = new Button();
    Button aboutButton = new Button();
    //About about = new About();
    HamburgerMenu(){
        this.setChild(box);

        preferencesButton.setLabel("Preferences");
        aboutButton.setLabel("About");

        box.append(preferencesButton);
        box.append(aboutButton);

        box.setVexpand(false);

        preferencesButton.setVexpand(false);
        aboutButton.setVexpand(false);

        box.getStyleContext().addClass("linked");
        //about.setTransientFor(getRoo);
        //aboutButton.onClicked(()->{
        //    about.
        //    //about.runDispose();
        //});
    }
}
