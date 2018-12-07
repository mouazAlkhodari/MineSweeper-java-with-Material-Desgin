package GUIGame.GUIElements;

import CustomProgressIndicators.RingProgressIndicator;
import javafx.scene.layout.VBox;

public class RingTimer extends VBox {
    public RingProgressIndicator timeProgress;
    public RingTimer(int timer) {
        super();
        getStylesheets().add("Styles/style.css");
        getStyleClass().addAll("center");
        timeProgress = new RingProgressIndicator(timer);
        timeProgress.getStylesheets().addAll("Styles/Ring.css");
        getChildren().addAll(timeProgress);
    }
    public RingProgressIndicator getTimeProgress(){ return timeProgress; }

    public void setProgress(double time) { timeProgress.setProgress(time);}

}
