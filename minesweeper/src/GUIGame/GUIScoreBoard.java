package GUIGame;

import GUIGame.GUIElements.Footer;
import GUIGame.GUIElements.MenuButton;
import GUIGame.GUIElements.Top;
import Models.ScoreBoard.PlayerBoard;
import Models.ScoreBoard.ScoreBoard;
import SaveLoad.Directories;
import SaveLoad.SaveLoadGame;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;
import java.util.Optional;


public class GUIScoreBoard extends ScoreBoard {
    public Scene scene;
    protected BorderPane layout = new BorderPane();
    protected Top Top = new Top("SCOREBOARD");
    protected Footer footer;
    protected MenuButton BackButton = new MenuButton("Back");
    protected MenuButton ReplayButton = new MenuButton("Replay");
    protected MenuButton deleteButton = new MenuButton("Delete");
    protected TableView <PlayerBoard> table;
//    protected JFXTreeTableView<PlayerBoard> table;

    protected  GUIGameMainMenu Begin;

    public GUIScoreBoard(GUIGameMainMenu menu) {
        super();
        initScene();
        UpdateView();
        Begin = menu;
    }
    void initScene() {
        initLayout();
        scene = new Scene(layout);
        table.prefWidthProperty().bind(scene.widthProperty());
    }

    public void deleteSelected() throws Exception {
        if(table.getSelectionModel().getSelectedItem()==null)
            throw new Exception("not valid selected");
        delete(table.getSelectionModel().getSelectedIndex());
    }
    public void delete(int index){
        SaveLoadGame.deleteFile(Directories.replay,table.getItems().get(index).getReplayedGame());
        SaveLoadGame.deleteFile(Directories.scoreboard,table.getItems().get(index).getScoreboardReg());
        table.getItems().remove(index);
    }
    void initLayout() {
             //init Tabele
        initTable();

        //inti FOOTER
        BackButton.setOnAction(e -> {
            Begin.Window.setScene(Begin.getWelcomescene());
            Begin.Window.centerOnScreen();
        });

        ReplayButton.getStyleClass().addAll("custombutton");
        ReplayButton.setOnAction(e -> {
            try {
                if(table.getSelectionModel().getSelectedIndex()==-1)throw new Exception("not valid selected");
                Begin.replayGame(table.getSelectionModel().getSelectedItem().getReplayedGame());
            }
            catch (Exception ex){
                System.out.println("not valid selected");
//                ex.printStackTrace();
            }
        });
        //noinspection Duplicates
        deleteButton.setOnAction(e-> {
            if(table.getSelectionModel().getSelectedIndex()==-1)
                return;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Look, you want to delete ["+ table.getSelectionModel().getSelectedItem().getScoreboardReg()+
                    "]\n that will remove its files from the data of the game"
            );
            alert.setContentText("Are you sure ?");
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
        footer=new Footer(BackButton,ReplayButton,deleteButton);

        layout.setTop(Top);
        layout.setCenter(table);
        layout.setBottom(footer);
        layout.getStylesheets().addAll("Styles/style.css");
        layout.getStyleClass().addAll("windowsize750");
    }

    void initTable() {
      //init Columns
        TableColumn<PlayerBoard, String> PlayerNameColumn = new TableColumn<>("Player Name");
        PlayerNameColumn.setMinWidth(150);
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
        TableColumn<PlayerBoard, String> GameTimeColumn = new TableColumn<>("Game Time");
        GameTimeColumn.setMinWidth(150);
        GameTimeColumn.setCellValueFactory(new PropertyValueFactory<>("GameTime"));
        TableColumn<PlayerBoard, Integer> FinalScoreColumn = new TableColumn<>("Final Score");
        FinalScoreColumn.setMinWidth(150);
        FinalScoreColumn.setCellValueFactory(new PropertyValueFactory<>("FinalScore"));
        TableColumn<PlayerBoard, String> GameDifficultyColumn = new TableColumn<>("Game Difficulty");
        GameDifficultyColumn.setMinWidth(150);
        GameDifficultyColumn.setCellValueFactory(new PropertyValueFactory<>("GameDifficulty"));
        TableColumn<PlayerBoard, String> MinesColumn = new TableColumn<>("Mines");
        MinesColumn.setMinWidth(150);
        MinesColumn.setCellValueFactory(new PropertyValueFactory<>("Mines"));
        TableColumn<PlayerBoard, String> ShieldsColumns = new TableColumn<>("Shields");
        ShieldsColumns.setMinWidth(150);
        ShieldsColumns.setCellValueFactory(new PropertyValueFactory<>("Shields"));

        table = new TableView<>();
        table.getStylesheets().addAll("Styles/table.css");
        table.getStyleClass().addAll("padding-lg-full-width");
//        table.selectionModelProperty().addListener((v,oldValuue,newValue) -> {
//           newValue.getSelectedCells().
//        });
        table.setItems(scoreboard);
        table.getColumns().addAll(PlayerNameColumn,GameTimeColumn,FinalScoreColumn,GameDifficultyColumn,MinesColumn,ShieldsColumns);
    }

    @Override
    public void UpdateView() {
            table.setItems(scoreboard);
    }
}
