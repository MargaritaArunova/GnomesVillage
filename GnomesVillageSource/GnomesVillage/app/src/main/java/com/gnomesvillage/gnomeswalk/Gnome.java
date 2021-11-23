package com.gnomesvillage.gnomeswalk;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.gnomesvillage.R;

public class Gnome {

    protected Integer x, y, dy;
    protected Bitmap currentBitmap, firstStep, secondStep, thirdStep, fourthStep;
    protected boolean haveCurrentPosition = false;
    protected Direction direction = Direction.up;

    public Gnome(Resources resources) {
        currentBitmap = BitmapFactory.decodeResource(resources, R.drawable.gnomesprite);
    }

    public void move() {
        if (haveCurrentPosition) {
            for (int i = 0; i < dy * 2; i++) {
                if (i < dy) {
                    y--;
                } else y++;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void moveInEnd(int canvasWidth) {
        if (x < canvasWidth) {
            x += 5;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    public void setCoordinates(Canvas canvas) {
        currentBitmap = Bitmap.createScaledBitmap(currentBitmap, canvas.getHeight(), canvas.getHeight() / 4, false);
        firstStep = Bitmap.createBitmap(currentBitmap, 0, 0,
                currentBitmap.getWidth() / 4, currentBitmap.getHeight());
        secondStep = Bitmap.createBitmap(currentBitmap, currentBitmap.getWidth() / 4, 0,
                currentBitmap.getWidth() / 4, currentBitmap.getHeight());
        thirdStep = Bitmap.createBitmap(currentBitmap, currentBitmap.getWidth() / 2, 0,
                currentBitmap.getWidth() / 4, currentBitmap.getHeight());
        fourthStep = Bitmap.createBitmap(currentBitmap, currentBitmap.getWidth() / 4 * 3, 0,
                currentBitmap.getWidth() / 4, currentBitmap.getHeight());
        currentBitmap = firstStep;

        if (!haveCurrentPosition) {
            dy = canvas.getWidth() / 3;
            x = 0;
            y = canvas.getHeight() / 3 * 2 - currentBitmap.getHeight();
            haveCurrentPosition = true;
        }
    }

    public void changeSprite() {
        if (direction == Direction.up) {
            if (currentBitmap == firstStep) {
                currentBitmap = secondStep;
            } else if (currentBitmap == secondStep) {
                currentBitmap = thirdStep;
            } else if (currentBitmap == thirdStep) {
                currentBitmap = fourthStep;
            } else if (currentBitmap == fourthStep) {
                direction = Direction.down;
            }
        } else if (direction == Direction.down) {
            if (currentBitmap == firstStep) {
                direction = Direction.up;
            } else if (currentBitmap == secondStep) {
                currentBitmap = firstStep;
            } else if (currentBitmap == thirdStep) {
                currentBitmap = secondStep;
            } else if (currentBitmap == fourthStep) {
                currentBitmap = thirdStep;
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
    }

    public Rect getGnomeRect() {
        return new Rect(x + currentBitmap.getWidth() / 2 - 3, y, x + currentBitmap.getWidth() / 2 + 3, y + currentBitmap.getHeight() / 7 * 6);
    }

}