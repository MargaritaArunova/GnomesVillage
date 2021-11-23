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

public class MaterialHandling extends DialogFragment implements View.OnClickListener {

    MaterialHandling.MaterialHandlingFragmentListener listener;

    protected ImageView house1, woodworking;
    protected TextView houseText1, houseText2, woodworkingText1, woodworkingText2;
    protected Integer width, height;

    public interface MaterialHandlingFragmentListener {
        void onClickOnMaterialHandling(String string);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf");

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        View view = inflater.inflate(R.layout.item_shop, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        house1 = view.findViewById(R.id.house1);
        house1.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ruin1),
                height / 3, height / 3, false));
        house1.setOnClickListener(this);

        houseText1 = view.findViewById(R.id.house1Text1);
        houseText2 = view.findViewById(R.id.house1Text2);
        houseText1.setTypeface(typeface);
        houseText2.setTypeface(typeface);
        houseText1.setTextSize(height / 21);
        houseText2.setTextSize(height / 21);

        woodworking = view.findViewById(R.id.woodworking);
        woodworking.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.woodworking),
                height / 3, height / 3, false));
        woodworking.setOnClickListener(this);

        woodworkingText1 = view.findViewById(R.id.woodworkingText1);
        woodworkingText2 = view.findViewById(R.id.woodworkingText2);
        woodworkingText1.setTypeface(typeface);
        woodworkingText2.setTypeface(typeface);
        woodworkingText1.setTextSize(height / 21);
        woodworkingText2.setTextSize(height / 21);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house1: {
                listener.onClickOnMaterialHandling("HOUSE1");
                dismiss();
                break;
            }
            case R.id.woodworking: {
                listener.onClickOnMaterialHandling("WOODWORKING");
                dismiss();
                break;
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (MaterialHandling.MaterialHandlingFragmentListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement MaterialHandlingFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MaterialHandling.MaterialHandlingFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement MaterialHandlingFragmentListener");
        }
    }

}