package com.lynx.bblashko.shapechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainMenu extends Activity {

    private View.OnTouchListener touchListener;
    private RelativeLayout thisLayout;
    private Button btn_difficulty;
    private Button btn_stats;
    private Button btn_begin;
    private Button btn_options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btn_stats = (Button) findViewById(R.id.btn_gos_statistics);
        btn_begin = (Button) findViewById(R.id.begin);
        btn_options = (Button) findViewById(R.id.options);
        final Intent statistcs_intent = new Intent(this, Statistics.class);
        final Intent difficulty_intent = new Intent(this, Difficulty.class);
        final Intent options_intent = new Intent(this, Options.class);

        btn_begin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(difficulty_intent);
                finish();
            }
        });
        btn_stats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(statistcs_intent);
                finish();
            }
        });
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(options_intent);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
