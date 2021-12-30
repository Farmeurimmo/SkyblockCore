package main.java.fr.verymc.utils;

public class Maths {

    public static double arrondiNDecimales(double d, int n) {
        double pow = Math.pow(10, n);
        return (Math.floor(d * pow)) / pow;
    }

}
