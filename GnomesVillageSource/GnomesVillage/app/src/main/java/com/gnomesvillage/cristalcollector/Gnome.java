package com.gnomesvillage.cristalcollector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.gnomesvillage.R;

public class Gnome {
    Direction direction = Direction.top;
    protected Bitmap gnomeBig, gnomeLittle, currentBitmap;
    protected int timeForBitmap = 10;
    protected int timeOfCurrentBitmap;
    protected int padding = 30;
    protected int dx, dy;
    protected int canvasWidth = 2000, canvasHeight = 2000;
    protected int degrees = 0;
    protected boolean canStart = false, wasKilled = false;

    public Gnome(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        gnomeBig = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.collectorgnome),
                width / 8 + 3, width / 8 + 3, false);
        gnomeLittle = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.collectorgnome),
                width / 8, width / 8, false);
        currentBitmap = gnomeBig;

    }

    public void drawGnome(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        Paint paint = new Paint();
        if (!canStart) {
            dx = 2;
            dy = canvasHeight - canvasWidth / 8 * 3 - 10 - gnomeBig.getHeight();
        }
        canvas.rotate(degrees, dx + currentBitmap.getWidth() / 2, dy + currentBitmap.getHeight() / 2);
        canvas.drawBitmap(currentBitmap, dx, dy, paint);
        canvas.rotate(-degrees, dx + currentBitmap.getWidth() / 2, dy + currentBitmap.getHeight() / 2);
    }

    public void changeBitmap() {
        timeOfCurrentBitmap = timeOfCurrentBitmap + 1;
        if (timeOfCurrentBitmap >= timeForBitmap) {
            if (currentBitmap == gnomeBig) {
                currentBitmap = gnomeLittle;
                timeOfCurrentBitmap = timeOfCurrentBitmap - timeForBitmap;
            } else {
                currentBitmap = gnomeBig;
                timeOfCurrentBitmap = timeOfCurrentBitmap - timeForBitmap;
            }
        }
    }

    public void move() {
        if (canStart) {
            switch (direction) {
                case right:
                    dx += canvasWidth / 80;
                    break;
                case left:
                    dx -= canvasWidth / 80;
                    break;
                case top:
                    dy -= canvasWidth / 80;
                    break;
                case bottom:
                    dy += canvasWidth / 80;
                    break;
            }
        }
    }

    public Rect getRect() {
        return new Rect(dx + padding, dy + padding, dx + gnomeLittle.getWidth() - padding, dy + gnomeLittle.getHeight() - padding);
    }

    public Rect getRectForPoints() {
        return new Rect(dx + 3, dy + 3, dx + gnomeLittle.getWidth() - 3, dy + gnomeLittle.getHeight() - 3);
    }

    public boolean wasKilled() {
        if (getX() - currentBitmap.getWidth() / 3 > 0 && getY() - currentBitmap.getHeight() / 3 > 0 &&
                getX() + currentBitmap.getWidth() / 3 < canvasWidth && getY() + currentBitmap.getHeight() / 3 < canvasHeight) {
            return false;
        } else return true;
    }

    public int getX() {
        return currentBitmap.getWidth() / 2 + dx;
    }

    public int getY() {
        return currentBitmap.getHeight() / 2 + dy;
    }

}