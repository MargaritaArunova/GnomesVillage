package com.gnomesvillage.cristalcollector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;

import com.gnomesvillage.R;

import java.util.ArrayList;

public class Points {

    Typeface typeface;
    protected Bitmap cristalRed;
    protected Bitmap cristalBlue;
    protected Bitmap cristalGreen;
    protected Bitmap cristalPurple;
    protected Bitmap cristalOrange;
    protected int cases = (int) (1 + Math.random() * 6), canvasHeight, canvasWidth;
    protected double dx, dy;
    protected Integer count = 0;

    public Points(Resources resources, Typeface typeface) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        cristalBlue = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalblue), width / 16, width / 16, false);
        cristalRed = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalred), width / 16, width / 16, false);
        cristalGreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalgreen), width / 16, width / 16, false);
        cristalOrange = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalorange), width / 16, width / 16, false);
        cristalPurple = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalpurple), width / 16, width / 16, false);
        this.typeface = typeface;
        dx = width / 8 + width * 0.375 - cristalRed.getWidth();
        dy = width / 16 + width * 0.375;
    }

    public void drawPoint(Canvas canvas) {
        Paint paint = new Paint();
        switch (cases) {
            case 1: {
                canvas.drawBitmap(cristalRed, (int) dx, (int) dy, paint);
                break;
            }
            case 2: {
                canvas.drawBitmap(cristalGreen, (int) dx, (int) dy, paint);
                break;
            }
            case 3: {
                canvas.drawBitmap(cristalOrange, (int) dx, (int) dy, paint);
                break;
            }
            case 4: {
                canvas.drawBitmap(cristalPurple, (int) dx, (int) dy, paint);
                break;
            }
            default: {
                canvas.drawBitmap(cristalBlue, (int) dx, (int) dy, paint);
                break;
            }
        }
    }

    public void drawCount(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setTypeface(typeface);
        canvas.drawText(String.valueOf(count), 50, 50, paint);
    }

    public void move(ArrayList<Integer> x, ArrayList<Integer> y) {
        dx = Math.random() * canvasWidth;
        dy = Math.random() * canvasHeight;
        for (int i = 0; i < x.size(); i++) {
            if (!(Math.abs(x.get(i) - dx) >= canvasWidth / 8) || !(Math.abs(y.get(i) - dy) >= canvasWidth / 8)
                    || !(dy > canvasWidth / 4) || !(dy <= canvasHeight - canvasWidth / 7 * 3 - cristalBlue.getHeight())) {
                dx = Math.random() * canvasWidth;
                dy = Math.random() * canvasHeight;
                i = -1;
            }
        }
        cases = (int) (1 + Math.random() * 6);
    }

    public Rect getRect() {
        return new Rect((int) dx, (int) dy, (int) dx + cristalPurple.getWidth(), (int) dy + cristalPurple.getHeight());
    }

    public double getX() {
        return dx + cristalPurple.getWidth() / 2;
    }

    public double getY() {
        return dy + cristalPurple.getHeight() / 2;
    }

}