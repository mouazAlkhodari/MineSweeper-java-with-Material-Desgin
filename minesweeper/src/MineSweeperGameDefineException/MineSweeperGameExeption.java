package MineSweeperGameDefineException;

public abstract class MineSweeperGameExeption extends Exception {



    String detailMessage;

    public MineSweeperGameExeption(String message) {
        detailMessage=message;
    }
    public String getMessage() {
        return detailMessage;
    }
    public abstract void handle();
}
