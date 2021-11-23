package com.gnomesvillage.orcland.capture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {
    protected Gnome gnome;
    protected Context context;

    public GameField(Context context) {
        super(context);
        gnome = new Gnome(getResources(), getHolder(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        this.context = context;
        getHolder().addCallback(this);
    }

    public GameField(Context context, AttributeSet attrs) {
        super(context, attrs);
        gnome = new Gnome(getResources(), getHolder(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"));
        this.context = context;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gnome.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gnome.requestStop();
        gnome.cancel(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gnome.timerIsExists) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (gnome.enemy.coordinates.size() > 0) {
                    for (int i = 0; i < gnome.height / 4; i++) {
                        gnome.enemy.moveEnemyY();
                        if (gnome.enemy.coordinates.size() > 0) {
                            if (gnome.enemy.getEnemyRect().intersect(gnome.getGnomeRect())) {
                                gnome.requestStop();
                            }
                        }
                    }

                }
                if (gnome.countOfPassed < 90) {
                    gnome.countOfPassed++;
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (!gnome.running) {
                    gnome.cancel(true);
                    Intent intent = new Intent();
                    if (gnome.time.equals("00 : 00")) {
                        intent.putExtra("Came to the orcs", true);
                    } else {
                        intent.putExtra("Came to the orcs", false);
                    }
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
            }
        } else if (Math.abs(event.getX() - gnome.width / 2) <= gnome.start.getWidth() / 2
                && Math.abs(event.getY() - gnome.height / 5 * 4) <= gnome.start.getHeight() / 2 && event.getAction() == MotionEvent.ACTION_UP) {
            gnome.timerIsExists = true;
            gnome.countDownTimer.start();
        }
        return true;
    }

}