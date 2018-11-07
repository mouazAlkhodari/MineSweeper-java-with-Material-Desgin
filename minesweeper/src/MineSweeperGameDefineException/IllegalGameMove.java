package MineSweeperGameDefineException;

public class IllegalGameMove extends MineSweeperGameExeption {
    public IllegalGameMove(){
        super("IllegalGameMove");
    }
    public IllegalGameMove(String messege){
        super(messege);
    }
    public void handle(){
        System.out.println(this.getMessage());
       // printStackTrace();
    }

}
