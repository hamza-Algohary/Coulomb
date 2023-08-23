package gui;

import java.util.HashMap;

import ch.bailu.gtk.glib.Glib;
import ch.bailu.gtk.glib.GlibConstants;
import ch.bailu.gtk.type.Strs;
import circuitsimulator.CircuitSimulator;
import circuitsimulator.Device;
import gui.oscilliscope.Trace;

public class Main {
    public static MainApplication app;
    public static CircuitSimulator simulator = new CircuitSimulator();
    public static int sample_frequency = 100;
    public static double sleep_ms = 0.0010;
    public static long counter = 0;  
    //public static I_Chart2D chart = new I_Chart2D();   
    public static boolean running(){
        return app.headerBar.runButton.running();
    }
    public static void main(String args[]){
        app = new MainApplication(args);

        //Control
        app.onStart((Void v)->{
            app.drawingArea.onAdd((Device device)->{
                simulator.addDevice(device);
                return null;
            });
            app.drawingArea.onRemove((Device device)->{
                simulator.removeDevice(device);
                return null;
            });

            return null;
        });

        //Simulation
        Glib.timeoutAdd(1, (cb, user_data) -> {
            Component.running = running();
            if(running()){
                try{
                    simulator.tick();
                    if(counter%sample_frequency==0){
                        simulator.tick();
                        //double v = simulator.data.get("I1");
                        //double time = simulator.clock.getSeconds();
                        //chart.addPoint(time , v);
                        //HashMap<String , Double> data =  (HashMap<String , Double>) simulator.data.clone();
                        //Trace.storage.append(data);
                        //System.out.println("I = " + v);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
                
            return GlibConstants.SOURCE_CONTINUE;
        }, null);

        //Animation
        Glib.timeoutAdd(Component.FrameDurationMilliSeconds, (cb, user_data) -> {
            app.drawingArea.queueDraw();
            if(running())
                app.oscilliscopes.refresh();
            return GlibConstants.SOURCE_CONTINUE;
        }, null);


        var result = app.run(args.length, new Strs(args));
        System.exit(result);

    }
}
