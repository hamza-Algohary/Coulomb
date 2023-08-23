package gui;

import java.util.function.Function;

import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Entry;
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
        
        entry.getBuffer().onInsertedText((int position , Str s , int number)->{
            try{
                onChange.apply(Double.parseDouble(entry.getBuffer().getText().toString()));
            }catch(Exception e){}
        });
        entry.getBuffer().onDeletedText((int position , int deleted)->{
            try{
               onChange.apply(Double.parseDouble(entry.getBuffer().getText().toString()));
            }catch(Exception e){}
        });
    }
    public void setName(String name){
        label.setLabel(name);
    }
    public void setOnChange(Function<Double , Void> onChange){
        this.onChange = onChange;
    }
}
