package gui;

import java.util.function.Function;

import ch.bailu.gtk.gtk.Adjustment;
import ch.bailu.gtk.gtk.Align;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.Label;
import ch.bailu.gtk.gtk.Orientation;
import ch.bailu.gtk.gtk.PositionType;
import ch.bailu.gtk.gtk.Scale;

public class TitledScale extends Box{
    public TitledScale(String title , double value, double down , double up , double step , Function<Double, Void> on_change){
        super(Orientation.VERTICAL , 0);
        Label label = new Label(title);
        //label.setUseMarkup(true);
        //label.setMarkup("<small>"+title+"</small>");
        label.setHalign(Align.CENTER);
        this.append(label);

        Adjustment adj = new Adjustment(value , down, up, step, 1,1);
        adj.onValueChanged(()->{
            on_change.apply(adj.getValue());
        });

        Scale scale = new Scale(Orientation.HORIZONTAL, adj);

        scale.addMark(value, PositionType.TOP, "");
        //scale.onChangeValue((int scroll, double v)->{
        //    on_change.apply(v);
        //    return true;
        //});
        this.append(scale);

        
        //label.setMarginTop(7);
        //label.setMarginStart(7);
        //label.setMarginEnd(5);
        //label.setMarginBottom(5);

        //scale.setMarginTop(5);
        //scale.setMarginStart(5);
        //scale.setMarginEnd(5);
        //scale.setMarginBottom(3);

        //this.setMarginBottom(8);
        //this.setMarginTop(12);
        //this.setMarginStart(10);
        //this.setMarginEnd(10);
        
        // Uncomment the following to enable card view.
        /*
        label.setMarginTop(5);
        scale.setMarginBottom(5);
        this.setMarginBottom(5);
        this.setMarginTop(5);

        this.addCssClass("card");
        */
    }
}
