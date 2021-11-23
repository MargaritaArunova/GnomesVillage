package com.gnomesvillage.skyjump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    Island island;
    protected Integer width = 0, height = 0, time = 0, cristals = 0;
    protected boolean timerIsStarted = false;

    public GameField(Context context) {
        super(context);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        getHolder().addCallback(this);
        island = new Island(getResources(), getHolder(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        this.context = context;
    }

    public GameField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        getHolder().addCallback(this);
        island = new Island(getResources(), getHolder(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cristals = island.countOfCristals;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (island.timerIsExists) {
                if (!timerIsStarted) {
                    timerIsStarted = true;
                    island.countDownTimer.start();
                }
                if (event.getX() < width / 2) {
                    island.gnome.direction = Direction.left;
                }
                if (event.getX() > width / 2) {
                    island.gnome.direction = Direction.right;
                }
                island.moveIslands();
                island.gnome.move();
                if (island.time.equals("00 : 00")) {
                    island.requestStop();
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(cristals.toString()));
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
                if (!island.getIslandRect().intersect(island.gnome.getGnomeRect())) {
                    island.gnome.fallDown();
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(cristals.toString()));
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();

                }
            } else if (Math.abs(event.getX() - island.canvasWidth / 2) <= island.start.getWidth() / 2
                    && Math.abs(event.getY() - island.canvasHeight / 5 * 4) <= island.start.getHeight() / 2) {
                island.timerIsExists = true;
            }

        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        island.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        island.requestStop();
        island.cancel(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

}