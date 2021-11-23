package com.gnomesvillage.jampinggnome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gnomesvillage.R;

public class GameField extends View {
    Timer timer;
    Gnome gnome;
    Island island;
    Cristal cristal;
    Bitmap preview, start;
    protected int canvasWidth, canvasHeight;
    protected boolean timerIsExists = false;

    public GameField(Context context) {
        super(context);
        cristal = new Cristal(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts.ttf"));
        island = new Island(getResources());
        gnome = new Gnome(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts.ttf"));
    }

    public GameField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cristal = new Cristal(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts.ttf"));
        island = new Island(getResources());
        gnome = new Gnome(getResources(), Typeface.createFromAsset(context.getAssets(), "fonts.ttf"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (timerIsExists) {
            if (gnome.hasIntersect < 7) {
                cristal.drawSky(canvas);
                cristal.drawCristals(canvas);
                island.drawIslands(canvas);
                gnome.drawGnome(canvas);
                cristal.drawCount(canvas);
                gnome.drawCountOfCrush(canvas);
            } else if (gnome.hasIntersect == 7) {
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gameoverportrait),
                        canvas.getWidth(), canvas.getHeight(), false), 0, 0, new Paint());
            }
        } else {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            preview = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jumpinggnomestart),
                    canvasWidth, canvasHeight, false);
            start = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start),
                    canvasHeight / 5 * 3, canvasHeight / 5, false);
            canvas.drawBitmap(preview, 0, 0, new Paint());
            canvas.drawBitmap(start, canvasWidth / 2 - start.getWidth() / 2,
                    canvasHeight / 5 * 4 - start.getHeight() / 2, new Paint());
        }
    }

    public void start() {
        timer = new Timer(Long.MAX_VALUE, 50, this, gnome, island, cristal);
        timer.start();
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !timerIsExists) {
            if (Math.abs(event.getX() - canvasWidth / 2) <= start.getWidth() / 2
                    && Math.abs(event.getY() - canvasHeight / 5 * 4) <= start.getHeight() / 2) {
                timerIsExists = true;
                start();
            }
        }

        return true;
    }

}