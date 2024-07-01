package gui;


import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Widget;
import ch.bailu.gtk.gtk.WindowControls;
import ch.bailu.gtk.gtk.WindowHandle;

public class MainHeaderBar extends WindowHandle {
    HamburgerMenuButton hamburgerMenuButton = new HamburgerMenuButton();
    RunButton runButton = new RunButton();
    IconSelector devicesSelector = new IconSelector();
    WindowControls controls = new WindowControls(1);

    public SelectorButton autoCenterButton = new SelectorButton("zoom-fit-best");

    Box box = new Box(Orientation.HORIZONTAL , 5);
    Box startBox = new Box(Orientation.HORIZONTAL, 0);

    public static int headerbarChildrenMargin = 7;

    MainHeaderBar(){
        box.append(startBox);
        
        addResetButton();

        box.append(autoCenterButton);
        adjustMargins(autoCenterButton);
        autoCenterButton.setActive(true);
        autoCenterButton.onToggled(()->{
            Main.app.drawingArea.queueDraw();
        });
        

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

        System.out.println("headerbar constructed");
    }

    private void addResetButton() {
        addButton("system-restart-symbolic" , ()->{    
            Main.simulator.reset();    
            Main.app.reset();
        });
        //Button resetButton = new Button();
        //resetButton.setIconName("system-restart-symbolic");
        //resetButton.onClicked(()->{
        //    Main.simulator.reset();
        //    Main.app.reset();
        //});

        //resetButton.setMarginBottom(headerbarChildrenMargin);
        //resetButton.setMarginTop(headerbarChildrenMargin);
        ////resetButton.setMarginStart(headerbarChildrenMargin);
        //resetButton.setMarginEnd(headerbarChildrenMargin);

        //box.append(resetButton);
    }

    //private ToggleButton toggle(String iconName){
    //    ToggleButton button = new ToggleButton();
    //    button.setIconName(iconName);
    //    return button;
    //}

    private void addButton(String iconName , Button.OnClicked handler){
        Button resetButton = new Button();
        resetButton.setIconName(iconName);
        resetButton.onClicked(handler);

        resetButton.addCssClass("flat");

        adjustMargins(resetButton);

        box.append(resetButton);  
    }

    private void adjustMargins(Widget resetButton) {
        resetButton.setMarginBottom(headerbarChildrenMargin);
        resetButton.setMarginTop(headerbarChildrenMargin);
        //resetButton.setMarginStart(headerbarChildrenMargin);
        resetButton.setMarginEnd(headerbarChildrenMargin);
    }
    
}
