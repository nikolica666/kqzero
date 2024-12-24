package hr.nipeta.kqzero.gameobjects.entities;

import java.util.Random;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    private static final Random RANDOM = new Random();
    private static final Direction[] DIRECTIONS = values();

    public static Direction random() {
        return DIRECTIONS[RANDOM.nextInt(DIRECTIONS.length)];
    }

}
