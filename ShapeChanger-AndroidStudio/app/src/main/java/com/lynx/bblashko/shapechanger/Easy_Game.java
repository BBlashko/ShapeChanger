package com.lynx.bblashko.shapechanger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Brett Blashko on 4/3/2015.
 */
public class Easy_Game {

    private GameView gameView;
    private Background background;
    private Player player;
    private ArrayList<Block> groundBlocks;
    private ArrayList<Boundary_Square> boundary_squares;
    private ArrayList<Boundary_Rectangle>boundary_rectangles;
    private ArrayList<Boundary_Triangle>boundary_triangles;
    private ArrayList<Token> tokens;

    public int screenHeight;
    public int screenWidth;

    private double xSpeed = 8;

    private Paint paint;
    public Random random;
    private String [] colors;

    public Easy_Game(GameView gameView, Player player, String [] colors) {
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
        boundary_squares.add(new Boundary_Square(gameView,player, xSpeed, (height *2) - 250, height + 250, screenWidth));
        boundary_rectangles = new ArrayList<>();
        boundary_triangles = new ArrayList<>();

        tokens = new ArrayList<>();
        tokens.add(new Token(gameView, screenWidth + screenWidth/2, screenHeight/2, xSpeed));
    }
    public void populateGroundBlocks(){
        groundBlocks = new ArrayList<>();
        for(int i = 0; i < 21; i++){
            Block block = new Block(gameView, xSpeed);
            block.setPosition(i * block.getWidth(), screenHeight - block.getHeight()-1);
            groundBlocks.add(block);
        }
    }
    public void generateTokens(){
        int format = random.nextInt(5);
        int min;
        int max;
        int height;
        switch(format){
            case 0:
                max = screenHeight - 96 - 20;
                min = 50;
                height = random.nextInt((max - min)) + min;
                tokens.add(new Token(gameView, screenWidth + screenWidth/2, height, xSpeed));
                break;
            case 1:
                max = screenHeight - 96 - 60;
                min = 50;
                height = random.nextInt((max - min)) + min;
                for(int i = 0; i < 3; i++){
                    tokens.add(new Token(gameView, screenWidth + screenWidth/2 + (i*110), height, xSpeed));
                    tokens.add(new Token(gameView, screenWidth + screenWidth/2 + (i*110) , height+110, xSpeed));
                    tokens.add(new Token(gameView, screenWidth + screenWidth/2 + (i*110), height+220, xSpeed));
                }
                break;
            case 2:
                max = screenHeight/2;
                min = 100;
                height = random.nextInt((max - min)) + min;
                for(int i = 0; i < 6; i++) {
                    tokens.add(new Token(gameView, screenWidth + screenWidth / 2, height + (i * 110), xSpeed));
                }
                break;
            case 3:
                height = 100;
                for(int i = 0; i < 6; i++) {
                    tokens.add(new Token(gameView, screenWidth + screenWidth/2 + (i * 110), height , xSpeed));
                }
                break;
            case 4:
                height = 300;
                for(int j = 0 ; j <= 6; j++){
                    for(int i = 3; i >= 0; i-- ){
                        if(j ==  3-i || j == 3+ i)
                            tokens.add(new Token(gameView, screenWidth + screenWidth/2 + 350 -(110*j), height+ (i*110) , xSpeed));
                    }
                }
                break;
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
                height = random.nextInt(screenHeight - 250 - 96);
                height2 = screenHeight-96 - height - 250;
                if(height2 >= screenHeight -96){
                    height2 = 0;
                }
                boundary_squares.add(new Boundary_Square(gameView, player,xSpeed, height, 0, screenWidth));
                boundary_squares.add(new Boundary_Square(gameView, player, xSpeed, height2, height + 250, screenWidth));
                break;
            case 1:
                max = screenHeight - 96 -200;
                min = (screenHeight/5) * 2;
                height = random.nextInt((max - min)) + min;
                height2 = screenHeight - 96 - height - 200;
                if(height2 >= screenHeight -96){
                    height2 = 0;
                }
                boundary_rectangles.add(new Boundary_Rectangle(gameView, player, xSpeed, height, 0, screenWidth));
                boundary_rectangles.add(new Boundary_Rectangle(gameView, player, xSpeed, height2, height + 200, screenWidth));
                break;

            case 2:
                height = random.nextInt(screenHeight - 220 - 96);
                height2 = screenHeight-96 - height - 220;
                if(height2 >= screenHeight -96){
                    height2 = 0;
                }
                boundary_triangles.add(new Boundary_Triangle(gameView, player, xSpeed, height, 0, screenWidth));
                boundary_triangles.add(new Boundary_Triangle(gameView, player,xSpeed, height2, height + 220, screenWidth));
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
        int size = tokens.size();
        for(int i = 0; i < size; i++){
            if (tokens.get(i).getx() < 0 - 20) {
                tokens.remove(i);
                i--;
                size--;
                if(size == 0) {
                    break;
                }
                else
                    continue;
            }
            if(player.checkTokens(tokens.get(i))){
                gameView.updateScore(25);
                gameView.updateTokens();
                tokens.remove(i);
                i--;
                size--;
                if(size == 0) {
                    break;
                }
                else
                    continue;
            }
            tokens.get(i).update();
        }
        size = boundary_squares.size();
        for (int i = 0; i < size; i++) {
            if (boundary_squares.get(i).getx() < 0 - boundary_squares.get(i).getWidth() &&boundary_squares.get(i + 1).getx() < 0 - boundary_squares.get(i + 1).getWidth()) {
                boundary_squares.remove(i);
                boundary_squares.remove(i);
                i--;
                size -= 2;
                determineNextBoundary();
                generateTokens();
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
                generateTokens();
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
                generateTokens();
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
        for(Token token : tokens){
            token.onDraw(canvas);
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
