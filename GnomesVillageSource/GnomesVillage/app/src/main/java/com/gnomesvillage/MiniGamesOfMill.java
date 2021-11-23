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

public class MiniGamesOfMill extends DialogFragment implements View.OnClickListener {

    MiniGamesOfMillFragmentListener listener;
    final String BAKE = "Bake", SELL = "Sell";

    public interface MiniGamesOfMillFragmentListener {
        void onMiniGameOfMillClick(String value);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (MiniGamesOfMillFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement MiniGamesOfMillListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MiniGamesOfMillFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement MiniGamesOfMillListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf");

        View view = inflater.inflate(R.layout.item_mill_minigames, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        TextView sellBread = view.findViewById(R.id.sellBread);
        ImageView sellingPicture = view.findViewById(R.id.sellingPicture);
        TextView bakeBread = view.findViewById(R.id.bakeBread);
        ImageView bakingPicture = view.findViewById(R.id.bakingPicture);

        sellingPicture.setOnClickListener(this);
        bakingPicture.setOnClickListener(this);

        sellBread.setTypeface(typeface);
        bakeBread.setTypeface(typeface);
        sellBread.setTextSize(width / 40);
        bakeBread.setTextSize(width / 40);
        sellingPicture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sellbread),
                height / 3, height / 3, false));
        bakingPicture.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bread),
                height / 3, height / 3, false));

        return view;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sellingPicture) {
            listener.onMiniGameOfMillClick(SELL);
            dismiss();
        }
        if (v.getId() == R.id.bakingPicture) {
            listener.onMiniGameOfMillClick(BAKE);
            dismiss();
        }
    }
}