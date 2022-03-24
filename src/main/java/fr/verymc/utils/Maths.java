package main.java.fr.verymc.utils;

public class Maths {

    public static double arrondiNDecimales(double d, int n) {
        double pow = Math.pow(10, n);
        return (Math.floor(d * pow)) / pow;
    }

    public static String reducedNumberWithLetter(double num){

        String toReturn = null;
        if (num/1000<1000 && num/1000>=1){
            toReturn = (num/1000)+"K";
        }
        if (num/1000000<1000 && num/1000000>=1){
            toReturn = (num/1000000)+"M";
        }
        if(toReturn!=null) {
            return toReturn;
        } else{
            return num+"";
        }
    }

    public static Double getArrond(double num, int n){
        double money = num;
        double pow = Math.pow(10, n);
        return (Math.floor(money * pow)) / pow;
    }
}
