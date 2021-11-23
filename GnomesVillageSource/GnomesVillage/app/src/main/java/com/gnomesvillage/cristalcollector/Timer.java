package com.gnomesvillage.cristalcollector;

import android.os.CountDownTimer;
import android.view.View;


public class Timer extends CountDownTimer {
    Gnome gnome;
    Points points;
    Tree tree;
    View view;

    public Timer(long millisInFuture, long countDownInterval, View view, Gnome gnome, Points points, Tree tree) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.gnome = gnome;
        this.points = points;
        this.tree = tree;
    }

    @Override
    public void onTick(long l) {
        view.invalidate();
        gnome.move();
        gnome.changeBitmap();
        if (gnome.getRectForPoints().intersect(points.getRect())) {
            points.move(tree.x, tree.y);
            points.count++;
        }
        if (tree.isCreated) {
            for (int i = 0; i < tree.x.size(); i++) {
                for (int j = 0; j < tree.y.size(); j++) {
                    if (gnome.getRect().intersect(tree.getRect(i, j))) {
                        gnome.wasKilled = true;
                    }
                }

            }
        }
        if (gnome.wasKilled()) {
            gnome.wasKilled = true;
        }
    }

    @Override
    public void onFinish() {
    }

}