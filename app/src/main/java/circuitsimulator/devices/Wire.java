package circuitsimulator.devices;

public class Wire extends IdealBattery{
    public Wire(String start , String end){
        super(start , end , 0);
    }

    /* 
    @Override
    public void draw(Context context){
        var color = Color.FOREGROUND_COLOR;
        context.setSourceRgb(color.red , color.green , color.blue);
        context.setLineWidth(Color.LINE_WIDTH);
        context.moveTo(0, 0.5);
        context.lineTo(1, 0.5);
        context.stroke();
    }
    */
}
