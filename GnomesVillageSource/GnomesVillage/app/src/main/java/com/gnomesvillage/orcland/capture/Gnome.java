package com.gnomesvillage.orcland.capture;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import com.gnomesvillage.R;


public class Gnome extends AsyncTask {
    protected Bitmap gnome, start, preview;
    protected volatile boolean running = true, timerIsExists = false;
    protected SurfaceHolder surfaceHolder;
    protected Typeface typeface;
    protected Integer width = 0, height = 0, x = 0, y = 0, countOfPassed = 0;
    protected Long timeInMillis = 0l;
    protected Enemy enemy;
    protected CountDownTimer countDownTimer;
    protected String time = "01 : 30";

    public Gnome(Resources resources, SurfaceHolder surfaceHolder, Typeface typeface) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        gnome = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.skyjumpgnome), width / 4, width / 4, false);
        preview = BitmapFactory.decodeResource(resources, R.drawable.capturestart);
        start = BitmapFactory.decodeResource(resources, R.drawable.start);
        this.surfaceHolder = surfaceHolder;
        this.typeface = typeface;

        enemy = new Enemy(resources);

        x = width / 2 - gnome.getWidth() / 2;
        y = height - gnome.getWidth();

        countDownTimer = new CountDownTimer(91000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = toStringTime((int) millisUntilFinished - 1000);
                timeInMillis = millisUntilFinished;
                if (millisUntilFinished > 3000) {
                    if (enemy.getEnemyRect().intersect(getGnomeRect())) {
                        requestStop();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public void requestStop() {
        running = false;
        cancel(true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setTypeface(typeface);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                if (!timerIsExists) {
                    try {
                        preview = Bitmap.createScaledBitmap(preview, canvas.getWidth(), canvas.getHeight(), false);
                        start = Bitmap.createScaledBitmap(start, canvas.getWidth() / 5 * 3, canvas.getWidth() / 5, false);
                        canvas.drawBitmap(preview, 0, 0, new Paint());
                        canvas.drawBitmap(start, canvas.getWidth() / 2 - start.getWidth() / 2,
                                canvas.getHeight() / 5 * 4 - start.getHeight() / 2, new Paint());
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                } else {
                    try {
                        canvas.drawARGB(255, 141, 177, 133);
                        enemy.moveEnemyX();
                        if (time.equals("00 : 03") || countOfPassed >= 90) {
                            enemy.canMakeNew = false;
                        }
                        if (countOfPassed >= 90) {
                            countDownTimer.cancel();
                            time = "00 : 00";
                        }
                        if (time.equals("00 : 00")) {
                            moveInEnd();
                            if (y <= 0 - gnome.getHeight()) {
                                requestStop();
                            }
                        }

                        //for enemy
                        if (countOfPassed < 90) {
                            for (int i = 0; i < enemy.coordinates.size(); i++) {
                                if (enemy.coordinates.get(i).onTheLeftSide) {
                                    canvas.drawBitmap(enemy.rotateBullet, enemy.coordinates.get(i).x, enemy.coordinates.get(i).y, paint);
                                } else {
                                    canvas.drawBitmap(enemy.bullet, enemy.coordinates.get(i).x, enemy.coordinates.get(i).y, paint);
                                }
                            }
                        }
                        //

                        //for gnome
                        canvas.drawBitmap(gnome, x, y, paint);
                        //

                        //texts
                        canvas.drawText(time, canvas.getWidth() - canvas.getWidth() / 4, 60, paint);
                        canvas.drawText(countOfPassed.toString(), 0, 60, paint);
                        //
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
        return null;
    }

    public void moveInEnd() {
        if (y >= 0 - gnome.getHeight()) {
            y -= 5;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    public Rect getGnomeRect() {
        return new Rect(x + gnome.getWidth() / 3, y + gnome.getHeight() / 4, x + gnome.getWidth() / 3 * 2, y + gnome.getHeight());
    }

    public String toStringTime(int time) {
        String stringSeconds, stringMinutes;
        int minutes = time / 60000;
        int seconds = (time - minutes * 60000) / 1000;
        stringMinutes = "0" + String.valueOf(minutes);
        if (seconds < 10) {
            stringSeconds = "0" + String.valueOf(seconds);
        } else stringSeconds = String.valueOf(seconds);

        return stringMinutes + " : " + stringSeconds;
    }

}