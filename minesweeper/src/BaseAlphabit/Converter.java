package BaseAlphabit;

public class Converter {
    public static int valueOf(String alphaNum) {// get integer value of number in alphabet base number...
        int ret=0;
        for(int i=0;i<alphaNum.length();i++){
            ret=ret*26+(Integer.valueOf(alphaNum.charAt(i))-(int)'A'+1);
        }
        return ret;
    }
    public static String valueOf(int num) {// get integer value of number in alphabet base number...
        String ret="";
        while(num!=0){
            ret+='A' + (num%26);
            num/=26;
        }
        return ret;
    }
}
