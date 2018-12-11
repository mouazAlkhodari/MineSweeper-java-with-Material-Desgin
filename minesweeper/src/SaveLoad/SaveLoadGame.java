package SaveLoad;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveLoadGame {
    public static<T> void saveGame(File path,String name, T object){// GUIGame
        try {
            File textFile = new File(path +File.separator+ name);
            FileOutputStream fileStream = new FileOutputStream(textFile);
            ObjectOutputStream out = new ObjectOutputStream(fileStream);

            out.writeObject(object);
            out.close();
            fileStream.close();
            System.out.print("Saved Successfully:");
            System.out.println(textFile);

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
            FileInputStream loadFile = new FileInputStream(new File(path + File.separator + name));
            ObjectInputStream loadStream = new ObjectInputStream(loadFile);
            T loadedObject;
            loadedObject = (T) loadStream.readObject();

            loadStream.close();
            loadFile.close();
            return loadedObject;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in IO");
        }
        return null;
    }
    public static void deleteFile(File path,String name){
            File deletedFile = new File(path + File.separator + name);
            System.out.println(deletedFile);
            if (deletedFile.exists())
                deletedFile.delete();
    }
}
