package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Brett Blashko on 5/4/2015.
 */
public class Token extends MapObject {

    private Paint paint;

    long startTime = System.nanoTime();
    long currentTime;
    long elapsed;

    public Token(GameView gameView, double x, double y, double xSpeed){
        super(gameView);

        this.gameView = gameView;
        this.y = y;
        this.x = x;
        this.xSpeed = xSpeed;

        radius = 50;

        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }
    public void update(){
        x -= xSpeed;
    }
    public void onDraw(Canvas canvas){
        canvas.drawCircle((int)x, (int)y, radius, paint);

    }
}
