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

public class FellingOfTrees extends DialogFragment implements View.OnClickListener {
    protected ImageView imageView;
    protected TextView text;
    protected FellingOfTreesFragmentListener listener;

    public interface FellingOfTreesFragmentListener {
        void onClickOnFellingOfTrees();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (FellingOfTrees.FellingOfTreesFragmentListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement FellingOfTreesFragmentListener");
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FellingOfTrees.FellingOfTreesFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement FellingOfTreesFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        View view = inflater.inflate(R.layout.felling_of_trees, container);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        imageView = view.findViewById(R.id.fellingOfTreesPicture);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.forestpicture),
                height / 3, height / 3, false));
        imageView.setOnClickListener(this);
        text = view.findViewById(R.id.fellingOfTreesText);
        text.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts-bold.ttf"));
        text.setTextSize(height / 21);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fellingOfTreesPicture) {
            listener.onClickOnFellingOfTrees();
            dismiss();
        }
    }

}