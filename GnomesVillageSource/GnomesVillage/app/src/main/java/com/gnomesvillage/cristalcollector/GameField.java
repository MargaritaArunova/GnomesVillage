package com.gnomesvillage.cristalcollector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gnomesvillage.R;


public class GameField extends View {
    Gnome gnome;
    Timer countDownTimer;
    Arrow arrow;
    Points points;
    Tree tree;
    Context context;
    protected boolean timerIsExists = false;
    protected int canvasWidth, canvasHeight;
    Bitmap preview, start, gameOver;

    public GameField(Context context) {
        super(context);
        arrow = new Arrow(getResources());
        gnome = new Gnome(getResources());
        points = new Points(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        tree = new Tree(getResources());
        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.gameoverportrait);
        this.context = context;
    }

    public GameField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        arrow = new Arrow(getResources());
        gnome = new Gnome(getResources());
        points = new Points(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        tree = new Tree(getResources());
        gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.gameoverportrait);
        this.context = context;
    }

    public void stop() {
        countDownTimer.cancel();
        countDownTimer = null;
    }

    public void start() {
        countDownTimer = new Timer(Long.MAX_VALUE, 50, this, gnome, points, tree);
        countDownTimer.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (timerIsExists) {
            if (!gnome.wasKilled) {
                canvas.drawColor(Color.rgb(255, 188, 121));
                tree.drawTrees(canvas);
                gnome.drawGnome(canvas);
                points.drawCount(canvas);
                points.drawPoint(canvas);
                arrow.drawArrow(canvas);
            } else {
                canvas.drawBitmap(Bitmap.createScaledBitmap(gameOver, canvasWidth, canvasHeight, false), 0, 0, new Paint());
            }
        } else {

            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            preview = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.crystalcollectorstart),
                    canvasWidth, canvasHeight, false);
            start = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start),
                    canvasHeight / 5 * 3, canvasHeight / 5, false);
            canvas.drawBitmap(preview, 0, 0, new Paint());
            canvas.drawBitmap(start, canvasWidth / 2 - start.getWidth() / 2,
                    canvasHeight / 5 * 4 - start.getHeight() / 2, new Paint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (timerIsExists) {
            if (event.getAction() == event.ACTION_DOWN) {
                if (gnome.wasKilled) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(points.count.toString()));
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
                gnome.canStart = true;
                if (Math.abs(event.getX() - arrow.getXDown()) <= arrow.arrowDown.getWidth() / 2 &&
                        event.getY() - arrow.getYDown() <= arrow.arrowDown.getHeight() / 2) {
                    gnome.direction = Direction.bottom;
                    gnome.degrees = 180;

                }
                if (Math.abs(event.getX() - arrow.getXUp()) <= arrow.arrowUp.getWidth() / 2 &&
                        event.getY() - arrow.getYUp() <= arrow.arrowUp.getHeight() / 2) {
                    gnome.direction = Direction.top;
                    gnome.degrees = 0;
                }
                if (Math.abs(event.getX() - arrow.getXRight()) <= arrow.arrowRight.getWidth() / 2 &&
                        event.getY() - arrow.getYRight() <= arrow.arrowRight.getHeight() / 2) {
                    gnome.direction = Direction.right;
                    gnome.degrees = 90;
                }
                if (Math.abs(event.getX() - arrow.getXLeft()) <= arrow.arrowLeft.getWidth() / 2 &&
                        event.getY() - arrow.getYLeft() <= arrow.arrowLeft.getHeight() / 2) {
                    gnome.direction = Direction.left;
                    gnome.degrees = 270;
                }
            }
        } else if (Math.abs(event.getX() - canvasWidth / 2) <= start.getWidth() / 2
                && Math.abs(event.getY() - canvasHeight / 5 * 4) <= start.getHeight() / 2) {
            timerIsExists = true;
            start();
        }
        return true;
    }

}