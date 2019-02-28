package com.example.romabulani.bmicalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
TextView tvName,tvEmail,tvPhone,tvVersion;
    Switch switch1;
    LinearLayout activity_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        this.setTitle("About Us");
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);



        tvName=(TextView)findViewById(R.id.tvName);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvPhone=(TextView)findViewById(R.id.tvPhone);
        tvVersion=(TextView)findViewById(R.id.tvVersion);
        switch1 =(Switch)findViewById(R.id.switch1);
        activity_about=(LinearLayout)findViewById(R.id.activity_about);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    activity_about.setBackgroundColor(Color.BLACK);
                else
                    activity_about.setBackgroundColor(Color.parseColor("#bfefff"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(AboutActivity.this,WelcomeActivity.class);
        startActivity(i);
        finish();
    }

}
