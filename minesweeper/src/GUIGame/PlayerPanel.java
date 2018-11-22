package GUIGame;

import CustomProgressIndicators.RingProgressIndicator;
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
    HBox topPanel;
    VBox leftPanel;
    private Label playerNameLeftLabel;
    private Label playerNameTopLabel;
    private Label playerScoreLabel;
    private Label playerNumberOfShieldLabel;
    private Label playerTimerLabel;
    private RingProgressIndicator timeProgress;
    private ImageView shieldsImage,scoreImage;
    private HBox Shields = new HBox(20);
    private HBox Score = new HBox(20);
    private Player player;

    public VBox getRightPanel() {
        return rightPanel;
    }
    public HBox getTopPanel(){
        return topPanel;
    }
    public VBox getLeftPanel() { return leftPanel;}
    public RingProgressIndicator getTimeProgress(){ return timeProgress; }
    public void setTime(double time){
        Platform.runLater(new Runnable() {
            @Override public void run() {
                timeProgress.setProgress(time);
                playerTimerLabel.setText("Time: " + String.valueOf(time));
            }
        });

    }

    public PlayerPanel(Player _player) {
        player=_player;
        playerNameLeftLabel=new Label(player.getName());
        playerNameTopLabel=new Label(player.getName());
        playerScoreLabel=new Label(String.valueOf(player.getCurrentScore().getScore()));
        playerNumberOfShieldLabel=new Label(String.valueOf(player.getNumberOfShield()));
        playerNumberOfShieldLabel.textProperty().addListener((v,oldValue,newValue) -> {
            if(Integer.valueOf(oldValue) < Integer.valueOf(newValue)) {
                ShieldsIncAnimation();
            } else if (Integer.valueOf(newValue) < Integer.valueOf(oldValue)) {
                ShieldsDecAnimation();
            }
        });
        playerTimerLabel=new Label("Time: " +player.getTimeforTimer());
        timeProgress = new RingProgressIndicator();
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
        rightPanel =new VBox();
            rightPanel.getStyleClass().add("playerboard");
            rightPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        rightPanel.getChildren().addAll(playerNameLeftLabel,Score,Shields);
        leftPanel = new VBox();
            leftPanel.getStyleClass().addAll("center");
        leftPanel.getChildren().addAll(timeProgress);
        topPanel =new HBox();
            topPanel.getStyleClass().add("playerboard");
            topPanel.setStyle("-fx-background-color: "+(player.getColor())+";");
        topPanel.getChildren().addAll(playerNameTopLabel);
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
