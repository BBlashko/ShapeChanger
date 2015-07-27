package com.lynx.bblashko.shapechanger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Brett Blashko on 2/25/2015.
 */
public class GameView extends SurfaceView {



    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private TouchHandler touchHandler;
    public static GestureDetector  mDetector;
    private DisplayMetrics displayMetrics;
    public int screenHeight;
    public int screenWidth;

    private static int score;
    private String [] colors;

    private int difficulty;
    private Easy_Game easy_game;
    private Medium_Game medium_game;
    private Hard_Game hard_game;
    private ShapeChanger_Game shapechanger_game;

    private Player player;

    private Paint paint;
    private Paint circlePaint;

    private Context context;

    private DeathListener deathListener;
    private int totalTime;
    private long startTime;

    private int tokens = 0;

    int secs = 0;
    int mins = 0;
    int hours = 0;
    int days = 0;


    public GameView(Context context, String [] colors, int difficulty, DeathListener dl) {
        super(context);

        this.context = context;
        this.difficulty = difficulty;
        this.colors = colors;
        this.deathListener = dl;

        displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        gameLoopThread = new GameLoopThread(this);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });




        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);

        circlePaint = new Paint();
        circlePaint.setColor(Color.YELLOW);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(2);

        startTime = System.nanoTime();
        switch (difficulty){
            case 0:
                player = new Player(this, getContext(), 8);
                easy_game = new Easy_Game(this, player, colors);
                break;
            case 1:
                player = new Player(this, getContext(), 15);
                medium_game = new Medium_Game(this, player, colors);
                break;
            case 2:
                player = new Player(this, getContext(),20);
                hard_game = new Hard_Game(this, player, colors);
                break;
            case 3:
                player = new Player(this, getContext(),22);
                shapechanger_game = new ShapeChanger_Game(this, player, colors);
                break;
        }
        touchHandler = new TouchHandler(player);
        mDetector = new GestureDetector(context, touchHandler);
        mDetector.setOnDoubleTapListener(touchHandler);
        score = 0;

    }
    protected void update(){
        if(!player.getDead()) {
            switch (difficulty) {
                case 0:
                    easy_game.update();
                    break;
                case 1:
                    medium_game.update();
                    break;
                case 2:
                    hard_game.update();
                    break;
                case 3:
                    shapechanger_game.update();
                    break;
            }
        }else{
            gameLoopThread.setRunning(false);
        }
    }
    protected void onDraw(Canvas canvas){
        if(!player.getDead()) {
            switch (difficulty) {
                case 0:
                    easy_game.onDraw(canvas);
                    break;
                case 1:
                    medium_game.onDraw(canvas);
                    break;
                case 2:
                    hard_game.onDraw(canvas);
                    break;
                case 3:
                    shapechanger_game.onDraw(canvas);
                    break;
            }
            canvas.drawText("Score: " + score, screenWidth - 400, 50, paint);
            canvas.drawCircle(screenWidth - 375, 100, 25, circlePaint);
            canvas.drawText(": " + tokens, screenWidth - 340, 115, paint);
        }
    }

    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }
    public void updateScore(int num){
        score += num;
    }
    public static int getScore(){
        return score;
    }
    public void setDeath(boolean dead){
        deathListener.onDeath(dead);
    }
    public void calculateTime(){
        totalTime = (int)(System.nanoTime() - startTime) / 1000000000;
        secs = totalTime;

        while (secs > 60)
        {
            secs -= 60;
            mins++;
            if (mins > 60)
            {
                hours++;
                mins = 0;
            }
            else if (hours > 24)
            {
                days++;
                hours = 0;
            }
        }
    }
    public int getTime(int d){
        if(d == 0){
            return days;
        }else if(d == 1){
            return hours;
        }else if(d == 2){
            return mins;
        }else {
            return secs;
        }
    }
    public void updateTokens(){
        tokens++;
    }
    public int getTokens(){
        return tokens;
    }
}
