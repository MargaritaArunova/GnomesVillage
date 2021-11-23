package com.gnomesvillage;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MiniGames extends DialogFragment implements View.OnClickListener {

    final int JUMPING_GNOME = 1;
    final int CRISTAL_COLLECTOR = 2;
    MiniGamesFragmentListener listener;


    public interface MiniGamesFragmentListener {
        void onMiniGameClick(Integer value);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (MiniGamesFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement MiniGameListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MiniGamesFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement MiniGameListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        View view = inflater.inflate(R.layout.item_minigames, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ImageView jumpingGnome = view.findViewById(R.id.jumpingGnome);
        ImageView cristalCollector = view.findViewById(R.id.cristalCollector);
        jumpingGnome.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.previewjumpinggnome),
                height / 3, height / 3, false));
        cristalCollector.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.previewcrystalcollector),
                height / 3, height / 3, false));
        jumpingGnome.setOnClickListener(this);
        cristalCollector.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.jumpingGnome) {
            listener.onMiniGameClick(JUMPING_GNOME);
            dismiss();
        }
        if (v.getId() == R.id.cristalCollector) {
            listener.onMiniGameClick(CRISTAL_COLLECTOR);
            dismiss();
        }
    }

}