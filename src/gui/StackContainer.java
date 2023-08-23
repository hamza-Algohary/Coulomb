package gui;

import java.util.Collections;
import java.util.Vector;
import java.util.function.Function;

import ch.bailu.gtk.cairo.Surface;
import ch.bailu.gtk.gdk.GdkConstants;
import ch.bailu.gtk.gtk.Box;
import ch.bailu.gtk.gtk.DrawingArea;
import ch.bailu.gtk.gtk.EventControllerMotion;
import ch.bailu.gtk.gtk.GestureClick;
import ch.bailu.gtk.gtk.GestureDrag;
import ch.bailu.gtk.gtk.Revealer;
import circuitsimulator.Device;
import circuitsimulator.devices.Constants;
import graphics.Color;
import graphics.Point;
import gui.components.ResistorComponent;
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
    Oscilliscopes oscilliscopes;
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
    }

    public void removeComponent(Component component) {
        onRemove.apply(component.getDevice());
        components.remove(component);
    }

    private boolean editMode() {
        return selector.getSelected().compareTo(Constants.HAND) == 0;
    }

    public StackContainer(IconSelector selector, Oscilliscopes oscilliscopes) {
        this.selector = selector;
        SelectorButton.container = this;
        this.oscilliscopes = oscilliscopes;

        // this.revealerContainer = revealContainer;

        this.setDrawFunc((cb, self, context, width, height, userData) -> {
            var bg = Color.BACKGROUND_COLOR;
            context.setSourceRgba(bg.red, bg.green, bg.blue, bg.alpha);
            context.paint();

            for (int i = components.size() - 1; i >= 0; i--) {
                components.get(i).onDraw(context);
            }

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
            if (editMode()) {
                for (var component : components) {
                    if (component.dragStart(new Point(x, y)))
                        break;
                }
            } else {
                addComponent(Component.newFromName(selector.getSelected()));
                components.lastElement().setStart(new Point(x, y));
                components.lastElement().setEnd(new Point(x, y));
            }
            this.queueDraw();

        });
        drag.onDragUpdate((x, y) -> {
            if (editMode()) {
                for (var component : components) {
                    if (component.dragUpdate(lastMouse))
                        break;
                }
            } else {
                components.lastElement().setEnd(lastMouse);
            }
            this.queueDraw();
        });
        drag.onDragEnd((x, y) -> {
            if (editMode()) {
                for (var component : components) {
                    if (component.dragEnd(lastMouse))
                        break;
                }
            } else {
                components.lastElement().setEnd(lastMouse);
            }
            this.queueDraw();

        });

        var motion = new EventControllerMotion();
        this.addController(motion);
        motion.onMotion((x, y) -> {
            lastMouse = new Point(x, y);
            if (editMode()) {
                for (var component : components) {
                    if (component.hover(new Point(x, y))) {
                        bringToFront(component);
                        break;
                    }
                }
            }
            this.queueDraw();
        });

        var press = new GestureClick();
        press.setButton(GdkConstants.BUTTON_PRIMARY);
        this.addController(press);

        press.onReleased((n_press, x, y) -> {
            if (n_press > 1) {
                for (var component : components) {
                    //System.out.println("OUTSIDE IF");
                    if(component.doubleClick(lastMouse)){
                        //System.out.println("INSIDE IF = "+component);
                        //System.out.println("IsNull? = " + (component == null));
                        oscilliscopes.addComponent(component);
                        break;
                    }
                }
            } else {
                if (editMode()) {
                    int i = 0;
                    for (; i < components.size(); i++) {
                        if (components.get(i).click(lastMouse)) {
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

    }

    public static final int KEY_DELETE = 119;

    public void keyReleased(int value, int code) {
        if (code == KEY_DELETE) {
            for (var component : components) {
                if (component.deletePressed()) {
                    removeComponent(component);
                    break;
                }
            }
        }
    }

    public Component[] getSelected() {
        Vector<Component> selectedComponents = new Vector<>();
        for (var component : components) {
            if (component.isselected()) {
                selectedComponents.add(component);
            }
        }
        // System.out.println(selectedComponents.size());
        return selectedComponents.toArray(new Component[0]);
    }

}
