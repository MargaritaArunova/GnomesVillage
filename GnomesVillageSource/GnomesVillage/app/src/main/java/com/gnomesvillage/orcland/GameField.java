package com.gnomesvillage.orcland;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gnomesvillage.R;

public class GameField extends RelativeLayout {

    protected ImageView back, fight;
    protected Integer width, height;

    public GameField(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        setLayoutParams(new ViewGroup.LayoutParams(layoutParams));

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        fight = new ImageView(context);
        back = new ImageView(context);
        back.setId(View.generateViewId());
        fight.setId(View.generateViewId());
        back.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrowback),
                height / 3, height / 9, false));
        fight.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fightarrow),
                height / 3, height / 9, false));
        fight.setX(0);
        fight.setY(height / 18);
        back.setX(0);
        back.setY(height - height / 9 - height / 18);
        addView(fight);
        addView(back);
        setBackgroundResource(R.drawable.orclandbackground);
    }

}