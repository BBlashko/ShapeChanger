package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Brett Blashko on 4/3/2015.
 */
public class ShapeChanger_Game {

    private GameView gameView;
    private Background background;
    private Player player;
    private ArrayList<Block> groundBlocks;
    private ArrayList<Boundary_Square> boundary_squares;
    private ArrayList<Boundary_Rectangle>boundary_rectangles;
    private ArrayList<Boundary_Triangle>boundary_triangles;

    public int screenHeight;
    public int screenWidth;

    private double xSpeed = 20;

    private Paint paint;
    public Random random;
    private String [] colors;

    public ShapeChanger_Game(GameView gameView, Player player, String [] colors) {
        this.gameView = gameView;
        this.screenHeight = gameView.getScreenHeight();
        this.screenWidth = gameView.getScreenWidth();
        this.player = player;
        this.colors = colors;

        random = new Random();

        background = new Background(gameView);

        populateGroundBlocks();
        boundary_squares = new ArrayList<>();
        int height = (screenHeight-96)/3;
        boundary_squares.add(new Boundary_Square(gameView,player, xSpeed,  height , 0, screenWidth));
        boundary_squares.add(new Boundary_Square(gameView,player, xSpeed, (height *2) - 200, height + 200, screenWidth));
        boundary_rectangles = new ArrayList<>();
        boundary_triangles = new ArrayList<>();

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
        int nextBoundary = random.nextInt(3);
        int height;
        int height2;
        int min;
        int max;


        switch(nextBoundary){
            case 0:
                height = random.nextInt(screenHeight - 200 - 96);
                height2 = screenHeight-96 - height - 200;
                if(height2 >= screenHeight -96){
                    height2 = 0;
                }
                boundary_squares.add(new Boundary_Square(gameView, player,xSpeed, height, 0, screenWidth));
                boundary_squares.add(new Boundary_Square(gameView, player, xSpeed, height2, height + 200, screenWidth));
                break;
            case 1:
                max = screenHeight - 96 -100;
                min = (screenHeight/5) * 2;
                height = random.nextInt((max - min)) + min;
                height2 = screenHeight - 96 - height - 100;
                if(height2 >= screenHeight -96){
                    height2 = 0;
                }
                boundary_rectangles.add(new Boundary_Rectangle(gameView, player, xSpeed, height, 0, screenWidth));
                boundary_rectangles.add(new Boundary_Rectangle(gameView, player, xSpeed, height2, height + 100, screenWidth));
                break;

            case 2:
                height = random.nextInt(screenHeight - 200 - 96);
                height2 = screenHeight-96 - height - 200;
                if(height2 >= screenHeight -96){
                    height2 = 0;
                }
                boundary_triangles.add(new Boundary_Triangle(gameView, player, xSpeed, height, 0, screenWidth));
                boundary_triangles.add(new Boundary_Triangle(gameView, player,xSpeed, height2, height + 200, screenWidth));
                break;

        }

    }
    protected void update() {

        background.update();
        player.update();
        //update GroundBlocks
        for (Block block : groundBlocks) {
            block.update();
            player.checkCollisions(block);

        }
        int size = boundary_squares.size();
        for (int i = 0; i < size; i++) {
            if (boundary_squares.get(i).getx() < 0 - boundary_squares.get(i).getWidth() &&boundary_squares.get(i + 1).getx() < 0 - boundary_squares.get(i + 1).getWidth()) {
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
        size = boundary_rectangles.size();
        for (int i = 0; i < size; i++) {
            if (boundary_rectangles.get(i).getx() < 0 - boundary_rectangles.get(i).getWidth() && boundary_rectangles.get(i + 1).getx() < 0 - boundary_rectangles.get(i + 1).getWidth()) {
                boundary_rectangles.remove(i);
                boundary_rectangles.remove(i);
                i--;
                size -= 2;
                determineNextBoundary();
                if(size == 0)
                    break;
                else
                    continue;
            }
            boundary_rectangles.get(i).update();
            player.checkCollisions(boundary_rectangles.get(i));
        }
        size = boundary_triangles.size();
        for (int i = 0; i < size; i++) {
            if (boundary_triangles.get(i).getx() < 0 - boundary_triangles.get(i).getWidth() && boundary_triangles.get(i + 1).getx() < 0 - boundary_triangles.get(i + 1).getWidth()) {
                boundary_triangles.remove(i);
                boundary_triangles.remove(i);
                i--;
                size -= 2;
                determineNextBoundary();
                if(size == 0)
                    break;
                else
                    continue;
            }
            boundary_triangles.get(i).update();
            player.checkCollisions(boundary_triangles.get(i));
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
        for(Boundary_Rectangle rectangle: boundary_rectangles){
            rectangle.onDraw(canvas);
        }
        for(Boundary_Triangle triangle: boundary_triangles){
            triangle.onDraw(canvas);
        }

    }
}
