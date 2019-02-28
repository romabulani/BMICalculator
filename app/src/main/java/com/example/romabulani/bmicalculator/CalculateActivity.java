package com.example.romabulani.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

import static android.R.attr.name;
import static com.example.romabulani.bmicalculator.R.drawable.bmi;

public class CalculateActivity extends AppCompatActivity {
    TextView tvResult,tvUnderweight,tvNormal,tvOverweight,tvObese;
    Button btnBack,btnShare,btnSave;
    TextToSpeech tts;
    FloatingActionButton fabTts;
    RelativeLayout activity_calculate;
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        this.setTitle("Result");
        tvResult=(TextView)findViewById(R.id.tvResult);
        tvNormal=(TextView)findViewById(R.id.tvNormal);
        tvUnderweight=(TextView)findViewById(R.id.tvUnderweight);
        tvOverweight=(TextView)findViewById(R.id.tvOverweight);
        tvObese=(TextView)findViewById(R.id.tvObese);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnShare=(Button)findViewById(R.id.btnShare);
        btnSave=(Button)findViewById(R.id.btnSave);
        fabTts=(FloatingActionButton)findViewById(R.id.fabTts);
        Intent i=getIntent();
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        final Float n=i.getFloatExtra("bmi",0);
        final Float w=i.getFloatExtra("weight",0);
        switch1 =(Switch)findViewById(R.id.switch1);
        activity_calculate=(RelativeLayout)findViewById(R.id.activity_calculate);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    activity_calculate.setBackgroundColor(Color.BLACK);
                else
                    activity_calculate.setBackgroundColor(Color.parseColor("#bfefff"));
            }
        });

        if(n<18.5)
        {
            tvResult.setText(String.format("Your BMI is %.2f and You are Underweight",n));
            tvUnderweight.setTextColor(Color.RED);
            tvUnderweight.setTypeface(null, Typeface.BOLD);

        }
        else
            if(n>=18.5 && n<=25)
            {
                tvResult.setText(String.format("Your BMI is %.2f and You are Normal",n));
                tvNormal.setTextColor(Color.RED);
                tvNormal.setTypeface(null, Typeface.BOLD);

            }
        else if(n>25 && n<=30)
            {
                tvResult.setText(String.format("Your BMI is %.2f and You are Overweight",n));
                tvOverweight.setTextColor(Color.RED);
                tvOverweight.setTypeface(null, Typeface.BOLD);

            }
        else  if (n>30)
            {
                tvResult.setText(String.format("Your BMI is %.2f and You are Obese",n));
                tvObese.setTextColor(Color.RED);
                tvObese.setTypeface(null, Typeface.BOLD);


            }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalculateActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();

            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                final SharedPreferences sp = getSharedPreferences("p1", MODE_PRIVATE);
                String m = "";
                if(n<18.5)
                    m="You are Underweight";
                else if(n>=18.5 && n<=25)
                    m="You are normal";
                else if(n>25 && n<=30)
                    m="You are Overweight";
                else if(n>30)
                    m="You are Obese";
                
                final String msg = "Name: "+sp.getString("n", "")+"\nAge: "+sp.getString("age","")+"\nPhone No: "+sp.getString("phone","")
                        +"\nGender: "+sp.getString("gender","")+"\nBMI: "+(String.format("%.2f\n",n)+m);

                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);


            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WelcomeActivity.db.addBmi(new Date().toString(),String.format("%.2f",n),w);
                Toast.makeText(CalculateActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                btnSave.setEnabled(false);

                    }
        });



        tts= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });
        fabTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(tvResult.getText().toString(),TextToSpeech.QUEUE_ADD,null);

            }
        });


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = builder.create();
        a.setTitle("EXIT");
        a.show();

    }

}
