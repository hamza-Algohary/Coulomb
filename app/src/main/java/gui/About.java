package gui;

import ch.bailu.gtk.gtk.AboutDialog;
import ch.bailu.gtk.gtk.License;
import ch.bailu.gtk.type.Strs;

public class About extends AboutDialog{
    public About(){
        this.setAuthors(new Strs(new String[]{"Hamza Algohary"}));
        this.setLicenseType(License.GPL_3_0);
        this.setProgramName("Amber Circuit Simulator");
        this.setIconName("electron");
    }
}
