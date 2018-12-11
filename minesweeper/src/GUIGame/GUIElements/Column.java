package GUIGame.GUIElements;

import GUIGame.GUIGame;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Column<S,T> extends TableColumn {
    public Column(String title, String memberName) {
        super(title);
        setMinWidth(150);
        setCellValueFactory(new PropertyValueFactory<>("FinalScore"));
    }
}
