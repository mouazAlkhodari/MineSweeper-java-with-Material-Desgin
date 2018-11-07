package userDefineException;

public class IllegalBoundsOfGrid extends MineSweeperGameExeption {


    public IllegalBoundsOfGrid(){
        super("IllegalBoundsOfGrid");
    }
    public IllegalBoundsOfGrid(String message) {
        super(message);
    }

    @Override
    public void handle() {
        System.out.println(this.getMessage());
        printStackTrace();
    }
}
