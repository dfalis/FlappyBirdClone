package com.example.dominik.flappybird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Pipe {
    private int width = (int)(GameContext.metrics.xdpi * 0.4);
    private float x;
    private float yTop = (int)(GameContext.metrics.ydpi * (Math.random()*2));
    private float space = (int)(GameContext.metrics.ydpi * 1.5);
    private Bitmap imageTop;
    private Bitmap imageBottom;
    private final float velocity = 5;
    private int yVelocity = (Math.random() > 0.5) ? 3 : -3;
    public boolean passed = false;

    public Pipe(GameContext gameContext) {
        Bitmap scaledBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(gameContext.getResources(),R.drawable.pipe), width, GameContext.metrics.heightPixels, false);
        Matrix matrix =  new Matrix();
        matrix.postRotate(180);
        this.imageTop = Bitmap.createBitmap(
                scaledBmp,
                0,0,
                scaledBmp.getWidth(),scaledBmp.getHeight(),
                matrix, true
        );
        imageBottom = scaledBmp;
        x = GameContext.metrics.widthPixels;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(imageTop, this.x, this.yTop-imageTop.getHeight(), null);
        canvas.drawBitmap(imageBottom, this.x, this.yTop + this.space, null);
    }

    public void update(GameContext gameContext, Bird bird) {
        x -= velocity;
        yTop += yVelocity;
        if(this.yTop + this.space > GameContext.metrics.heightPixels - GameContext.metrics.xdpi){
            if(yVelocity > 0)
                yVelocity = -yVelocity;
        } else if (this.yTop < GameContext.metrics.xdpi) {
            if(yVelocity < 0) {
                yVelocity = -yVelocity;
            }
        }
        if(this.hitsBird(bird)) {
            gameContext.gameOver();
        }
    }

    public boolean isOutOfScreen() {
        return (this.x + this.width < 0);
    }

    public boolean hitsBird(Bird bird) {

        if (bird.getX() + bird.getSize() > this.x && bird.getX() < this.x + this.width) {
            if (bird.getY() < this.yTop || bird.getY() + bird.getSize() > this.yTop + this.space) {
                return true;
            }
        }
        return false;
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean checkIfPassed(Bird bird){
        if(isPassed()) {
            return false;
        }
        if(bird.getX() > this.x) {
            passed = true;
        }
        return passed;
    }
}
