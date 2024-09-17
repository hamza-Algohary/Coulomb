package gui;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.function.Function;

import ch.bailu.gtk.cairo.Context;
import ch.bailu.gtk.cairo.Surface;
import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.EventControllerMotion;
import ch.bailu.gtk.gtk.EventControllerScroll;
import ch.bailu.gtk.gtk.EventControllerScrollFlags;
import ch.bailu.gtk.gtk.GestureClick;
import ch.bailu.gtk.gtk.GestureDrag;
import circuitsimulator.Device;
import circuitsimulator.devices.Constants;
import graphics.Color;
import graphics.MyContext;
import graphics.Point;
import gui.oscilliscope.Oscilliscopes;

public class StackContainer extends DrawingArea {
    Vector<Component> components = new Vector<>();
    private Surface surface;
    IconSelector selector;
    Function<Device, Void> onAdd = (Device) -> {
        return null;
    }, onRemove = (Device) -> {
        return null;
    };
    Point lastMouse = new Point();
    Point startDraggingMouse = new Point();
    Point scalingCenter = new Point();

    Oscilliscopes oscilliscopes;

    public String message = "";
    public String description = "Untitled Circuit";

    File path = null;

    Point shift = new Point(0 , 0);
    double scale = 1.2;

    boolean dragging = false;

    boolean autoCenterOn(){
        return Main.app.headerBar.autoCenterButton.getActive();
    }

    void disableAutoCenter(){
        Main.app.headerBar.autoCenterButton.setActive(false);
    }    
    void enableAutoCenter(){
        Main.app.headerBar.autoCenterButton.setActive(true);
    }

    // Revealer revealer = new Revealer();
    // Box revealerContainer;
    // Field fields[] = new Field[]{
    // new Field(),new Field()
    // };

    private void bringToFront(Component component) {
        Collections.swap(components, 0, components.indexOf(component));
    }

    public void onAdd(Function<Device, Void> function) {
        this.onAdd = function;
    }

    public void onRemove(Function<Device, Void> function) {
        this.onRemove = function;
    }

    public void addComponent(Component component) {
        components.add(component);
        onAdd.apply(component.getDevice());
        if (component.oscilliscope != null && component.showOscilliscope)
            oscilliscopes.addComponent(component);       
    }

    public void removeComponent(Component component) {
        onRemove.apply(component.getDevice());
        oscilliscopes.remove(component);
        components.remove(component);
        queueDraw();
    }

    private boolean editMode() {
        return selector.getSelected().compareTo(Constants.HAND) == 0;
    }

    //private void zoom(Context context , int width , int height){
        //int scalechange = newscale - oldscale;
        //int offsetX = -(zoomPointX * scalechange);
        //int offsetY = -(zoomPointY * scalechange);
    //}

