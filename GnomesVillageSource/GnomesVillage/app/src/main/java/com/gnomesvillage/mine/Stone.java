package com.gnomesvillage.mine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import com.gnomesvillage.R;


public class Stone extends AsyncTask {

    protected Bitmap currentStone, firstConditionOfStone, secondConditionOfStone, thirdConditionOfStone, fourthConditionOfStone, fifthConditionOfStone;
    protected Bitmap oneCristal, threeCristals, fiveCristals, splitCristal, countCristal, background, preview, start, gameOver;
    protected String time = "01 : 30";
    protected Integer countOfTouching = 0, width = 0, height = 0, countOfCristals = 0, canvasWidth = 0, canvasHeight = 0;
    protected boolean running = true, haveSize = false, timerIsStarted = false, canTouch = true, timerIsExists = false;
    Typeface typeface;
    SurfaceHolder surfaceHolder;
    CountDownTimer countDownTimer;
    Pick pick;

    public Stone(Resources resources, Typeface typeface, SurfaceHolder surfaceHolder) {
        oneCristal = BitmapFactory.decodeResource(resources, R.drawable.cristalred);
        threeCristals = BitmapFactory.decodeResource(resources, R.drawable.threecristal);
        fiveCristals = BitmapFactory.decodeResource(resources, R.drawable.fivecristals);
        splitCristal = BitmapFactory.decodeResource(resources, R.drawable.splitcristal);
        countCristal = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cristalred),
                50, 50, false);
        background = BitmapFactory.decodeResource(resources, R.drawable.minebackground);
        start = BitmapFactory.decodeResource(resources, R.drawable.start);
        preview = BitmapFactory.decodeResource(resources, R.drawable.minestart);
        gameOver = BitmapFactory.decodeResource(resources, R.drawable.gameoverlandscape);

        firstConditionOfStone = BitmapFactory.decodeResource(resources, R.drawable.firstconditionofstone);
        secondConditionOfStone = BitmapFactory.decodeResource(resources, R.drawable.secondconditionofstone);
        thirdConditionOfStone = BitmapFactory.decodeResource(resources, R.drawable.thirdconditionofstone);
        fourthConditionOfStone = BitmapFactory.decodeResource(resources, R.drawable.fourthconditionofstone);
        fifthConditionOfStone = BitmapFactory.decodeResource(resources, R.drawable.fifthconditionofstone);

        currentStone = firstConditionOfStone;

        this.typeface = typeface;
        this.surfaceHolder = surfaceHolder;
        pick = new Pick(resources);

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

    public void setSize(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        haveSize = true;


        oneCristal = Bitmap.createScaledBitmap(oneCristal, height / 6, height / 6, false);
        threeCristals = Bitmap.createScaledBitmap(threeCristals, height / 6, height / 6, false);
        fiveCristals = Bitmap.createScaledBitmap(fiveCristals, height / 6, height / 6, false);
        splitCristal = Bitmap.createScaledBitmap(splitCristal, height / 6, height / 6, false);
        background = Bitmap.createScaledBitmap(background, width, height, false);

        firstConditionOfStone = Bitmap.createScaledBitmap(firstConditionOfStone, height / 2, height / 2, false);
        secondConditionOfStone = Bitmap.createScaledBitmap(secondConditionOfStone, height / 2, height / 2, false);
        thirdConditionOfStone = Bitmap.createScaledBitmap(thirdConditionOfStone, height / 2, height / 2, false);
        fourthConditionOfStone = Bitmap.createScaledBitmap(fourthConditionOfStone, height / 2, height / 2, false);
        fifthConditionOfStone = Bitmap.createScaledBitmap(fifthConditionOfStone, height / 2, height / 2, false);

        currentStone = firstConditionOfStone;

        pick.setPickSize(height);
    }

    public void changeBitmap() {
        if (currentStone == firstConditionOfStone) {
            currentStone = secondConditionOfStone;
        } else if (currentStone == secondConditionOfStone) {
            currentStone = thirdConditionOfStone;
        } else if (currentStone == thirdConditionOfStone) {
            currentStone = fourthConditionOfStone;
        } else if (currentStone == fourthConditionOfStone) {
            currentStone = fifthConditionOfStone;
        } else if (currentStone == fifthConditionOfStone) {
            canTouch = false;
            int test = (int) (1 + Math.random() * 4);
            if (test == 1) {
                currentStone = oneCristal;
            } else if (test == 2) {
                currentStone = threeCristals;
            } else if (test == 3) {
                currentStone = fiveCristals;
            } else if (test == 4) {
                currentStone = splitCristal;
            }
        } else if (currentStone == oneCristal || currentStone == threeCristals || currentStone == fiveCristals || currentStone == splitCristal) {
            currentStone = firstConditionOfStone;
        }
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
                if (time.equals("00 : 00")) {
                    requestStop();
                }
                canvas.drawColor(Color.LTGRAY);
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
                    try {
                        if (!time.equals("00 : 00")) {
                            canvasWidth = canvas.getWidth();
                            canvasHeight = canvas.getHeight();
                            canvas.drawBitmap(background, 0, 0, paint);
                            //game
                            if (!haveSize) {
                                setSize(canvas.getWidth(), canvas.getHeight());
                            }
                            if (!(currentStone == oneCristal || currentStone == threeCristals || currentStone == fiveCristals || currentStone == splitCristal)) {
                                canvas.drawBitmap(currentStone,
                                        canvas.getWidth() / 2 - firstConditionOfStone.getWidth() / 2,
                                        canvas.getHeight() / 2 - firstConditionOfStone.getHeight() / 2, paint);
                            } else {
                                canvas.drawBitmap(currentStone, canvas.getWidth() / 2 - oneCristal.getWidth() / 2, canvas.getHeight() / 2 - oneCristal.getHeight() / 2, paint);
                            }
                            //

                            canvas.drawText(time, canvas.getWidth() - canvas.getWidth() / 5, 60, paint);
                            canvas.drawBitmap(countCristal, 10, 10, paint);
                            canvas.drawText(countOfCristals.toString(), 15 + countCristal.getWidth(), 60, paint);
                            canvas.drawBitmap(pick.currentPick,
                                    canvas.getWidth() / 2 + firstConditionOfStone.getWidth() / 20,
                                    canvas.getHeight() / 2 - firstConditionOfStone.getHeight() / 2, paint);

                        } else {
                            canvas.drawBitmap(Bitmap.createScaledBitmap(gameOver,
                                    canvas.getWidth(), canvas.getHeight(), false), 0, 0, new Paint());
                        }

                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    if (currentStone == oneCristal || currentStone == threeCristals || currentStone == fiveCristals || currentStone == splitCristal) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                        }
                    }
                    canTouch = true;
                }
            }
        }
        return null;
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