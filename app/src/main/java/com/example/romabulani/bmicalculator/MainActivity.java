package com.example.romabulani.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    TextView tvDetails;
    EditText etName, etPhone, etAge;
    RadioGroup rgGender;
    Button btnRegister;
    SharedPreferences sp;
    Switch switch1;
    LinearLayout activity_main;
    public boolean  isValid(String str) {
        int i;
        for (i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isLetter(ch) || ch == ' ')
                continue;

            return false;
        }
        return true;
    }
    public boolean  isValidp(String str)
    {

        int i;
        for(i=0;i<str.length();i++)
            if(str.charAt(i)>='0'&&str.charAt(i)<='9')
                return  true;
        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Registration");
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);


        tvDetails = (TextView) findViewById(R.id.tvDetails);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhone = (EditText) findViewById(R.id.etPhone);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        sp = getSharedPreferences("p1", MODE_PRIVATE);
        String s = sp.getString("name", "");
        switch1 =(Switch)findViewById(R.id.switch1);
        activity_main=(LinearLayout)findViewById(R.id.activity_main);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    activity_main.setBackgroundColor(Color.BLACK);
                else
                    activity_main.setBackgroundColor(Color.parseColor("#bfefff"));
            }
        });







        btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String n=etName.getText().toString();
                    if(n.length()==0 || !isValid(n))
                    {
                        etName.setError("Please Enter Valid Name");
                        etName.requestFocus();
                        return;
                    }
                    String age=etAge.getText().toString();
                    if(age.length()==0 || age.equals("0"))
                    {
                        etAge.setError("Please Enter Valid Age");
                        etAge.requestFocus();
                        return;
                    }
                    String phone=etPhone.getText().toString();
                    if(phone.length()!=10 || !isValidp(phone))
                    {
                        etPhone.setError("Enter Valid Phone Number");
                        etPhone.requestFocus();
                        return;
                    }

                    int id=rgGender.getCheckedRadioButtonId();
                    if(id<0)
                        Toast.makeText(MainActivity.this, "Please Enter Gender Details", Toast.LENGTH_SHORT).show();
                    else {

                        RadioButton rb = rgGender.findViewById(id);
                        String gender = rb.getText().toString();


                        Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("n", n);
                        editor.putString("age", age);
                        editor.putString("phone", phone);
                        editor.putString("gender", gender);
                        editor.commit();
                        Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(i);
                        finish();
                    }

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

