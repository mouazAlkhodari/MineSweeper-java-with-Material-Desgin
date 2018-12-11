package SaveLoad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class Directories {
    public static File save = new File("data" + File.separator+ "savedGames");
    public static File replay = new File("data" + File.separator+ "replayGames");
    public static File scoreboard = new File("data" + File.separator+ "scoreBoard");
    public static String QuickGame="save Quick-saved-game.save";
    public static File datadir= new File("data" + File.separator);
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
        if(item==null || item.lastIndexOf('.')==-1)return item;
        System.out.println(item);
        item=item.substring(0,item.lastIndexOf('.'));
        return item;
    }
}
