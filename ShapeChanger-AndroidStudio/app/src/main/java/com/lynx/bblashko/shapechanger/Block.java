package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Brett Blashko on 3/10/2015.
 */
public class Block extends MapObject{
    private GameView gameView;

    private Rect bounds;
    private Paint paint;
    private Paint paint2;

    public Block(GameView gameView, double speed){
        super(gameView);
        this.gameView = gameView;

        xSpeed = speed;

        width = 96;
        height = 96;
        collisionWidth = 96;
        collisionHeight = 96;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint2 = new Paint();
        paint2.setColor(Color.BLACK);

    }
    public void update(){

        x -= xSpeed;
        if(x < -width){
            x += screenWidth + width;
        }
    }
    public void onDraw(Canvas canvas){
        canvas.drawRect((int) x+1, (int) y+1, (int) x + width-1, (int) y + height-1, paint2);
        canvas.drawLine((int)x, (int)y, (int)x + width, (int)y, paint);
        canvas.drawLine((int)x + width, (int)y, (int)x + width, (int)y + height, paint);
        canvas.drawLine((int)x, (int)y + height, (int)x + width, (int)y + height, paint);

    }
}
