package com.gnomesvillage.cristalcollector;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.gnomesvillage.R;

public class Arrow {
    protected Bitmap arrowRight, arrowUp, arrowDown, arrowLeft;
    protected boolean isScaled;
    protected int canvasWidth;
    protected int canvasHeight;

    public Arrow(Resources resources) {
        arrowUp = BitmapFactory.decodeResource(resources, R.drawable.arrow);
    }

    public Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void scaleBitmap(Integer canvasWidth) {
        arrowUp = Bitmap.createScaledBitmap(arrowUp, canvasWidth / 7, canvasWidth / 7, false);
        arrowDown = rotateBitmap(arrowUp, 180);
        arrowRight = rotateBitmap(arrowUp, 90);
        arrowLeft = rotateBitmap(arrowUp, 270);
    }

    public void drawArrow(Canvas canvas) {
        Paint paint = new Paint();
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        if (!isScaled) {
            scaleBitmap(canvasWidth);
        }
        canvas.drawBitmap(arrowUp, canvas.getWidth() / 2 - arrowUp.getWidth() / 2,
                canvas.getHeight() - (arrowUp.getHeight() * 3 + 10), paint);
        canvas.drawBitmap(arrowLeft, (float) (canvas.getWidth() / 2 - arrowLeft.getWidth() * 1.5 - 5),
                canvas.getHeight() - (arrowLeft.getHeight() * 2 + 5), paint);
        canvas.drawBitmap(arrowRight, (float) (canvas.getWidth() / 2 + arrowRight.getWidth() * 0.5 + 5),
                canvas.getHeight() - (arrowRight.getHeight() * 2 + 5), paint);
        canvas.drawBitmap(arrowDown, canvas.getWidth() / 2 - arrowDown.getWidth() / 2,
                canvas.getHeight() - (arrowDown.getHeight() + 5), paint);
    }

    public int getXRight() {
        return canvasWidth / 2 + arrowRight.getWidth();
    }

    public int getXLeft() {
        return canvasWidth / 2 - arrowLeft.getWidth();
    }

    public int getXUp() {
        return canvasWidth / 2;
    }

    public int getXDown() {
        return canvasWidth / 2;
    }

    public int getYRight() {
        return (int) (canvasHeight - arrowRight.getHeight() * 1.5);
    }

    public int getYLeft() {
        return (int) (canvasHeight - arrowLeft.getHeight() * 1.5);
    }

    public int getYUp() {
        return (int) (canvasHeight - arrowUp.getHeight() * 2.5);
    }

    public int getYDown() {
        return (int) (canvasHeight - arrowDown.getHeight() * 0.5);
    }

}