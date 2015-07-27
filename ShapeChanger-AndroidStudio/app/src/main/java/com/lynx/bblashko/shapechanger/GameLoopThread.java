package com.lynx.bblashko.shapechanger;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

/**
 * Created by Brett Blashko on 2/25/2015.
 */
public class GameLoopThread extends Thread{

    private GameView gameView;
    private boolean running = false;
    private int FPS = 55;

    public GameLoopThread(GameView gameView){
        this.gameView = gameView;

    }
    public void setRunning(Boolean b){
        running = b;
    }
    @SuppressLint("WrongCall")
    public void run(){
        long ticks = 1000/FPS;
        long startTime;
        long sleepTime;

        while(running){
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try{
                c = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()){
                    gameView.update();
                    gameView.onDraw(c);
                }
            }finally {
                if(c != null){
                    gameView.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticks - (System.currentTimeMillis() - startTime);
            try{
                if(sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
