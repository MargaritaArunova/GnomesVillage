package com.gnomesvillage.orcland.capture;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.gnomesvillage.R;

import java.util.ArrayList;

public class Enemy {
    protected Bitmap bullet, rotateBullet;
    protected Integer height, width, speed = 0;
    protected boolean canMakeNew = true;
    protected ArrayList<Coordinates> coordinates;

    public Enemy(Resources resources) {
        coordinates = new ArrayList<>();

        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        speed = height / 80;
        bullet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.enemyarrow), width / 4, width / 16, false);
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        rotateBullet = Bitmap.createBitmap(bullet, 0, 0, bullet.getWidth(), bullet.getHeight(), matrix, false);
        setCoordinates();
    }

    public void setCoordinates() {
        for (int i = 1; height - i * height / 4 >= 0; i++) {
            if (i % 2 == 1) {
                int test = (int) (1 + Math.random() * 2);
                if (test > 1) {
                    coordinates.add(new Coordinates(0, height - i * height / 4 - height / 8, true));
                } else {
                    coordinates.add(new Coordinates(0, height - i * height / 4 - height / 8, false));
                }
            }
        }
    }

    public void moveEnemyY() {
        if (coordinates.size() > 0) {
            for (int j = 0; j < coordinates.size(); j++) {
                coordinates.get(j).y++;
            }
            if (coordinates.size() > 0) {
                if (coordinates.get(0).y >= height) {
                    coordinates.remove(0);
                    if (canMakeNew) {
                        int test = (int) (1 + Math.random() * 2);
                        if (test > 1) {
                            coordinates.add(new Coordinates(0, 0, true));
                        } else {
                            coordinates.add(new Coordinates(0, 0, false));
                        }
                    }
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void moveEnemyX() {
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).onTheLeftSide) {
                coordinates.get(i).x += speed;
                if (coordinates.get(i).x >= width) {
                    coordinates.get(i).x = 0 - bullet.getWidth();
                }
            } else {
                coordinates.get(i).x -= speed;
                if (coordinates.get(i).x <= 0 - bullet.getWidth()) {
                    coordinates.get(i).x = width;
                }
            }
        }

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
    }

    public Rect getEnemyRect() {
        if (coordinates.size() > 0) {
            return new Rect(coordinates.get(0).x, coordinates.get(0).y + bullet.getHeight() / 3,
                    coordinates.get(0).x + bullet.getWidth(), coordinates.get(0).y + bullet.getHeight() / 3 * 2);
        } else return new Rect(0, 0, 0, 0);
    }

    public class Coordinates {
        protected Integer x, y;
        protected boolean onTheLeftSide;

        public Coordinates(Integer x, Integer y, boolean onTheLeftSide) {
            this.x = x;
            this.y = y;
            this.onTheLeftSide = onTheLeftSide;
        }
    }

}