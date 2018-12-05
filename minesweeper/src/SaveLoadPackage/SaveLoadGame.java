package SaveLoadPackage;


import java.io.*;

public class SaveLoadGame {
    public static<T> void saveGame(String fname, T game){// GUIGame
        try {
            File textFile = new File(fname);
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

    public static<T> T loadGame(String fname){
        try{
            // Reading Object From a File
            FileInputStream loadFile = new FileInputStream(fname);
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
