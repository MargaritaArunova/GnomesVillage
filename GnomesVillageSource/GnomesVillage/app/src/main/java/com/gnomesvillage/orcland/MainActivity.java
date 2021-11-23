package com.gnomesvillage.orcland;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gnomesvillage.R;

public class MainActivity extends Activity implements View.OnClickListener {
    protected GameField gameField;
    protected boolean firstTouch = true, orcLandIsOur;
    protected final Integer CAPTURE = 1, FIGHT = 2;
    protected Intent capture, fight;
    protected final String CASTLE_IS_OUR = "Castle is our!!!";
    protected SharedPreferences sharedPreferences;
    protected SoundPool soundPool;
    protected Integer soundIdButtonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        orcLandIsOur = sharedPreferences.getBoolean(CASTLE_IS_OUR, false);

        capture = new Intent(this, com.gnomesvillage.orcland.capture.MainActivity.class);
        fight = new Intent(this, com.gnomesvillage.orcland.fight.MainActivity.class);

        gameField = new GameField(this);
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(gameField);

        gameField.back.setOnClickListener(this);
        gameField.fight.setOnClickListener(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdButtonClick = soundPool.load(this, R.raw.button_click, 1);

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
    protected void onPause() {
        super.onPause();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CASTLE_IS_OUR, orcLandIsOur);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CASTLE_IS_OUR, orcLandIsOur);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE) {
                firstTouch = false;
                if (data.getBooleanExtra("Came to the orcs", false)) {
                    startActivityForResult(fight, FIGHT);
                }
            }
            if (requestCode == FIGHT) {
                if (data.getBooleanExtra("Are gnomes win?", false)) {
                    orcLandIsOur = true;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != View.NO_ID) {
            soundPool.play(soundIdButtonClick, 1, 1, 0, 0, 1);
            if (v.getId() == gameField.fight.getId()) {
                if (!sharedPreferences.getBoolean(CASTLE_IS_OUR, false)) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    if (!orcLandIsOur) {
                        if (firstTouch) {
                            startActivityForResult(capture, CAPTURE);
                        }
                    }
                } else {
                    Toast.makeText(this, "You have already won this castle!!!" + "\n" +
                            "It totally your castle!!!", Toast.LENGTH_LONG).show();
                }
            }
            if (v.getId() == gameField.back.getId()) {
                Intent intent = new Intent();
                if (orcLandIsOur) {
                    intent.putExtra("Castle is our!!!", true);
                } else {
                    intent.putExtra("Castle is our!!!", false);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

}