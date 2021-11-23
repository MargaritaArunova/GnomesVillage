package com.gnomesvillage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameField extends RelativeLayout {

    protected ImageView home, forge, mill, cave, cristal, sword, bread, arableLand, wheatView, arrowToOrcLand, arrowToPeasants, forest;
    protected Bitmap newArrow, currentArrow;
    protected Integer width, height, cristals, breads, swords, wheat, villagers;
    protected TextView cristalText, breadText, swordText, arableLandTimeText, wheatText, millTimeText;
    protected CountDownTimer arableLandTime, millTime;
    protected Bitmap firstConditionOfWheat, secondConditionOfWheat;


    public GameField(Context context) {
        super(context);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf");
        home = new ImageView(context);
        forge = new ImageView(context);
        mill = new ImageView(context);
        cave = new ImageView(context);
        bread = new ImageView(context);
        cristal = new ImageView(context);
        sword = new ImageView(context);
        arableLand = new ImageView(context);
        wheatView = new ImageView(context);
        arrowToOrcLand = new ImageView(context);
        arrowToPeasants = new ImageView(context);
        forest = new ImageView(context);

        cristalText = new TextView(context);
        breadText = new TextView(context);
        swordText = new TextView(context);
        arableLandTimeText = new TextView(context);
        wheatText = new TextView(context);
        millTimeText = new TextView(context);

        cristals = 0;
        breads = 0;
        swords = 0;
        wheat = 0;
        villagers = 0;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        setLayoutParams(new ViewGroup.LayoutParams(layoutParams));

        cristalText.setTypeface(typeface);
        swordText.setTypeface(typeface);
        breadText.setTypeface(typeface);
        arableLandTimeText.setTypeface(typeface);
        wheatText.setTypeface(typeface);
        millTimeText.setTypeface(typeface);
        setBackgroundResource(R.drawable.background);
        creatingLocations();

        arableLandTime = new CountDownTimer(91000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                arableLandTimeText.setText(toStringTime((int) millisUntilFinished - 1000));
                if (arableLandTimeText.getText().equals("00 : 00")) {
                    arableLand.setImageBitmap(secondConditionOfWheat);
                    arableLandTimeText.setText("Done!");
                }
            }

            @Override
            public void onFinish() {
            }
        };
        millTime = new CountDownTimer(91000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                millTimeText.setText(toStringTime((int) millisUntilFinished - 1000));
                if (millTimeText.getText().equals("00 : 00")) {
                    millTimeText.setText("Done!");
                }
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public void creatingLocations() {
        home.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.home),
                (int) (height / 3.1), (int) (height / 3.1), false));
        forge.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.forge),
                (int) (height / 2.9), (int) (height / 2.9), false));
        mill.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mill),
                (int) (height / 3) + height / 80, (int) (height / 3) + height / 80, false));
        cave.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cave),
                (int) (width / 3.1), (int) (width / 3.1), false));
        forest.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.forest2),
                (int) (height), (int) (height), false));
        cristal.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cristalpurple),
                height / 18, height / 18, false));
        sword.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.swordpicture),
                height / 18, height / 18, false));
        bread.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bread),
                height / 18, height / 18, false));
        arrowToOrcLand.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrowtoorcland),
                height / 3, height / 9, false));
        arrowToPeasants.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrowtopeasants),
                height / 3, height / 9, false));
        newArrow = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrowtocastle),
                height / 3, height / 9, false);
        currentArrow = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrowtoorcland),
                height / 3, height / 9, false);
        wheatView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wheat),
                height / 18, height / 18, false));
        firstConditionOfWheat = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arableland),
                (int) (height / 2.1), (int) (height / 2.1), false);
        secondConditionOfWheat = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arablelandwhithwheat),
                (int) (height / 2.1), (int) (height / 2.1), false);
        arableLand.setImageBitmap(firstConditionOfWheat);

        cristalText.setTextSize(height / 21);
        cristalText.setTextColor(Color.BLACK);
        cristalText.setText(String.valueOf(cristals));
        breadText.setTextSize(height / 21);
        breadText.setTextColor(Color.BLACK);
        breadText.setText(String.valueOf(breads));
        swordText.setTextSize(height / 21);
        swordText.setTextColor(Color.BLACK);
        swordText.setText(String.valueOf(swords));
        wheatText.setTextSize(height / 21);
        wheatText.setTextColor(Color.BLACK);
        wheatText.setText(String.valueOf(wheat));
        arableLandTimeText.setTextSize(height / 21);
        arableLandTimeText.setTextColor(Color.BLACK);
        arableLandTimeText.setText("");
        millTimeText.setTextSize(height / 21);
        millTimeText.setTextColor(Color.BLACK);
        millTimeText.setText("");

        home.setX(width / 3 - (int) (height / 2.5));
        home.setY(height / 4 + height / 20);
        home.setId(View.generateViewId());

        forge.setX(width / 6);
        forge.setY((int) (height / 1.5));
        forge.setId(View.generateViewId());

        mill.setX((int) (width / 2.075));
        mill.setY((height / 2));
        mill.setId(View.generateViewId());

        cave.setX((int) (width - (int) (width / 3.5)));
        cave.setY(-5);
        cave.setId(View.generateViewId());

        forest.setX(width / 2 - (int) (height / 12 * 7));
        forest.setY(-height / 40);

        arableLand.setX((int) (width / 2.075) + (int) (height / 3.5));
        arableLand.setY(height / 2);
        arableLand.setId(View.generateViewId());
        arableLandTimeText.setX((int) (width / 2.075) + (int) (height / 3.5) + (int) (height / 4.6) - (int) (height / 21 * 2.5));
        arableLandTimeText.setY(height / 2 - 2 * (height / 21) + (int) (height / 6.9));

        millTimeText.setX((int) (width / 2.075) + height / 7 + 5 - (height / 21) * 2);
        millTimeText.setY(height / 2 - height / 21 - 3);

        arrowToOrcLand.setX(width - height / 3);
        arrowToOrcLand.setY(height - height / 9 - height / 18);
        arrowToOrcLand.setId(View.generateViewId());

        arrowToPeasants.setX(0);
        arrowToPeasants.setY(height - height / 9 - height / 18);
        arrowToPeasants.setId(View.generateViewId());

        cristal.setX(0);
        cristal.setY(0);
        cristalText.setX(height / 18 + 5);
        cristalText.setY(0);
        bread.setX(0);
        bread.setY(height / 18);
        breadText.setX(height / 18 + 5);
        breadText.setY(height / 18);
        sword.setX(0);
        sword.setY(height / 9);
        swordText.setX(height / 18 + 5);
        swordText.setY(height / 9);
        wheatView.setX(0);
        wheatView.setY(height / 6);
        wheatText.setX(height / 18 + 5);
        wheatText.setY(height / 6);

        addView(forest);
        addView(home);
        addView(forge);
        addView(mill);
        addView(cave);
        addView(arableLand);
        addView(arableLandTimeText);
        addView(millTimeText);
        addView(cristal);
        addView(cristalText);
        addView(bread);
        addView(breadText);
        addView(sword);
        addView(swordText);
        addView(wheatView);
        addView(wheatText);
        addView(arrowToOrcLand);
        addView(arrowToPeasants);
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