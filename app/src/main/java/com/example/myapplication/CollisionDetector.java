package com.example.myapplication;

public class CollisionDetector {
    private static final float COLLISION_REDUCTION_FACTOR = 0.4f;
    private static final float TREE_ROCK_COLLISION_REDUCTION_FACTOR = 0.8f;
    private static final float RIVER_COLLISION_REDUCTION_FACTOR = 0.25f;
    private static final float ZOOM_FACTOR = 2.0f;
    private static final float SCALE_FACTOR = 2.0f;
    private static final float ROCK_SCALE_FACTOR = 1.5f;

    public boolean isColliding(float x, float y, Creature creature, GameWorld gameWorld) {
        int creatureSize = creature.getSize();
        int scaledElementSize = (int)(10 * SCALE_FACTOR);
        int rockSize = (int)(5 * ROCK_SCALE_FACTOR);

        for (int[] tree : gameWorld.getTrees()) {
            if (Math.abs(x - tree[0]) < (creatureSize + scaledElementSize) / 2 * TREE_ROCK_COLLISION_REDUCTION_FACTOR &&
                    Math.abs(y - tree[1]) < (creatureSize + scaledElementSize) / 2 * TREE_ROCK_COLLISION_REDUCTION_FACTOR) {
                return true;
            }
        }

        for (int[] bush : gameWorld.getBushes()) {
            if (Math.abs(x - bush[0]) < (creatureSize + scaledElementSize) / 2 * COLLISION_REDUCTION_FACTOR &&
                    Math.abs(y - bush[1]) < (creatureSize + scaledElementSize) / 2 * COLLISION_REDUCTION_FACTOR) {
                return true;
            }
        }

        for (int[] rock : gameWorld.getRocks()) {
            if (Math.abs(x - rock[0]) < (creatureSize + rockSize) / 2 * TREE_ROCK_COLLISION_REDUCTION_FACTOR &&
                    Math.abs(y - rock[1]) < (creatureSize + rockSize) / 2 * TREE_ROCK_COLLISION_REDUCTION_FACTOR) {
                return true;
            }
        }

        for (int[] river : gameWorld.getRivers()) {
            float minX = Math.min(river[0], river[2]);
            float maxX = Math.max(river[0], river[2]);
            float minY = Math.min(river[1], river[3]);
            float maxY = Math.max(river[1], river[3]);
            float riverThickness = 40 / ZOOM_FACTOR;
            if (x > minX - (creatureSize / 2 * RIVER_COLLISION_REDUCTION_FACTOR + riverThickness / 2) &&
                    x < maxX + (creatureSize / 2 * RIVER_COLLISION_REDUCTION_FACTOR + riverThickness / 2) &&
                    y > minY - (creatureSize / 2 * RIVER_COLLISION_REDUCTION_FACTOR + riverThickness / 2) &&
                    y < maxY + (creatureSize / 2 * RIVER_COLLISION_REDUCTION_FACTOR + riverThickness / 2)) {
                float dx = river[2] - river[0];
                float dy = river[3] - river[1];
                float length = (float) Math.sqrt(dx * dx + dy * dy);
                if (length > 0) {
                    float t = ((x - river[0]) * dx + (y - river[1]) * dy) / (length * length);
                    t = Math.max(0, Math.min(1, t));
                    float closestX = river[0] + t * dx;
                    float closestY = river[1] + t * dy;
                    float distanceToLine = (float) Math.sqrt((x - closestX) * (x - closestX) + (y - closestY) * (y - closestY));
                    if (distanceToLine < (creatureSize / 2 * RIVER_COLLISION_REDUCTION_FACTOR + riverThickness / 2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}