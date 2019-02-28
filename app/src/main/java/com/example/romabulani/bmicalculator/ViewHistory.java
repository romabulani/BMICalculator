package com.example.romabulani.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class ViewHistory extends AppCompatActivity {
TextView tvData;
    Switch switch1;
    RelativeLayout activity_view_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);


            tvData=(TextView)findViewById(R.id.tvData);
        this.setTitle("Records");
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);


        String data=WelcomeActivity.db.viewBmi();
        tvData.setMovementMethod(new ScrollingMovementMethod());
            if(data.length()==0)
                tvData.setText("No records to show");
            else
                tvData.setText(data);
        switch1 =(Switch)findViewById(R.id.switch1);
        activity_view_history=(RelativeLayout)findViewById(R.id.activity_view_history);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    activity_view_history.setBackgroundColor(Color.BLACK);
                else
                    activity_view_history.setBackgroundColor(Color.parseColor("#bfefff"));
            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(ViewHistory.this,WelcomeActivity.class);
        startActivity(i);
        finish();
    }

}
