package com.gnomesvillage.skyjump;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.gnomesvillage.R;

public class Gnome {

    protected Integer x = 0, y = 0, dx = 0, canvasHeight = 0;
    protected Bitmap gnome;
    protected boolean haveCurrentPosition = false;
    public Direction direction = Direction.notExist;

    public Gnome(Resources resources) {
        gnome = BitmapFactory.decodeResource(resources, R.drawable.skyjumpgnome);
    }

    public void move() {
        if (haveCurrentPosition) {
            switch (direction) {
                case left: {
                    for (int i = 0; i <= dx; i++) {
                        x--;
                        if (i <= dx / 2) {
                            y--;
                        } else y++;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                        }
                    }
                    direction = Direction.notExist;
                    break;
                }
                case right: {
                    for (int i = 0; i <= dx; i++) {
                        x++;
                        if (i <= dx / 2) {
                            y--;
                        } else y++;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                        }
                    }
                    direction = Direction.notExist;
                    break;
                }
            }
        }
    }

    public void setCoordinates(Canvas canvas, int y) {
        canvasHeight = canvas.getHeight();
        gnome = Bitmap.createScaledBitmap(gnome, canvas.getHeight() / 5, canvas.getHeight() / 5, false);
        if (!haveCurrentPosition) {
            dx = canvas.getWidth() / 6;
            if (dx % 2 == 0){
                dx++;
            }
            x = canvas.getWidth() / 2 + canvas.getWidth() / 12 - gnome.getWidth() / 2;
            this.y = y - gnome.getHeight() + 10;
            haveCurrentPosition = true;
        }
    }

    public void fallDown() {
        while (y < canvasHeight + gnome.getHeight()) {
            y++;
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
            }
        }
    }

    public Rect getGnomeRect() {
        return new Rect(x, y + gnome.getHeight() - 1, x + gnome.getWidth(), y + gnome.getHeight() + 1);
    }

}