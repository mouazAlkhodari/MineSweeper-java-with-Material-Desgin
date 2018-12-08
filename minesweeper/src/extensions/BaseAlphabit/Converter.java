package extensions.BaseAlphabit;

public class Converter {
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
