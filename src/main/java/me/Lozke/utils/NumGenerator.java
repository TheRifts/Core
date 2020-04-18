package me.Lozke.utils;

import java.util.Random;

public class NumGenerator {

    private static final Random random = new Random();

    //Get number from 1 to num
    public static int roll(int num) {
        return random.nextInt(num)+1;
    }

    //Get number from min to max inclusive
    public static int rollInclusive(int min, int max) {
        return min + random.nextInt(max-min+1);
    }

    //Get number from 0 to num-1
    public static int index(int num) {
        return random.nextInt(num);
    }

    //Get double from [0,1)
    public static double fraction() {
        return random.nextDouble();
    }
}