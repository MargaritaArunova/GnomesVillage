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

public class MiniGamesOfArableLand extends DialogFragment implements View.OnClickListener {
    MiniGamesOfArableLandFragmentListener listener;
    protected final Integer PLANT_WHEAT = 5;

    public interface MiniGamesOfArableLandFragmentListener {
        void onMiniGameOfArableLandClick(Integer value);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (MiniGamesOfArableLandFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement MiniGamesOfArableLandFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MiniGamesOfArableLandFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement MiniGamesOfArableLandFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf");
        View view = inflater.inflate(R.layout.item_arable_land_minigames, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ImageView plantWheatPicture = view.findViewById(R.id.plantWheatPicture);
        TextView plantWheatText = view.findViewById(R.id.plantWheat);
        TextView plantWheatTextPrice = view.findViewById(R.id.plantWheatPrice);
        plantWheatPicture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wheat),
                height / 3, height / 3, false));
        plantWheatText.setTypeface(typeface);
        plantWheatText.setTextSize(height / 21);
        plantWheatTextPrice.setTypeface(typeface);
        plantWheatTextPrice.setTextSize(height / 21);
        plantWheatPicture.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.plantWheatPicture) {
            listener.onMiniGameOfArableLandClick(PLANT_WHEAT);
            dismiss();
        }
    }

}