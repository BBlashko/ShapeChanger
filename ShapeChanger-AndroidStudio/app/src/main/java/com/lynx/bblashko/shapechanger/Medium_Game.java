package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Brett Blashko on 4/3/2015.
 */
public class Medium_Game {
    private GameView gameView;
    private Player player;

    private int screenHeight;
    private int screenWidth;

    private double xSpeed = 17;

    private Background background;
    private Random random;
    private ArrayList<Block> groundBlocks;
    private ArrayList<Boundary_Square> boundary_squares;

    public Medium_Game(GameView gameView, Player player, String [] colors){
        this.gameView = gameView;
        this.player = player;

        this.screenHeight = gameView.getScreenHeight();
        this.screenWidth = gameView.getScreenWidth();


        random = new Random();
        background = new Background(gameView);

        populateGroundBlocks();
        boundary_squares = new ArrayList<>();

        int height = (screenHeight-96)/3;
        boundary_squares.add(new Boundary_Square(gameView,player, xSpeed, height , 0, screenWidth));
        boundary_squares.add(new Boundary_Square(gameView,player, xSpeed, (height *2) - 200, height + 200, screenWidth));
    }
    public void populateGroundBlocks(){
        groundBlocks = new ArrayList<>();
        for(int i = 0; i < 21; i++){
            Block block = new Block(gameView, xSpeed);
            block.setPosition(i * block.getWidth(), screenHeight - block.getHeight()-1);
            groundBlocks.add(block);
        }
    }
    protected void determineNextBoundary(){
        int height;
        int height2;

        height = random.nextInt(screenHeight - 200 - 96);
        height2 = screenHeight-96 - height - 200;
        if(height2 >= screenHeight -96){
            height2 = 0;
        }
        boundary_squares.add(new Boundary_Square(gameView, player, xSpeed, height, 0, screenWidth));
        boundary_squares.add(new Boundary_Square(gameView, player, xSpeed, height2, height + 200, screenWidth));
    }
    protected void update(){
        background.update();
        player.update();
        //update GroundBlocks
        for (Block block : groundBlocks) {
            block.update();
            player.checkCollisions(block);

        }
        int size = boundary_squares.size();
        for (int i = 0; i < size; i++) {
            if (boundary_squares.get(i).getx() < 0 - boundary_squares.get(i).getWidth() && boundary_squares.get(i + 1).getx() < 0 - boundary_squares.get(i + 1).getWidth()) {
                boundary_squares.remove(i);
                boundary_squares.remove(i);
                i--;
                size -= 2;
                determineNextBoundary();
                if(size == 0)
                    break;
            }
            boundary_squares.get(i).update();
            player.checkCollisions(boundary_squares.get(i));
        }
    }
    protected void onDraw(Canvas canvas){
        //repaints screen black
        canvas.drawColor(Color.BLACK);

        background.onDraw(canvas);
        player.onDraw(canvas);
        //Draw groundBlocks;
        for(Block block: groundBlocks){
            block.onDraw(canvas);
        }
        for(Boundary_Square square : boundary_squares){
            square.onDraw(canvas);
        }

    }
}
