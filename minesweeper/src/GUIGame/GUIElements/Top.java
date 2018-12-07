package GUIElements;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Top extends VBox {
    protected Label Title = new Label("Label");
    public  Top(String name) {
        super();
        getStylesheets().addAll("Styles/style.css");
        getStyleClass().addAll("playerboard");
        Title.getStyleClass().addAll("h1");
        Title.setText(name);
        getChildren().addAll(Title);
    }
    public Top() { this("LABEL"); }
    public void setTitle(String newTitle) { Title.setText(newTitle);}
    public String getTitle() { return Title.getText();}

}
