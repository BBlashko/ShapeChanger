package com.lynx.bblashko.shapechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameOver extends Activity {
    private int score;
    private Button restart;
    private Button main_menu;
    private Button statistics;

    private TextView score_message;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        final Intent intent_main_menu = new Intent(this, MainMenu.class);
        final Intent intent_restart = new Intent(this, Difficulty.class);
        final Intent intent_statistics = new Intent(this, Statistics.class);

        restart = (Button) findViewById(R.id.btn_gos_restart);
        main_menu = (Button) findViewById(R.id.btn_gos_main_menu);
        statistics = (Button) findViewById(R.id.btn_gos_statistics);

        score_message = (TextView) findViewById(R.id.tv_gos_score);
        message = (TextView) findViewById(R.id.tv_gos_message);

        if(getIntent().getExtras() != null){
            score = getIntent().getIntExtra("score", 0);
        }
        String message_text = "You suck...";

        if(score > 10000){
            message_text = "You've made me PROUD!";
        }else if(score > 5000){
            message_text = "You've been practicing!";
        }else if( score > 1000){
            message_text = "Not bad...";
        }else if(score > 500){
            message_text = "Practice makes perfect";
        }

        score_message.setText("Score: " + score);
        message.setText(message_text);

        restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent_restart);
                finish();
            }
        });
        main_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent_main_menu);
                finish();
            }
        });
        statistics.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent_statistics);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
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

}
