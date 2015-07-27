package com.lynx.bblashko.shapechanger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.GestureDetector;

/**
 * Created by Brett Blashko on 3/12/2015.
 */
public class Player extends MapObject{
    private GameView gameView;

    private Paint paint;
    private Paint paint2;

    private Path path;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    private Context context;

    private DeathListener deathListener;

    public Player(GameView gameView, Context context, double xSpeed){
        super(gameView);
        this.context = context;
        this.gameView = gameView;
        this.xSpeed = xSpeed;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint2 = new Paint();
        paint2.setColor(Color.BLUE);

        path = new Path();
        screenWidth = gameView.getScreenWidth();
        screenHeight = gameView.getScreenHeight();

        dead = false;

        x = 250;
        y = screenHeight/2;
        width = 100;
        height = 100;
        collisionWidth = 100;
        collisionHeight = 100;

        dx = 0;
        dy = 0;

        shape = 0;

        jumpStart = -1.5;
        rectJumpStart = -.75;
        fallSpeed = .05;
        maxFallSpeed = 1;
        downSpeed = 2.5;
        burstStartSpeed = 3.5;
        burstStopSpeed = -.2;

        jumping = false;
        falling = true;
        doubleJumpReady = false;
        doubleJump = false;
        burst = false;
        once = true;
    }
    public void updatePosition(){
        if(dead){
            Log.d("dead = ", "true");
        }
        if(!burst) {
            if (jumping && !falling) {
                if (shape == 0)
                    dy = jumpStart;
                else
                    dy = rectJumpStart;
                falling = true;
            } else if (falling && doubleJump && once) {
                dy = jumpStart;
                once = false;
            }
            if (falling && fallSpeed < maxFallSpeed) {
                dy += fallSpeed;
            }
            if (down) {
                dy = downSpeed;
            }
            if(!falling && dx < 0){
                if(x <= 250){
                    dx = 0;
                }
                else {
                    x -= xSpeed;
                }
            }
            if(x <= 250){
                x = 250;
            }
        }else{
            if(dx <= 0){
                falling = true;
                burst = false;
            }else{
                dx += burstStopSpeed;
            }
        }


    }
    public void setShape(int shape){
        //Shape 0 = square;
        //Shape 1 = rectangle;
        //Shape 3 = triangle

        this.shape = shape;
        if(shape == 0){
            paint.setColor(Color.RED);
            width = 100;
            height = 100;
            collisionWidth = 100;
            collisionHeight = 100;
        }
        else if(shape == 1){
            paint.setColor(Color.CYAN);
            width = 200;
            height = 50;
            collisionWidth = 200;
            collisionHeight = 50;
        }
        else if(shape == 2){
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            width = 120;
            height = 120;
            collisionWidth = 120;
            collisionHeight = 120;
        }

    }
    public int getShape(){
        return shape;
    }
    public void update(){
        updatePosition();
    }
    public void onDraw(Canvas canvas){
        if(shape == 0 || shape == 1) {
            canvas.drawRect((int) x, (int) y, (int) x + width, (int) y + height, paint);
        }else{
            Point a = new Point((int)x, (int) y);
            Point b = new Point((int) x, (int) y+height);
            Point c = new Point((int) x+width, (int) y+height/2);


            path.reset();
            path.moveTo(a.x, a.y);
            path.lineTo(b.x, b.y);
            path.lineTo(c.x, c.y);
            canvas.drawPath(path, paint);
        }
    }
    public void setJumping(){
        if(!jumping){
            jumping = true;
        }else if(jumping && !doubleJump){
            doubleJump = true;
        }
    }
    public void setDown(){
        down = true;
    }
    public void setBurst(){
        if(x == 250) {
            burst = true;
            dx = burstStartSpeed;
            falling = false;
            dy = 0;
        }
    }

    public boolean getDead(){
        return dead;
    }

}
