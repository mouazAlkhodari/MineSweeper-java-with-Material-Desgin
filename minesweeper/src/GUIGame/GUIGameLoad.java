package GUIGame;

import SaveLoadPackage.Directories;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIGameLoad {
    Scene scene;
    ListView<String> games;
    VBox layout;
    GUIGameMainMenu begin;
    GUIGameLoad(GUIGameMainMenu _begin){
        begin=_begin;
        initLayout();
        scene=new Scene(layout);
    }
    private void initLayout(){
        games.setItems(Directories.getItems(Directories.save));

    }
}
