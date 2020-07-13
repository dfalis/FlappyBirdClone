package com.example.dominik.flappybird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Bird {
    private int size = (int)(GameContext.metrics.xdpi * 0.3);
    private float x;
    private float y;
    private Bitmap image;
    private float velocity = 5;
    private float jumpHeight = (float)(GameContext.metrics.ydpi * 0.06);

    public Bird(GameContext gameContext) {
        this.image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(gameContext.getResources(),R.drawable.bird), this.getSize(), this.getSize(), false);
        x = GameContext.metrics.widthPixels / 5;
        y = GameContext.metrics.heightPixels / 2;
    }

    public void update(GameContext gameContext) {
        velocity += 1f;
        y += velocity;

        if(this.hitsBorder()) {
            gameContext.gameOver();
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(image, size, size, false), this.x, this.y, null);
    }

    public boolean hitsBorder() {
        return (this.y < 0 || this.y > GameContext.metrics.heightPixels);
    }

    public void jump(){
        velocity = -jumpHeight;
    }

    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }
    public int getSize() {
        return this.size;
    }
}
