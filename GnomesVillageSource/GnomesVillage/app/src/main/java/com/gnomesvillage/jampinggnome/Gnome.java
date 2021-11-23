package com.gnomesvillage.jampinggnome;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.gnomesvillage.R;


public class Gnome {

    Typeface typeface;
    protected Integer x = 500, y = 100;
    protected boolean isCreated = false;
    protected final Integer constant = 5;
    protected int canvasWidth, canvasHeight;
    protected Integer hasIntersect = 0;
    protected Bitmap gnome;

    public Gnome(Resources resources, Typeface typeface) {
        gnome = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.jumpinggnome),
                175, 175, false);
        this.typeface = typeface;
    }

    public void drawGnome(Canvas canvas) {
        Paint paint = new Paint();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        y = canvasHeight / 2 - gnome.getHeight() / 2;
        if (!isCreated) {
            x = canvasWidth / 2 - gnome.getWidth() / 2;
            isCreated = true;
        }
        canvas.drawBitmap(gnome, x, y, paint);
    }

    public void drawCountOfCrush(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
        paint.setTypeface(typeface);
        canvas.drawText(hasIntersect.toString() + "/7", 50, 50, paint);
    }

    public void moveX(float pitch) {
        int angle = 0;
        if (pitch == -1) {
            angle = 80;
        } else if (pitch == -2) {
            angle = 70;
        } else if (pitch == -3) {
            angle = 60;
        } else if (pitch == -4) {
            angle = 50;
        } else if (pitch == -5) {
            angle = 40;
        } else if (pitch == -6) {
            angle = 30;
        } else if (pitch == -7) {
            angle = 20;
        } else if (pitch <= -8) {
            angle = 10;
        } else if (pitch == 1) {
            angle = 100;
        } else if (pitch == 2) {
            angle = 110;
        } else if (pitch == 3) {
            angle = 120;
        } else if (pitch == 4) {
            angle = 130;
        } else if (pitch == 5) {
            angle = 140;
        } else if (pitch == 6) {
            angle = 150;
        } else if (pitch == 7) {
            angle = 160;
        } else if (pitch >= 8) {
            angle = 170;
        }
        if (pitch < 0) {
            x += (int) Math.cos(angle) * constant;
        } else if (pitch > 0) {
            x -= (int) Math.cos(angle * 10) * constant;
        }
    }

    public Rect getRectForIslands() {
        return new Rect(x + gnome.getWidth() / 3, y + gnome.getHeight() / 7, x + gnome.getWidth() / 3, y + gnome.getHeight() - gnome.getHeight() / 7);
    }

    public Rect getRectForCristal() {
        return new Rect(x + gnome.getWidth() / 7, y - 10, x + gnome.getWidth() - gnome.getWidth() / 7, y + gnome.getHeight() - 10);
    }

    public void beyondCanvas() {
        if (x > canvasWidth) {
            x = 0;
        } else if (x + gnome.getHeight() < 0) {
            x = canvasWidth;
        }
    }

    public boolean wasKilled() {
        if (hasIntersect >= 7) {
            return true;
        } else return false;
    }

}