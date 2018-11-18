package GUIGame;

import Models.Player.Player;
import Models.Player.PlayerStatus;
import Models.Shield.Shield;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;

import java.io.FileInputStream;

public class PlayerPanel {
    VBox leftPanel;
    HBox topPanel;
    public Label playerNameLeftLabel,playerNameTopLabel,playerScoreLabel,playerNumberOfShieldLabel,playerTimerLabel;
    public ImageView shieldsImage,scoreImage;
    public HBox Shields = new HBox(20);
    public HBox Score = new HBox(20);
    Player player;
    public PlayerPanel(Player _player) {
        player=_player;
        playerNameLeftLabel=new Label(player.getName());
        playerNameTopLabel=new Label(player.getName());
        playerScoreLabel=new Label(String.valueOf(player.getCurrentScore().getScore()));
        playerNumberOfShieldLabel=new Label(String.valueOf(player.getNumberOfShield()));
        playerTimerLabel=new Label("Time: " );
            playerNameLeftLabel.getStyleClass().add("h2");
            playerNameTopLabel.getStyleClass().add("h2");
            playerScoreLabel.getStyleClass().addAll("h1","center");
            playerNumberOfShieldLabel.getStyleClass().addAll("h1","center");
            playerTimerLabel.getStyleClass().add("h2");

        shieldsImage = new ImageView(new Image("images/shields.png"));
        shieldsImage.setFitHeight(75);shieldsImage.setFitWidth(75);
        Shields.getChildren().addAll(shieldsImage,playerNumberOfShieldLabel);

        scoreImage = new ImageView(new Image("images/score.png"));
        scoreImage.setFitHeight(75);scoreImage.setFitWidth(75);
        Score.setStyle("-fx-border-width: 0 0 0.5 0;-fx-border-color: #485761;");
        Score.getChildren().addAll(scoreImage,playerScoreLabel);
        leftPanel =new VBox();
            leftPanel.getStyleClass().add("playerboard");
            leftPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        leftPanel.getChildren().addAll(playerNameLeftLabel,Score,Shields);

        topPanel =new HBox();
            topPanel.getStyleClass().add("playerboard");
            topPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        topPanel.getChildren().addAll(playerNameTopLabel,playerTimerLabel);
    }

    public void Update(){
        playerScoreLabel.setText(String.valueOf(player.getCurrentScore().getScore()));
        if(player.getCurrentStatus()== PlayerStatus.Lose)playerScoreLabel.setText(playerScoreLabel.getText() + " Lose");
        if(player.getNumberOfShield()>=0)
            playerNumberOfShieldLabel.setText(String.valueOf(player.getNumberOfShield()));

        if(player.getCurrentStatus()== PlayerStatus.Playing){
            playerNameLeftLabel.setStyle("-fx-font-weight: Bold");
        }
        else{
            playerNameLeftLabel.setStyle("-fx-font-weight: normal");
        }
    }
    public VBox getLeftPanel() {
        return leftPanel;
    }
    public HBox getTopPanel(){
        return topPanel;
    }

    public void ShieldsIncAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.millis(500), shieldsImage);
        st.setByX(1.75);
        st.setByY(1.75);
        st.play();
        st.setOnFinished(e -> {
            ScaleTransition st1 = new ScaleTransition(Duration.millis(200), shieldsImage);
            st1.setToY(1);
            st1.setToX(1);
            st1.play();
        });
    }

    public void ShieldsDecAnimation() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50),shieldsImage);
        tt.setByX(10);
        tt.play();
        tt.setOnFinished(e -> {
            tt.setByX(-20);
            tt.play();
            tt.setOnFinished(e1 -> {
                tt.setByX(20);
                tt.play();
                tt.setOnFinished(e2 -> {
                    tt.setByX(-20);
                    tt.play();
                });
                tt.setOnFinished(e3 -> {
                    tt.setByX(-10);
                    tt.play();
                    tt.setOnFinished(e4 -> {
                        tt.stop();
                    });
                });
            });
        });
    }

}
