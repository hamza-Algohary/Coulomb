package gui;

import ch.bailu.gtk.gtk.Button;

public class HamburgerMenuButton extends Button {
    HamburgerMenu menu = new HamburgerMenu();
    HamburgerMenuButton(){
        this.setIconName("view-more-symbolic");
        this.onClicked(()->{
            menu.popup();
        });

        menu.setParent(this);
    }
    
}
