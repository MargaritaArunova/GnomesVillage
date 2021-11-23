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

public class RoadToOrcLand extends DialogFragment implements View.OnClickListener {
    protected final Integer GNOMES_WALK = 6;
    protected RoadToOrcLandFragmentListener listener;

    public interface RoadToOrcLandFragmentListener {
        void onRoadToOrcLandClick(Integer value);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (RoadToOrcLand.RoadToOrcLandFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement RoadToOrcLandFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RoadToOrcLand.RoadToOrcLandFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement RoadToOrcLandFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf");
        View view = inflater.inflate(R.layout.road_to_orc_land, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ImageView picture = view.findViewById(R.id.roadToOrc);
        TextView text1 = view.findViewById(R.id.questionPart1);
        TextView text2 = view.findViewById(R.id.questionPart2);
        TextView positiveButton = view.findViewById(R.id.positiveButton);
        TextView negativeButton = view.findViewById(R.id.negativeButton);
        picture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.previewgnomeswalk),
                height / 3, height / 3, false));
        text1.setTypeface(typeface);
        text1.setTextSize(height / 21);
        text2.setTypeface(typeface);
        text2.setTextSize(height / 21);
        positiveButton.setTypeface(typeface);
        positiveButton.setTextSize(height / 21);
        negativeButton.setTypeface(typeface);
        negativeButton.setTextSize(height / 21);
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.positiveButton) {
            listener.onRoadToOrcLandClick(GNOMES_WALK);
            dismiss();

        }
        if (v.getId() == R.id.negativeButton) {
            dismiss();
        }
    }

}