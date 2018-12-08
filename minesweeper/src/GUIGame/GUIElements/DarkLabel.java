package GUIGame.GUIElements;

import javafx.scene.control.Label;

public class DarkLabel extends Label {
    public DarkLabel(String title) {
        super(title);
        getStylesheets().addAll("Styles/style.css");
        getStyleClass().addAll("buttonlabel","h3","padding-sm");
    }

    public DarkLabel() {
        this("label");
    }
}
