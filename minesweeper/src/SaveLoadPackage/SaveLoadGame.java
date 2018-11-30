package SaveLoadPackage;

import GUIGame.GUIGame;
import Models.Game.NormalGame;

import java.io.*;

public class SaveLoadGame {
    public static void saveGame(String fname, GUIGame game){
            GameSave singleSave = new GameSave();
            singleSave.grid = game.getGrid();
            singleSave.currentPlayer = game.getCurrentPlayer();
            singleSave.moves = game.getGameMoves();
            singleSave.status = game.getGameStatus();
            singleSave.flagsNumber = game.getFlagsNumber();
            singleSave.shieldsNumber = game.getShieldsNumber();
            singleSave.players = game.getPlayers();
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
    public static void loadGame(String fname){
        try{

            ObjectInputStream loadStream = new ObjectInputStream(new FileInputStream(fname));
            NormalGame game = (NormalGame)loadStream.readObject();
            loadStream.close();
            game.GetMove();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
