package com.gnomesvillage.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {
    Stone stone;
    Context context;

    public GameField(Context context) {
        super(context);

        getHolder().addCallback(this);

        stone = new Stone(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"), getHolder());
        this.context = context;
    }

    public GameField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);

        stone = new Stone(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf"), getHolder());
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && stone.canTouch) {
            if (stone.timerIsExists) {
                stone.pick.changePickBitmap();
                if (!stone.timerIsStarted) {
                    stone.timerIsStarted = true;
                    stone.countDownTimer.start();
                }
                if (stone.time.equals("00 : 00")) {
                    stone.requestStop();
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(stone.countOfCristals.toString()));
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
                if (Math.abs(stone.width / 2 - event.getX()) <= stone.firstConditionOfStone.getWidth() / 3 &&
                        Math.abs(stone.height / 2 - event.getY()) <= stone.firstConditionOfStone.getHeight() / 3) {
                    stone.countOfTouching++;
                    if (stone.currentStone == stone.oneCristal || stone.currentStone == stone.threeCristals ||
                            stone.currentStone == stone.fiveCristals || stone.currentStone == stone.splitCristal) {
                        if (stone.countOfTouching == 1) {
                            if (stone.currentStone == stone.oneCristal) {
                                stone.countOfCristals += 1;
                            } else if (stone.currentStone == stone.threeCristals) {
                                stone.countOfCristals += 3;
                            } else if (stone.currentStone == stone.fiveCristals) {
                                stone.countOfCristals += 5;
                            }
                            stone.countOfTouching = 0;
                            stone.changeBitmap();
                        }
                    } else if (stone.countOfTouching >= 5) {
                        stone.countOfTouching = 0;
                        stone.changeBitmap();
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