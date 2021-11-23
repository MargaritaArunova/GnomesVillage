package com.gnomesvillage.mine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.gnomesvillage.R;

public class Pick {

    protected Bitmap firstPick, secondPick, thirdPick, fourthPick, currentPick;
    Direction direction = Direction.down;

    public Pick(Resources resources) {
        firstPick = BitmapFactory.decodeResource(resources, R.drawable.pick);
    }

    public void setPickSize(Integer height) {
        Matrix matrix = new Matrix();
        firstPick = Bitmap.createScaledBitmap(firstPick, height / 3, height / 3, false);

        matrix.postRotate(-18);
        secondPick = Bitmap.createBitmap(firstPick, 0, 0, firstPick.getWidth(), firstPick.getHeight(), matrix, false);

        matrix.postRotate(-36);
        thirdPick = Bitmap.createBitmap(firstPick, 0, 0, firstPick.getWidth(), firstPick.getHeight(), matrix, false);

        matrix.postRotate(-54);
        fourthPick = Bitmap.createBitmap(firstPick, 0, 0, firstPick.getWidth(), firstPick.getHeight(), matrix, false);

        currentPick = firstPick;
    }

    public void changePickBitmap() {
        if (direction != Direction.down) {
            direction = Direction.down;
        }
        if (direction == Direction.down) {
            if (currentPick == firstPick) {
                currentPick = secondPick;
            }
        }
        while (currentPick != firstPick) {
            if (direction == Direction.down) {
                if (currentPick == firstPick) {
                    currentPick = secondPick;
                } else if (currentPick == secondPick) {
                    currentPick = thirdPick;
                } else if (currentPick == thirdPick) {
                    currentPick = fourthPick;
                } else if (currentPick == fourthPick) {
                    direction = Direction.up;
                }
            } else if (direction == Direction.up) {
                if (currentPick == firstPick) {
                    direction = Direction.down;
                } else if (currentPick == secondPick) {
                    currentPick = firstPick;
                } else if (currentPick == thirdPick) {
                    currentPick = secondPick;
                } else if (currentPick == fourthPick) {
                    currentPick = thirdPick;
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

}