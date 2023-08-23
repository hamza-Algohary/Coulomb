package circuitsimulator;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import javax.swing.*;
import java.awt.*;

public class I_Chart2D extends Chart2D {
    Chart2D chart = new Chart2D();
    private ITrace2D trace = new Trace2DLtd(50);  
    private ITrace2D limitup = new Trace2DLtd(50) , limitdown = new Trace2DLtd(50) , zero = new Trace2DLtd(50);  
    public static double up = 50 , down = -50;
    public I_Chart2D() {
        trace.setColor(Color.RED);
        limitdown.setColor(Color.BLACK);
        limitup.setColor(Color.BLACK);
        zero.setColor(Color.BLACK);

        chart.addTrace(trace);
        chart.addTrace(limitup);
        chart.addTrace(limitdown);
        chart.addTrace(zero);

        //JPanel panel = new JPanel();
        //panel.add(chart, BorderLayout.CENTER);
        //panel.setPreferredSize(new Dimension(600,800) );
        //panel.setLayout(new BorderLayout());
        //panel.setBackground(Color.RED);

        JFrame frame = new JFrame("Oscilloscope");
        frame.getContentPane().add(chart);
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();

        frame.setVisible(true);
    }
    public void addPoint(double x , double y){
        trace.addPoint(x , y);    
        limitdown.addPoint(x , down);
        limitup.addPoint(x , up);
        zero.addPoint(x , 0);   
    }
}