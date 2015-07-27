package com.lynx.bblashko.shapechanger;

import android.graphics.Rect;

/**
 * Created by Brett Blashko on 3/12/2015.
 */
public class MapObject {
    protected GameView gameView;

    protected int screenHeight;
    protected int screenWidth;
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected double xSpeed;

    protected int width;
    protected int height;

    protected int radius;

    protected int collisionWidth;
    protected int collisionHeight;

    protected int shape;

    protected boolean dead;

    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;
    protected boolean doubleJump;
    protected boolean doubleJumpReady;
    protected boolean burst;
    protected boolean grounded;

    protected double fallSpeed;
    protected double jumpStart;
    protected double rectJumpStart;
    protected double downSpeed;
    protected double burstStartSpeed;
    protected double burstStopSpeed;
    protected double maxFallSpeed;

    protected boolean groundBlock;

    protected double ytemp;
    protected double xtemp;
    protected double xdest;
    protected double ydest;

    protected boolean bottom;
    protected boolean right;
    protected boolean left;
    protected boolean once;

    protected Rect playerBounds;
    protected Rect bottomBounds;
    protected Rect rightBounds;
    protected Rect topBounds;

    public MapObject(GameView gameView){
        this.gameView = gameView;
        screenHeight = gameView.getScreenHeight();
        screenWidth = gameView.getScreenWidth();
    }
    public boolean intersects(MapObject o){
        Rect r1 = getRectangle();
        Rect r2 = o.getRectangle();
        return r1.intersect(r2);
    }
    public boolean intersectsPlayerBottom(MapObject o){
        Rect r2 = o.getRectangle();
        return bottomBounds.intersect(r2);
    }
    public boolean intersectsPlayerTop(MapObject o){
        Rect r2 = o.getRectangle();
        return topBounds.intersect(r2);
    }
    public boolean intersectsPlayerRight(MapObject o){
        Rect r2 = o.getRectangle();
        return rightBounds.intersect(r2);
    }
    public boolean intersectsPlayer(MapObject o){
        Rect r2 = o.getCircleRectangle();
        boolean intersection = playerBounds.intersect(r2);
        return intersection;
    }
    public Rect getRectangle(){
        return new Rect((int)(x), (int)(y), (int)x + collisionWidth, (int) y + collisionHeight);
    }
    public Rect getCircleRectangle(){
        return new Rect((int)(x - radius), (int)(y - radius), (int)x + radius, (int) y + radius);
    }
    public void setPlayerBounds(){
        bottomBounds = new  Rect((int)x, (int)y + height- 2, (int)x + collisionWidth - 5,(int) y + height);
        rightBounds = new Rect((int)x + collisionWidth - 2, (int) y+30,(int) x + collisionWidth, (int)y + collisionHeight-30);
        topBounds = new Rect((int)x, (int) y,(int) x + collisionWidth- 5, (int)y + 2);
        playerBounds = new Rect((int)x, (int) y,(int) x + collisionWidth,(int) y + collisionHeight);
    }
    public boolean checkTokens(MapObject o){
        setPlayerBounds();
        return intersectsPlayer(o);
    }
    public void checkCollisions(MapObject o){

        xtemp = x;
        ytemp = y;
        //bottom collisions
        xtemp += dx;
        ytemp += dy;

        setPosition(xtemp, ytemp);
        setPlayerBounds();
        if(intersectsPlayerTop(o)){
            dy = 0;
            ytemp = o.gety() + o.getHeight();
            if(y < screenHeight - 96 - height)
                falling = true;

//            groundBlock = true;
        }
//        else{
//            groundBlock = false;
//        }
        if(intersectsPlayerBottom(o)){
            dy = 0;
            ytemp = o.gety() - height;
            falling = false;
            down = false;
            jumping = false;
            doubleJump = false;
            once = true;
           // groundBlock = true;
        }else {
            if(y < screenHeight - 96 - height-1)
                falling = true;

            //groundBlock = false;
        }

        if(intersectsPlayerRight(o)){
            setPosition(xtemp, ytemp);
            gameView.calculateTime();
            dead = true;
            gameView.setDeath(true);

        }
        setPosition(xtemp, ytemp);
    }
    public int getx() { return (int)x; }
    public int gety() { return (int)y; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCWidth() { return collisionWidth; }
    public int getCHeight() { return collisionHeight; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }




}
