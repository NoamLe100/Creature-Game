package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;

public class TextureManager {
    private Bitmap grassTexture;
    private Bitmap bushTexture;
    private Bitmap treeTexture;
    private Bitmap rockTexture;
    private Bitmap scaledBushTexture;
    private Bitmap scaledTreeTexture;
    private Bitmap scaledRockTexture;
    private final float scaleFactor;
    private final float rockScaleFactor;
    private final Context context;
    private final Random random;

    public TextureManager(Context context, float scaleFactor, float rockScaleFactor) {
        this.context = context;
        this.scaleFactor = scaleFactor;
        this.rockScaleFactor = rockScaleFactor;
        this.random = new Random();
        generateGrassTexture();
        loadBushTexture();
        loadTreeTexture();
        loadRockTexture();
    }

    private void generateGrassTexture() {
        int size = 50;
        grassTexture = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(grassTexture);
        Paint grassPaint = new Paint();
        grassPaint.setColor(Color.rgb(34, 139, 34));
        canvas.drawRect(0, 0, size, size, grassPaint);
        grassPaint.setColor(Color.rgb(46, 180, 46));
        for (int i = 0; i < 10; i++) {
            canvas.drawCircle(random.nextInt(size), random.nextInt(size), 3, grassPaint);
        }
    }

    private void loadBushTexture() {
        bushTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_400);
        scaledBushTexture = Bitmap.createScaledBitmap(bushTexture, (int)(bushTexture.getWidth() * scaleFactor), (int)(bushTexture.getHeight() * scaleFactor), true);
    }

    private void loadTreeTexture() {
        treeTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_0);
        scaledTreeTexture = Bitmap.createScaledBitmap(treeTexture, (int)(treeTexture.getWidth() * scaleFactor), (int)(treeTexture.getHeight() * scaleFactor), true);
    }

    private void loadRockTexture() {
        rockTexture = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_20);
        scaledRockTexture = Bitmap.createScaledBitmap(rockTexture, (int)(rockTexture.getWidth() * rockScaleFactor), (int)(rockTexture.getHeight() * rockScaleFactor), true);
    }

    public Bitmap getGrassTexture() {
        return grassTexture;
    }

    public Bitmap getScaledBushTexture() {
        return scaledBushTexture;
    }

    public Bitmap getScaledTreeTexture() {
        return scaledTreeTexture;
    }

    public Bitmap getScaledRockTexture() {
        return scaledRockTexture;
    }

    public Bitmap getBushTexture() {
        return bushTexture;
    }

    public Bitmap getTreeTexture() {
        return treeTexture;
    }

    public Bitmap getRockTexture() {
        return rockTexture;
    }
}