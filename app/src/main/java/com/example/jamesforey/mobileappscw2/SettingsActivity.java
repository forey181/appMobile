package com.example.jamesforey.mobileappscw2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class SettingsActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    Button sizebtn, colourbtn;
    TextView dummyText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sizebtn = findViewById(R.id.sizebtn);
        colourbtn = findViewById(R.id.colourbtn);

        sizebtn.setOnClickListener(this);
        colourbtn.setOnClickListener(this);

        dummyText = findViewById(R.id.dummy_text);


        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

        Intent intent = getIntent();
        intent.getIntExtra("settings",0);
    }

    private void updateTextView() {
        dummyText.setTextColor(SettingsActivity.getColor(this));
        dummyText.setTextSize(SettingsActivity.getSize(this));
    }

    public void onClick(View v) {

        if (v == colourbtn){

            if(getColor(this) == Color.BLACK) {
                Toast.makeText(this, getString(R.string.colour_blue), Toast.LENGTH_SHORT).show();
                storeColor(Color.BLUE, this);
            } else {
                Toast.makeText(this, getString(R.string.colour_black), Toast.LENGTH_SHORT).show();
                storeColor(Color.BLACK, this);
            }
        }

        if(v == sizebtn){
            if (getSize(this) == 15.0f) {
                Toast.makeText(this, getString(R.string.size_25), Toast.LENGTH_SHORT).show();
                storefloat(25, this);
            } else {
                Toast.makeText(this, getString(R.string.size_15), Toast.LENGTH_SHORT).show();
                storefloat(15, this);
            }
        }
    }

    public static void storeColor ( int color ,Context context){
        SharedPreferences m = context.getSharedPreferences("Setting",MODE_PRIVATE);
        SharedPreferences.Editor editor = m.edit();
        editor.putInt("color", color);
        editor.apply();
    }

    public static int getColor(Context context){
        SharedPreferences m = context.getSharedPreferences("Setting",MODE_PRIVATE);
        return m.getInt("color", Color.BLACK);
    }

    public static void storefloat ( int fontSize ,Context context){
        SharedPreferences m = context.getSharedPreferences("Setting",MODE_PRIVATE);
        m.edit().putFloat("size", fontSize).apply();
    }
    public static float getSize(Context context){
        SharedPreferences m = context.getSharedPreferences("Setting",MODE_PRIVATE);
        float selector = m.getFloat("size", 15f);
        return selector;

    }
}
