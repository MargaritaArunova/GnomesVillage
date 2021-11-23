package com.gnomesvillage.gnomeswalk;

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
import android.view.SurfaceHolder;

import com.gnomesvillage.R;

import java.util.ArrayList;


public class Stone extends AsyncTask {

    Gnome gnome;
    CountDownTimer countDownTimer;
    Typeface typeface;

    protected Bitmap stone, cristal, preview, start, background, gameOver;
    protected ArrayList<Integer> x;
    protected ArrayList<Boolean> isDrawing;
    protected Integer y, dx, dx1, background1X = 0, background2X;
    protected boolean isCreated = false, timerIsStarted = false;
    protected Integer canvasWidth, canvasHeight, countOfCristals = 0, speed, untilMustEnd = -1;
    protected Long timeInMillis = 6000l;
    protected volatile boolean running = true, timerIsExists = false, mustEnd = false;
    protected SurfaceHolder surfaceHolder;
    protected String time = "01 : 30";

    public Stone(Resources resources, SurfaceHolder surfaceHolder, Typeface typeface) {
        this.surfaceHolder = surfaceHolder;
        this.typeface = typeface;

        background = BitmapFactory.decodeResource(resources, R.drawable.gnomeswalkbackground);
        stone = BitmapFactory.decodeResource(resources, R.drawable.firstconditionofstone);
        cristal = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalred),
                50, 50, false);

        x = new ArrayList<>();
        isDrawing = new ArrayList<>();
        gnome = new Gnome(resources);
        preview = BitmapFactory.decodeResource(resources, R.drawable.gnomeswalkstart);
        start = BitmapFactory.decodeResource(resources, R.drawable.start);
        gameOver = BitmapFactory.decodeResource(resources, R.drawable.gameoverlandscape);

        countDownTimer = new CountDownTimer(91000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = toStringTime((int) millisUntilFinished - 1000);
                changeSpeed(time);
                timeInMillis = millisUntilFinished - 1000;
                if (mustEnd && untilMustEnd == -1) {
                    untilMustEnd = (int) (millisUntilFinished - 1000);
                }
                if (untilMustEnd - millisUntilFinished >= 2000) {
                    requestStop();
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

    public void createStone(Integer canvasHeight, Integer canvasWidth) {
        stone = Bitmap.createScaledBitmap(stone, canvasWidth / 8, canvasWidth / 8, false);
        background = Bitmap.createScaledBitmap(background, canvasWidth * 2, canvasHeight, false);
        background2X = canvasWidth * 2;
        y = canvasHeight / 3 * 2 - stone.getHeight() / 4 * 3;
        dx = canvasWidth / 3;
        speed = (int) (canvasHeight * 0.00625) * 2;
        dx1 = (int) (canvasHeight * 0.00625);
        isDrawing.add(false);
        isDrawing.add(false);
        for (int i = 0; i * dx < canvasWidth; i++) {
            int test = (int) (1 + Math.random() * 2);
            if (i > 1) {
                if (test == 1) {
                    isDrawing.add(true);
                } else {
                    isDrawing.add(false);
                }
            }
            x.add(i * dx);
        }

    }

    public void moveBackground() {
        background1X -= speed;
        background2X -= speed;
        if (background1X <= 0 - background.getWidth()) {
            background1X = canvasWidth * 2;
        }
        if (background2X <= 0 - background.getWidth()) {
            background2X = canvasWidth * 2;
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

    }

    public void moveStone() {
        if (isCreated) {
            for (int i = 0; i < x.size(); i++) {
                x.set(i, x.get(i) - speed);
            }
            if (x.get(0) <= 0 - stone.getWidth()) {
                if (isDrawing.get(0)) {
                    countOfCristals++;
                }

                x.remove(0);
                isDrawing.remove(0);


                x.add(canvasWidth);
                int test = (int) (1 + Math.random() * 2);
                if (test == 1 && timeInMillis > 5000) {
                    isDrawing.add(true);
                } else {
                    isDrawing.add(false);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void changeSpeed(String time) {
        if (time.equals("01:15")) {
            speed += dx1;
        } else if (time.equals("01:00")) {
            speed += dx1;
        } else if (time.equals("00:45")) {
            speed += dx1;
        } else if (time.equals("00:30")) {
            speed += dx1;
        } else if (time.equals("00:15")) {
            speed += dx1;
        }
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
                        canvasWidth = canvas.getWidth();
                        canvasHeight = canvas.getHeight();
                        preview = Bitmap.createScaledBitmap(preview, canvas.getWidth(), canvas.getHeight(), false);
                        start = Bitmap.createScaledBitmap(start, canvas.getWidth() / 5 * 3, canvas.getWidth() / 5, false);
                        canvas.drawBitmap(preview, 0, 0, new Paint());
                        canvas.drawBitmap(start, canvas.getWidth() / 2 - start.getWidth() / 2,
                                canvas.getHeight() / 5 * 4 - start.getHeight() / 2, new Paint());
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                } else {
                    if (time.equals("00 : 00")) {
                        gnome.changeSprite();
                        gnome.moveInEnd(canvasWidth);
                        if (gnome.x >= canvasWidth) {
                            requestStop();
                        }
                    } else {
                        if (timerIsStarted) {
                            moveStone();
                            moveBackground();
                            gnome.changeSprite();
                        }
                    }
                    try {
                        if (!mustEnd) {
                            canvasWidth = canvas.getWidth();
                            canvasHeight = canvas.getHeight();
                            if (!isCreated) {
                                createStone(canvasHeight, canvasWidth);
                                isCreated = true;
                            }

                            //stone
                            canvas.drawBitmap(background, background1X, 0, paint);
                            canvas.drawBitmap(background, background2X, 0, paint);
                            for (int i = 0; i < x.size(); i++) {
                                if (isDrawing.get(i)) {
                                    canvas.drawBitmap(stone, x.get(i), y, paint);
                                }
                            }

                            canvas.drawBitmap(cristal, 10, 10, paint);
                            canvas.drawText(String.valueOf(countOfCristals), 15 + cristal.getWidth(), 60, paint);
                            //

                            //for gnome
                            {
                                if (!gnome.haveCurrentPosition) {
                                    gnome.setCoordinates(canvas);
                                }
                                canvas.drawBitmap(gnome.currentBitmap, gnome.x, gnome.y, paint);
                            }
                            //

                            //for timer
                            {
                                canvas.drawText(time, canvas.getWidth() - canvas.getWidth() / 5, 60, paint);
                            }
                            //
                            for (int i = 0; i < x.size(); i++) {
                                if (getStoneRect(i).intersect(gnome.getGnomeRect()) && isDrawing.get(i)) {
                                    mustEnd = true;
                                    break;
                                }
                            }
                        } else {
                            canvas.drawBitmap(Bitmap.createScaledBitmap(gameOver, canvasWidth, canvasHeight, false), 0, 0, paint);
                        }
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        return null;
    }

    public Rect getStoneRect(int index) {
        return new Rect(x.get(index) + 5, y + stone.getHeight() / 3, x.get(index) + stone.getWidth() - 5, y + stone.getHeight() / 3 * 2);
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