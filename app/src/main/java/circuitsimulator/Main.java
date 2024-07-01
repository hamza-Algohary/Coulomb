package circuitsimulator;



import algebra.Utils;
import ch.bailu.gtk.adw.HeaderBar;
import ch.bailu.gtk.gdk.Display;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Application;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.gtk.CssProvider;
import ch.bailu.gtk.gtk.FlowBox;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Popover;
import ch.bailu.gtk.gtk.ScrolledWindow;
import ch.bailu.gtk.gtk.StyleContext;
import ch.bailu.gtk.gtk.WindowControls;
import ch.bailu.gtk.gtk.WindowHandle;
import ch.bailu.gtk.type.Strs;


public class Main {
    public static class MyHeaderBar extends WindowHandle{
        WindowControls controls = new WindowControls(0);
        Box box = new Box(Orientation.HORIZONTAL, 3);
        MyHeaderBar(){
            this.setChild(box);
            //controls.setSide(PackType.END);
            
            box.append(controls);
            box.setHalign(Align.END);
            Label label = new Label("THIS IS A LABEL FROM THE RIGHT.");
            box.append(label);
            label.setHalign(Align.END);
            //label.setProperty(, null);
            box.show();
            controls.show();
            this.show();
        }
    }
    public static void main1(String args[]){
        //ScientificNumber number = new ScientificNumber("50.5p");
        //System.out.println(number.toDouble());

        System.out.println(Utils.doubleToString(15e-6));
    }
    public static void main2(String[] args) {
        var app = new Application("com.example.hello",
                ApplicationFlags.FLAGS_NONE);

        app.onActivate(() -> {
            // Create a new window
            var window = new ApplicationWindow(app);
            var headerbar = new HeaderBar();

            window.setTitle("Circuit Simulator");
            var styleProvider = new CssProvider();

            MyHeaderBar headerbar2 = new MyHeaderBar();
            //window.setTitlebar(headerbar2);
            String stylesheet = " window , headerbar{background:#1e1e1e;} window:not(.maximized){border-radius:15px;}headerbar{border:none;box-shadow:none;} button.myButton{border-radius:100px;} popover.mypopover{border-radius:15px;background:#1e1e1e;}";
            styleProvider.loadFromData(stylesheet, stylesheet.length());
            StyleContext.addProviderForDisplay(Display.getDefault(), styleProvider.asStyleProvider() , 1000);
            
            //headerbar.getStyleContext().addProvider(styleProvider.asStyleProvider() , 1000);
            window.setOverflow(1);
            var buttons = new Button[24];
            var moreButtons = new Button[12];
            var moreDevicesButton = new Button();
            var runButton = new Button();
            var menuButton = new Button();
            moreDevicesButton.setIconName("view-more");
            moreDevicesButton.getStyleContext().addClass("myButton");
            runButton.getStyleContext().addClass("myButton");
            menuButton.getStyleContext().addClass("myButton");
            runButton.setIconName("player_start");
            menuButton.setIconName("application-menu");
            String icons[] = {"firefox" , "vlc" , "code" , "nemo"  , "firefox" , "vlc" , "code" , "nemo" , "firefox" , "vlc" , "code" , "nemo" , "firefox" , "vlc" , "code" , "nemo"  , "firefox" , "vlc" , "code" , "nemo" , "firefox" , "vlc" , "code" , "nemo"}; 
            for(int i = 0 ; i<buttons.length ; i++){
                buttons[i] = new Button();
                buttons[i].setIconName(icons[i]);
                buttons[i].getStyleContext().addClass("myButton");
            }
            for(int i = 0 ; i<moreButtons.length ; i++){
                moreButtons[i] = new Button();
                moreButtons[i].setIconName(icons[i]);
                moreButtons[i].getStyleContext().addClass("myButton");
                //moreButtons[i].setSizeRequest(90, 90);
                //var theme = IconTheme.getForDisplay(Display.getDefault());
                //var icon = theme.lookupIcon(icons[i], null, 25 , 1 , Direction.LTR , 0);
                //Image image = Image.newFromPaintableImage(icon.asPaintable());
                //moreButtons[i].setChild(image);
                //moreButtons[i].setLabel(icons[i]);
            }
            Box titleBox = new Box(Orientation.HORIZONTAL, 3);
            ScrolledWindow scrolledBox = new ScrolledWindow();
            scrolledBox.setChild(titleBox);
            //scrolledBox.setSizeRequest(400, -1);
            for(var button : buttons){
                titleBox.append(button);
            }

            var moreDevicesPopOver = new Popover();
            moreDevicesPopOver.setParent(moreDevicesButton);
            var moreDevicesMenu = new FlowBox();
            moreDevicesMenu.setMaxChildrenPerLine(4);
            moreDevicesMenu.setMinChildrenPerLine(4);
            moreDevicesMenu.setColumnSpacing(2);
            moreDevicesMenu.setRowSpacing(2);
            for(var button : moreButtons){
                moreDevicesMenu.append(button);
            }
            var menuPopover = new Popover();
            menuPopover.setParent(menuButton);
            var moreMenu = new Box(Orientation.VERTICAL , 0);
            moreMenu.setHomogeneous(true);
            
            menuPopover.setChild(moreMenu);
            moreDevicesPopOver.setChild(moreDevicesMenu);

            var preferencesButton = Button.newWithLabelButton("Prefernces");
            var aboutButton = Button.newWithLabelButton("About");
            preferencesButton.setValign(Align.START);
            aboutButton.setValign(Align.START);
            moreMenu.append(preferencesButton);
            moreMenu.append(aboutButton);
            
            titleBox.setHalign(Align.BASELINE);
            scrolledBox.setHalign(Align.BASELINE);
            titleBox.setHexpand(true);
            scrolledBox.setHexpand(true);
            scrolledBox.setHexpandSet(true);  
            
            headerbar.packStart(moreDevicesButton);
            headerbar.packStart(runButton);
            headerbar.packEnd(menuButton);
            Box main_box = new Box(Orientation.VERTICAL, 0);
            main_box.append(headerbar);
            main_box.append(headerbar2);
            //main_box.append(new WindowControls(PackType.END));
            window.setChild(main_box);
            headerbar.packStart(scrolledBox);

            menuPopover.setOverflow(1);
            moreDevicesPopOver.setOverflow(1);
            moreDevicesPopOver.getStyleContext().addClass("mypopover");
            menuPopover.getStyleContext().addClass("mypopover");


           // menuPopover.getStyleContext().addProvider(styleProvider.asStyleProvider() , 1000);
           // moreDevicesPopOver.getStyleContext().addProvider(styleProvider.asStyleProvider() , 1000);

            menuPopover.setOffset(0, 0);
            moreDevicesPopOver.setOffset(100, 0);
            menuPopover.setHasArrow(false);
            moreDevicesPopOver.setHasArrow(false);
            moreDevicesButton.onClicked(()->{moreDevicesPopOver.popup();});
            menuButton.onClicked(()->{menuPopover.popup();});
            
            headerbar.show();
            window.show();
        });

        // Start application main loop
        var result = app.run(args.length, new Strs(args));


        // Terminate with exit code
        System.exit(result);
    }
}