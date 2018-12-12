package SaveLoad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.*;
import java.io.File;

public class Directories {
    private static String HOME = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private static String DATADIR = new File(HOME + File.separator  + "MineSweeper").toString();
    public static File save = new File(DATADIR + File.separator + "data" + File.separator+ "savedGames");
    public static File replay = new File(DATADIR + File.separator + "data" + File.separator+ "replayGames");
    public static File scoreboard = new File(DATADIR + File.separator + "data" + File.separator+ "scoreBoard");
    public static String QuickGame="save Quick-saved-game.save";
    public static File quicksave = new File(DATADIR + File.separator + "data" + File.separator);
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

    public static void CheckDir() {
        System.out.println("test");
        if(!save.exists()) {
            save.mkdirs();
        }
        if (!replay.exists()) {
            replay.mkdirs();
        }
        if(!scoreboard.exists()) {
            scoreboard.mkdirs();
        }
        if(!quicksave.exists()) {
            quicksave.mkdirs();
        }
    }
}
