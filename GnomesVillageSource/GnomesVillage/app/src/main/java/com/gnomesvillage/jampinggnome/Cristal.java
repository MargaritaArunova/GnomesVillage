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
import java.util.ArrayList;

public class Cristal {

    Typeface typeface;
    protected Bitmap cristal, sky;
    protected ArrayList<Integer> y, x;
    protected ArrayList<Boolean> wasIntersect;
    protected final Integer dy = 450;
    protected Integer canvasWidth, canvasHeight;
    protected boolean isCreated = false;
    protected Integer countOfCristals = 0;

    public Cristal(Resources resources, Typeface typeface) {
        cristal = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalred),
                65, 65, false);
        sky = BitmapFactory.decodeResource(resources, R.drawable.sky);
        x = new ArrayList<>();
        y = new ArrayList<>();
        wasIntersect = new ArrayList<>();
        this.typeface = typeface;
    }

    public void createCristals() {
        for (int i = 0; dy * i <= canvasHeight; i++) {
            x.add((int) (1 + Math.random() * (canvasWidth - cristal.getWidth())));
            y.add(dy * i);
            wasIntersect.add(false);
        }

    }

    public void drawCristals(Canvas canvas) {
        Paint paint = new Paint();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        if (isCreated == false) {
            createCristals();
            isCreated = true;
        } else {
            for (int i = 0; i < y.size(); i++) {
                if (!wasIntersect.get(i)) {
                    canvas.drawBitmap(cristal, x.get(i), y.get(i), paint);
                }
            }
        }
    }

    public void drawCount(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
        paint.setTypeface(typeface);
        canvas.drawText(countOfCristals.toString(), canvas.getWidth() - 100, 50, paint);
    }

    public void drawSky(Canvas canvas) {
        Paint paint = new Paint();
        sky = Bitmap.createScaledBitmap(sky, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(sky, 0, 0, paint);
    }

    public void moveCristals(ArrayList<Integer> islandX, int width) {
        for (int i = 0; i < y.size(); i++) {
            y.set(i, y.get(i) - 10);
        }
        if (y.get(0) < 0) {
            y.add(y.get(y.size() - 1) + dy);
            int test = (int) (1 + Math.random() * (canvasWidth - cristal.getWidth()));
            while (true) {
                for (int i = islandX.size() / 2; i < islandX.size(); i++) {
                    if (Math.abs((cristal.getWidth() / 2 + test) - (islandX.get(i) + width / 2)) <=
                            width + cristal.getWidth()) {
                        test = (int) (1 + Math.random() * (canvasWidth - cristal.getWidth()));
                        break;
                    }
                }
                break;

            }
            x.add(test);
            wasIntersect.add(false);
        }
        if (y.get(0) + cristal.getHeight() < 0) {
            y.remove(0);
            x.remove(0);
            wasIntersect.remove(0);
        }
    }

    public Rect getRect(int number) {
        return new Rect(x.get(number) + 5,
                y.get(number) + cristal.getHeight() / 5,
                x.get(number) + cristal.getWidth() - 5,
                y.get(number) + cristal.getHeight() / 5 * 4);
    }

}