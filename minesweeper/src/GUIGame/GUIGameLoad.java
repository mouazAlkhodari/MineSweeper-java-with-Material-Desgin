package GUIGame;

import GUIGame.GUIElements.Footer;
import GUIGame.GUIElements.MenuButton;
import GUIGame.GUIElements.Top;
import SaveLoad.Directories;
import SaveLoad.SaveLoad;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.util.Optional;


public class GUIGameLoad {
    protected Scene scene;
    protected ListView<String> games;
    protected BorderPane Layout = new BorderPane();
    protected VBox loadList;
    protected Top Top = new Top("LOAD GAME");
    protected Footer footer;
    protected GUIGameMainMenu begin;
    protected MenuButton back = new MenuButton("Back");
    protected MenuButton load = new MenuButton("Load");
    protected MenuButton delete = new MenuButton("delete");


    public Scene getScene() {
        return scene;
    }

    GUIGameLoad(GUIGameMainMenu _begin){
        begin=_begin;
        initLayout();
        scene=new Scene(Layout);
    }
    public void addGame(String game){
        games.getItems().add(Directories.getVal(game));
        games.getSelectionModel().selectFirst();

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
    public void delete(String name){
        delete(games.getItems().lastIndexOf(name));
    }
    public void deleteSelected() throws Exception {
        if(games.getSelectionModel().getSelectedItem()==null)
            throw new Exception("not valid selected");
        delete(games.getSelectionModel().getSelectedIndex());
    }
    public void delete(int index){
        if(index<=-1 || index >= games.getItems().size())return;
        SaveLoad.deleteFile(Directories.save,games.getItems().get(index)+".save");
        games.getItems().remove(index);
        games.getSelectionModel().selectFirst();
    }
    private void initList() {
        games=new ListView<>();
        games.setItems(Directories.getItems(Directories.save));

        games.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        games.getSelectionModel().selectFirst();

        games.getStyleClass().add("list-view");
        games.getStylesheets().addAll("Styles/style.css");
        loadList=new VBox();
        loadList.getChildren().addAll(games);
        loadList.getStylesheets().addAll("Styles/style.css");
        loadList.getStyleClass().addAll("padding-lg");
    }

    private void initFooter() {
        back.setOnAction(e->{
            begin.Window.setScene(begin.getWelcomescene());
            begin.Window.centerOnScreen();
        });
        load.getStyleClass().addAll("custombutton");
        load.setOnAction(e->{
            try {
                if(games.getSelectionModel().getSelectedIndex()==-1)throw new Exception("not valid selected");
                begin.loadGame(games.getSelectionModel().getSelectedItem());
            }
            catch (Exception ex){
                System.out.println("not valid selected");
//`                ex.printStackTrace();
            }
        });
        //noinspection Duplicates
        delete.setOnAction(e-> {
            if(games.getSelectionModel().getSelectedIndex()==-1)return;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Look, you want to delete ["+ games.getSelectionModel().getSelectedItem()+
                    "]\n that will remove its files from the data of the game"
                    );
            alert.setContentText("Are you sure?");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().addAll("Styles/style.css");
            dialogPane.getStyleClass().add("myDialog");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                try {
                    deleteSelected();
                } catch (Exception ex) {
                    System.out.println("not valid selected");
                }
            }
        });

        footer = new Footer(back,load,delete);
    }
}
