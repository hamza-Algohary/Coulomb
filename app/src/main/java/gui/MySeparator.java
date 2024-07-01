package gui;

import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Separator;

public class MySeparator extends Separator{
    MySeparator(){
        super(Orientation.HORIZONTAL);
        this.setMarginBottom(10);
        this.setMarginTop(5);
    }
}
