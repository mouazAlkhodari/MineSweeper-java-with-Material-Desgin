package GUIGame;

import Models.Player.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlayerPanel {
    HBox Panel;
    Player player;
    public PlayerPanel(Player _player) {
        Panel =new HBox();
        player=_player;
        Panel.getStyleClass().add("playerboard");
        Label playerNameLabel=new Label(_player.getName() + " : ");
        Label playerScoreLabel=new Label(String.valueOf(_player.getCurrentScore().getScore()));
        playerNameLabel.getStyleClass().add("h2");
        playerScoreLabel.getStyleClass().add("h2");
        Panel.setStyle("-fx-background-color: "+(_player.getColor())+";");
        Panel.getChildren().addAll(playerNameLabel,playerScoreLabel);
    }
    public HBox getPanel() {
        return Panel;
    }

}
