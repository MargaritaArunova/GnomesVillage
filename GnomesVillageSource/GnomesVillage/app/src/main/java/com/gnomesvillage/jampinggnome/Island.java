package com.gnomesvillage.jampinggnome;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.gnomesvillage.R;

import java.util.ArrayList;


public class Island {
    protected ArrayList<Bitmap> islands;
    protected ArrayList<Integer> x;
    protected ArrayList<Integer> y;
    protected ArrayList<Boolean> wasIntersect;
    protected boolean isCreated = false;
    protected Bitmap island;
    protected int canvasWidth, canvasHeight;
    protected final Integer dy = 455;

    public Island(Resources resources) {
        islands = new ArrayList<>();
        x = new ArrayList<>();
        y = new ArrayList<>();
        wasIntersect = new ArrayList<>();
        island = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cloud),
                200, 200, false);
    }

    public void createIslands() {
        for (int i = 0; dy * i <= canvasHeight; i++) {
            islands.add(island);
            int test = (int) (1 + Math.random() * (canvasWidth - island.getWidth()));
            while (Math.abs(test - canvasWidth / 2) < island.getWidth() * 1.5) {
                test = (int) (1 + Math.random() * (canvasWidth - island.getWidth()));
            }
            x.add(test);
            y.add(dy * i);
            wasIntersect.add(false);
        }
    }

    public void drawIslands(Canvas canvas) {
        Paint paint = new Paint();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        if (isCreated == false) {
            createIslands();
            isCreated = true;
        } else {
            for (int i = 0; i < islands.size(); i++) {
                if (!wasIntersect.get(i)) {
                    canvas.drawBitmap(islands.get(i), x.get(i), y.get(i), paint);
                }
            }
        }
    }

    public void moveIslands() {
        for (int i = 0; i < islands.size(); i++) {
            y.set(i, y.get(i) - 10);
        }
        if (y.get(0) < 0) {
            y.add(y.get(y.size() - 1) + dy);
            x.add((int) (1 + Math.random() * (canvasWidth - island.getWidth())));
            islands.add(island);
            wasIntersect.add(false);
        }
        if (y.get(0) + island.getHeight() < 0) {
            y.remove(0);
            x.remove(0);
            islands.remove(0);
            wasIntersect.remove(0);
        }
    }

    public Rect getRect(int number) {
        return new Rect(x.get(number) + 15,
                y.get(number) + islands.get(number).getHeight() / 3,
                x.get(number) + islands.get(number).getWidth() - 15,
                y.get(number) + islands.get(number).getHeight() / 3 * 2);
    }

}