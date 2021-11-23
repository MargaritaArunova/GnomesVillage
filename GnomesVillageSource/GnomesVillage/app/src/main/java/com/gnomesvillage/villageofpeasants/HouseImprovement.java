package com.gnomesvillage.villageofpeasants;

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

import com.gnomesvillage.R;

public class HouseImprovement extends DialogFragment implements View.OnClickListener {

    HouseFragmentListener listener;

    protected ImageView improvement;
    protected TextView text1, text2;
    protected Integer width, height;

    public interface HouseFragmentListener {
        void onClickOnHouseImprovement();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (HouseFragmentListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement HouseFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (HouseFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement HouseFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf");

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        View view = inflater.inflate(R.layout.item_improve, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        improvement = view.findViewById(R.id.houseimprove);
        improvement.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.improvehouse),
                width / 2, width / 8, false));

        improvement.setOnClickListener(this);

        text1 = view.findViewById(R.id.houseImprove1);
        text2 = view.findViewById(R.id.houseImprove2);
        text1.setTextSize(height / 21);
        text2.setTextSize(height / 21);
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.houseimprove: {
                listener.onClickOnHouseImprovement();
                dismiss();
                break;
            }
        }
    }

}