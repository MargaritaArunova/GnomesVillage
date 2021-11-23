package com.gnomesvillage.cristalcollector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.gnomesvillage.R;

import java.util.ArrayList;

public class Tree {

    protected Bitmap tree;
    protected int dx, dy;
    protected ArrayList<Integer> x, y;
    protected int canvasWidth, canvasHeight;
    protected boolean isCreated = false;

    public Tree(Resources resources) {
        tree = BitmapFactory.decodeResource(resources, R.drawable.tree);
        y = new ArrayList<>();
        x = new ArrayList<>();
    }

    public void createTrees() {
        tree = Bitmap.createScaledBitmap(tree, canvasWidth / 8, canvasWidth / 8, false);
        dx = (int) (canvasWidth * 0.375);
        dy = (int) (canvasWidth * 0.375);
        x.add(canvasWidth / 8);
        y.add(canvasWidth / 16);
        for (int i = 1; x.get(i - 1) + dx <= canvasWidth - tree.getWidth(); i++) {
            x.add(x.get(i - 1) + dx);
        }
        for (int i = 1; y.get(i - 1) + dy <= canvasHeight - canvasWidth / 7 * 2.5 - tree.getHeight(); i++) {
            y.add(y.get(i - 1) + dy);
        }
        int sizeY = y.size();
        int sizeX = x.size();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                y.add(y.get(j));
            }
        }

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                x.add(x.get(j));
            }
        }

    }

    public void drawTrees(Canvas canvas) {
        Paint paint = new Paint();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        if (isCreated == false) {
            createTrees();
            isCreated = true;
        } else {
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < y.size(); j++) {
                    canvas.drawBitmap(tree, x.get(i), y.get(j), paint);
                }

            }
        }

    }

    public Rect getRect(int xIndex, int yIndex) {
        return new Rect(x.get(xIndex) + 15,
                y.get(yIndex) + 15,
                x.get(xIndex) + tree.getWidth() - 15,
                y.get(yIndex) + tree.getHeight() - 15);
    }

}