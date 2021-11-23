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

public class Woodworking extends DialogFragment implements View.OnClickListener {

    WoodworkingFragmentListener listener;

    protected ImageView tree;
    protected TextView text1, text2;
    protected Integer width, height;

    public interface WoodworkingFragmentListener {
        void onClickOnWoodworking();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Woodworking.WoodworkingFragmentListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement WoodworkingFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Woodworking.WoodworkingFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement WoodworkingFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf");


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        View view = inflater.inflate(R.layout.item_woodworking, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        tree = view.findViewById(R.id.tree);
        tree.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.makewoodpicture),
                height / 3, height / 3, false));
        tree.setOnClickListener(this);

        text1 = view.findViewById(R.id.woodworking1);
        text2 = view.findViewById(R.id.woodworking2);
        text1.setTextSize(height / 21);
        text2.setTextSize(height / 30);
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tree: {
                listener.onClickOnWoodworking();
                dismiss();
                break;
            }
        }
    }

}