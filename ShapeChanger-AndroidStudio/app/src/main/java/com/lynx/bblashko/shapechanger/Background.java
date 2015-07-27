package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

/**
 * Created by Brett Blashko on 2/25/2015.
 */
public class Background {
    private GameView gameView;
    private double[] yCoords;
    private double[] xCoords;
    private double yExtra;
    private boolean odd;

    private double xSpeed;
    private int screenHeight;
    private int screenWidth;

    private Paint paint;
    private Random random;


    public Background(GameView gameView){
        this.gameView = gameView;

        Log.d("widht height", "w = " +screenWidth + " h = " + screenHeight);

        screenWidth = gameView.getScreenWidth();
        screenHeight = gameView.getScreenHeight();

        yCoords = new double [11];
        xCoords = new double [11];
        paint  = new Paint();

        xSpeed = 6;

        random = new Random();

        //

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        paint.setColor(Color.rgb(r, g, b));
        initiateCoords();

    }
    public int randomYCoord(){
        int value;
        if (odd) {
            value = random.nextInt((540 - 175) + 1) + 175;
            odd = false;
        }else {
            value = random.nextInt((905 - 540) + 1) + 540;
            odd = true;
        }
        return value;
    }
    public void initiateCoords(){
        for(int i = 0; i < 11; i++){
            if ((i & 1) == 0 ) {
                yCoords[i] = random.nextInt((540 - 175) + 1) + 175;
            } else {
                yCoords[i] = random.nextInt((905 - 540) + 1) + 540;
            }
            xCoords[i] = i*192 - 1;
        }
//        for(int i = 0; i < 10; i++){
//
//        }

    }
    public void update(){
        for(int i = 0; i < 11; i++){
            xCoords[i] -= xSpeed;
            if(xCoords[i] < -192){
                xCoords[i] = screenWidth;
                yExtra = yCoords[i];
                yCoords[i] = randomYCoord();
            }
        }
    }
    public void onDraw(Canvas canvas){
        for(int i = 0; i < 11; i++) {

            int xnext = i + 1;
            int ynext = i + 1;

            if(xnext >= 11){
                xnext = 0;
            }
            if(ynext >= 11){
                ynext= 0;

            }
            if(xCoords[i] < 0) {
                canvas.drawLine(0, (int)yExtra, (int)xCoords[i], (int)yExtra,paint);
                canvas.drawLine((int)xCoords[i], (int)yExtra, (int)xCoords[i], (int)yCoords[i], paint);
                canvas.drawLine((int)xCoords[i], (int)yCoords[i], (int)xCoords[xnext], (int)yCoords[i], paint);
                canvas.drawLine((int)xCoords[xnext],(int) yCoords[i], (int)xCoords[xnext], (int)yCoords[ynext], paint);
            }
            else if (xCoords[i] >= (screenWidth -192)) {
                canvas.drawLine((int)xCoords[i], (int)yCoords[i], screenWidth, (int)yCoords[i], paint);
                canvas.drawLine((int)xCoords[xnext], (int)yCoords[i], (int)xCoords[xnext], (int)yCoords[ynext], paint);

            }
            else{
                canvas.drawLine((int)xCoords[i], (int)yCoords[i], (int)xCoords[xnext], (int)yCoords[i], paint);
                canvas.drawLine((int)xCoords[xnext],(int) yCoords[i], (int)xCoords[xnext], (int)yCoords[ynext], paint);
            }







        }
    }
}