    public StackContainer(IconSelector selector, Oscilliscopes oscilliscopes) {
        this.selector = selector;
        SelectorButton.container = this;
        this.oscilliscopes = oscilliscopes;

        // this.revealerContainer = revealContainer;

        this.setDrawFunc((cb, self, context, width, height, userData) -> {

            if(autoCenterOn()){
                autoCenter(context, width, height);
            }
            context.translate(shift.x, shift.y);
            scale(context);

            //scalingCenter = new Point((int)scalingCenter.x , (int)scalingCenter.y);
            //context.translate(scalingCenter.x, scalingCenter.y);
            //context.translate(width/2d, height/2d);
            //context.translate(-width/2d, -height/2d);
            //var adjustment = scalingCenter.scale(scale-1) ;
            //context.translate(-scalingCenter.x, -scalingCenter.y);

            //context.translate(-scalingCenter.x, -scalingCenter.y);

            var bg = Color.BACKGROUND_COLOR;
            context.setSourceRgba(bg.red, bg.green, bg.blue, bg.alpha);
            context.paint();

            for (int i = components.size() - 1; i >= 0; i--) {
                components.get(i).onDraw(context);
            }

            for (int i = components.size() - 1; i >= 0; i--) {
                components.get(i).drawAdditionalInfo(context);
            }

            MyContext con = new MyContext(context);
            con.moveTo(20, height - 20);
            con.setSource(Color.SHARPER_FOREGROUND_COLOR);
            con.showText(message);
            con.newPath();



        }, null, (cb, data) -> {
            this.unregisterCallbacks();
        });

        this.onResize((width, height) -> {
            if (surface != null)
                surface.destroy();
            surface = this.getNative().getSurface().createSimilarSurface(0, width, height);
            this.queueDraw();
        });

        var drag = new GestureDrag();
        drag.setButton(GdkConstants.BUTTON_PRIMARY);
        this.addController(drag);

        drag.onDragBegin((x, y) -> {
            startDraggingMouse = lastMouse;
            dragging = true;
            if (editMode()) {
                for (var component : components) {
                    if (component.dragStart(lastMouseScaled())){
                        disableAutoCenter();
                        System.out.println("Started dragging...");
                        break;
                    }
                }
            } else {
                disableAutoCenter();
                addComponent(Component.newFromName(selector.getSelected()));
                components.lastElement().setStart(lastMouseScaled());
                components.lastElement().setEnd(lastMouseScaled());
            }
            if(Main.app.ctrlIsPressed) 
                disableAutoCenter();
            
            this.queueDraw();

        });
        drag.onDragUpdate((x, y) -> {
            if (editMode()) {
                if(Main.app.ctrlIsPressed){
                    Point delta = lastMouse.relativeTo(startDraggingMouse);
                    shift = shift.add(delta);
                    startDraggingMouse = lastMouse;
                    //System.out.println("Shifting = " + shift.toString() + "Delta = " + delta.toString());
                    this.queueDraw();
                }else{
                    for (var component : components) {
                        if (component.dragUpdate(lastMouseScaled()))
                            break;
                    }
                }
            } else {
                components.lastElement().setEnd(lastMouseScaled());
            }
            this.queueDraw();
        });
        
        drag.onDragEnd((x, y) -> {
            dragging = false;
            if (editMode()) {
                for (var component : components) {
                    if (component.dragEnd(lastMouseScaled())) {
                        removeComponentIfSmall(component);
                        break;
                    }
                }
            } else {
                components.lastElement().setEnd(lastMouseScaled());
                removeComponentIfSmall(components.lastElement());
            }
            this.queueDraw();

        });

        var motion = new EventControllerMotion();
        this.addController(motion);
        motion.onMotion((x, y) -> {
            lastMouse = new Point(x, y);
            if (editMode()) {
                boolean eventHandled = false;
                for (var component : components) {
                    if (component.hover(lastMouseScaled() , eventHandled) && !eventHandled) {
                        if(!dragging)
                            bringToFront(component);
                        eventHandled = true;
                    }
                }
            }
            this.queueDraw();
        });

        var press = new GestureClick();
        press.setButton(GdkConstants.BUTTON_PRIMARY);
        this.addController(press);

        press.onPressed((n_press, x, y)->{
            
        });

        this.setFocusable(true);
        this.setFocusOnClick(true);

        press.onReleased((n_press, x, y) -> {
            if (n_press > 1) {
                for (var component : components) {
                    // System.out.println("OUTSIDE IF");
                    if (component.doubleClick(lastMouseScaled())) {
                        // System.out.println("INSIDE IF = "+component);
                        // System.out.println("IsNull? = " + (component == null));
                        component.showI = true;
                        component.showV = true;
                        oscilliscopes.addComponent(component);
                        break;
                    }
                }
            } else {
                if (editMode()) {
                    int i = 0;
                    for (; i < components.size(); i++) {
                        if (components.get(i).click(lastMouseScaled())) {
                            removeComponentIfSmall(components.get(i));
                            i++;
                            break;
                        }
                    }
                    for (; i < components.size(); i++) {
                        components.get(i).unclick();
                    }
                }
            }

            this.queueDraw();
        });

        var scroller = new EventControllerScroll(EventControllerScrollFlags.VERTICAL);
        this.addController(scroller);
        
        scroller.onScrollBegin(()->{
            this.scalingCenter = lastMouse;
        });

        scroller.onScroll( (double dx, double dy)->{
            this.scale -= 0.1*dy;
            //System.out.println("Scale = " + scale);
            this.queueDraw();
            return true;
        });

    }

    public static final int KEY_DELETE = 65535;

    public void keyReleased(int value, int code) {
        if (value == KEY_DELETE) {
            deleteSelectedComponent();
        } 
    }

    public void deleteSelectedComponent() {
        for (int i=0 ; i<components.size() ; i++) {
            if (components.get(i).isselected()) {
                removeComponent(components.get(i));
                i--;
                //break;
            }
        }
    }

