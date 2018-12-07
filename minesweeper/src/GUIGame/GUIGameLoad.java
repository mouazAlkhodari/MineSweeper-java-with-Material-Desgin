package GUIGame;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIGameLoad {
    Scene scene;
    ListView<HBox> games;
    VBox layout;
    GUIGameLoad(){

        scene=new Scene(layout);
    }
}
