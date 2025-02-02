package gui;

import ch.bailu.gtk.adw.ActionRow;
import ch.bailu.gtk.adw.PreferencesRow;
import ch.bailu.gtk.adw.ToastOverlay;
import ch.bailu.gtk.gtk.*;

public class HamburgerMenu extends Popover {
    Box box = new Box(Orientation.VERTICAL , 0);
    Stack stack = new Stack();
    ExamplesBox examplesBox;
    //ListBox list = new ListBox();
    //Button preferencesButton = Button.newWithLabelButton("Preferences");
    //Button aboutButton = Button.newWithLabelButton("About");
    //Button myCircuitsButton = Button.newWithLabelButton("My Circuits");
    //AboutDialog aboutDialog = new AboutDialog();
    //LightDarkSwitch lightDarkSwitch = new LightDarkSwitch();

    //Label preferencesLabel = new Label("Preferences");
    //Label aboutLabel = new Label("About");
    //About about = new About();

    double limit(double value , double down , double up){
        return Math.min(Math.max(value , down) , up); 
    }

    HamburgerMenu(){
        examplesBox = new ExamplesBox(this);

        this.setChild(stack);
        stack.addChild(box);
        stack.addChild(examplesBox);

        this.setOffset(80,5);
        this.setHasArrow(false);
        //preferencesButton.setLabel("Preferences");
        //aboutButton.setLabel("About");

        box.append(new LightDarkSwitch());

        box.append(new MySeparator());

        box.append(new TitledScale("Simulation Speed" , Main.simulation_speed_controller , 1 , 100, 1 , (Double value)->{
            Main.simulation_speed_controller = (int)limit(value, 1, 100);
            return null;
        }));

        box.append(new TitledScale("Current Speed" , CurrentDrawer.speed_controller , 1 , 20 , 0.2 , (Double value)->{
            CurrentDrawer.speed_controller = (int)limit(value, 1, 20);
            return null;
        }));

        box.append(new MySeparator());

        Runnable saveAs = ()->{
            //FileChooserDialog fileChooserDialog = new FileChooserDialog("Save circuit as" , Main.app.window , FileChooserAction.SAVE , new String("Save") , ResponseType.APPLY , new String("Cancel") , ResponseType.CANCEL);
            FileChooserNative fileChooserDialog = new FileChooserNative("Save circuit as" , Main.app.window , FileChooserAction.SAVE , null , null);
            fileChooserDialog.setModal(true);
            fileChooserDialog.show();
            fileChooserDialog.onResponse((int response)->{
                System.out.println("Saving....");
                if(response == ResponseType.ACCEPT){
                    System.out.println("Really Saving....");
                    Main.app.drawingArea.saveAs(fileChooserDialog.asFileChooser().getFile().getPath().toString());
                }
                fileChooserDialog.hide();
            });
            
        };

        Runnable open = ()->{
            //FileChooserDialog fileChooserDialog = new FileChooserDialog("Save circuit as" , Main.app.window , FileChooserAction.OPEN , new String("Open") , ResponseType.APPLY , new String("Cancel") , ResponseType.CANCEL);
            FileChooserNative fileChooserDialog = new FileChooserNative("Choose a circuit" , Main.app.window , FileChooserAction.OPEN , null , null);
            fileChooserDialog.setModal(true);
            fileChooserDialog.show();
            fileChooserDialog.onResponse((int response)->{
                System.out.println("Opening....");
                if(response == ResponseType.ACCEPT){
                    Main.app.drawingArea.load(fileChooserDialog.asFileChooser().getFile().getPath().toString());
                }
                fileChooserDialog.hide();
            });            
        };
        
        box.append(new MenuButton("Examples" , Align.START , ()->{
            stack.setVisibleChild(examplesBox);
        }));
        box.append(new MenuButton("Save" , Align.START , ()->{
            if(Main.app.drawingArea.hasPath()){
                Main.app.drawingArea.save();
            }else{
                saveAs.run();
            }
        }, this));
        box.append(new MenuButton("Save as" , Align.START , saveAs , this));
        box.append(new MenuButton("Open" , Align.START , open , this));

        box.append(new MenuButton("Clear" , Align.START , ()->{
            Main.app.drawingArea.clearComponents();
        } , this));



        box.append(new MySeparator());

        box.append(new MenuButton("Keyboard Shortcuts", Align.START, ()->{
            new MyShortuctsWindow(Main.app.window).show();
        }, this));

        //box.append(new MenuButton("Preferences" , Align.START , ()->{}));
        box.append(new MenuButton("About" , Align.START , ()->{
            //new AboutDialog().show();
            new MyAbout().run(Main.app.window , "0.6.0");
        } , this));

        stack.setTransitionType(StackTransitionType.SLIDE_LEFT_RIGHT);
        //box.append(preferencesButton);
        //box.append(aboutButton);
        /* 
        Label preferencesLabel = new Label("Preferences");
        preferencesButton.setChild(preferencesLabel);
        preferencesLabel.setHalign(Align.START);

        Label aboutLabel = new Label("About");
        aboutButton.setChild(aboutLabel);
        aboutLabel.setHalign(Align.START);
        //box.setVexpand(false);

        //box.append(preferencesLabel);
        //box.append(aboutLabel);

        //preferencesLabel.addCssClass("button");
        //aboutLabel.addCssClass("button");
        //preferencesButton.setVexpand(false);
        //aboutButton.setVexpand(false);

        //box.getStyleContext().addClass("linked");
        preferencesButton.addCssClass("flat");
        aboutButton.addCssClass("flat");


        //preferencesLabel.setJustify(1);
        //aboutLabel.setJustify(1);

        //preferencesLabel.getStyleContext();
        //preferencesButton.setChild(preferencesLabel);
        //aboutButton.setChild(aboutLabel);

        //preferencesLabel.setHexpand(true);
        //aboutLabel.setHexpand(true);

        //preferencesLabel.addCssClass("button");
        //aboutLabel.addCssClass("button");
        //preferencesButton.setHalign(Align.START);
        //aboutButton.setHalign(Align.START);
        //preferencesButton.setHexpand(true);
        //aboutButton.setHexpand(true);
        
        aboutButton.onClicked(()->{
            aboutDialog.show();
        });

        //var press1 = new GestureClick();
        //press1.setButton(GdkConstants.BUTTON_PRIMARY);
        //preferencesLabel.addController(press1);
            
        //press1.onReleased((n_press, x, y)->{
        //
        //});

        //var press2 = new GestureClick();
        //press2.setButton(GdkConstants.BUTTON_PRIMARY);
        //aboutLabel.addController(press2);

        //press2.onPressed((n_press, x, y)->{
        //    aboutDialog.show();
        //});

        //Box box = new Box(Orientation.HORIZONTAL, 0);
        

        //about.setTransientFor(getRoo);
        //aboutButton.onClicked(()->{
        //    about.
        //    //about.runDispose();
        //});*/
    }
}
