package com.example.dominik.flappybird;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameContext extends SurfaceView implements SurfaceHolder.Callback {

    private static GameThread uiThread;
    private Bird bird;
    public ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    private Bitmap imgBird;
    public Bitmap imgPipe;
    private Bitmap imgBg;
//    public static float width;
//    public static float height;
    static DisplayMetrics metrics;
    private long timeBefore = System.nanoTime();
    private int currentFrame = 0;
    private final int pipePerFps = 240;
    public int score = 0;
    Paint paint = new Paint();
    public boolean gameOver = false;

    public GameContext(Context context)  {
        super(context);

        // get dimensions of screen and density
        metrics = getResources().getDisplayMetrics();

        paint.setColor(Color.BLACK);
        paint.setTextSize((float)(metrics.ydpi * 0.25));

        this.setFocusable(true);
        this.getHolder().addCallback(this);
        // on screen click listener
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bird.jump();
                if (!uiThread.isRunning() && !gameOver){
                    uiThread.setRunning(true);
                    uiThread.start();
                }
            }
        });
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        canvas.drawBitmap(imgBg, 0, 0, null);
        bird.draw(canvas);
        for(Pipe pipe : pipes) {
            pipe.draw(canvas);
        }
        canvas.drawText("Score: "+score, (float)(metrics.xdpi * 0.1), (float)(metrics.ydpi * 0.25), paint);
    }

    public void update(){
        bird.update(this);

        ListIterator<Pipe> iterator = pipes.listIterator();
        while(iterator.hasNext()){
            Pipe pipe = iterator.next();
            pipe.update(this, bird);
            if(pipe.checkIfPassed(bird)) {
                score++;
            }
            if(pipe.isOutOfScreen()) {
                iterator.remove();
            }
        }

        if(currentFrame >= pipePerFps) {
            pipes.add(new Pipe(this));
            currentFrame = 0;
        }
        currentFrame++;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        imgBg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg), getWidth(), getHeight(), false);
        bird = new Bird(this);

        this.uiThread = new GameThread(this, holder);

        Canvas canvas = holder.lockCanvas();
        this.update();
        this.draw(canvas);
        canvas.drawText("Tap to start", bird.getX() + bird.getSize(), bird.getY() + bird.getSize(), paint);
        holder.unlockCanvasAndPost(canvas);
    }

    public void gameOver() {
        uiThread.setRunning(false);
        gameOver = true;
        Intent over = new Intent(this.getContext(), GameOver.class);
        over.putExtra("SCORE", score);
        getContext().startActivity(over);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}
