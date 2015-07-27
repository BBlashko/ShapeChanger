package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Brett Blashko on 3/21/2015.
 */
public class Boundary_Rectangle extends MapObject{

    private Paint paint_fill;
    private Paint paint_border;
    private Player player;

    private boolean oneTime;

    public Boundary_Rectangle(GameView gameView, Player player,double speed, int height,int  y, int x){
        super(gameView);
        this.player = player;
        xSpeed = speed;
        this.x = x;
        this.y = y;
        this.height = height;
        width = 75;
        collisionWidth = 75;
        collisionHeight = height;

        paint_border = new Paint();
        paint_border.setColor(Color.CYAN);
        paint_border.setStrokeWidth(3);
        paint_border.setStyle(Paint.Style.STROKE);
        paint_fill = new Paint();
        paint_fill.setColor(Color.rgb(0, 139, 139));

    }
    public void update(){
        x -= xSpeed;
    }
    public void onDraw(Canvas canvas){
        canvas.drawRect((int) x, (int) y, (int) x + width, (int) y + height, paint_fill);
        canvas.drawLine((int) x, (int) y, (int) x+width, (int) y, paint_border);
        canvas.drawLine((int) x, (int) y, (int) x, (int) y + height, paint_border);
        canvas.drawLine((int) x, (int) y+height, (int) x+width, (int) y + height, paint_border);
        canvas.drawLine((int) x+ width, (int) y+height, (int) x+width, (int) y, paint_border);

        if(player.getx() + player.getWidth() <= x + width/2) {
            oneTime = true;
            canvas.drawLine((int) x + width/2, 0, (int) x + width/2, screenHeight, paint_border);
        }else if(oneTime && player.getShape() == 1){
            oneTime = false;
            gameView.updateScore(50);
        }else if(oneTime){
            gameView.setDeath(true);
        }
    }
}
