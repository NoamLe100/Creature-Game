package com.example.myapplication;

import android.graphics.Bitmap;

public class Creature {
    private String name;
    private int level;
    private int XP;
    private int requiredXP;
    private int hp;
    private int power;
    private float x, y;
    private Bitmap texture;
    private float speed;
    private float cameraX, cameraY;
    private int size;
    private boolean isMoving;
    private float targetX, targetY;

    public Creature(String name, int size, float x, float y) {
        this.name = name;
        this.level = 1;
        this.XP = 0;
        this.hp = 50;
        this.power = 10;
        this.x = x;
        this.y = y;
        this.requiredXP = 250;
        this.speed = 5.0f;
        this.size = size;
        this.isMoving = false;
        this.targetX = x;
        this.targetY = y;
        this.cameraX = x;
        this.cameraY = y;
    }

    public void setTexture(Bitmap texture) {
        this.texture = texture;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getXP() {
        return XP;
    }

    public void addXP(int amount) {
        XP += amount;
        checkLevelUp();
    }

    public int getLevel() {
        return level;
    }

    private void checkLevelUp() {
        while (XP >= requiredXP) {
            XP -= requiredXP;
            level++;
            hp += (int) (hp * 0.05);
            power += (int) (power * 0.05);
            requiredXP += (int) (requiredXP * 0.1);
        }
    }

    public int getRequiredXP() {
        return requiredXP;
    }

    public int getHP() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public float getCameraX() {
        return cameraX;
    }

    public float getCameraY() {
        return cameraY;
    }

    public void setTarget(float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.isMoving = true;
    }

    public void updatePosition(int mapWidth, int mapHeight, CollisionDetector collisionDetector, GameWorld gameWorld) {
        if (isMoving) {
            float dx = targetX - x;
            float dy = targetY - y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance > 5) {
                float moveX = (dx / distance) * speed;
                float moveY = (dy / distance) * speed;
                float newX = x + moveX;
                float newY = y + moveY;

                newX = Math.max(size / 2, Math.min(newX, mapWidth - size / 2));
                newY = Math.max(size / 2, Math.min(newY, mapHeight - size / 2));

                if (!collisionDetector.isColliding(newX, newY, this, gameWorld)) {
                    x = newX;
                    y = newY;
                } else {
                    if (!collisionDetector.isColliding(newX, y, this, gameWorld)) {
                        x = newX;
                    }
                    if (!collisionDetector.isColliding(x, newY, this, gameWorld)) {
                        y = newY;
                    }
                }
            } else {
                isMoving = false;
            }
        }
        cameraX = x;
        cameraY = y;
    }
}