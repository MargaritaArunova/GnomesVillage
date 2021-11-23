package com.gnomesvillage.jampinggnome;

import android.os.CountDownTimer;
import android.view.View;


public class Timer extends CountDownTimer {

    View view;
    Gnome gnome;
    Island island;
    Cristal cristal;

    public Timer(long millisInFuture, long countDownInterval, View view, Gnome gnome, Island island, Cristal cristal) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.gnome = gnome;
        this.island = island;
        this.cristal = cristal;
    }

    @Override
    public void onTick(long l) {
        view.invalidate();
        if (island.isCreated && cristal.isCreated) {
            island.moveIslands();
            cristal.moveCristals(island.x, island.island.getWidth());
            for (int i = 0; i < island.islands.size() / 2 + 1; i++) {
                if (gnome.getRectForIslands().intersect(island.getRect(i)) && !island.wasIntersect.get(i)) {
                    island.wasIntersect.set(i, true);
                    gnome.hasIntersect++;
                }
            }
            for (int i = 0; i < cristal.y.size() / 2 + 2; i++) {
                if (gnome.getRectForCristal().intersect(cristal.getRect(i)) && !cristal.wasIntersect.get(i)) {
                    cristal.countOfCristals++;
                    cristal.wasIntersect.set(i, true);
                }
            }
        }
        gnome.beyondCanvas();
        if (gnome.wasKilled()) {
            cancel();
        }
    }

    @Override
    public void onFinish() {

    }

}