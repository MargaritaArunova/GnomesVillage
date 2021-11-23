package com.gnomesvillage.orcland.fight;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import com.gnomesvillage.R;

import java.util.ArrayList;

public class Gnome extends AsyncTask {
    protected Bitmap gnome, preview, start, bullet, gnomeHead, orcHead, fireButton;
    protected SurfaceHolder surfaceHolder;
    protected Typeface typeface;
    protected Integer width, height, y, x, dy = -1, bulletX, bulletY, bulletEndingX, gnomeHP = 20;
    protected volatile boolean running = true;
    protected boolean canStart = false, canDrawBullet = false, bulletIsGenerated = false, canDrawGnome = true;
    protected ArrayList<Integer> spacing;
    protected Orcs orcs;

    public Gnome(Resources resources, SurfaceHolder surfaceHolder, Typeface typeface) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        orcs = new Orcs(resources);
        spacing = new ArrayList<>();

        this.surfaceHolder = surfaceHolder;
        this.typeface = typeface;

        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        gnomeHead = BitmapFactory.decodeResource(resources, R.drawable.collectorgnome);
        gnomeHead = Bitmap.createBitmap(gnomeHead, 0, 0, gnomeHead.getWidth(), gnomeHead.getHeight(), matrix, false);
        gnomeHead = Bitmap.createScaledBitmap(gnomeHead, height / 10, height / 10, false);

        gnome = BitmapFactory.decodeResource(resources, R.drawable.gnomesprite);
        gnome = Bitmap.createBitmap(gnome, gnome.getWidth() / 4, 0, gnome.getWidth() / 4, gnome.getHeight());
        gnome = Bitmap.createScaledBitmap(gnome, height / 5, height / 5, false);

        fireButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.firebutton), width / 6, width / 9, false);
        orcHead = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.orchead), height / 10, height / 10, false);
        bullet = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.gnomebullet), height / 9, height / 18, false);
        preview = BitmapFactory.decodeResource(resources, R.drawable.fightstart);
        start = BitmapFactory.decodeResource(resources, R.drawable.start);

        bulletX = width / 5;
        bulletY = y;
        bulletEndingX = width - height / 9 * 2;
        y = height / 2 - gnome.getWidth() / 2;
        x = width / 5;
    }


    public void requestStop() {
        running = false;
        cancel(true);
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setTypeface(typeface);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                if (!canStart) {
                    try {
                        preview = Bitmap.createScaledBitmap(preview, canvas.getWidth(), canvas.getHeight(), false);
                        start = Bitmap.createScaledBitmap(start, canvas.getWidth() / 5 * 3, canvas.getWidth() / 5, false);
                        canvas.drawBitmap(preview, 0, 0, new Paint());
                        canvas.drawBitmap(start, canvas.getWidth() / 2 - start.getWidth() / 2,
                                canvas.getHeight() / 5 * 4 - start.getHeight() / 2, new Paint());
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                } else {
                    try {
                        canvas.drawARGB(255, 141, 177, 133);
                        //for orc
                        if (orcs.orcHP.get(0) > 0) {
                            if (orcs.bulletCoordinates.get(0).canDrawing) {
                                canvas.drawBitmap(orcs.orcBullet, orcs.bulletCoordinates.get(0).x, orcs.bulletCoordinates.get(0).y, paint);
                            }
                        } else {
                            orcs.bulletCoordinates.get(0).setCanDrawing(false);
                        }
                        if (orcs.orcHP.get(1) > 0) {
                            if (orcs.bulletCoordinates.get(1).canDrawing) {
                                canvas.drawBitmap(orcs.orcBullet, orcs.bulletCoordinates.get(1).x, orcs.bulletCoordinates.get(1).y, paint);
                            }
                        } else {
                            orcs.bulletCoordinates.get(1).setCanDrawing(false);
                        }
                        if (orcs.orcHP.get(2) > 0) {
                            if (orcs.bulletCoordinates.get(2).canDrawing) {
                                canvas.drawBitmap(orcs.orcBullet, orcs.bulletCoordinates.get(2).x, orcs.bulletCoordinates.get(2).y, paint);
                            }
                        } else {
                            orcs.bulletCoordinates.get(2).setCanDrawing(false);
                        }
                        for (int i = 0; i < 3; i++) {
                            if (orcs.orcHP.get(i) > 0) {
                                canvas.drawBitmap(orcs.orc, width - height / 3, height / 3 * i, paint);
                            }
                        }
                        //
                        if (orcs.orcHP.get(0) <= 0 && orcs.orcHP.get(1) <= 0 && orcs.orcHP.get(2) <= 0) {
                            moveGnomeInEnd();
                        }
                        if (x >= width) {
                            requestStop();
                        }
                        if (gnomeHP <= 0) {
                            requestStop();
                        }
                        if (dy != -1) {
                            if (y != dy - gnome.getWidth()) {
                                moveGnome();
                                if (!canDrawBullet) {
                                    bulletY = y;
                                }
                            }
                        }
                        if (canDrawBullet) {
                            moveBullet();
                        }
                        if (!orcs.bulletCoordinates.get(0).canDrawing && !orcs.bulletCoordinates.get(1).canDrawing
                                && !orcs.bulletCoordinates.get(2).canDrawing) {
                            orcs.bulletIsGenerated = false;

                        }
                        if (!orcs.bulletIsGenerated) {
                            orcs.motionVector(width / 5, y, 0);
                            orcs.motionVector(width / 5, y, 1);
                            orcs.motionVector(width / 5, y, 2);
                        }
                        if (orcs.bulletIsGenerated) {
                            if (orcs.bulletCoordinates.get(0).canDrawing || orcs.bulletCoordinates.get(1).canDrawing ||
                                    orcs.bulletCoordinates.get(2).canDrawing) {
                                orcs.moveOrcBullet(0);
                                orcs.moveOrcBullet(1);
                                orcs.moveOrcBullet(2);
                            }
                        }
                        for (int i = 0; i < 3; i++) {
                            if (getGnomeRect().intersect(orcs.getBulletRect(i)) && orcs.bulletCoordinates.get(i).canDrawing) {
                                orcs.bulletCoordinates.get(i).setCanDrawing(false);
                                orcs.bulletCoordinates.get(i).setX(width - height / 3);
                                orcs.bulletCoordinates.get(i).setY(height / 3 * i + height / 6);
                                gnomeHP--;
                            }
                            if (getBulletRect().intersect(orcs.getOrcRect(i)) && canDrawBullet) {
                                canDrawBullet = false;
                                bulletIsGenerated = false;
                                bulletX = width / 5;
                                orcs.orcHP.set(i, orcs.orcHP.get(i) - 1);
                            }
                        }


                        //for gnome
                        if (bulletX == null || bulletY == null) {
                            bulletX = width / 5;
                            bulletY = y + gnome.getWidth() / 2 - bullet.getWidth() / 2;
                        }
                        canvas.drawBitmap(gnome, x, y, paint);
                        if (canDrawBullet) {
                            canvas.drawBitmap(bullet, bulletX, bulletY, paint);
                        }
                        //
                        //texts
                        canvas.drawBitmap(gnomeHead, 0, 0, paint);
                        canvas.drawText(gnomeHP.toString(), gnomeHead.getWidth() + 2, 60, paint);
                        canvas.drawBitmap(orcHead, 0, gnomeHead.getHeight(), paint);
                        canvas.drawText(String.valueOf(orcs.orcHP.get(0) + orcs.orcHP.get(1) + orcs.orcHP.get(2)), orcHead.getWidth() + 2, 120, paint);
                        //
                        //fireButton
                        canvas.drawBitmap(fireButton, 0, height - width / 9, paint);

                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }
        return null;
    }

    public void generateBullet() {
        if (!canDrawBullet) {
            if (y < height / 18 * 5) {
                if (orcs.orcHP.get(0) > 0) {
                    dy = height / 9;
                } else if (orcs.orcHP.get(1) > 0) {
                    dy = height / 9 * 4;
                } else if (orcs.orcHP.get(2) > 0) {
                    dy = height / 9 * 7;
                }
            } else if (y >= height / 18 * 5 && y < height / 18 * 11) {
                if (orcs.orcHP.get(1) > 0) {
                    dy = height / 9 * 4;
                } else if (orcs.orcHP.get(0) > 0) {
                    dy = height / 9;
                } else if (orcs.orcHP.get(2) > 0) {
                    dy = height / 9 * 7;
                }
            } else if (y >= height / 18 * 11) {
                if (orcs.orcHP.get(2) > 0) {
                    dy = height / 9 * 7;
                } else if (orcs.orcHP.get(1) > 0) {
                    dy = height / 9 * 4;
                } else if (orcs.orcHP.get(0) > 0) {
                    dy = height / 9;
                }
            }
            bulletIsGenerated = true;
        }
    }

    public void moveBullet() {
        bulletX += height / 100;
        if (bulletX >= bulletEndingX) {
            canDrawBullet = false;
            bulletIsGenerated = false;
            bulletX = width / 5;
        }
    }

    public void moveGnome() {
        if (y > dy) {
            y -= height / 100;
        } else {
            y += height / 100;
        }
        if (y > dy - height / 100 && y < dy + height / 100) {
            if (!canDrawBullet) {
                bulletY = y + gnome.getWidth() / 2 - bullet.getWidth() / 2;
            }
            dy = -1;
            if (bulletIsGenerated) {
                canDrawBullet = true;
            }
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
    }

    public void moveGnomeInEnd() {
        x += height / 100;
    }

    public Rect getBulletRect() {
        if (bulletX != null && bulletY != null) {
            return new Rect(bulletX, bulletY, bulletX + height / 9, bulletY + height / 18);
        } else {
            return new Rect(0, 0, 0, 0);
        }
    }

    public Rect getGnomeRect() {
        return new Rect(width / 5 + height / 10 - 2, y + height / 25,
                width / 5 + height / 10 + 2, y + height / 5 - height / 25);
    }

}