package com.example.myapplication;

import java.util.Random;

public class GameWorld {
    private final int[][] trees;
    private final int[][] rivers;
    private final int[][] bushes;
    private final int[][] rocks;
    private static final int NUM_TREES = 50;
    private static final int NUM_RIVERS = 10;
    private static final int NUM_BUSHES = 30;
    private static final int NUM_ROCKS = 20;
    private static final long TREE_SEED = 12345L;
    private final Random random;

    public GameWorld() {
        trees = new int[NUM_TREES][2];
        rivers = new int[NUM_RIVERS][4];
        bushes = new int[NUM_BUSHES][2];
        rocks = new int[NUM_ROCKS][2];
        random = new Random();
    }

    public void generateWorld(int width, int height) {
        Random treeRandom = new Random(TREE_SEED);
        for (int i = 0; i < NUM_TREES; i++) {
            int x, y;
            do {
                x = treeRandom.nextInt(width);
                y = treeRandom.nextInt(height);
            } while (isTooClose(x, y, trees, 50) || isTooClose(x, y, bushes, 50) || isTooClose(x, y, rocks, 50) || isTooClose(x, y, rivers, 50));
            trees[i][0] = x;
            trees[i][1] = y;
        }

        for (int i = 0; i < NUM_RIVERS; i++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (isTooClose(x, y, trees, 50) || isTooClose(x, y, bushes, 50) || isTooClose(x, y, rocks, 50));
            rivers[i][0] = x;
            rivers[i][1] = y;
            rivers[i][2] = Math.min(width, x + random.nextInt(width / 3) - width / 6);
            rivers[i][3] = Math.min(height, y + random.nextInt(height / 3) - height / 6);
        }

        for (int i = 0; i < NUM_BUSHES; i++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (isTooClose(x, y, trees, 50) || isTooClose(x, y, bushes, 50) || isTooClose(x, y, rocks, 50) || isTooClose(x, y, rivers, 50));
            bushes[i][0] = x;
            bushes[i][1] = y;
        }

        for (int i = 0; i < NUM_ROCKS; i++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (isTooClose(x, y, trees, 50) || isTooClose(x, y, bushes, 50) || isTooClose(x, y, rocks, 50) || isTooClose(x, y, rivers, 50));
            rocks[i][0] = x;
            rocks[i][1] = y;
        }
    }

    private boolean isTooClose(int x, int y, int[][] elements, int minDistance) {
        for (int[] element : elements) {
            int distX = Math.abs(x - element[0]);
            int distY = Math.abs(y - element[1]);
            if (distX < minDistance && distY < minDistance) {
                return true;
            }
        }
        return false;
    }

    public int[][] getTrees() {
        return trees;
    }

    public int[][] getRivers() {
        return rivers;
    }

    public int[][] getBushes() {
        return bushes;
    }

    public int[][] getRocks() {
        return rocks;
    }
}