package com.example.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity implements GameView.DrawCallback {
    private GameView gameView;
    private GameWorld gameWorld;
    private Creature creature;
    private CollisionDetector collisionDetector;
    private TextureManager textureManager;
    private Paint paint;
    private static final float ZOOM_FACTOR = 2.0f;
    private static final float SCALE_FACTOR = 2.0f;
    private static final float ROCK_SCALE_FACTOR = 1.5f;
    private static final int CREATURE_SIZE = 200;
    private boolean isWorldGenerated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // יצירת ה-GameView ושילובו בפריסת הפעילות
        FrameLayout layout = new FrameLayout(this);
        gameView = new GameView(this, null);
        layout.addView(gameView);
        setContentView(layout);

        // אתחול הקומפוננטות
        paint = new Paint();
        gameWorld = new GameWorld();
        creature = new Creature("Dragon", CREATURE_SIZE, 100, 100);
        collisionDetector = new CollisionDetector();
        textureManager = new TextureManager(this, SCALE_FACTOR, ROCK_SCALE_FACTOR);

        // טעינת המרקם של היצור
        loadCreatureTexture();

        // הגדרת ה-DrawCallback עבור GameView
        gameView.setDrawCallback(this);

        // טיפול באירועי מגע ישירות ב-MainActivity
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float targetX = (event.getX() / ZOOM_FACTOR) + creature.getCameraX() - (gameView.getWidth() / (2 * ZOOM_FACTOR));
                    float targetY = (event.getY() / ZOOM_FACTOR) + creature.getCameraY() - (gameView.getHeight() / (2 * ZOOM_FACTOR));
                    creature.setTarget(targetX, targetY);
                }
                return true;
            }
        });
    }

    private void loadCreatureTexture() {
        Bitmap creatureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.creature3);
        Bitmap scaledCreatureBitmap = Bitmap.createScaledBitmap(creatureBitmap, creature.getSize(), creature.getSize(), true);
        creature.setTexture(scaledCreatureBitmap);
    }

    @Override
    public void onUpdate() {
        // ודא שהעולם נוצר לפני עדכון המשחק
        if (!isWorldGenerated && gameView.getWidth() > 0 && gameView.getHeight() > 0) {
            gameWorld.generateWorld(gameView.getWidth() * 2, gameView.getHeight() * 2);
            isWorldGenerated = true;
        }

        // עדכון מצב המשחק: תנועת היצור וזיהוי התנגשויות
        creature.updatePosition(gameView.getWidth() * 2, gameView.getHeight() * 2, collisionDetector, gameWorld);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // ודא שהעולם נוצר לפני ציור
        if (!isWorldGenerated || canvas == null) {
            return;
        }

        // ציור הרקע האדום על כל המסך
        paint.setShader(null);
        paint.setColor(Color.RED);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        // חישוב ההיסט של המצלמה ביחס למסך
        float offsetX = creature.getCameraX() - (canvas.getWidth() / (2 * ZOOM_FACTOR));
        float offsetY = creature.getCameraY() - (canvas.getHeight() / (2 * ZOOM_FACTOR));

        // שמירת מצב ה-Canvas לפני תרגום וזום
        canvas.save();
        // החלת זום ותרגום
        canvas.scale(ZOOM_FACTOR, ZOOM_FACTOR);
        canvas.translate(-offsetX, -offsetY);

        // ציור הדשא על כל העולם
        paint.setShader(new BitmapShader(textureManager.getGrassTexture(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        canvas.drawRect(0, 0, gameView.getWidth() * 2, gameView.getHeight() * 2, paint);

        // ציור נהרות
        paint.setShader(null);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(40 / ZOOM_FACTOR);
        for (int[] river : gameWorld.getRivers()) {
            canvas.drawLine(river[0], river[1], river[2], river[3], paint);
        }

        // ציור עצים
        for (int[] tree : gameWorld.getTrees()) {
            float drawX = tree[0] - (textureManager.getScaledTreeTexture().getWidth() - textureManager.getTreeTexture().getWidth()) / 2;
            float drawY = tree[1] - (textureManager.getScaledTreeTexture().getHeight() - textureManager.getTreeTexture().getHeight()) / 2;
            canvas.drawBitmap(textureManager.getScaledTreeTexture(), drawX, drawY, null);
        }

        // ציור שיחים
        for (int[] bush : gameWorld.getBushes()) {
            float drawX = bush[0] - (textureManager.getScaledBushTexture().getWidth() - textureManager.getBushTexture().getWidth()) / 2;
            float drawY = bush[1] - (textureManager.getScaledBushTexture().getHeight() - textureManager.getBushTexture().getHeight()) / 2;
            canvas.drawBitmap(textureManager.getScaledBushTexture(), drawX, drawY, null);
        }

        // ציור סלעים
        for (int[] rock : gameWorld.getRocks()) {
            float drawX = rock[0] - (textureManager.getScaledRockTexture().getWidth() - textureManager.getRockTexture().getWidth()) / 2;
            float drawY = rock[1] - (textureManager.getScaledRockTexture().getHeight() - textureManager.getRockTexture().getHeight()) / 2;
            canvas.drawBitmap(textureManager.getScaledRockTexture(), drawX, drawY, null);
        }

        // שחזור מצב ה-Canvas כדי שהיצור והטקסט יצוירו במרכז המסך
        canvas.restore();

        // ציור ה-Creature במרכז המסך
        if (creature.getTexture() != null) {
            float creatureScreenX = canvas.getWidth() / 2 - creature.getSize() / 2;
            float creatureScreenY = canvas.getHeight() / 2 - creature.getSize() / 2;
            canvas.drawBitmap(creature.getTexture(), creatureScreenX, creatureScreenY, null);

            // ציור הטקסט מתחת ליצור
            paint.setColor(Color.WHITE);
            paint.setTextSize(75);
            paint.setTextAlign(Paint.Align.CENTER);
            String levelText = "Level: " + creature.getLevel();
            canvas.drawText(levelText, creatureScreenX + (creature.getSize() / 2) + 20, creatureScreenY + creature.getSize() + 40, paint);

            // ציור התאריך והשעה מתחת ל-Level
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a z, יום EEEE, d בMMMM yyyy", new Locale("he"));
            String dateTimeText = sdf.format(new Date());
            canvas.drawText(dateTimeText, creatureScreenX + (creature.getSize() / 2) + 20, creatureScreenY + creature.getSize() + 120, paint);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ודא שהעולם נוצר מחדש כאשר הפעילות חוזרת לפעולה
        if (gameView.getWidth() > 0 && gameView.getHeight() > 0) {
            gameWorld.generateWorld(gameView.getWidth() * 2, gameView.getHeight() * 2);
            isWorldGenerated = true;
        }
    }
}