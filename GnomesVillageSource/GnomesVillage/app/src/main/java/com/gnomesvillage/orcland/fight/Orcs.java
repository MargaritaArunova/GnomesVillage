package com.gnomesvillage.orcland.fight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.gnomesvillage.R;

import java.util.ArrayList;

public class Orcs {
    protected Bitmap orc, orcBullet;
    protected Integer width, height, frequency;
    protected ArrayList<Coordinates> bulletCoordinates;
    protected ArrayList<Integer> orcHP;
    protected boolean bulletIsGenerated = false;

    public Orcs(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        frequency = 0;

        bulletCoordinates = new ArrayList<>();
        orcHP = new ArrayList<>();
        orc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.orc), height / 3, height / 3, false);
        orcBullet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.orcbullet), height / 9, height / 9, false);
        bulletCoordinates.add(new Coordinates(width - height / 3, height / 6, 0, 0, 0, false));
        bulletCoordinates.add(new Coordinates(width - height / 3, height / 2, 0, 0, 0, false));
        bulletCoordinates.add(new Coordinates(width - height / 3, height / 6 * 5, 0, 0, 0, false));
        orcHP.add(20);
        orcHP.add(20);
        orcHP.add(20);
    }

    public void motionVector(Integer gnomeX, Integer gnomeY, Integer currentIndex) {
        bulletIsGenerated = true;
        int deltaX, deltaY;
        deltaX = gnomeX - bulletCoordinates.get(currentIndex).x;
        deltaY = gnomeY - bulletCoordinates.get(currentIndex).y;
        bulletCoordinates.get(currentIndex).setEndingX(0 - height / 9);
        bulletCoordinates.get(currentIndex).setEndingY(gnomeY);
        if (deltaY != 0) {
            bulletCoordinates.get(currentIndex).setFrequencyY(Math.abs(deltaX) / deltaY);
        } else {
            bulletCoordinates.get(currentIndex).setFrequencyY(0);
        }
        bulletCoordinates.get(currentIndex).setCanDrawing(true);
    }

    public void moveOrcBullet(Integer currentIndex) {
        if (bulletCoordinates.get(currentIndex).canDrawing) {
            bulletCoordinates.get(currentIndex).setX(bulletCoordinates.get(currentIndex).x -= 2);
            if (bulletCoordinates.get(currentIndex).frequencyY != 0) {
                frequency += 2;
                if (frequency >= Math.abs(bulletCoordinates.get(currentIndex).frequencyY)) {
                    if (bulletCoordinates.get(currentIndex).frequencyY > 0) {
                        bulletCoordinates.get(currentIndex).setY(bulletCoordinates.get(currentIndex).y += 1);
                    } else {
                        bulletCoordinates.get(currentIndex).setY(bulletCoordinates.get(currentIndex).y -= 1);
                    }
                }
            }
            if (bulletCoordinates.get(currentIndex).x <= bulletCoordinates.get(currentIndex).endingX) {
                bulletCoordinates.set(currentIndex, new Coordinates(width - height / 3, height / 3 * currentIndex + height / 6, 0, 0, 0, false));
            }
        }
    }

    public Rect getBulletRect(Integer index) {
        return new Rect(bulletCoordinates.get(index).x + height / 45, bulletCoordinates.get(index).y + height / 27,
                bulletCoordinates.get(index).x + height / 9 - height / 45, bulletCoordinates.get(index).y + height / 9 - height / 27);
    }

    public Rect getOrcRect(Integer index) {
        return new Rect(width - height / 5, height / 3 * index, width, height / 3 * (index + 1) - 1);
    }

    public class Coordinates {
        protected Integer x, y, endingX, endingY, frequencyY;
        protected Boolean canDrawing;

        public Coordinates(Integer x, Integer y, Integer endingX, Integer endingY, Integer frequencyY, Boolean canDrawing) {
            this.x = x;
            this.y = y;
            this.endingX = endingX;
            this.endingY = endingY;
            this.canDrawing = canDrawing;
            this.frequencyY = frequencyY;
        }

        public void setFrequencyY(Integer frequencyY) {
            this.frequencyY = frequencyY;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public void setEndingX(Integer endingX) {
            this.endingX = endingX;
        }

        public void setEndingY(Integer endingY) {
            this.endingY = endingY;
        }

        public void setCanDrawing(Boolean canDrawing) {
            this.canDrawing = canDrawing;
        }
    }

}