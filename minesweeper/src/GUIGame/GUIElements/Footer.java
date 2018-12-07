package GUIGame.GUIElements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.List;

public class Footer extends HBox {
    public Footer(Node ... elements) {
        super(80);
        setPadding(new Insets(20));
        getStylesheets().addAll("Styles/style.css");
        getStyleClass().addAll("center");
        getChildren().addAll(elements);
    }
    public Footer() {
        this(null);
    }


}
