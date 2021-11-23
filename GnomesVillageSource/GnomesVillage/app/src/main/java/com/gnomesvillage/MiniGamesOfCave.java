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

public class MiniGamesOfCave extends DialogFragment implements View.OnClickListener {
    protected final Integer SKY_JUMP = 3;
    protected final Integer MINE = 4;
    MiniGamesOfCaveFragmentListener listener;

    public interface MiniGamesOfCaveFragmentListener {
        void onMiniGameOfCaveClick(Integer value);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (MiniGamesOfCaveFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement MiniGamesOfCaveListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MiniGamesOfCaveFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement MiniGamesOfCaveListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        View view = inflater.inflate(R.layout.item_cave_minigames, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ImageView skyJump = view.findViewById(R.id.skyJump);
        ImageView mine = view.findViewById(R.id.mine);
        skyJump.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.previewskyjump),
                height / 3, height / 3, false));
        mine.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.previewmine),
                height / 3, height / 3, false));

        skyJump.setOnClickListener(this);
        mine.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.skyJump) {
            listener.onMiniGameOfCaveClick(SKY_JUMP);
            dismiss();
        }
        if (v.getId() == R.id.mine) {
            listener.onMiniGameOfCaveClick(MINE);
            dismiss();
        }
    }

}