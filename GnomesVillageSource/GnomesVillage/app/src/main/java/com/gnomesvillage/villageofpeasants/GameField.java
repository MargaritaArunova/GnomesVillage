package com.gnomesvillage.villageofpeasants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gnomesvillage.R;

import java.util.ArrayList;

public class GameField extends RelativeLayout {

    protected ArrayList<Integer> conditions, villagers;
    protected ImageView house1, house2, house3, shop, woodworking, forest, woodView, treesView, breadsView, arrowBack;
    protected Bitmap house, ruin1, ruin2, forestBitmap;
    protected Integer width, height, currentCountOfHouses = 0;
    protected Integer woodenplank = 0, trees = 0, bread;
    protected Boolean woodworkingIsHere = false;
    protected CountDownTimer improveTime, materialHandingTimer, woodworkingTime, buildWoodworkingTime, fellingOfTreesTimer;
    protected TextView improveTimeText, materialHandingTimeText, woodworkingTimeText, buildWoodworkingTimeText, fellingOfTreesTimeText,
            woodCount, treeCount, breadCount, villagers1, villagers2, villagers3;
    protected Typeface typeface;
    protected int i, indexForVillagers;

    public GameField(final Context context) {
        super(context);

        typeface = Typeface.createFromAsset(context.getAssets(), "fonts-bold.ttf");

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        setLayoutParams(new ViewGroup.LayoutParams(layoutParams));

        conditions = new ArrayList<>();
        conditions.add(-1);
        conditions.add(-1);
        conditions.add(-1);
        villagers = new ArrayList<>();
        villagers.add(0);
        villagers.add(0);
        villagers.add(0);

        woodworking = new ImageView(context);
        shop = new ImageView(context);
        house2 = new ImageView(context);
        house3 = new ImageView(context);
        house1 = new ImageView(context);
        forest = new ImageView(context);
        woodView = new ImageView(context);
        treesView = new ImageView(context);
        breadsView = new ImageView(context);
        arrowBack = new ImageView(context);

        materialHandingTimeText = new TextView(context);
        improveTimeText = new TextView(context);
        woodworkingTimeText = new TextView(context);
        buildWoodworkingTimeText = new TextView(context);
        fellingOfTreesTimeText = new TextView(context);

        woodCount = new TextView(context);
        treeCount = new TextView(context);
        breadCount = new TextView(context);
        villagers1 = new TextView(context);
        villagers2 = new TextView(context);
        villagers3 = new TextView(context);

        materialHandingTimeText.setText("");
        improveTimeText.setText("");
        woodworkingTimeText.setText("");
        buildWoodworkingTimeText.setText("");
        fellingOfTreesTimeText.setText("");

        createCoordinates();

        improveTime = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                improveTimeText.setY(0);
                if (i == 0) {
                    improveTimeText.setX(width / 5 - (int) (height / 3.5) + height / 63);
                } else if (i == 1) {
                    improveTimeText.setX(width / 2 - (int) (height / 3.5) / 2 + height / 63);
                } else if (i == 2) {
                    improveTimeText.setX(width / 5 * 4 + height / 63);
                }
                improveTimeText.setText(toStringTime((int) millisUntilFinished - 1000));
                if (improveTimeText.getText().equals("00 : 00")) {
                    improveTimeText.setText("");
                    if (conditions.get(i) == -1) {
                        conditions.set(i, 0);
                    }
                    if (conditions.get(i) == 0) {
                        if (i == 0) {
                            house1.setImageBitmap(ruin1);
                            conditions.set(i, 1);
                        } else if (i == 1) {
                            house2.setImageBitmap(ruin1);
                            conditions.set(i, 1);
                        } else if (i == 2) {
                            house3.setImageBitmap(ruin1);
                            conditions.set(i, 1);
                        }
                    }
                    if (i == 0) {
                        if (conditions.get(0) < 3) {
                            if (conditions.get(i) == 1) {
                                house1.setImageBitmap(ruin2);
                                conditions.set(i, 2);
                            } else if (conditions.get(i) == 2) {
                                house1.setImageBitmap(house);
                                conditions.set(i, 3);
                                if (villagers1.getParent() != null) {
                                    removeView(villagers1);
                                }
                                addView(villagers1);
                            }
                        }
                    } else if (i == 1) {
                        if (conditions.get(1) < 3) {
                            if (conditions.get(i) == 1) {
                                house2.setImageBitmap(ruin2);
                                conditions.set(i, 2);
                            } else if (conditions.get(i) == 2) {
                                house2.setImageBitmap(house);
                                conditions.set(i, 3);
                                if (villagers2.getParent() != null) {
                                    removeView(villagers2);
                                }
                                addView(villagers2);
                            }
                        }
                    } else if (i == 2) {
                        if (conditions.get(2) < 3) {
                            if (conditions.get(i) == 1) {
                                house3.setImageBitmap(ruin2);
                                conditions.set(i, 2);
                            } else if (conditions.get(i) == 2) {
                                house3.setImageBitmap(house);
                                conditions.set(i, 3);
                                if (villagers3.getParent() != null) {
                                    removeView(villagers3);
                                }
                                addView(villagers3);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };

        materialHandingTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                materialHandingTimeText.setText(toStringTime((int) millisUntilFinished - 1000));
                if (materialHandingTimeText.getText().equals("00 : 00")) {
                    materialHandingTimeText.setText("");
                    if (currentCountOfHouses == 1) {
                        if (house1.getParent() != null) {
                            removeView(house1);
                        }
                        if (conditions.get(0) == -1) {
                            conditions.set(0, 0);
                        }
                        addView(house1);
                    } else if (currentCountOfHouses == 2) {
                        if (house2.getParent() != null) {
                            removeView(house2);
                        }
                        if (conditions.get(1) == -1) {
                            conditions.set(1, 0);
                        }
                        addView(house2);
                    } else if (currentCountOfHouses == 3) {
                        if (house3.getParent() != null) {
                            removeView(house3);
                        }
                        if (conditions.get(2) == -1) {
                            conditions.set(2, 0);
                        }
                        addView(house3);
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };

        woodworkingTime = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                woodworkingTimeText.setText(toStringTime((int) millisUntilFinished - 1000));
                if (woodworkingTimeText.getText().equals("00 : 00")) {
                    woodworkingTimeText.setText("");
                    woodenplank = woodenplank + 30;
                    woodCount.setText(woodenplank.toString());
                }
            }

            @Override
            public void onFinish() {

            }
        };

        buildWoodworkingTime = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                buildWoodworkingTimeText.setX(width / 2 - height / 10);
                buildWoodworkingTimeText.setY(height / 2 - height / 14);
                buildWoodworkingTimeText.setText(toStringTime((int) millisUntilFinished - 1000));
                if (buildWoodworkingTimeText.getText().equals("00 : 00")) {
                    buildWoodworkingTimeText.setText("");
                    addView(woodworking);
                    woodworkingIsHere = true;
                }

            }

            @Override
            public void onFinish() {

            }
        };

        fellingOfTreesTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                fellingOfTreesTimeText.setText(toStringTime((int) (millisUntilFinished) - 1000));
                if (fellingOfTreesTimeText.getText().equals("00 : 00")) {
                    fellingOfTreesTimeText.setText("");
                    trees += 20;
                    treeCount.setText(trees.toString());
                }
            }

