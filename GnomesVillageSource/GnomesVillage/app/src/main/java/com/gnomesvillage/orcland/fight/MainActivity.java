package com.gnomesvillage.orcland.fight;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnSystemUiVisibilityChangeListener {
    GameField gameField;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameField = new GameField(this);
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        gameField.setOnSystemUiVisibilityChangeListener(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(gameField);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameField.gnome.cancel(true);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameField.gnome.cancel(true);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameField.gnome.cancel(true);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}