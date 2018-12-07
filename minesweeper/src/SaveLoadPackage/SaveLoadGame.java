package SaveLoadPackage;


import GUIGame.GUIGame;
import Models.Player.Player;

import java.io.*;

public class SaveLoadGame {
    public static<T> void saveGame(File path,String name, T game){// GUIGame
        try {
            File textFile = new File(path.getCanonicalPath() +File.separator+ name);
            System.out.println(textFile);
            FileOutputStream fileStream = new FileOutputStream(textFile);
            ObjectOutputStream out = new ObjectOutputStream(fileStream);

            out.writeObject(game);
            out.close();
            fileStream.close();
            System.out.println("Saved Successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception");
        }
    }

    public static<T> T loadGame(File path,String name){
        try{
            // Reading Object From a File
            System.out.println("#Load"+name);
            FileInputStream loadFile = new FileInputStream(new File(path.getCanonicalPath()+File.separator + name));
            ObjectInputStream loadStream = new ObjectInputStream(loadFile);
            T loadedObject;
            loadedObject = (T) loadStream.readObject();

            loadStream.close();
            loadFile.close();
            return loadedObject;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
