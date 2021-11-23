package com.gnomesvillage.skyjump;

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

public class Island extends AsyncTask {

    Gnome gnome;
    CountDownTimer countDownTimer;
    Typeface typeface;

    protected Bitmap island, cristal, preview, start, gameOver;
    protected ArrayList<Integer> x, y;
    protected boolean isCreated = false;
    protected Integer canvasWidth, canvasHeight, countOfCristals = 0;
    protected int dy, countOfPassed = 0;
    protected volatile boolean running = true, timerIsExists = false;
    protected SurfaceHolder surfaceHolder;
    protected String time = "01 : 30";

    public Island(Resources resources, SurfaceHolder surfaceHolder, Typeface typeface) {
        island = BitmapFactory.decodeResource(resources, R.drawable.hummock);
        x = new ArrayList<>();
        y = new ArrayList<>();
        this.surfaceHolder = surfaceHolder;
        cristal = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalgreen),
                50, 50, false);
        this.typeface = typeface;
        gnome = new Gnome(resources);
        preview = BitmapFactory.decodeResource(resources, R.drawable.skyjumpstart);
        start = BitmapFactory.decodeResource(resources, R.drawable.start);
        gameOver = BitmapFactory.decodeResource(resources, R.drawable.gameoverlandscape);

        countDownTimer = new CountDownTimer(91000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = toStringTime((int) millisUntilFinished - 1000);
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

    public void createIslands(Integer canvasHeight, Integer canvasWidth) {
        dy = canvasHeight / 5 + canvasWidth / 18;
        if (dy % 5 != 0) {
            dy += (5 - dy % 5);
        }
        x.add(canvasWidth / 2);
        island = Bitmap.createScaledBitmap(island, canvasWidth / 6, canvasWidth / 18, false);
        for (int i = 0; canvasHeight - i * dy >= 0; i++) {
            y.add(canvasHeight - i * dy - 30);
            if (i > 0) {
                int cases = (int) (Math.random() * 10);
                if (cases < 6) {
                    if (x.get(i - 1) - island.getWidth() >= 0) {
                        x.add(x.get(i - 1) - island.getWidth());
                    } else {
                        x.add(x.get(i - 1) + island.getWidth());
                    }
                } else if (cases > 5) {
                    if (x.get(i - 1) + island.getWidth() <= canvasWidth - island.getWidth()) {
                        x.add(x.get(i - 1) + island.getWidth());
                    } else {
                        x.add(x.get(i - 1) - island.getWidth());
                    }
                }
            }
        }
    }

    public void addX(ArrayList<Integer> x) {
        int cases = (int) (Math.random() * 10);
        if (cases < 6) {
            if (x.get(x.size() - 1) - island.getWidth() >= 0) {
                x.add(x.get(x.size() - 1) - island.getWidth());
            } else {
                x.add(x.get(x.size() - 1) + island.getWidth());
            }
        } else if (cases > 5) {
            if (x.get(x.size() - 1) + island.getWidth() <= canvasWidth - island.getWidth()) {
                x.add(x.get(x.size() - 1) + island.getWidth());
            } else {
                x.add(x.get(x.size() - 1) - island.getWidth());
            }
        }
    }

    public void moveIslands() {
        if (isCreated) {
            int k = 0;
            while (k < dy) {
                for (int i = 0; i < y.size(); i++) {
                    y.set(i, y.get(i) + 5);
                }
                k += 5;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            if (y.get(0) >= canvasHeight) {
                countOfPassed++;
                if (countOfPassed >= 5) {
                    countOfPassed = 0;
                    countOfCristals++;
                }
                y.remove(0);
                x.remove(0);
                y.add(y.get(y.size() - 1) - dy);
                addX(x);
            }
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

                    canvas.drawColor(Color.LTGRAY);
                    try {
                        if (time.equals("00 : 00") || gnome.y > canvas.getHeight() - 3) {
                            canvas.drawBitmap(Bitmap.createScaledBitmap(gameOver,
                                    canvas.getWidth(), canvas.getHeight(), false), 0, 0, new Paint());
                            requestStop();
                        } else {
                            canvasWidth = canvas.getWidth();
                            canvasHeight = canvas.getHeight();
                            if (!isCreated) {
                                createIslands(canvasHeight, canvasWidth);
                                isCreated = true;
                            }

                            //island
                            for (int i = 0; i < y.size(); i++) {
                                canvas.drawBitmap(island, x.get(i), y.get(i), paint);
                            }
                            canvas.drawBitmap(cristal, 10, 10, paint);
                            canvas.drawText(String.valueOf(countOfCristals), 15 + cristal.getWidth(), 60, paint);
                            //

                            //for gnome
                            {
                                if (!gnome.haveCurrentPosition) {
                                    gnome.setCoordinates(canvas, y.get(0));
                                }
                                canvas.drawBitmap(gnome.gnome, gnome.x, gnome.y, paint);
                            }
                            //

                            //for timer
                            {
                                canvas.drawText(time, canvasWidth - canvasWidth / 5, 60, paint);
                            }
                            //
                        }
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

    public Rect getIslandRect() {
        return new Rect(x.get(0), y.get(0) - 1, x.get(0) + island.getWidth(), y.get(0) + 15);
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