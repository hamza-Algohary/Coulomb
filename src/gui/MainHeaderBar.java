package gui;


import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.WindowControls;
import ch.bailu.gtk.gtk.WindowHandle;

public class MainHeaderBar extends WindowHandle {
    HamburgerMenuButton hamburgerMenuButton = new HamburgerMenuButton();
    RunButton runButton = new RunButton();
    IconSelector devicesSelector = new IconSelector();
    WindowControls controls = new WindowControls(1);

    Box box = new Box(Orientation.HORIZONTAL , 5);
    Box startBox = new Box(Orientation.HORIZONTAL, 0);

    public static int headerbarChildrenMargin = 7;

    MainHeaderBar(){
        box.append(startBox);
        box.append(devicesSelector);
        box.append(controls);

        startBox.append(hamburgerMenuButton);
        startBox.append(runButton);
        startBox.addCssClass("linked");

        startBox.setMarginBottom(headerbarChildrenMargin);
        startBox.setMarginTop(headerbarChildrenMargin);
        startBox.setMarginStart(headerbarChildrenMargin);
        startBox.setMarginEnd(headerbarChildrenMargin);

        controls.setMarginBottom(headerbarChildrenMargin);
        controls.setMarginTop(headerbarChildrenMargin);
        controls.setMarginStart(headerbarChildrenMargin);
        controls.setMarginEnd(headerbarChildrenMargin);

        this.setChild(box);
        this.show();
    }
    
}
