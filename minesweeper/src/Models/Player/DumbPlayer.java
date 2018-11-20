package Models.Player;

import Models.Grid.Square;
import Models.Move.MoveType;
import Models.Move.PlayerMove;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DumbPlayer extends Player {

    private int width,height;
    // Constructors

    public DumbPlayer(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }
    public DumbPlayer(String _name, int width, int height) {
        super(_name);
        this.width = width;
        this.height = height;
    }
    public DumbPlayer(String _name,String _color,int width,int height){
        super(_name,_color);
        this.width = width;
        this.height = height;
    }
    public DumbPlayer(String _name,PlayerStatus _playerStatus,int width,int height){ super(_name,_playerStatus);this.width = width;
        this.height = height;
    }
    public DumbPlayer(String name,PlayerStatus currentStatus,String _color) { super(name,currentStatus,_color);this.width = width;
        this.height = height;
    }
    public DumbPlayer(String name, Score currentScore, PlayerStatus currentStatus,String _color) { super(name,currentScore,currentStatus,_color);this.width = width;
        this.height = height;
    }


    @Override
    public PlayerMove GetPlayerMove() {
        PlayerMove move;
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random rand = new Random();
        int randomx = rand.nextInt(height-2)+1;
        int randomy = rand.nextInt(width-2) +1;
        MoveType randowmType= (rand.nextBoolean()?MoveType.Reveal:MoveType.Mark);
        move = new PlayerMove(new DumbPlayer(height,width),new Square(randomx,randomy));
        if(!canPlay)
            move=new PlayerMove();
        return  move;
    }
}
