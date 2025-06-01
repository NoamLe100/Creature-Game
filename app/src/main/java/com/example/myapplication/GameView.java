package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private SurfaceHolder surfaceHolder;
    private DrawCallback drawCallback;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public void setDrawCallback(DrawCallback callback) {
        this.drawCallback = callback;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        thread = new GameThread(holder);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface DrawCallback {
        void onDraw(Canvas canvas);
        void onUpdate();
    }

    private class GameThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private boolean running;

        public GameThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;
        }

        public void setRunning(boolean isRunning) {
            this.running = isRunning;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        synchronized (surfaceHolder) {
                            if (drawCallback != null) {
                                drawCallback.onUpdate();
                                drawCallback.onDraw(canvas);
                            }
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}