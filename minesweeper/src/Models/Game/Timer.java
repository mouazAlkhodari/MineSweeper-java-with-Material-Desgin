package Models.Game;
import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Timer extends Thread {

    private int currentTime;

    public Timer() {
        this.currentTime = 10;
    }

    public void SetTimer(int time) {
        this.currentTime = time;
    }

    public void run()
    {
        while(currentTime > 0)
        {
            currentTime--;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Interrupted Timer");
            }
            System.out.print("Timer : "+ currentTime);
        }
    }

    public int getCurrentTime() {
        return currentTime;
    }
}
