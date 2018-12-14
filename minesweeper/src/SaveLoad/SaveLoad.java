package SaveLoad;


import java.io.*;

public class SaveLoad {
    public static<T> void saveObject(File path, String name, T object){// GUIGame
        Thread saveThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File textFile = new File(path +File.separator+ name);
                    FileOutputStream fileStream = new FileOutputStream(textFile);
                    ObjectOutputStream out = new ObjectOutputStream(fileStream);

                    out.writeObject(object);
                    out.close();
                    fileStream.close();
//                    System.out.println("Saved Successfully: "+textFile);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    //         System.out.println("IO Exception");
                }
            }
        });
        saveThread.start();
//        saveThread.setDaemon(true);
    }

    public static<T> T loadObject(File path, String name){
        try{
            // Reading Object From a File
            File textFile=new File(path + File.separator + name);
            FileInputStream loadFile = new FileInputStream(textFile);
            ObjectInputStream loadStream = new ObjectInputStream(loadFile);
            T loadedObject;
            loadedObject = (T) loadStream.readObject();

//            System.out.println("Load Successfully:"+textFile);

            loadStream.close();
            loadFile.close();
            return loadedObject;
        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Error in IO" +textFile);
        }
        return null;
    }
    public static void deleteFile(File path,String name){
        File deletedFile = new File(path + File.separator + name);
        if (deletedFile.exists())
            if(deletedFile.delete()) ;//System.out.println("deleted Successfully:"+deletedFile);
//                else System.out.println("not Deleted:"+deletedFile);
    }
}
