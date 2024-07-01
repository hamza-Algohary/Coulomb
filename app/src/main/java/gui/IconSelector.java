package gui;

import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.ScrolledWindow;
import circuitsimulator.devices.Constants;

public class IconSelector extends ScrolledWindow {

    int numberOfDevices = 24;

    Box box = new Box(Orientation.HORIZONTAL, 3);

    String iconNames[] = {
            Constants.HAND, Constants.V, Constants.I, Constants.R, Constants.C, Constants.L, Constants.SWITCH,
            Constants.W, Constants.AC_V, Constants.D, Constants.ZD, Constants.LED /*
                                                                                   * , Constants.BJT_NPN ,Constants.
                                                                                   * BJT_PNP , Constants.F , Constants.G
                                                                                   * , Constants.AC_I
                                                                                   */
    };

    String keys[] = {null , "b" , "i" , "r" , "c" , "l" , "s" , "w" , "v" , "d" , "z" , null};
    SelectorButton buttons[] = new SelectorButton[iconNames.length + 1];

    IconSelector() {
        this.setChild(box);

        for (int i = 0; i < iconNames.length; i++) {
            buttons[i] = new SelectorButton();
            buttons[i].setDevice(iconNames[i]);
            buttons[i].setGroup(buttons[0]);
            box.append(buttons[i]);
        }
        this.setSizeRequest(375, -1);

        this.setHexpand(true);
        this.setHalign(Align.BASELINE);
        this.addCssClass("centered");
        box.setHalign(Align.CENTER);

        buttons[0].setActive(true);

    }

    public String getSelected() {
        for (int i = 0; i < buttons.length; i++) {
            // System.out.println("ICON = " + iconNames[i]);
            if (buttons[i].getActive()) {
                return iconNames[i];
            }
        }
        return null;
    }

    public void updateSettings(){
        for(var button : buttons){
            if(button != null)
                button.updateSettings();
        }
    }
}
