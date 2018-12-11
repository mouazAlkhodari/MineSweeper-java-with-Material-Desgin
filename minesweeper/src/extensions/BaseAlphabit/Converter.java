package extensions.BaseAlphabit;

import java.sql.Time;

public class Converter {

    public static String TimeIntToString(double Time) {
        int time=(int)Time;
        int seconds = time%60;
        time /= 60;
        int minutes = time%60;
        time /= 60;
        int hours = time%60;
        return new Time(hours,minutes,seconds).toString().replaceFirst("00:","");
    }
    // get Integer Value Of Alphabet Numer
    public static int valueOf(String alphaNum) {
        int IntegerValue=0;
        for(int i=0;i<alphaNum.length();i++){
            IntegerValue=IntegerValue*26+(Integer.valueOf(alphaNum.charAt(i))-(int)'A'+1);
        }
        return IntegerValue;
    }
    // get Alphabet Value Of Integer Number
    public static String valueOf(int num) {
        if(num==0)
            return "A";
        String AlphaBetValue="";
        while(num!=0){
            AlphaBetValue+=(char)('A' + (num%26));
            num/=26;
        }
        // AlphabetValue need To revers (:V)
        return AlphaBetValue;
    }
}
