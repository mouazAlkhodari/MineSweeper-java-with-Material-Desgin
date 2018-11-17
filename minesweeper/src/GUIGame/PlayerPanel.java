package GUIGame;

import Models.Player.Player;
import Models.Player.PlayerStatus;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerPanel {
    VBox leftPanel;
    HBox topPanel;
    public Label playerNameLabel,playerScoreLabel,playerNumberOfShieldLabel,playerTimerLabel;
    Player player;
    public PlayerPanel(Player _player) {
        player=_player;

        playerNameLabel=new Label(player.getName() + " : ");
        playerScoreLabel=new Label(String.valueOf(player.getCurrentScore().getScore()));
        playerNumberOfShieldLabel=new Label("Sheild: "+String.valueOf(player.getNumberOfShield()));
        playerTimerLabel=new Label("Time: " );
            playerNameLabel.getStyleClass().add("h2");
            playerScoreLabel.getStyleClass().add("h2");
            playerNumberOfShieldLabel.getStyleClass().add("h2");
            playerTimerLabel.getStyleClass().add("h2");

        leftPanel =new VBox();
            leftPanel.getStyleClass().add("playerboard");
            leftPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        leftPanel.getChildren().addAll(playerNameLabel,playerScoreLabel,playerNumberOfShieldLabel);

        topPanel =new HBox();
            topPanel.getStyleClass().add("playerboard");
            topPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        topPanel.getChildren().addAll(playerNameLabel,playerTimerLabel);
    }
    public VBox getLeftPanel() {
        leftPanel.getChildren().clear();
        leftPanel.getChildren().addAll(playerNameLabel,playerScoreLabel,playerNumberOfShieldLabel);
        return leftPanel;
    }
    public void Update(){
        playerScoreLabel.setText(String.valueOf(player.getCurrentScore().getScore()));
        if(player.getCurrentStatus()== PlayerStatus.Lose)playerScoreLabel.setText(playerScoreLabel.getText() + " Lose");
        if(player.getNumberOfShield()>=0)
            playerNumberOfShieldLabel.setText(String.valueOf("#_#: "+player.getNumberOfShield()));

        if(player.getCurrentStatus()== PlayerStatus.Playing){
            playerNameLabel.setStyle("-fx-font-weight: Bold");
        }
        else{
            playerNameLabel.setStyle("-fx-font-weight: normal");
        }
    }
    public HBox getTopPanel(){
        topPanel.getChildren().clear();
        topPanel.getChildren().addAll(playerNameLabel,playerTimerLabel);
        topPanel.getStyleClass().add("playerboard");
        topPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        return topPanel;
    }
}
