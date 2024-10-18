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
import ch.bailu.gtk.gtk.Stack
import ch.bailu.gtk.gtk.ScrolledWindow
import ch.bailu.gtk.gtk.Expander
import ch.bailu.gtk.gtk.Separator
import ch.bailu.gtk.gtk.StackTransitionType
import ch.bailu.gtk.gdk.Clipboard
import ch.bailu.gtk.adw.Clamp
import ch.bailu.gtk.adw.ToastOverlay;
import ch.bailu.gtk.adw.Toast;
import ch.bailu.gtk.pango.Alignment
import ch.bailu.gtk.type.Str
import graphics.Color
import gui.rowBox

public class MyAbout {
    public fun run(parentWindow : Window , version : String) = aboutDialog(parentWindow , version)
}



public fun aboutDialog(parentWindow : Window , version : String) {
    val creditsPage = vbox(10).margins(12).children(
        Label("Code by").cssClasses("title-4").halign(Align.START).hexpand(true),
        hbox(0).children(Label("Hamza Algohary").halign(Align.START).margins(20,10)).cssClasses("card").sizeRequest(-1, 50).hexpand(true),
        Label("Icon Design").cssClasses("title-4").halign(Align.START).hexpand(true),
        hbox(0).children(Label("Alhusain Algohary").halign(Align.START).margins(20,10)).cssClasses("card").sizeRequest(-1, 50).hexpand(true)
    ).shown()

    val licensePage = vbox(10).margins(12).children(
        Label("Coulomb is open source software, its code is released under the terms of GNU General Public License v3").cssClasses("title-5").apply{wrap=true},
        Expander("Details").child(
            ScrolledWindow().vexpand(true).cssClasses("view").child(
                Label(AboutDialog.LICENSE).apply{
                    wrap = true
                }
            )
        )
    ).shown()

    var window = Window()
    var headerBar = HeaderBar()
    var stack = Stack()
    stack.setTransitionType(StackTransitionType.SLIDE_LEFT_RIGHT)
    var backButton = Button().child(Icon("back")).cssClasses("flat")
    backButton.ref()
    backButton - {
        window.setTitle("")
        headerBar.remove(backButton)
        stack.setVisibleChildName("Main")
    }


    window.cssClasses("about","background","csd").title("").defaultSize(360, 578) + window@ {
        titlebar(
            headerBar.cssClasses("flat")
        )

        child(stack)
        setResizable(false);
        setTransientFor(parentWindow);

        stack.addNamed(
                ToastOverlay().child(
                    WindowHandle().child(
                        vbox(10).margins(12).children(
                            Color.logoImage().sizeRequest(124, 124), 

                            Label("Coulomb").cssClasses("title-1") ,

                            Label("Hamza Algohary") ,

                            Clamp().apply {  
                                setChild(
                                    Button().cssClasses("app-version","accent").child(Label(version)).sizeRequest(70, -1) - {
                                        Color.copyToClipboard(version)
                                        toastOverlay.sendToast("Copied to clipboard")
                                    }
                                )
                                setMaximumSize(50)
                            } , 

                            vbox(14).children(
                                rowBox(
                                    hbox().cssClasses("title").margins(0,12).children(
                                        Label("Website").halign(Align.START).margins(20,0).hexpand(true),
                                        Icon("external-link").halign(Align.END)
                                    )
                                ).cssClasses("card" , "activatable").margins(3,0).onClick {
                                    Platform.launch_url("https://github.com/hamza-Algohary/Coulomb")
                                },

                                rowBox(
                                    hbox().cssClasses("title").margins(0,12).children(
                                        Label("Report an issue").halign(Align.START).margins(20,0).hexpand(true),
                                        Icon("external-link").halign(Align.END)
                                    )
                                ).cssClasses("card" , "activatable").margins(3,0).onClick {
                                    Platform.launch_url("https://github.com/hamza-Algohary/Coulomb/issues")
                                } ,

                                ListBox().cssClasses("boxed-list","header").children(
                                    hbox().cssClasses("title").margins(0,12).children(
                                        Label("Credits").halign(Align.START).margins(19,0).hexpand(true),
                                        Icon("front").halign(Align.END)
                                    ).onClick {
                                        this@window.setTitle("Credits")
                                        headerBar.packStart(backButton)
                                        stack.setVisibleChild(creditsPage)
                                    },
                                    hbox().cssClasses("title").margins(0,12).children(
                                        Label("License").halign(Align.START).margins(19,0).hexpand(true),
                                        Icon("front").halign(Align.END)
                                    ).onClick { 
                                        this@window.setTitle("License")
                                        headerBar.packStart(backButton)
                                        stack.setVisibleChild(licensePage)
                                    }
                                ).apply { 
                                    setSelectionMode(SelectionMode.NONE)    
                                }
                            )

                        )
                    )
                )
            , "Main")

            stack.addNamed(licensePage, "license")
            stack.addNamed(creditsPage , "credits")
        

    }




}

