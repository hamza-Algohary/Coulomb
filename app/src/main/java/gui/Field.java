package gui;

import java.util.function.Function;

import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Entry;
import ch.bailu.gtk.gtk.EventControllerKey;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.type.Str;

public class Field extends Box{
    Label label = new Label("");
    Entry entry = new Entry();
    Function<Double , Void> onChange = (Double x)->{return null;};
    public Field(){
        super(Orientation.HORIZONTAL, 0);
        //this.addCssClass("linked");
        this.append(label);
        this.append(entry);

        Runnable handler = ()->{
            try{
                System.out.println("Changing...");
                onChange.apply(new ScientificNumber(entry.getBuffer().getText().toString()).toDouble());
                System.out.println("Changed.");
            }catch(Exception e){}
            Main.app.drawingArea.queueDraw();
        };
        entry.getBuffer().onInsertedText((int position , Str s , int number)->{handler.run();});
        entry.getBuffer().onDeletedText((int position , int deleted)->{handler.run();});

        EventControllerKey keys = new EventControllerKey();
        this.addController(keys);
        
        keys.onKeyReleased((int keyval, int keycode, int state)->{handler.run();});
    }
    public void setName(String name){
        label.setLabel(name);
    }
    public void setOnChange(Function<Double , Void> onChange){
        this.onChange = onChange;
    }
}
