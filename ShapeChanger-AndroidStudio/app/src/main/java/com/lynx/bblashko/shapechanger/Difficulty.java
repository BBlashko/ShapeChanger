package com.lynx.bblashko.shapechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Difficulty extends Activity implements DeathListener{

    private Button main_menu;
    private Button start_game;

    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private RadioButton shapechanger;

    private String[] colors = new String[4];

    public static Activity context = null;

    private DeathListener deathListener;
    private boolean dead = false;

    private GameView gameView;

    private int d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        context = this;

        main_menu = (Button) findViewById(R.id.btn_sdmain);
        start_game = (Button) findViewById((R.id.btn_sdbegin));

        easy = (RadioButton) findViewById(R.id.rbtn_easy);
        medium = (RadioButton) findViewById(R.id.rbtn_medium);
        hard = (RadioButton) findViewById(R.id.rbtn_hard);
        shapechanger = (RadioButton) findViewById(R.id.rbtn_shapechanger);
        setConfigurations();
        final Intent main = new Intent(this, MainMenu.class);

        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(main);
                finish();
            }
        });
        start_game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int checkedRadioId = radioGroup.getCheckedRadioButtonId();
                if(checkedRadioId == R.id.rbtn_easy){
                    d = 0;
                }else if(checkedRadioId == R.id.rbtn_medium){
                    d = 1;
                }else if(checkedRadioId == R.id.rbtn_hard){
                    d = 2;
                }else if(checkedRadioId == R.id.rbtn_shapechanger){
                    d = 3;
                }
                startGame(d);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!medium.isEnabled()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Medium Difficulty is Locked", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hard.isEnabled()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Medium Difficulty is Locked", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        shapechanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shapechanger.isEnabled()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Medium Difficulty is Locked", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


    }
    public void startGame(int d){
        gameView = new GameView(getApplicationContext(), colors, d, this);
        setContentView(gameView);
    }
    public void setConfigurations(){
        easy.setEnabled(true);
        easy.setChecked(true);

        try {
            File file = getFileStreamPath("shaperChangerConfig.txt");
            if(!file.exists()){
                createConfigFile();
            }
            FileInputStream fileIn = openFileInput("mytextfile.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);



            char[] inputBuffer= new char[26];
            String s="";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }


            while((line = bufferedReader.readLine()) != null){
                Log.d("config file text = ", line);
                if(line.contains("currentDifficulty")){
                    String[] temp = line.split("=");
                    switch(Integer.parseInt(temp[1])){
                        case 0:
                            easy.setChecked(true);
                            break;
                        case 1:
                            medium.setChecked(true);
                            break;
                        case 2:
                            hard.setChecked(true);
                            break;
                        case 3:
                            shapechanger.setChecked(true);
                            break;
                    }
                }

                else if(line.contains("medium")){
                    String[] temp = line.split("=");
                    if(temp[1].equals("true"))
                        medium.setEnabled(true);
                    else
                        medium.setEnabled(false);
                }
                else if(line.contains("hard")){
                    String[] temp = line.split("=");
                    if(temp[1].equals("true"))
                        hard.setEnabled(true);
                    else
                        hard.setEnabled(false);
                }
                else if(line.contains("shapechanger")){
                    String[] temp = line.split("=");
                    if(temp[1].equals("true"))
                        shapechanger.setEnabled(true);
                    else
                        shapechanger.setEnabled(false);
                }

                else if(line.contains("square_color")){
                    String[] temp = line.split("=");
                    colors[0] = temp[1];
                }
                else  if(line.contains("rectangle_color")){
                    String[] temp = line.split("=");
                    colors[1] = temp[1];
                }
                else  if(line.contains("triangle_color")){
                    String[] temp = line.split("=");
                    colors[2] = temp[1];
                }
                else  if(line.contains("background")){
                    String[] temp = line.split("=");
                    colors[3] = temp[1];
                }


            }
            InputRead.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        GameView.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_difficulty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeath(Boolean death) {
        final Intent intent_gameOver = new Intent(this, GameOver.class);
        intent_gameOver.putExtra("score", gameView.getScore());
        startActivity(intent_gameOver);
        finish();
        updateStats(new int[]{gameView.getScore(), 1,  gameView.getTime(3),gameView.getTime(2), gameView.getTime(1), gameView.getTime(0), gameView.getTokens()});

    }
    public void updateStats(int [] stats){
        try {
            File file = getFileStreamPath("statistics.txt");

            FileOutputStream writer = new FileOutputStream(file);

            String currentLine;
            int num = 0;
            int line = -1;
//            while((currentLine = reader.readLine()) != null){
//                line++;
//                if(line == 2 + (d * num++)) {
//                    currentLine = currentLine.substring(currentLine.indexOf("=") + 1);
//                    writer.write(currentLine + stats[num]);
//                }
//                else if(line == 2 + (d * num) && num++ == 1) {
//                    String [] split = currentLine.split("=");
//                    int deaths = Integer.parseInt(split[1]);
//                    deaths += stats[num];
//                    currentLine = split[0] + "=" + String.valueOf(deaths);
//                    writer.write(currentLine);
//                }
//                else if(line == 2 + (d * num) && num++ == 2) {
//                    String [] split = currentLine.split("=");
//                    int seconds = Integer.parseInt(split[1]);
//                    seconds += stats[num];
//                    while(seconds > 60){
//                        seconds -= 60;
//                        stats[3]++;
//                    }
//                    currentLine = split[0] + "=" + String.valueOf(seconds);
//                    writer.write(currentLine);
//                }
//                else if(line == 2 + (d * num) && num++ == 3) {
//                    String [] split = currentLine.split("=");
//                    int minutes = Integer.parseInt(split[1]);
//                    minutes += stats[num];
//                    while(minutes > 60){
//                        minutes -= 60;
//                        stats[4]++;
//                    }
//                    currentLine = split[0] + "=" + String.valueOf(minutes);
//                    writer.write(currentLine);
//                }
//                else if(line == 2 + (d * num) && num++ == 4) {
//                    String [] split = currentLine.split("=");
//                    int hours = Integer.parseInt(split[1]);
//                    hours += stats[num];
//                    while(hours > 24){
//                        hours -= 24;
//                        stats[5]++;
//                    }
//                    currentLine = split[0] + "=" + String.valueOf(hours);
//                    writer.write(currentLine);
//                }
//                else if(line == 2 + (d * num) && num++ == 5) {
//                    String [] split = currentLine.split("=");
//                    int days = Integer.parseInt(split[1]);
//                    days += stats[num];
//                    currentLine = split[0] + "=" + String.valueOf(days);
//                    writer.write(currentLine);
//                }
//                else{
//                    writer.write(currentLine);
//                }
//            }
            writer.write("testing".getBytes());
            writer.close();
        }catch (Exception ex){
            Log.e("updating Stats error", ex.getMessage());
        }
    }
    private void createConfigFile(){
        String [] configStrings = {"----- Configurations -----\n",
                     "currentDifficulty=0\n",
                     "medium=true\n",
                     "hard=true\n",
                     "shapechanger=true\n",
                     "square_color=#FF0000\n",
                     "rectangle_color=#00FFFF\n",
                     "triangle_color=#00FF00\n",
                     "background=random"};
        try {
            FileOutputStream fileout = openFileOutput("shaperChangerConfig.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            for(String s : configStrings) {
                outputWriter.write(s);
            }
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
