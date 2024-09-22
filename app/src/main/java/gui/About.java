package gui;

import ch.bailu.gtk.gtk.Window;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.HeaderBar;
import ch.bailu.gtk.gtk.ListBox;
import ch.bailu.gtk.gtk.Orientation;


public class About extends Window{
    public About(){
        this.setTitlebar(new HeaderBar());

        ListBox main_box = new ListBox();
        this.setChild(main_box);

        
    }
}
