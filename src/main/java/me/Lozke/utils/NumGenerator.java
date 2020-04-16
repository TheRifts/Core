package me.Lozke.utils;

import java.util.Random;

public class NumGenerator {

    private static final Random random = new Random();

    public static int Roll(int num) {
        return random.nextInt(num);
    }

    public static int Roll(int min, int max) {
        return random.nextInt(max) + min;
    }
}