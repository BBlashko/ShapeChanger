package com.lynx.bblashko.shapechanger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Options extends Activity implements ColorPicker.OnColorChangedListener {

    private Button colorPicker;
    private Button main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        final Context context = this;
        final ColorPicker.OnColorChangedListener colorListener = this;
        colorPicker = (Button) findViewById(R.id.button);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker color = new ColorPicker(context, colorListener, "picker",Color.BLACK, Color.WHITE);
                color.show();
            }
        });
        final Intent main_intent = new Intent(this, MainMenu.class);
        main_menu = (Button) findViewById(R.id.btn_main);
        main_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(main_intent);
                finish();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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
    public void colorChanged(String key, int color) {
        Log.d("Color","COLOR CHANGED");
    }
}
