package GUIGame;

import SaveLoadPackage.Directories;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIGameLoad {
    Scene scene;
    ListView<String> games;
    VBox layout;
    GUIGameMainMenu begin;
    Button back,load;

    public Scene getScene() {
        return scene;
    }

    GUIGameLoad(GUIGameMainMenu _begin){
        begin=_begin;
        initLayout();
        scene=new Scene(layout);
    }
    public void addGame(String game){
        games.getItems().add(0,Directories.getVal(game));
    }
    private void initLayout(){
        // load The List
        games=new ListView<>();
        games.setItems(Directories.getItems(Directories.save));

        // Add Buttons
        HBox Buttons=new HBox(80);
        Buttons.setPadding(new Insets(20));
        Buttons.getStyleClass().addAll("center");
        back=new Button("Back");
            back.getStyleClass().addAll("menubutton","h3");
            back.setPrefSize(60,40);
            back.setOnAction(e->{
                begin.Window.setScene(begin.getWelcomescene());
                begin.Window.centerOnScreen();
            });
        load=new Button("Load");
            load.getStyleClass().addAll("menubutton","h3");
            load.setPrefSize(60,40);
            load.setOnAction(e->{
                if(!games.getItems().isEmpty())
                    begin.loadGame(games.getSelectionModel().getSelectedItem());
            });

        Buttons.getChildren().addAll(back,load);

        layout=new VBox();
        layout.getStylesheets().add("Styles/style.css");
        layout.getStyleClass().addAll("windowsize");
        layout.getChildren().addAll(games,Buttons);
    }
}
