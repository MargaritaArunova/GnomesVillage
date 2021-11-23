package com.gnomesvillage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Swords extends DialogFragment implements View.OnClickListener {

    SwordsFragmentListener listener;
    protected ImageView swordForOneTime, swordForFiveTimes, swordForFifteenTimes, swordForTwentyFiveTimes, swordForFiftyTimes;
    protected TextView price1, price2, price3, price4, price5;
    protected TextView damage1, damage2, damage3, damage4, damage5;

    public interface SwordsFragmentListener {
        void OnClickOnSword(Integer value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swordForOneTime: {
                listener.OnClickOnSword(1);
                dismiss();
                break;
            }
            case R.id.swordForFiveTimes: {
                listener.OnClickOnSword(5);
                dismiss();
                break;
            }
            case R.id.swordForFifteenTimes: {
                listener.OnClickOnSword(15);
                dismiss();
                break;
            }
            case R.id.swordForTwentyFiveTimes: {
                listener.OnClickOnSword(25);
                dismiss();
                break;
            }
            case R.id.swordForFiftyTimes: {
                listener.OnClickOnSword(50);
                dismiss();
                break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_swords, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        price1 = view.findViewById(R.id.price1);
        price2 = view.findViewById(R.id.price2);
        price3 = view.findViewById(R.id.price3);
        price4 = view.findViewById(R.id.price4);
        price5 = view.findViewById(R.id.price5);
        price1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        price2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        price3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        price4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        price5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        price1.setTextSize(width / 65);
        price2.setTextSize(width / 65);
        price3.setTextSize(width / 65);
        price4.setTextSize(width / 65);
        price5.setTextSize(width / 65);

        damage1 = view.findViewById(R.id.damage1);
        damage2 = view.findViewById(R.id.damage2);
        damage3 = view.findViewById(R.id.damage3);
        damage4 = view.findViewById(R.id.damage4);
        damage5 = view.findViewById(R.id.damage5);
        damage1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        damage2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        damage3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        damage4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        damage5.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        damage1.setTextSize(width / 65);
        damage2.setTextSize(width / 65);
        damage3.setTextSize(width / 65);
        damage4.setTextSize(width / 65);
        damage5.setTextSize(width / 65);

        swordForOneTime = view.findViewById(R.id.swordForOneTime);
        swordForFiveTimes = view.findViewById(R.id.swordForFiveTimes);
        swordForFifteenTimes = view.findViewById(R.id.swordForFifteenTimes);
        swordForTwentyFiveTimes = view.findViewById(R.id.swordForTwentyFiveTimes);
        swordForFiftyTimes = view.findViewById(R.id.swordForFiftyTimes);


        swordForOneTime.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.swordforonetime),
                width / 5, width / 5, false));
        swordForFiveTimes.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.swordforfivetimes),
                width / 5, width / 5, false));
        swordForFifteenTimes.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.swordforfifteentimes),
                width / 5, width / 5, false));
        swordForTwentyFiveTimes.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.swordfortwentyfivetimes),
                width / 5, width / 5, false));
        swordForFiftyTimes.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.swordforfiftytimes),
                width / 5, width / 5, false));


        swordForOneTime.setOnClickListener(this);
        swordForFiveTimes.setOnClickListener(this);
        swordForFifteenTimes.setOnClickListener(this);
        swordForTwentyFiveTimes.setOnClickListener(this);
        swordForFiftyTimes.setOnClickListener(this);

        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (SwordsFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement SwordsFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SwordsFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement SwordsFragmentListener");
        }
    }
}