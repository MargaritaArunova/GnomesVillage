package com.gnomesvillage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, View.OnSystemUiVisibilityChangeListener,
        MiniGames.MiniGamesFragmentListener, MiniGamesOfCave.MiniGamesOfCaveFragmentListener, MiniGamesOfMill.MiniGamesOfMillFragmentListener,
        Swords.SwordsFragmentListener, MiniGamesOfArableLand.MiniGamesOfArableLandFragmentListener, RoadToOrcLand.RoadToOrcLandFragmentListener {

    SoundPool soundPool;
    GameField gameField;
    Intent jumpingGnome, cristalCollector, skyJump, mine, gnomesWalk, orcLand, villageOfPeasant;
    MiniGames miniGames;
    MiniGamesOfCave miniGamesOfCave;
    MiniGamesOfMill miniGamesOfMill;
    MiniGamesOfArableLand miniGamesOfArableLand;
    RoadToOrcLand roadToOrcLand;
    Swords swords;
    SharedPreferences sharedPreferences;
    final int JUMPING_GNOME_REQUEST = 1;
    final int CRISTAL_COLLECTOR_REQUEST = 2;
    final int SKY_JUMP_REQUEST = 3;
    final int MINE_REQUEST = 4;
    final int PLANT_WHEAT_REQUEST = 5;
    final int GNOMES_WALK_REQUEST = 6;
    final int ORC_LAND = 7;
    final int VILLAGE_OF_PEASANT = 8;
    Integer soundIdButtonClick;

    final String CRISTALS = "CRISTALS", SWORDS = "SWORDS", BREADS = "BREADS", WHEAT = "WHEAT", ARROW = "ARROW", VILLAGERS = "VILLAGERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameField = new GameField(this);
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        gameField.setOnSystemUiVisibilityChangeListener(this);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        gameField.cristals = sharedPreferences.getInt(CRISTALS, 10);
        gameField.swords = sharedPreferences.getInt(SWORDS, 0);
        gameField.breads = sharedPreferences.getInt(BREADS, 0);
        gameField.wheat = sharedPreferences.getInt(WHEAT, 0);
        gameField.cristalText.setText(String.valueOf(gameField.cristals));
        gameField.swordText.setText(String.valueOf(gameField.swords));
        gameField.breadText.setText(String.valueOf(gameField.breads));
        gameField.wheatText.setText(String.valueOf(gameField.wheat));
        if (sharedPreferences.getBoolean(ARROW, false)) {
            gameField.arrowToOrcLand.setImageBitmap(gameField.newArrow);
            gameField.currentArrow = gameField.newArrow;
        }

        setContentView(gameField);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameField.home.setOnClickListener(this);
        gameField.cave.setOnClickListener(this);
        gameField.forge.setOnClickListener(this);
        gameField.mill.setOnClickListener(this);
        gameField.arableLand.setOnClickListener(this);
        gameField.arrowToOrcLand.setOnClickListener(this);
        gameField.arrowToPeasants.setOnClickListener(this);

        miniGames = new MiniGames();
        miniGamesOfCave = new MiniGamesOfCave();
        miniGamesOfMill = new MiniGamesOfMill();
        miniGamesOfArableLand = new MiniGamesOfArableLand();
        roadToOrcLand = new RoadToOrcLand();
        swords = new Swords();

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdButtonClick = soundPool.load(this, R.raw.button_click, 1);

        jumpingGnome = new Intent(this, com.gnomesvillage.jampinggnome.MainActivity.class);
        cristalCollector = new Intent(this, com.gnomesvillage.cristalcollector.MainActivity.class);
        skyJump = new Intent(this, com.gnomesvillage.skyjump.MainActivity.class);
        mine = new Intent(this, com.gnomesvillage.mine.MainActivity.class);
        gnomesWalk = new Intent(this, com.gnomesvillage.gnomeswalk.MainActivity.class);
        orcLand = new Intent(this, com.gnomesvillage.orcland.MainActivity.class);
        villageOfPeasant = new Intent(this, com.gnomesvillage.villageofpeasants.MainActivity.class);

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
        editor.putInt(CRISTALS, gameField.cristals);
        editor.putInt(SWORDS, gameField.swords);
        editor.putInt(BREADS, gameField.breads);
        editor.putInt(WHEAT, gameField.wheat);
        editor.putInt(VILLAGERS, gameField.villagers);
        if (gameField.currentArrow.equals(gameField.newArrow)) {
            editor.putBoolean(ARROW, true);
        } else {
            editor.putBoolean(ARROW, false);
        }
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CRISTALS, gameField.cristals);
        editor.putInt(SWORDS, gameField.swords);
        editor.putInt(BREADS, gameField.breads);
        editor.putInt(WHEAT, gameField.wheat);
        editor.putInt(VILLAGERS, gameField.villagers);
        if (gameField.currentArrow.equals(gameField.newArrow)) {
            editor.putBoolean(ARROW, true);
        } else {
            editor.putBoolean(ARROW, false);
        }
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != View.NO_ID) {
            soundPool.play(soundIdButtonClick, 1, 1, 0, 0, 1);
            if (v.getId() == gameField.arrowToOrcLand.getId()) {
                roadToOrcLand.show(getFragmentManager(), roadToOrcLand.getClass().getName());
            }
            if (v.getId() == gameField.home.getId()) {
                miniGames.show(getFragmentManager(), miniGames.getClass().getName());
            }
            if (v.getId() == gameField.cave.getId()) {
                miniGamesOfCave.show(getFragmentManager(), miniGamesOfCave.getClass().getName());
            }
            if (v.getId() == gameField.forge.getId()) {
                swords.show(getFragmentManager(), swords.getClass().getName());
            }
            if (v.getId() == gameField.mill.getId() && gameField.millTimeText.getText().equals("")) {
                miniGamesOfMill.show(getFragmentManager(), miniGamesOfMill.getClass().getName());
            } else if (v.getId() == gameField.mill.getId() && gameField.millTimeText.getText().equals("Done!")) {
                gameField.millTimeText.setText("");
                gameField.breads += 5;
                gameField.breadText.setText(gameField.breads.toString());


                sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CRISTALS, gameField.cristals);
                editor.putInt(SWORDS, gameField.swords);
                editor.putInt(BREADS, gameField.breads);
                editor.putInt(WHEAT, gameField.wheat);
                editor.putInt(VILLAGERS, gameField.villagers);
                if (gameField.currentArrow.equals(gameField.newArrow)) {
                    editor.putBoolean(ARROW, true);
                } else {
                    editor.putBoolean(ARROW, false);
                }
                editor.commit();
            }
            if (v.getId() == gameField.arableLand.getId() && gameField.arableLandTimeText.getText().equals("")) {
                miniGamesOfArableLand.show(getFragmentManager(), miniGamesOfArableLand.getClass().getName());
            } else if (v.getId() == gameField.arableLand.getId() && gameField.arableLandTimeText.getText().equals("Done!")) {
                gameField.wheat += 1;
                gameField.wheatText.setText(gameField.wheat.toString());
                gameField.arableLandTimeText.setText("");
                gameField.arableLand.setImageBitmap(gameField.firstConditionOfWheat);

                sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(CRISTALS, gameField.cristals);
                editor.putInt(SWORDS, gameField.swords);
                editor.putInt(BREADS, gameField.breads);
                editor.putInt(WHEAT, gameField.wheat);
                editor.putInt(VILLAGERS, gameField.villagers);
                if (gameField.currentArrow.equals(gameField.newArrow)) {
                    editor.putBoolean(ARROW, true);
                } else {
                    editor.putBoolean(ARROW, false);
                }
                editor.commit();
            }
            if (v.getId() == gameField.arrowToPeasants.getId()) {
                villageOfPeasant.putExtra("BREAD", gameField.breads);
                startActivityForResult(villageOfPeasant, VILLAGE_OF_PEASANT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode != ORC_LAND && requestCode != VILLAGE_OF_PEASANT) {
                gameField.cristals += Integer.valueOf(data.getData().toString());
                gameField.cristalText.setText(String.valueOf(gameField.cristals));
            }
            if (requestCode == GNOMES_WALK_REQUEST) {
                if (data.getIntExtra("Came to the orcs", 0) == 1) {
                    startActivityForResult(orcLand, ORC_LAND);
                }
            }
            if (requestCode == ORC_LAND) {
                if (data.getBooleanExtra("Castle is our!!!", false)) {
                    gameField.arrowToOrcLand.setImageBitmap(gameField.newArrow);
                    gameField.currentArrow = gameField.newArrow;
                    if (!sharedPreferences.getBoolean(ARROW, false)) {
                        gameField.swordText.setText(String.valueOf(0));
                        gameField.swords = 0;
                    }
                } else {
                    gameField.swordText.setText(String.valueOf(0));
                    gameField.swords = 0;
                }
            }
            if (requestCode == VILLAGE_OF_PEASANT) {
                gameField.villagers = data.getIntExtra("Total number of peasants", 0);
                gameField.breads = data.getIntExtra("Bread", 0);
                gameField.breadText.setText(gameField.breads.toString());
            }

            sharedPreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(CRISTALS, gameField.cristals);
            editor.putInt(SWORDS, gameField.swords);
            editor.putInt(BREADS, gameField.breads);
            editor.putInt(WHEAT, gameField.wheat);
            editor.putInt(VILLAGERS, gameField.villagers);
            if (gameField.currentArrow.equals(gameField.newArrow)) {
                editor.putBoolean(ARROW, true);
            } else {
                editor.putBoolean(ARROW, false);
            }
            editor.commit();
        }
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        gameField.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onMiniGameClick(Integer value) {
        if (value == JUMPING_GNOME_REQUEST) {
            startActivityForResult(jumpingGnome, JUMPING_GNOME_REQUEST);
        }
        if (value == CRISTAL_COLLECTOR_REQUEST) {
            startActivityForResult(cristalCollector, CRISTAL_COLLECTOR_REQUEST);
        }
    }

    @Override
    public void onMiniGameOfCaveClick(Integer value) {
        if (value == SKY_JUMP_REQUEST) {
            startActivityForResult(skyJump, SKY_JUMP_REQUEST);
        }
        if (value == MINE_REQUEST) {
            startActivityForResult(mine, MINE_REQUEST);
        }
    }

    @Override
    public void OnClickOnSword(Integer value) {
        if (value == 1) {
            if (gameField.cristals - 100 >= 0) {
                gameField.cristals = gameField.cristals - 100;
                gameField.swords += value;
                gameField.swordText.setText(gameField.swords.toString());
                gameField.cristalText.setText(String.valueOf(gameField.cristals));
            } else {
                Toast.makeText(this, "You can't make this sword." + "\n" + "You haven't enough crystals!", Toast.LENGTH_LONG).show();
            }
        } else if (value == 5) {
            if (gameField.cristals - 450 >= 0) {
                gameField.cristals = gameField.cristals - 450;
                gameField.swords += value;
                gameField.swordText.setText(gameField.swords.toString());
                gameField.cristalText.setText(String.valueOf(gameField.cristals));
            } else {
                Toast.makeText(this, "You can't make this sword." + "\n" + "You haven't enough crystals!", Toast.LENGTH_LONG).show();
            }
        } else if (value == 15) {
            if (gameField.cristals - 1300 >= 0) {
                gameField.cristals = gameField.cristals - 1300;
                gameField.swords += value;
                gameField.swordText.setText(gameField.swords.toString());
                gameField.cristalText.setText(String.valueOf(gameField.cristals));
            } else {
                Toast.makeText(this, "You can't make this sword." + "\n" + "You haven't enough crystals!", Toast.LENGTH_LONG).show();
            }
        } else if (value == 25) {
            if (gameField.cristals - 2100 >= 0) {
                gameField.cristals = gameField.cristals - 2100;
                gameField.swords += value;
                gameField.swordText.setText(gameField.swords.toString());
                gameField.cristalText.setText(String.valueOf(gameField.cristals));
            } else {
                Toast.makeText(this, "You can't make this sword." + "\n" + "You haven't enough crystals!", Toast.LENGTH_LONG).show();
            }
        } else if (value == 50) {
            if (gameField.cristals - 4510 >= 0) {
                gameField.cristals = gameField.cristals - 4150;
                gameField.swords += value;
                gameField.swordText.setText(gameField.swords.toString());
                gameField.cristalText.setText(String.valueOf(gameField.cristals));
            } else {
                Toast.makeText(this, "You can't make this sword." + "\n" + "You haven't enough crystals!", Toast.LENGTH_LONG).show();
            }
        }
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CRISTALS, gameField.cristals);
        editor.putInt(SWORDS, gameField.swords);
        editor.putInt(BREADS, gameField.breads);
        editor.putInt(WHEAT, gameField.wheat);
        editor.putInt(VILLAGERS, gameField.villagers);
        if (gameField.currentArrow.equals(gameField.newArrow)) {
            editor.putBoolean(ARROW, true);
        } else {
            editor.putBoolean(ARROW, false);
        }
        editor.commit();
    }

    @Override
    public void onMiniGameOfMillClick(String value) {
        if (value == "Bake") {
            if (gameField.wheat - 1 >= 0) {
                gameField.wheat -= 1;
                gameField.wheatText.setText(gameField.wheat.toString());
                gameField.millTime.start();
            } else {
                Toast.makeText(this, "You can't bake bread." + "\n" + "You haven't enough wheat!", Toast.LENGTH_LONG).show();
            }
        }
        if (value == "Sell") {
            if (gameField.breads > 0) {
                gameField.breads -= 1;
                gameField.breadText.setText(gameField.breads.toString());
                gameField.cristals += 5;
                gameField.cristalText.setText(gameField.cristals.toString());
            } else {
                Toast.makeText(this, "You can't sell bread." + "\n" + "You haven't bread!", Toast.LENGTH_LONG).show();
            }

        }
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CRISTALS, gameField.cristals);
        editor.putInt(SWORDS, gameField.swords);
        editor.putInt(BREADS, gameField.breads);
        editor.putInt(WHEAT, gameField.wheat);
        editor.putInt(VILLAGERS, gameField.villagers);
        if (gameField.currentArrow.equals(gameField.newArrow)) {
            editor.putBoolean(ARROW, true);
        } else {
            editor.putBoolean(ARROW, false);
        }
        editor.commit();
    }

    @Override
    public void onMiniGameOfArableLandClick(Integer value) {
        if (value == PLANT_WHEAT_REQUEST) {
            if (gameField.cristals - 10 >= 0) {
                gameField.cristals -= 10;
                gameField.cristalText.setText(gameField.cristals.toString());
                gameField.arableLandTime.start();
            } else {
                Toast.makeText(this, "You can't plant wheat." + "\n" + "You haven't enough crystals!", Toast.LENGTH_LONG).show();
            }
        }
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CRISTALS, gameField.cristals);
        editor.putInt(SWORDS, gameField.swords);
        editor.putInt(BREADS, gameField.breads);
        editor.putInt(WHEAT, gameField.wheat);
        editor.putInt(VILLAGERS, gameField.villagers);
        if (gameField.currentArrow.equals(gameField.newArrow)) {
            editor.putBoolean(ARROW, true);
        } else {
            editor.putBoolean(ARROW, false);
        }
        editor.commit();
    }

    @Override
    public void onRoadToOrcLandClick(Integer value) {
        if (value == GNOMES_WALK_REQUEST) {
            if (gameField.swords >= 200 && !sharedPreferences.getBoolean(ARROW, false)) {
                if (gameField.villagers >= 30) {
                    startActivityForResult(gnomesWalk, GNOMES_WALK_REQUEST);
                } else {
                    Toast.makeText(this,
                            "You can't go to orcs!" + "\n" + "You haven't enough villagers that will attack orcs", Toast.LENGTH_LONG).show();
                }
            } else if (gameField.swords < 200 && !sharedPreferences.getBoolean(ARROW, false)) {
                Toast.makeText(this,
                        "You can't go to orcs!" + "\n" + "You haven't enough swords" + "\n" + "You need at least 200 swords",
                        Toast.LENGTH_LONG).show();
            } else if (sharedPreferences.getBoolean(ARROW, false)) {
                startActivityForResult(gnomesWalk, GNOMES_WALK_REQUEST);
            }
        }
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CRISTALS, gameField.cristals);
        editor.putInt(SWORDS, gameField.swords);
        editor.putInt(BREADS, gameField.breads);
        editor.putInt(WHEAT, gameField.wheat);
        editor.putInt(VILLAGERS, gameField.villagers);
        if (gameField.currentArrow.equals(gameField.newArrow)) {
            editor.putBoolean(ARROW, true);
        } else {
            editor.putBoolean(ARROW, false);
        }
        editor.commit();
    }

}