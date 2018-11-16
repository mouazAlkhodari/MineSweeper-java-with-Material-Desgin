package GUIGame;

import Models.Player.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerPanel {
    Player player;
    public PlayerPanel(Player _player) {
        player=_player;

    }
    public VBox getPanel() {
        return getleftPanel();
    }
    public VBox getleftPanel() {
        VBox Panel =new VBox();
        Panel.getStyleClass().add("playerboard");
        Label playerNameLabel=new Label(player.getName() + " : ");
        Label playerScoreLabel=new Label(String.valueOf(player.getCurrentScore().getScore()));
        Label playerNumberOfShield=new Label("Sheild: "+String.valueOf(player.getNumberOfShield()));
        playerNameLabel.getStyleClass().add("h2");
        playerScoreLabel.getStyleClass().add("h2");
        playerNumberOfShield.getStyleClass().add("h2");
        Panel.setStyle("-fx-background-color: "+(player.getColor())+";");
        Panel.getChildren().addAll(playerNameLabel,playerScoreLabel,playerNumberOfShield);
        return Panel;
    }
}