    public void keyPressed(int value , int code){
        if (Main.app.ctrlIsPressed == true && (value == GdkConstants.KEY_A || value == GdkConstants.KEY_a) ){
            selectAll();
        }
    }

    public Component[] getSelected() {
        Vector<Component> selectedComponents = new Vector<>();
        for (var component : components) {
            if (component.isselected()) {
                selectedComponents.add(component);
            }
        }
        return selectedComponents.toArray(new Component[0]);
    }

    public void removeComponentIfSmall(Component component) {
        if (component.start().distanceFrom(component.end()) == 0) {
            removeComponent(component);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void clearComponents() {
        while (!components.isEmpty()) {
            removeComponent(components.lastElement());
        }
    }

    public void parse(String text) {
        clearComponents();

        var lines = text.split("\n", 0);
        if (lines.length < 2)
            return;

        description = lines[0];

        for (int i=1 ; i<lines.length ; i++) {
            Component component = Component.parse(lines[i]);
            if (component != null){
                addComponent(component);
            }else{
                System.out.println("Couldn't Load Component...");
            }
        }
    }

    private void setPath(String pathName) {
        path = new File(pathName);

    }

    public void save() {
        if (path != null) {
            try {
                path.createNewFile();
                try {
                    Files.writeString(path.toPath(), toString());
                } catch (Exception e) {
                    System.out.println("Couldn't save file.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("Couldn't save file.");
                e.printStackTrace();
            }
        }
    }

    public void saveAs(String pathName) {
        setPath(pathName);
        save();
    }

    public void load(String pathName) {
        setPath(pathName);
        if (hasPath()) {
            try {
                parse(Files.readString(path.toPath()));
                enableAutoCenter();
            } catch (Exception e) {
                System.out.println("Couldn't Open File.");
                e.printStackTrace();
            }
        }else{
            System.out.println("File Not Found");
        }
    }

    public void loadExample(String pathName) {
        //setPath(pathName);
        //if (hasPath()) {
            try {
                parse(Color.stringFromResource(pathName));
                enableAutoCenter();
            } catch (Exception e) {
                System.out.println("Couldn't Open File.");
                e.printStackTrace();
            }
        //}else{
        //    System.out.println("File Not Found");
        //}
    }

    @Override
    public String toString() {
        String text = "";
        text += description + "\n";
        for (var component : components) {
            text += component.toString() + "\n";
        }

        //System.out.println(text);
        return text;
    }

    public boolean hasPath() {
        return path != null && path.exists();
    }

    public void reset(){
        oscilliscopes.reset();
    }

    public void selectAll(){
        for(var component : components){
            component.selected = true;
        }
    }

    public Point lastMouseScaled(){
        //System.out.println("SHIFT = " + shift + "SCALE = " + scale);
        return lastMouse.relativeTo(shift).scale(1/scale);
    }

    public void autoCenter(Context context , int width , int height){
        if(components.size() == 0)
            return;

        var points = getAllNodes();
        Point center = Point.average(points);
        limitScale();
        shift = new Point (width/2d - center.x*scale, height/2d - center.y*scale);

        //context.translate(width/2d - center.x*scale, height/2d - center.y*scale);

        //var bounds = Point.getBounds(points);
        //double bwidth = bounds[1].x - bounds[0].x; 
        //double bheight = bounds[1].y - bounds[0].y; 

        //scale = Math.min(width*1.2/bwidth, height*1.2/bheight);

        //scale(context);
        //context.scale(scale, scale);
        
    }

    public void autoScale(Context context , int width , int height){
        var points = getAllNodes();

        var bounds = Point.getBounds(points);
        double bwidth = bounds[1].x - bounds[0].x; 
        double bheight = bounds[1].y - bounds[0].y; 
        scale = Math.min(width*1.2/bwidth, height*1.2/bheight);
    }

     

    public void scale(Context context){
        limitScale();
        context.scale(scale , scale);
    }

    private void limitScale() {
        scale = Math.min(Math.max(scale , 0.1) , 3);
    }

    public Point[] getAllNodes(){
        ArrayList<Point> points = new ArrayList<>();
        for(var component : components){
            if(!points.contains(component.start())) points.add(component.start());
            if(!points.contains(component.end())) points.add(component.end());
        }
        return points.toArray(new Point[0]);
    } 

}
