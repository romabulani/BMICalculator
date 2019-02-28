package com.example.romabulani.bmicalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class RateActivity extends AppCompatActivity {
    RatingBar rabRating;
    EditText etFeedback;
    Button btnSend;
    RelativeLayout activity_rate;
    Switch switch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rabRating=(RatingBar)findViewById(R.id.rabRating);
        this.setTitle("Rate Us");
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);



        etFeedback=(EditText)findViewById(R.id.etFeedback);
        btnSend=(Button)findViewById(R.id.btnSend);
        switch1 =(Switch)findViewById(R.id.switch1);
        activity_rate=(RelativeLayout)findViewById(R.id.activity_rate);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    activity_rate.setBackgroundColor(Color.BLACK);
                else
                    activity_rate.setBackgroundColor(Color.parseColor("#bfefff"));
            }
        });



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating=String.valueOf(rabRating.getRating());

                String msg= "Rating: "+ rating+"\nFeedback: "+etFeedback.getText().toString();
                Intent i=new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+"romabulani@gmail.com"));
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                    startActivity(i);



            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(RateActivity.this,WelcomeActivity.class);
        startActivity(i);
        finish();
    }

}
