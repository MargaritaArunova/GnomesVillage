package com.gnomesvillage.gnomeswalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {

    Context context;
    Stone stone;
    protected Integer width = 0, height = 0, time = 0, cristals = 0;

    public GameField(Context context) {
        super(context);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        getHolder().addCallback(this);
        stone = new Stone(getResources(), getHolder(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        this.context = context;
    }

    public GameField(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

        getHolder().addCallback(this);
        stone = new Stone(getResources(), getHolder(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cristals = stone.countOfCristals;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (stone.timerIsExists) {
                if (stone.timerIsStarted) {
                    stone.gnome.move();
                }
                if (!stone.timerIsStarted) {
                    stone.timerIsStarted = true;
                    stone.countDownTimer.start();

                }
                if (!stone.running) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(cristals.toString()));
                    if (stone.time.equals("00 : 00")) {
                        intent.putExtra("Came to the orcs", 1);
                    } else {
                        intent.putExtra("Came to the orcs", 0);
                    }
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
                for (int i = 0; i < stone.x.size(); i++) {
                    if (stone.getStoneRect(i).intersect(stone.gnome.getGnomeRect()) && stone.isDrawing.get(i)) {
                        stone.requestStop();
                        break;
                    }
                }

            } else if (Math.abs(event.getX() - stone.canvasWidth / 2) <= stone.start.getWidth() / 2
                    && Math.abs(event.getY() - stone.canvasHeight / 5 * 4) <= stone.start.getHeight() / 2) {
                stone.timerIsExists = true;
            }

        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        stone.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stone.requestStop();
        stone.cancel(true);
    }

}