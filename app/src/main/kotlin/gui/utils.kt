package gui;

import ch.bailu.gtk.gtk.Widget
import ch.bailu.gtk.gtk.Box
import ch.bailu.gtk.gtk.Align
import ch.bailu.gtk.gtk.Orientation
import ch.bailu.gtk.gtk.WindowHandle
import ch.bailu.gtk.gtk.Button
import ch.bailu.gtk.gtk.ScrolledWindow
import ch.bailu.gtk.gtk.Window
import ch.bailu.gtk.gtk.HeaderBar
import ch.bailu.gtk.gtk.ListBox
import ch.bailu.gtk.gtk.ListBoxRow
import ch.bailu.gtk.gtk.GestureClick;
import ch.bailu.gtk.gtk.Stack
import ch.bailu.gtk.gtk.Expander
import ch.bailu.gtk.adw.ToastOverlay
import ch.bailu.gtk.adw.Toast
import graphics.Color

public var toastOverlay = ToastOverlay()
public var mainStack = Stack()

public fun <T : Widget> T.margins(top : Int , start : Int = top , bottom : Int = top , end : Int = start) = this.apply {
    setMarginTop(top)
    setMarginBottom(bottom)
    setMarginStart(start)
    setMarginEnd(end)
}

public fun <T : Widget> T.cssClasses(vararg classes : String) = this.apply { 
    classes.forEach { addCssClass(it) }
}

public fun <T : Widget> T.hexpand(expand : Boolean) = this.apply{ setHexpand(expand) }
public fun <T : Widget> T.vexpand(expand : Boolean) = this.apply{ setVexpand(expand) }
public fun <T : Widget> T.halign(align : Int) = this.apply{ setHalign(align) }
public fun <T : Widget> T.valign(align : Int) = this.apply{ setValign(align) }

public fun <T : Widget> T.sizeRequest(width : Int , height : Int) = this.apply { setSizeRequest(width, height)}

// public fun <T : Widget> T.invoke(func : T.()->()) = this.apply {
//     this.func()
// }

// public operator fun Button.invoke(handler : ()-> Unit) = this.apply {
//     this.onClicked(handler)
// }

public fun hbox(spacing : Int = 0) = Box(Orientation.HORIZONTAL, spacing)
public fun vbox(spacing : Int = 0) = Box(Orientation.VERTICAL  , spacing) 

public fun Box.children(vararg widgets : Widget) = this.apply {
    widgets.forEach { append(it) }
}
public fun ListBox.children(vararg widgets : Widget) = this.apply {
    widgets.forEach { append(rowBox(it)) }
}

public fun ToastOverlay.child(child : Widget) = this.apply {
    toastOverlay = this
    setChild(child)
}

public fun ToastOverlay.sendToast(title : String) = this.apply {
    this.addToast(Toast(title))
}

public fun rowBox(child : Widget) = ListBoxRow().sizeRequest(-1, 50).apply {
    setChild(child)
}

public fun ListBoxRow.action(action : ()->Unit) = this.apply {
    this.activatable = true
    this.onActivate(action);
}

public fun <T : Widget> T.onClick(action : ()->Unit) = this.apply {
    addController(GestureClick().apply { 
        this.onReleased { a,b,c ->
            action()
        }
    })
}

public fun HeaderBar.children(vararg widgets : Widget) = this.apply {
    widgets.forEach { packStart(it) }
}
public fun Stack.child(child : Widget) = Stack().apply{
    this.addChild(child)
    this.setVisibleChild(child)
}

public fun WindowHandle.child(child : Widget) = this.apply{ this.setChild(child) }
public fun Button.child(child : Widget) = this.apply{ this.setChild(child) }
public fun ScrolledWindow.child(child : Widget) = this.apply{ this.setChild(child) }
public fun Window.child(child : Widget) = this.apply{ this.setChild(child) }
public fun Expander.child(child : Widget) = this.apply{ this.setChild(child) }

public fun Window.defaultSize(width : Int , height : Int) = this.apply {
    this.setDefaultSize(width, height)
}

public fun Window.title(title : String) = this.apply {
    this.setTitle(title)
}

public fun Window.titlebar(titlebar : Widget?) = this.apply {
    this.setTitlebar(titlebar)
}

public operator fun <T : Widget> T.plus (block : T.() -> Unit) = this.apply {
    block()
    show()
}

public operator fun Button.minus(handler : ()-> Unit) = this.apply {
    this.onClicked(handler)
}

public fun <T:Widget> T.shown() = this.apply {
    show()
}