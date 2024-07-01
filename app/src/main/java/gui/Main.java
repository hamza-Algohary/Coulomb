package gui;


import java.util.concurrent.TimeUnit;

import ch.bailu.gtk.glib.Glib;
import ch.bailu.gtk.glib.GlibConstants;
import ch.bailu.gtk.type.Strs;
import circuitsimulator.CircuitSimulator;
import circuitsimulator.Device;
//import circuitsimulator.CircuitSimulator.NON_CONVERGANCE;

public class Main {
    public static MainApplication app;
    public static CircuitSimulator simulator = new CircuitSimulator();
    public static int sample_frequency = 100;

    public static int simulation_speed_controller = 100;

    //public static double sleep_ms = 0.001*(101-simulation_speed_controller);
    public static double sleep_ms(){
        return 0.001*(101-simulation_speed_controller)*100;
    }
    public static long counter = 0; 

    //public static I_Chart2D chart = new I_Chart2D();   
    public static boolean running(){
        return app.headerBar.runButton.running();
    }
    public static boolean last_was_dark = false;
    public static Exception currentError = null;
    public static boolean exit = false;

    public static Thread workerThread = new Thread(()-> {
        while(!exit){
            try {
                TimeUnit.MICROSECONDS.sleep((int)(sleep_ms()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("Working....");
            int counter = 0;
            if(running()){
                //System.out.println("Really Working.");
                try{
                    simulator.tick();
                    if(counter % sample_frequency == 0)
                        app.oscilliscopes.sample();
                    currentError = null;
                }catch(Exception e){
                    currentError = e;
                }

                counter++;
            }

        }
    });

    public static void main(String args[]){

        String path = ""; 

        for(int i=1 ; i<args.length ; i++){
            path += args[i]; 
        }

        var inputFileExists = !path.isEmpty();

        if(!inputFileExists){
            path = "examples/double-clipper";
        }

        final String finalPath = path;

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

            if(inputFileExists)
                app.drawingArea.load(finalPath);
            else
                app.drawingArea.loadExample(finalPath);

            workerThread.start();

            return null;
        });


        //Simulation
        Glib.timeoutAdd(1, (cb, user_data) -> {
            Component.running = running();
            //if(running()){
                //try{
                    //simulator.tick();
                    //app.drawingArea.setMessage("");
                    //if(counter%sample_frequency==0){
                        //simulator.tick();
                        //double v = simulator.data.get("I1");
                        //double time = simulator.clock.getSeconds();
                        //chart.addPoint(time , v);
                        //HashMap<String , Double> data =  (HashMap<String , Double>) simulator.data.clone();
                        //Trace.storage.append(data);
                        //System.out.println("I = " + v);
                    //}
                //}catch (Exception e){
                //    app.headerBar.runButton.running = false;
                //    app.headerBar.runButton.updateSettings();
                //    app.drawingArea.setMessage("Can't solve this circuit.");
                //    System.out.println(e);
                //}
                //System.out.println("MESSAGE = " + app.drawingArea.message);

                if(currentError != null){
                    app.headerBar.runButton.running = false;
                    app.headerBar.runButton.updateSettings();
                    app.drawingArea.setMessage("Can't solve this circuit.");
                    currentError.printStackTrace();
                    currentError = null;
                }
                
                if(running()){
                    app.drawingArea.queueDraw();
                    app.oscilliscopes.draw();
                    app.drawingArea.setMessage("");
                }
            //}     
            return GlibConstants.SOURCE_CONTINUE;
        }, null);

        //Animation & Settings
        Glib.timeoutAdd(Component.FrameDurationMilliSeconds, (cb, user_data) -> {
            if(last_was_dark != app.getStyleManager().getDark()){
                last_was_dark = app.getStyleManager().getDark();
                app.updateSettings();
            }
            return GlibConstants.SOURCE_CONTINUE;
        }, null);

        var result = app.run(args.length, new Strs(args));
        exit = true;
        System.exit(result);
    }
}
