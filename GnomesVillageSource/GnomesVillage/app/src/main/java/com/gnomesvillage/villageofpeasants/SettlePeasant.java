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

public class SettlePeasant extends DialogFragment implements View.OnClickListener {
    protected SettlePeasantFragmentListener listener;
    protected ImageView picture;
    protected TextView text1, text2;

    public interface SettlePeasantFragmentListener {
        void onClickOnSettlePeasant();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (SettlePeasant.SettlePeasantFragmentListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement SettlePeasantFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SettlePeasant.SettlePeasantFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement SettlePeasantFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        View view = inflater.inflate(R.layout.call_a_peasant, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        picture = view.findViewById(R.id.settleAPeasantPicture);
        picture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.settlepeasant),
                height / 3, height / 3, false));
        picture.setOnClickListener(this);
        text1 = view.findViewById(R.id.settleAPeasantText);
        text2 = view.findViewById(R.id.settleAPeasantPrice);
        text1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        text2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        text1.setTextSize(height / 21);
        text2.setTextSize(height / 21);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settleAPeasantPicture) {
            listener.onClickOnSettlePeasant();
            dismiss();
        }
    }

}