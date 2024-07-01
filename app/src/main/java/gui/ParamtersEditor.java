package gui;

import algebra.Utils;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.Revealer;

public class ParamtersEditor extends Revealer{
    StackContainer container;  
    Box box = new Box(Orientation.HORIZONTAL, 10);
    Field fields[] = new Field[]{
        new Field(),new Field()
    };

    public ParamtersEditor(StackContainer container) {
        this.container = container;
        this.setChild(box);

        for(var field : fields){
            box.append(field);
        }
        box.setMarginBottom(5);
        box.setMarginTop(5);
        box.setMarginStart(5);
        box.setMarginEnd(5);


        Component.onSelectedChange((Void x)->{

            var selected = container.getSelected();
            hideAll();
            if(selected.length == 1){
                for(int i=0 ; i<fields.length && i<selected[0].getFields().length ; i++){
                    fields[i].label.setLabel(selected[0].getFields()[i] + " = ");

                    //String value = new BigDecimal(selected[0].getValue(i)).toPlainString();
                    String value =  Utils.doubleToString(selected[0].getValue(i));
                    //System.out.println("Number = " + new BigDecimal(selected[0].getValue(i)).toPlainString()  + " , Prefixed = " + value);
                    fields[i].entry.getBuffer().setText(value , value.length());

                    final int index = i;
                    fields[i].onChange = (Double number)->{
                        selected[0].setValue(index, number);
                        return null;
                    };

                    fields[i].show();
                }
                setRevealChild(true);
            }else{
                setRevealChild(false);
            }
            return null;
        });
    }
    
    public void hideAll(){
        for(var field : fields){
            field.onChange = null;
            field.hide();
        }
    }
}