            @Override
            public void onFinish() {

            }
        };

    }

    public void createCoordinates() {
        forestBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.forest),
                (int) (width / 2.6), (int) (width / 2.6), false);

        house = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.house),
                (int) (height / 4.2), (int) (height / 4.2), false);

        ruin1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ruin1),
                (int) (height / 4.2), (int) (height / 4.2), false);

        ruin2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ruin2),
                (int) (height / 4.2), (int) (height / 4.2), false);

        shop.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shop),
                (int) (height / 3.2), (int) (height / 3.2), false));

        woodworking.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.woodworking),
                (int) (height / 2.6), (int) (height / 2.6), false));

        breadsView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bread),
                height / 20, height / 20, false));

        woodView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wood),
                height / 20, height / 20, false));

        treesView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tree),
                height / 20, height / 20, false));

        arrowBack.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrowbackflip),
                height / 3, height / 9, false));

        arrowBack.setX(width - height / 3);
        arrowBack.setY(height - height / 9 - height / 15);
        arrowBack.setId(View.generateViewId());

        house1.setImageBitmap(ruin1);
        house1.setId(View.generateViewId());
        house1.setX(width / 5 - (int) (height / 3.5));
        house1.setY(height / 20);

        house2.setImageBitmap(ruin1);
        house2.setId(View.generateViewId());
        house2.setX(width / 2 - (int) (height / 3.5) / 2);
        house2.setY(height / 20);

        house3.setImageBitmap(ruin1);
        house3.setId(View.generateViewId());
        house3.setX(width / 5 * 4);
        house3.setY(height / 20);

        shop.setId(View.generateViewId());
        shop.setX(width / 2 - height / 7);
        shop.setY(height / 2);

        woodworking.setId(View.generateViewId());
        woodworking.setX(width / 2 + (int) (height / 3.5));
        woodworking.setY(height / 2 - height / 30);

        forest.setId(View.generateViewId());
        forest.setImageBitmap(forestBitmap);
        forest.setX(0);
        forest.setY(height - width / 3 - height / 12);

        breadsView.setX(width / 2 - height / 20);
        breadsView.setY(height - height / 20 * 3 - height / 40);

        woodView.setX(width / 2 - height / 20);
        woodView.setY(height - height / 20 - height / 80);

        treesView.setX(width / 2 - height / 20);
        treesView.setY(height - height / 20 * 2 - height / 60);

        villagers1.setTypeface(typeface);
        villagers1.setTextSize(height / 21);
        villagers1.setX(width / 5 - (int) (height / 3.5) + height / 49);
        villagers1.setY(height / 20 + (int) (height / 4.2));
        villagers1.setTextColor(Color.BLACK);

        villagers2.setTypeface(typeface);
        villagers2.setTextSize(height / 21);
        villagers2.setX(width / 2 - (int) (height / 3.5) / 2 + height / 49);
        villagers2.setY(height / 20 + (int) (height / 4.2));
        villagers2.setTextColor(Color.BLACK);

        villagers3.setTypeface(typeface);
        villagers3.setTextSize(height / 21);
        villagers3.setX(width / 5 * 4 + height / 49);
        villagers3.setY(height / 20 + (int) (height / 4.2));
        villagers3.setTextColor(Color.BLACK);

        woodCount.setTypeface(typeface);
        woodCount.setTextSize(height / 21);
        woodCount.setX(width / 2);
        woodCount.setY(height - height / 20 - height / 40);
        woodCount.setTextColor(Color.BLACK);

        treeCount.setTypeface(typeface);
        treeCount.setTextSize(height / 21);
        treeCount.setX(width / 2);
        treeCount.setY(height - height / 20 * 2 - height / 35);
        treeCount.setTextColor(Color.BLACK);

        breadCount.setTypeface(typeface);
        breadCount.setTextSize(height / 21);
        breadCount.setX(width / 2);
        breadCount.setY(height - height / 20 * 3 - height / 30);
        breadCount.setTextColor(Color.BLACK);

        materialHandingTimeText.setTypeface(typeface);
        materialHandingTimeText.setTextSize(height / 21);
        materialHandingTimeText.setTextColor(Color.BLACK);
        materialHandingTimeText.setText("");
        materialHandingTimeText.setX(width / 2 - height / 10);
        materialHandingTimeText.setY(height / 2 - height / 14);

        improveTimeText.setTypeface(typeface);
        improveTimeText.setTextSize(height / 21);
        improveTimeText.setTextColor(Color.BLACK);
        improveTimeText.setText("");

        buildWoodworkingTimeText.setTypeface(typeface);
        buildWoodworkingTimeText.setTextSize(height / 21);
        buildWoodworkingTimeText.setTextColor(Color.BLACK);
        buildWoodworkingTimeText.setText("");

        woodworkingTimeText.setTypeface(typeface);
        woodworkingTimeText.setTextSize(height / 21);
        woodworkingTimeText.setTextColor(Color.BLACK);
        woodworkingTimeText.setText("");
        woodworkingTimeText.setX(width / 2 + (int) (height / 3.5) + height / 21);
        woodworkingTimeText.setY(height / 2 - height / 21);

        fellingOfTreesTimeText.setTypeface(typeface);
        fellingOfTreesTimeText.setTextSize(height / 21);
        fellingOfTreesTimeText.setX(height / 40);
        fellingOfTreesTimeText.setY(height/2 - height / 21);
        fellingOfTreesTimeText.setTextColor(Color.BLACK);

        setBackgroundResource(R.drawable.background);

        addView(buildWoodworkingTimeText);
        addView(woodworkingTimeText);
        addView(improveTimeText);
        addView(materialHandingTimeText);
        addView(fellingOfTreesTimeText);
        addView(woodCount);
        addView(treeCount);
        addView(breadCount);
        addView(breadsView);
        addView(woodView);
        addView(treesView);
        addView(shop);
        addView(forest);
        addView(arrowBack);
    }

    public void setBitmaps(Integer i) {
        if (i == 0) {
            if (conditions.get(i) == 0) {
                house1.setImageBitmap(ruin1);
            } else if (conditions.get(i) == 1) {
                house1.setImageBitmap(ruin2);
                conditions.set(i, 2);
            } else if (conditions.get(i) >= 2) {
                house1.setImageBitmap(house);
                conditions.set(i, 3);
            }
            if (conditions.get(i) == 0) {
                conditions.set(i, 1);
            }
        } else if (i == 1) {
            if (conditions.get(i) == 0) {
                house2.setImageBitmap(ruin1);
            } else if (conditions.get(i) == 1) {
                house2.setImageBitmap(ruin2);
                conditions.set(i, 2);
            } else if (conditions.get(i) >= 2) {
                house2.setImageBitmap(house);
                conditions.set(i, 3);
            }
            if (conditions.get(i) == 0) {
                conditions.set(i, 1);
            }
        } else if (i == 2) {
            if (conditions.get(i) == 0) {
                house3.setImageBitmap(ruin1);
            } else if (conditions.get(i) == 1) {
                house3.setImageBitmap(ruin2);
                conditions.set(i, 2);
            } else if (conditions.get(i) >= 2) {
                house3.setImageBitmap(house);
                conditions.set(i, 3);
            }
            if (conditions.get(i) == 0) {
                conditions.set(i, 1);
            }
        }
        if (conditions.get(0) != -1 || conditions.get(1) != -1 || conditions.get(2) != -1 || woodworkingIsHere) {
            if (woodworking.getParent() != null) {
                removeView(woodworking);
            }
            addView(woodworking);
            woodworkingIsHere = true;
        }
        if (conditions.get(0) > -1) {
            if (house1.getParent() != null) {
                removeView(house1);
            }
            addView(house1);
            if (conditions.get(0) >= 3) {
                if (villagers1.getParent() != null) {
                    removeView(villagers1);
                }
                addView(villagers1);
            }
        }
        if (conditions.get(1) > -1) {
            if (house2.getParent() != null) {
                removeView(house2);
            }
            addView(house2);
            if (conditions.get(1) >= 3) {
                if (villagers2.getParent() != null) {
                    removeView(villagers2);
                }
                addView(villagers2);
            }
        }
        if (conditions.get(2) > -1) {
            if (house3.getParent() != null) {
                removeView(house3);
            }
            addView(house3);
            if (conditions.get(2) >= 3) {
                if (villagers3.getParent() != null) {
                    removeView(villagers3);
                }
                addView(villagers3);
            }
        }
    }

    public void setText(Integer index) {
        if (index == 0) {
            villagers1.setText(villagers.get(index).toString() + " / 10");
        }
        if (index == 1) {
            villagers2.setText(villagers.get(index).toString() + " / 10");
        }
        if (index == 2) {
            villagers3.setText(villagers.get(index).toString() + " / 10");
        }
    }

    public void addHouse() {
        currentCountOfHouses++;
    }

    public void setIndex(int index) {
        i = index;
    }

    public void setIndexForVillagers(int index) {
        indexForVillagers = index;
    }

    public String toStringTime(int time) {
        String stringSeconds, stringMinutes;
        int minutes = time / 60000;
        int seconds = (time - minutes * 60000) / 1000;
        stringMinutes = "0" + String.valueOf(minutes);
        if (seconds < 10) {
            stringSeconds = "0" + String.valueOf(seconds);
        } else stringSeconds = String.valueOf(seconds);

        return stringMinutes + " : " + stringSeconds;
    }

}