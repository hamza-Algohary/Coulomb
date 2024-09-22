package gui;

import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.Image
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.WindowHandle
import ch.bailu.gtk.gtk.ListBox
import ch.bailu.gtk.gtk.ListBoxRow
import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.SelectionMode
import ch.bailu.gtk.gdk.Clipboard
import ch.bailu.gtk.adw.Clamp
import ch.bailu.gtk.pango.Alignment
import graphics.Color
import gui.rowBox

public class MyAbout {
    public fun run(parentWindow : Window) = aboutDialog(parentWindow)
}

public fun aboutDialog(parentWindow : Window) {
    Window().cssClasses("about","background","csd").title("").defaultSize(360, 578) + {
        titlebar(
            HeaderBar().cssClasses("flat")
        )

        child(
            WindowHandle().child(
                vbox(10).margins(12).children(
                    Color.logoImage().sizeRequest(124, 124), 

                    Label("Coulomb").cssClasses("title-1") ,

                    Label("Hamza Algohary") ,

                    Clamp().apply {  
                        setChild(
                            Button().cssClasses("app-version","accent").child(Label("0.6.0")).sizeRequest(70, -1)
                        )
                        setMaximumSize(50)
                    } , 

                    vbox(14).children(
                        rowBox(
                            hbox().cssClasses("title").margins(0,12).children(
                                Label("Website").halign(Align.START).margins(20,0).hexpand(true),
                                Color.newImage("external-link").halign(Align.END)
                            )
                        ).cssClasses("card" , "activatable").margins(3,0).onClick {
                            Color.launch_uri("https://github.com/hamza-Algohary/Coulomb")
                        },

                        rowBox(
                            hbox().cssClasses("title").margins(0,12).children(
                                Label("Report an issue").halign(Align.START).margins(20,0).hexpand(true),
                                Color.newImage("external-link").halign(Align.END)
                            )
                        ).cssClasses("card" , "activatable").margins(3,0).onClick {
                            Color.launch_uri("https://github.com/hamza-Algohary/Coulomb/issues")
                        } ,

                        ListBox().cssClasses("boxed-list","header").children(
                            hbox().cssClasses("title").margins(0,12).children(
                                Label("Credits").halign(Align.START).margins(19,0).hexpand(true),
                                Color.newImage("front").halign(Align.END)
                            ),
                            hbox().cssClasses("title").margins(0,12).children(
                                Label("License").halign(Align.START).margins(19,0).hexpand(true),
                                Color.newImage("front").halign(Align.END)
                            )
                        ).apply { 
                            setSelectionMode(SelectionMode.NONE)    
                        }
                    )

                )
            )
        )

        setResizable(false);
        setTransientFor(parentWindow);
    }

    
}

