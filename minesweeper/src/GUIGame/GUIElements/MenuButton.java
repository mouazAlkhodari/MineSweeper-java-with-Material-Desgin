package GUIGame.GUIElements;


import javafx.scene.control.Button;

public class MenuButton extends Button {
    public MenuButton() { this("button"); }
    public MenuButton(String title) {
        super(title);
        getStylesheets().addAll("Styles/style.css");
        getStyleClass().addAll("menubutton","h3");
    }
}
