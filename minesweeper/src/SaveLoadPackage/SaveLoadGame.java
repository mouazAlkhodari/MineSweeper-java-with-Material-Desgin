package SaveLoadPackage;

import GUIGame.GUIGame;


import java.io.*;

public class SaveLoadGame {
    public static void saveGame(String fname, GUIGame game){// GUIGame

            GameSave singleSave = new GameSave(game);
        try {

            File textFile = new File(fname);
            FileOutputStream fileStream = new FileOutputStream(textFile);
            ObjectOutputStream out = new ObjectOutputStream(fileStream);
            out.writeObject(singleSave);
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
//    public static void loadGame(String fname){
//        try{
//            // Reading Object From a File
//            FileInputStream loadFile = new FileInputStream(fname);
//            ObjectInputStream loadStream = new ObjectInputStream(loadFile);
//            GameSave loadedGame = new GameSave();
//            loadedGame = (GameSave)loadStream.readObject();
//            GUIGame currentGame = new GUIGame(loadedGame.gameTime,loadedGame.currentRules,loadedGame.currentPlayer,loadedGame.grid,loadedGame.status,loadedGame.players,loadedGame.moves,loadedGame.flagsNumber,loadedGame.shieldsNumber);
//            loadStream.close();
//            loadFile.close();
//            currentGame.StartGame();
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
