package com.lynx.bblashko.shapechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Statistics extends Activity {

    private Button main_menu;

    //Easy stats
    private String easy_deaths;
    private String easy_days;
    private String easy_hours;
    private String easy_minutes;
    private String easy_seconds;
    private String easy_highscore;
    private String easy_tokens;
    //Medium stats
    private String medium_deaths;
    private String medium_days;
    private String medium_hours;
    private String medium_minutes;
    private String medium_seconds;
    private String medium_highscore;
    private String medium_tokens;
    //Hard stats
    private String hard_deaths;
    private String hard_days;
    private String hard_hours;
    private String hard_minutes;
    private String hard_seconds;
    private String hard_highscore;
    private String hard_tokens;
    //Shapechanger stats
    private String shapechanger_deaths;
    private String shapechanger_days;
    private String shapechanger_hours;
    private String shapechanger_minutes;
    private String shapechanger_seconds;
    private String shapechanger_highscore;
    private String shapechanger_tokens;

    private TabHost tabHost;
    private TextView easyText;
    private TextView mediumText;
    private TextView hardText;
    private TextView shapechangerText;
    private TextView easyText2;
    private TextView mediumText2;
    private TextView hardText2;
    private TextView shapechangerText2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        final Intent main = new Intent(this, MainMenu.class);
        main_menu = (Button) findViewById(R.id.btn_main);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(main);
            }
        });
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        easyText = (TextView) findViewById(R.id.tb_easy);
        mediumText = (TextView) findViewById(R.id.tb_medium);
        hardText = (TextView) findViewById(R.id.tb_hard);
        shapechangerText = (TextView) findViewById(R.id.tb_shapechanger);
        easyText2 = (TextView) findViewById(R.id.tb_easy2);
        mediumText2 = (TextView) findViewById(R.id.tb_medium2);
        hardText2 = (TextView) findViewById(R.id.tb_hard2);
        shapechangerText2 = (TextView) findViewById(R.id.tb_shapechanger2);

        TabHost.TabSpec easy_tab = tabHost.newTabSpec("easy");
        easy_tab.setContent(R.id.easy);
        easy_tab.setIndicator("Easy");
        tabHost.addTab(easy_tab);
       
        TabHost.TabSpec medium_tab = tabHost.newTabSpec("medium");
        medium_tab.setContent(R.id.medium);
        medium_tab.setIndicator("Medium");
        tabHost.addTab(medium_tab);

        TabHost.TabSpec hard_tab = tabHost.newTabSpec("hard");
        hard_tab.setContent(R.id.hard);
        hard_tab.setIndicator("Hard");
        tabHost.addTab(hard_tab);

        TabHost.TabSpec shapechanger_tab = tabHost.newTabSpec("shapechanger");
        shapechanger_tab.setContent(R.id.shapechanger);
        shapechanger_tab.setIndicator("Shapechanger");
        tabHost.addTab(shapechanger_tab);

        loadStats();
        setTextboxes();
    }
    public void loadStats(){
        try {
            InputStream inputStream = getAssets().open("com/lynx/bblashko/shapechanger/statistics.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while((line = bufferedReader.readLine()) != null){
                if(line.contains("highscore"))
                {
                    if(line.contains("easy"))
                    {
                        easy_highscore = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                        medium_highscore = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_highscore = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_highscore = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
                else if(line.contains("deaths"))
                {
                    if(line.contains("easy"))
                    {
                        easy_deaths = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                        medium_deaths = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_deaths = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_deaths = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
                else if(line.contains("days"))
                {
                    if(line.contains("easy"))
                    {
                        easy_days = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                        medium_days = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_days = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_days = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
                else if(line.contains("hours"))
                {
                    if(line.contains("easy"))
                    {
                        easy_hours = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                        medium_hours = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_hours = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_hours = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
                else if(line.contains("minutes"))
                {
                    if(line.contains("easy"))
                    {
                        easy_minutes = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                        medium_minutes = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_minutes = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_minutes = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
                else if(line.contains("seconds"))
                {
                    if(line.contains("easy"))
                    {
                        easy_seconds = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                       medium_seconds = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_seconds = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_seconds = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
                else if(line.contains("tokens"))
                {
                    if(line.contains("easy"))
                    {
                        easy_tokens = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("medium"))
                    {
                        medium_tokens = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("hard"))
                    {
                        hard_tokens = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                    else if(line.contains("shapechanger"))
                    {
                        shapechanger_tokens = line.substring(line.indexOf('=')+1);
                        continue;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setTextboxes(){
        easyText.setText("Highscore:"
                + "\nDeaths:"
                + "\nTokens Collected:"
                + "\n\nTime Played:"
                + "\n     Days:"
                + "\n     Hours:"
                + "\n     Minutes:"
                + "\n     Seconds:");
        easyText2.setText("" + easy_highscore
                + "\n" + easy_deaths
                + "\n" + easy_tokens
                + "\n\n"
                + "\n" + easy_days
                + "\n" + easy_hours
                + "\n" + easy_minutes
                + "\n" + easy_seconds);
        mediumText.setText("Highscore:"
                + "\nDeaths:"
                + "\nTokens Collected:"
                + "\n\nTime Played:"
                + "\n     Days:"
                + "\n     Hours:"
                + "\n     Minutes:"
                + "\n     Seconds:");
        mediumText2.setText("" + medium_highscore
                + "\n" + medium_deaths
                + "\n" + medium_tokens
                + "\n\n"
                + "\n" + medium_days
                + "\n" + medium_hours
                + "\n" + medium_minutes
                + "\n" + medium_seconds);
        hardText.setText("Highscore:"
                + "\nDeaths:"
                + "\nTokens Collected:"
                + "\n\nTime Played:"
                + "\n     Days:"
                + "\n     Hours:"
                + "\n     Minutes:"
                + "\n     Seconds:");
        hardText2.setText("" + hard_highscore
                + "\n" + hard_deaths
                + "\n" + hard_tokens
                + "\n\n"
                + "\n" + hard_days
                + "\n" + hard_hours
                + "\n" + hard_minutes
                + "\n" + hard_seconds);
        shapechangerText.setText("Highscore:"
                + "\nDeaths:"
                + "\nTokens Collected:"
                + "\n\nTime Played:"
                + "\n     Days:"
                + "\n     Hours:"
                + "\n     Minutes:"
                + "\n     Seconds:");
        shapechangerText2.setText("" + shapechanger_highscore
                + "\n" + shapechanger_deaths
                + "\n" + shapechanger_tokens
                + "\n\n"
                + "\n" + shapechanger_days
                + "\n" + shapechanger_hours
                + "\n" + shapechanger_minutes
                + "\n" + shapechanger_seconds);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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
