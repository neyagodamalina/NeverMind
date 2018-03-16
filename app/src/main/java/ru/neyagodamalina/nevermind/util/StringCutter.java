package ru.neyagodamalina.nevermind.util;

/**
 * Created by developer on 12.03.2018.
 */

public class StringCutter {
    public static String cut(String s){
        return s.length() > 30 ? s.substring(0, 30) + "..." : s;
    }
}
