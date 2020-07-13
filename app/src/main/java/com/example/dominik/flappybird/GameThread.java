package com.example.dominik.flappybird;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
    private boolean running = false;
    private GameContext gameContext;
    private SurfaceHolder surfaceHolder;
    private int cycle= 1000/60;

    public GameThread(GameContext gameContext, SurfaceHolder surfaceHolder)  {
        this.gameContext = gameContext;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();

        while(running)  {
            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas) {
                    this.gameContext.update();
                    this.gameContext.draw(canvas);
                }
            } catch (Exception e)  {
                // stop thread, error from synchronized probably 'monitor-enter v', if canvas is null
                running = false;
            } finally {
                if (canvas != null)  {
                    // draw everything to canvas
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            long now = System.nanoTime() ;
            long waitTime = (now - startTime)/1000000;

            if (cycle - waitTime > 0) {
                waitTime = cycle - waitTime;
                try {
                    this.sleep(waitTime);
                } catch (InterruptedException e)  {}
            }

            startTime = System.nanoTime();
        }
    }

    public void setRunning(boolean running)  {
        this.running = running;
    }

    public boolean isRunning() {
        return this.running;
    }
}
