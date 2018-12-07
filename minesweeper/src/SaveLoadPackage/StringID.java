package SaveLoadPackage;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDateTime;


public class StringID {
    private static String getID() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String SaveID() {
        return new String("save " + getID() + ".save");
    }

    public static String ReplayID() {
        return new String("replay " + getID() + ".replay");
    }

    public static String ScoreBoardID() {
        return new String("scoreboard " + getID()  + ".score");
    }


}
