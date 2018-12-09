package GUIGame;

import GUIGame.GUIElements.RingTimer;
import GUIGame.GUIElements.Top;
import Models.Player.Player;
import Models.Player.PlayerStatus;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;

public class PlayerPanel {
    VBox rightPanel;
    Top topPanel;
    RingTimer ringTimer;
    private Label playerNameLeftLabel;
    private Label playerScoreLabel;
    private Label playerNumberOfShieldLabel;
    //private Label gameTimerLabel;
    private ImageView shieldsImage,scoreImage;
    private HBox Shields = new HBox(20);
    private HBox Score = new HBox(20);
    private Player player;

    public VBox getRightPanel() {
        return rightPanel;
    }
    public Top getTopPanel(){
        return topPanel;
    }
    public RingTimer getRingTimer() { return ringTimer;}
     public void setTime(double time){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                ringTimer.setProgress(time);
            }
        });
    }

    public PlayerPanel(Player _player) {
        player=_player;

        ringTimer = new RingTimer(player.getTimeforTimer());
        topPanel =new Top("Current Player: "+player.getName());
        topPanel.setStyle("-fx-background-color: "+(player.getColor())+";-fx-pref-height: 25;");

        playerNameLeftLabel=new Label(player.getName());
        playerScoreLabel=new Label(String.valueOf(player.getCurrentScore().getScore()));
        playerNumberOfShieldLabel=new Label(String.valueOf(player.getShieldCountBegin()));
        playerNumberOfShieldLabel.textProperty().addListener((v,oldValue,newValue) -> {
            if(Integer.valueOf(oldValue) < Integer.valueOf(newValue)) {
                ShieldsIncAnimation();
            } else if (Integer.valueOf(newValue) < Integer.valueOf(oldValue)) {
                ShieldsDecAnimation();
            }
        });
            playerNameLeftLabel.getStyleClass().addAll("h2");
            playerScoreLabel.getStyleClass().addAll("h1","center");
            playerNumberOfShieldLabel.getStyleClass().addAll("h1","center");

        shieldsImage = new ImageView(new Image("images/shields.png"));
        shieldsImage.setFitHeight(75);shieldsImage.setFitWidth(75);
        Shields.getChildren().addAll(shieldsImage,playerNumberOfShieldLabel);

        scoreImage = new ImageView(new Image("images/score.png"));
        scoreImage.setFitHeight(75);scoreImage.setFitWidth(75);
        Score.getStyleClass().addAll("line");
        Score.getChildren().addAll(scoreImage,playerScoreLabel);
        rightPanel =new VBox();
            rightPanel.setMaxWidth(200);
            rightPanel.getStyleClass().add("playerboard");
            rightPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        rightPanel.getChildren().addAll(playerNameLeftLabel,Score,Shields);


    }

    public void Update(){
        playerScoreLabel.setText(String.valueOf(player.getCurrentScore().getScore()));
        if(player.getCurrentStatus()== PlayerStatus.Lose)playerScoreLabel.setText(playerScoreLabel.getText() + " Lose");
        playerNumberOfShieldLabel.setText(String.valueOf(player.getNumberOfShield()));

        if(player.getCurrentStatus()== PlayerStatus.Playing){
            playerNameLeftLabel.setStyle("-fx-font-weight: Bold");
        }
        else{
            playerNameLeftLabel.setStyle("-fx-font-weight: normal");
        }
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
