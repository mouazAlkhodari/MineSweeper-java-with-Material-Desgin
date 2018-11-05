package GUIGame;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;



public class GUIGameWithOptions extends Application {
    WelcomeScene welcomescene = new WelcomeScene();
    OptionsScene optionsScene = new OptionsScene();
    Stage Window;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Window = primaryStage;
        Window.setScene(welcomescene.scene);
        Window.show();
    }

    class WelcomeScene {
        Scene scene;
        VBox WelcomeLayout;
        Label Welcome;
        Button newGameButton;

        private void initLayout() {
            WelcomeLayout = new VBox(20);
            Welcome = new Label("MineSweeper");
            newGameButton = new Button("NEW GAME");
            newGameButton.setOnAction(e -> {
                Window.setScene(optionsScene.scene);
            });
            //Setting Style
            WelcomeLayout.getStyleClass().add("vbox");
            Welcome.getStyleClass().add("h1");
            Welcome.getStylesheets().add("Styles/style.css");
            newGameButton.getStylesheets().add("Styles/style.css");
            WelcomeLayout.getStylesheets().add("Styles/style.css");
            //Adding Components to layout
            WelcomeLayout.getChildren().addAll(Welcome, newGameButton);
        }

        public WelcomeScene() {
            initLayout();
            scene = new Scene(WelcomeLayout);

        }
    }

    class OptionsScene {
        Scene scene;
        //SettingLabels;
        Label WidthLabel = new Label("Width: ");
        Label HeightLabel = new Label("Height: ");
        Label MinesLabel = new Label("Mines Count: ");

        TextField WidthInput = new TextField();
        TextField HeightInput = new TextField();
        TextField MinesInput = new TextField();

        GridPane optionsGrid = new GridPane();
        public OptionsScene() {
           initScene();
        }

        private void initOptionsGrid() {
            optionsGrid.add(WidthLabel,0,0);
            optionsGrid.add(WidthInput,1,0);
            optionsGrid.add(HeightLabel,0,1);
            optionsGrid.add(HeightInput,1,1);
            optionsGrid.add(MinesLabel,0,2);
            optionsGrid.add(MinesInput,1,2);
        }

        private void initScene() {
            scene = new Scene(optionsGrid);
            initOptionsGrid();

        }
    }
}