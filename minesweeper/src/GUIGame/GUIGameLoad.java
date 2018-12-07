package GUIGame;

import GUIElements.MenuButton;
import GUIElements.Top;
import SaveLoadPackage.Directories;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUIGameLoad {
    protected Scene scene;
    protected ListView<String> games;
    protected BorderPane Layout = new BorderPane();
    protected VBox loadList;
    protected Top Top = new Top("LOAD GAME");
    protected HBox footer=new HBox(80);
    protected GUIGameMainMenu begin;
    protected MenuButton back = new MenuButton("Back");
    protected MenuButton load = new MenuButton("Load");

    public Scene getScene() {
        return scene;
    }

    GUIGameLoad(GUIGameMainMenu _begin){
        begin=_begin;
        initLayout();
        scene=new Scene(Layout);
    }
    public void addGame(String game){
        games.getItems().add(0,Directories.getVal(game));
    }
    private void initLayout(){
        initFooter();
        initList();
        Layout.getStylesheets().add("Styles/style.css");
        Layout.getStyleClass().addAll("windowsize");
        Layout.setTop(Top);
        Layout.setCenter(loadList);
        Layout.setBottom(footer);

    }

    private void initList() {
        games=new ListView<>();
        games.setItems(Directories.getItems(Directories.save));
        loadList=new VBox();
        loadList.getChildren().addAll(games);

    }

    private void initFooter() {
        footer.setPadding(new Insets(20));
        footer.getStyleClass().addAll("center");
       back.setOnAction(e->{
            begin.Window.setScene(begin.getWelcomescene());
            begin.Window.centerOnScreen();
        });
        load.setOnAction(e->{
            if(!games.getItems().isEmpty())
                begin.loadGame(games.getSelectionModel().getSelectedItem());
        });

        footer.getChildren().addAll(back,load);
    }
}
