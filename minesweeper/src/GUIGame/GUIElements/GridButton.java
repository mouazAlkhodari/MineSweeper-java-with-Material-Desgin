package GUIGame.GUIElements;

import javafx.scene.control.Button;

public class GridButton extends Button {
    public GridButton(double width,double height) {
        super();
        getStylesheets().addAll("Styles/style.css");
        setMinSize(width,height);
        setMaxSize(width,height);
    }

    public GridButton() { this(50,50); }

    public void SetClosed() {
        getStyleClass().removeAll("pressed", "openedMine", "marked");
        getStyleClass().add("notpressed");
    }

    public void SetEmpty(String color) {
        setStyle("-fx-background-color: " + color + "");
        getStyleClass().add("pressed");
    }

    public void SetNumber(int surrounding, String color) {
       getStyleClass().add("f" + (String.valueOf(surrounding) + ""));
       setText("" + surrounding);
       setStyle("-fx-background-color: " + color + "");
       getStyleClass().add("pressed");
    }

    public void SetMine() {
        getStyleClass().addAll("pressed", "openedMine");
    }

    public void SetMarked() {
        getStyleClass().removeAll("notpressed", "closed");
        getStyleClass().addAll("pressed", "marked");

    }
}
