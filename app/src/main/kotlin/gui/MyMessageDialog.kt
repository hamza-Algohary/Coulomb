package gui

import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.gtk.Label
import ch.bailu.gtk.gtk.Expander
import ch.bailu.gtk.gtk.ScrolledWindow
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.adw.HeaderBar

class MyMessageDialog {
    public fun run(parent_window : Window) = messageDialog(parent_window)
}

public fun messageDialog (parent_window : Window) {
    Window().title("Important Notes").defaultSize(400, -1) + window@{
        titlebar(
            HeaderBar().cssClasses("flat").apply{
                this.showEndTitleButtons = false
            }
        )
        child(
            vbox(10).margins(0 , 20 , 20 , 20).children(
                Label("• Simlulation of circuits involving capacitors and inductors may not be accurate, sometimes completely wrong.\n\n• Currently you can't connect two capacitors in parallel nor two inductors in series.\n\n• Most of circuits with non linear devices can't be calculated.\n\n• The reason for the above is that inductors and capacitors are modeled as current sources and batteries respectively. Also the backend currently is not good at solving non-linear equations.").apply { wrap = true },
                Button.newWithLabelButton("Ok").cssClasses("suggested-action") - {
                    this@window.hide()
                }
            )
        )
        resizable = false
        transientFor = parent_window
        show()
    }
}