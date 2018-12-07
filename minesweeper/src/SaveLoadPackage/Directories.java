package SaveLoadPackage;

import Models.ScoreBoard.PlayerBoard;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class Directories {
    public static File save = new File("data" + File.separator+ "savedGames");
    public static File replay = new File("data" + File.separator+ "replayGames");
    public static File scoreboard = new File("data" + File.separator+ "scoreBoard");
    public static ObservableList<String> getItems(File path){
        ObservableList<String> Items = FXCollections.observableArrayList();
        if(path.exists()){
            if(path.isDirectory()){
                    for(String item:path.list())
                        Items.add(getVal(item));
                    }
            }
        return Items;
    }
    public static String getVal(String item){
        item=item.substring(item.lastIndexOf(' '),item.length());
        return item;
    }
}
