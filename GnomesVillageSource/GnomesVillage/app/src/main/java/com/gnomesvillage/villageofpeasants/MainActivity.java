package com.gnomesvillage.villageofpeasants;

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

public class MainActivity extends Activity implements View.OnClickListener ,View.OnSystemUiVisibilityChangeListener,
            HouseImprovement.HouseFragmentListener, MaterialHandling.MaterialHandlingFragmentListener,
            Woodworking.WoodworkingFragmentListener, SettlePeasant.SettlePeasantFragmentListener,
            FellingOfTrees.FellingOfTreesFragmentListener {

    GameField gameField;
    SoundPool soundPool;
    Integer soundIdButtonClick;
    HouseImprovement improvement;
    MaterialHandling materialHandling;
    Woodworking woodworking;
    SettlePeasant settlePeasant;
    FellingOfTrees fellingOfTrees;
    SharedPreferences sharedPreferences;
    final String HOUSE1 = "HOUSE1", HOUSE2 = "HOUSE2", HOUSE3 = "HOUSE3", TREE = "TREE", WOOD = "WOOD", WOODWORKING = "WOODWORKING",
            HOUSE_COUNT = "HOUSE_COUNT", VILLAGERS_IN_HOUSE1 = "VILLAGERS_IN_HOUSE1", VILLAGERS_IN_HOUSE2 = "VILLAGERS_IN_HOUSE2",
            VILLAGERS_IN_HOUSE3 = "VILLAGERS_IN_HOUSE3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameField = new GameField(this);
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        gameField.setOnSystemUiVisibilityChangeListener(this);
        setContentView(gameField);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        gameField.conditions.set(0, sharedPreferences.getInt(HOUSE1, -1));
        gameField.conditions.set(1, sharedPreferences.getInt(HOUSE2, -1));
        gameField.conditions.set(2, sharedPreferences.getInt(HOUSE3, -1));
        gameField.villagers.set(0, sharedPreferences.getInt(VILLAGERS_IN_HOUSE1, 0));
        gameField.villagers.set(1, sharedPreferences.getInt(VILLAGERS_IN_HOUSE2, 0));
        gameField.villagers.set(2, sharedPreferences.getInt(VILLAGERS_IN_HOUSE3, 0));
        gameField.trees = sharedPreferences.getInt(TREE, 0);
        gameField.woodenplank = sharedPreferences.getInt(WOOD, 0);
        gameField.woodworkingIsHere = sharedPreferences.getBoolean(WOODWORKING, false);
        gameField.treeCount.setText(gameField.trees.toString());
        gameField.woodCount.setText(gameField.woodenplank.toString());
        gameField.currentCountOfHouses = sharedPreferences.getInt(HOUSE_COUNT, 0);
        gameField.bread = getIntent().getIntExtra("BREAD", 0);
        gameField.breadCount.setText(gameField.bread.toString());
        for (int i = 0; i < 3; i++) {
            gameField.setBitmaps(i);
        }
        for (int i = 0; i < 3; i++) {
            gameField.setText(i);
        }

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdButtonClick = soundPool.load(this, R.raw.button_click, 1);

        improvement = new HouseImprovement();
        materialHandling = new MaterialHandling();
        woodworking = new Woodworking();
        settlePeasant = new SettlePeasant();
        fellingOfTrees = new FellingOfTrees();

        gameField.shop.setOnClickListener(this);
        gameField.house1.setOnClickListener(this);
        gameField.house2.setOnClickListener(this);
        gameField.house3.setOnClickListener(this);
        gameField.woodworking.setOnClickListener(this);
        gameField.forest.setOnClickListener(this);
        gameField.arrowBack.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HOUSE1, gameField.conditions.get(0));
        editor.putInt(HOUSE2, gameField.conditions.get(1));
        editor.putInt(HOUSE3, gameField.conditions.get(2));
        editor.putBoolean(WOODWORKING, gameField.woodworkingIsHere);
        editor.putInt(WOOD, gameField.woodenplank);
        editor.putInt(TREE, gameField.trees);
        editor.putInt(HOUSE_COUNT, gameField.currentCountOfHouses);
        editor.putInt(VILLAGERS_IN_HOUSE1, gameField.villagers.get(0));
        editor.putInt(VILLAGERS_IN_HOUSE2, gameField.villagers.get(1));
        editor.putInt(VILLAGERS_IN_HOUSE3, gameField.villagers.get(2));
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HOUSE1, gameField.conditions.get(0));
        editor.putInt(HOUSE2, gameField.conditions.get(1));
        editor.putInt(HOUSE3, gameField.conditions.get(2));
        editor.putBoolean(WOODWORKING, gameField.woodworkingIsHere);
        editor.putInt(WOOD, gameField.woodenplank);
        editor.putInt(TREE, gameField.trees);
        editor.putInt(HOUSE_COUNT, gameField.currentCountOfHouses);
        editor.putInt(VILLAGERS_IN_HOUSE1, gameField.villagers.get(0));
        editor.putInt(VILLAGERS_IN_HOUSE2, gameField.villagers.get(1));
        editor.putInt(VILLAGERS_IN_HOUSE3, gameField.villagers.get(2));
        editor.commit();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() != View.NO_ID) {
            soundPool.play(soundIdButtonClick, 1, 1, 0, 0, 1);
            if (v.getId() == gameField.woodworking.getId() && gameField.woodworkingTimeText.getText().equals("")) {
                woodworking.show(getFragmentManager(), woodworking.getClass().getName());
            }

            if (v.getId() == gameField.shop.getId()) {
                if (gameField.currentCountOfHouses < 3) {
                    if (gameField.materialHandingTimeText.getText().equals("") && gameField.buildWoodworkingTimeText.getText().equals("")) {
                        materialHandling.show(getFragmentManager(), materialHandling.getClass().getName());
                    }
                } else {
                    Toast.makeText(this, "You've already bought all the houses!", Toast.LENGTH_LONG).show();
                }
            }

            if (v.getId() == gameField.house1.getId() && gameField.currentCountOfHouses >= 1
                    && gameField.improveTimeText.getText().equals("")) {
                if (gameField.conditions.get(0) < 3) {
                    gameField.setIndex(0);
                    if (gameField.improveTimeText.getText().equals("")) {
                        improvement.show(getFragmentManager(), improvement.getClass().getName());
                    } else if (gameField.improveTimeText.getText().equals("")) {
                        gameField.improveTimeText.setText("");
                    }
                } else if (gameField.villagers.get(0) < 10) {
                    gameField.setIndexForVillagers(0);
                    settlePeasant.show(getFragmentManager(), settlePeasant.getClass().getName());
                } else if (gameField.villagers.get(0) >= 10) {
                    Toast.makeText(this, "You can't settle in the house more than 10 gnomes!!!", Toast.LENGTH_LONG).show();
                }
            }

            if (v.getId() == gameField.house2.getId() && gameField.currentCountOfHouses >= 2
                    && gameField.improveTimeText.getText().equals("")) {
                if (gameField.conditions.get(1) < 3) {
                    gameField.setIndex(1);
                    if (gameField.improveTimeText.getText().equals("")) {
                        improvement.show(getFragmentManager(), improvement.getClass().getName());
                    } else if (gameField.improveTimeText.getText().equals("00 : 00")) {
                        gameField.improveTimeText.setText("");
                    }
                } else if (gameField.villagers.get(1) < 10) {
                    gameField.setIndexForVillagers(1);
                    settlePeasant.show(getFragmentManager(), settlePeasant.getClass().getName());
                } else if (gameField.villagers.get(1) >= 10) {
                    Toast.makeText(this, "You can't settle in the house more than 10 gnomes!!!", Toast.LENGTH_LONG).show();
                }
            }

            if (v.getId() == gameField.house3.getId() && gameField.currentCountOfHouses >= 3
                    && gameField.improveTimeText.getText().equals("")) {
                if (gameField.conditions.get(2) < 3) {
                    gameField.setIndex(2);
                    if (gameField.improveTimeText.getText().equals("")) {
                        improvement.show(getFragmentManager(), improvement.getClass().getName());
                    } else if (gameField.improveTimeText.getText().equals("00 : 00")) {
                        gameField.improveTimeText.setText("");
                    }
                } else if (gameField.villagers.get(2) < 10) {
                    gameField.setIndexForVillagers(2);
                    settlePeasant.show(getFragmentManager(), settlePeasant.getClass().getName());
                } else if (gameField.villagers.get(2) >= 10) {
                    Toast.makeText(this, "You can't settle in the house more than 10 gnomes!!!", Toast.LENGTH_LONG).show();
                }
            }

            if (v.getId() == gameField.forest.getId()) {
                if (gameField.fellingOfTreesTimeText.getText().equals("")) {
                    fellingOfTrees.show(getFragmentManager(), fellingOfTrees.getClass().getName());
                }
            }

            if (v.getId() == gameField.arrowBack.getId()) {
                Intent intent = new Intent();
                intent.putExtra("Total number of peasants",
                        gameField.villagers.get(0) + gameField.villagers.get(1) + gameField.villagers.get(2));
                intent.putExtra("Bread", gameField.bread);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onClickOnHouseImprovement() {
        if (gameField.woodenplank - 30 >= 0) {
            gameField.woodenplank = gameField.woodenplank - 30;
            gameField.improveTime.start();
        } else {
            Toast.makeText(this, "You can't improve this house." + "\n" + "You haven't enough resources!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickOnMaterialHandling(String string) {
        if (string == "WOODWORKING") {
            if (gameField.trees - 30 >= 0 && !gameField.woodworkingIsHere) {
                gameField.trees = gameField.trees - 30;
                gameField.treeCount.setText(gameField.trees.toString());
                gameField.buildWoodworkingTime.start();
            } else {
                Toast.makeText(this, "You can't build the woodworking!" + "\n" +
                        "You haven't enough trees!!!", Toast.LENGTH_LONG).show();
            }
        } else if (gameField.currentCountOfHouses < 3) {
            if (gameField.materialHandingTimeText.getText().equals("")) {
                if (gameField.woodenplank - 60 >= 0) {
                    gameField.addHouse();
                    gameField.woodenplank = gameField.woodenplank - 60;
                    gameField.woodCount.setText(gameField.woodenplank.toString());
                    gameField.materialHandingTimer.start();
                } else {
                    Toast.makeText(this, "You can't build a house!" + "\n" +
                            "You haven't enough wooden plank!!!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onClickOnWoodworking() {
        if (gameField.trees - 10 >= 0) {
            gameField.trees = gameField.trees - 10;
            gameField.treeCount.setText(gameField.trees.toString());
            gameField.woodworkingTime.start();
        } else {
            Toast.makeText(this, "You can't come to the tree processing this house." + "\n" +
                    "You haven't enough resources!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickOnSettlePeasant() {
        if (gameField.bread - 20 >= 0) {
            gameField.bread -= 20;
            gameField.breadCount.setText(gameField.bread.toString());
            gameField.villagers.set(gameField.indexForVillagers, gameField.villagers.get(gameField.indexForVillagers) + 1);
            for (int i = 0; i < 3; i++) {
                gameField.setText(i);
            }
        }
    }

    @Override
    public void onClickOnFellingOfTrees() {
        if (gameField.fellingOfTreesTimeText.getText().equals("")) {
            gameField.fellingOfTreesTimer.start();
        }
    }

}