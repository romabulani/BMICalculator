package com.example.romabulani.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import  android.location.Address;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;
import static com.google.android.gms.location.LocationServices.*;

public class WelcomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    TextView tvCity, tvTemperature, tvWelcome, tvHeight, tvFeet, tvInch, tvWeight,tvTemp,tvCityName;
    Spinner spnFeet, spnInch;
    EditText etWeight;
    Button btnCalculate, btnViewHistory;
    GoogleApiClient gac;
    Location loc;
    static MyDbHandler db;
    FloatingActionButton fabCall,fabChat;
    Switch switch1;
    RelativeLayout activity_welcome;

    String msg;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.setTitle("Details");

        tvCity = (TextView) findViewById(R.id.tvCity);
        tvTemperature = (TextView) findViewById(R.id.tvTemperature);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvFeet = (TextView) findViewById(R.id.tvFeet);
        tvInch = (TextView) findViewById(R.id.tvInch);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvTemp=(TextView)findViewById(R.id.tvTemp);
        tvCityName=(TextView)findViewById(R.id.tvCityName);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        etWeight = (EditText) findViewById(R.id.etWeight);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnViewHistory = (Button) findViewById(R.id.btnViewHistory);
        switch1 =(Switch)findViewById(R.id.switch1);
        fabChat=(FloatingActionButton)findViewById(R.id.fabChat);
        activity_welcome=(RelativeLayout)findViewById(R.id.activity_welcome);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    activity_welcome.setBackgroundColor(Color.BLACK);
                else
                    activity_welcome.setBackgroundColor(Color.parseColor("#bfefff"));
            }
        });


        fabCall=(FloatingActionButton)findViewById(R.id.fabCall);
        db=new MyDbHandler(this);
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);


        final SharedPreferences sp;
        sp = getSharedPreferences("p1", MODE_PRIVATE);
        final String n = sp.getString("n", "");
        tvWelcome.setText("Welcome " + n);

        final ArrayList<Integer> feet = new ArrayList<>();

        feet.add(1);
        feet.add(2);
        feet.add(3);
        feet.add(4);
        feet.add(5);
        feet.add(6);
        feet.add(7);
        feet.add(8);
        feet.add(9);
        feet.add(10);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, feet);
        spnFeet.setAdapter(adapter);

        final ArrayList<Integer> inch = new ArrayList<>();
        inch.add(0);
        inch.add(1);
        inch.add(2);
        inch.add(3);
        inch.add(4);
        inch.add(5);
        inch.add(6);
        inch.add(7);
        inch.add(8);
        inch.add(9);
        inch.add(10);
        inch.add(11);
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, inch);
        spnInch.setAdapter(adapter1);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        gac = builder.build();//location ends

       btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=spnFeet.getSelectedItemPosition();
                int resfeet=feet.get(id);
                int id2=spnInch.getSelectedItemPosition();
                int resinch=inch.get(id2);
                float height=(float) (resfeet*12+resinch)/40;
                if(etWeight.getText().toString().length()==0 || etWeight.getText().toString().equals("0"))
                {
                    etWeight.setError("Please Enter Valid Weight");
                    etWeight.requestFocus();
                    return;
                }
                float weight=Float.parseFloat(etWeight.getText().toString());
                float bmi=weight/(height*height);

                Intent i = new Intent(WelcomeActivity.this, CalculateActivity.class);
                i.putExtra("bmi",bmi);
                i.putExtra("weight",Float.parseFloat(etWeight.getText().toString()));
                startActivity(i);
                finish();

            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this, ViewHistory.class);
                startActivity(i);
                finish();

            }
        });
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_DIAL);

                i.setData(Uri.parse("tel:"+"9881962879"));
                startActivity(i);
            }
        });
        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(WelcomeActivity.this,ChatActivity.class);
                startActivity(i);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1,menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.website)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://"+"www.hsph.harvard.edu/obesity-prevention-source/obesity-definition/obesity-definition-full-story/"));
            startActivity(i);

        }
        if (item.getItemId()==R.id.about)
        {
            Intent i=new Intent(WelcomeActivity.this,AboutActivity.class);
                    startActivity(i);
            finish();
            }

        if (item.getItemId()==R.id.rate)
        {
            Intent i=new Intent(WelcomeActivity.this,RateActivity.class);
            startActivity(i);
            finish();
        }

        return  super.onOptionsItemSelected(item);
    }

    class MyTask extends AsyncTask<String,Void,Double> {

        @Override
        protected Double doInBackground(String... strings) {
            double temp=0.0;
            String line="",json="";

            try {
                URL url=new  URL(strings[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr= new InputStreamReader(con.getInputStream());
                BufferedReader br=new BufferedReader(isr);

                while ((line=br.readLine())!=null)
                {
                    json=json+line+"\n";
                }

                JSONObject o=new JSONObject(json);
                JSONObject p=o.getJSONObject("main");
                temp=p.getDouble("temp");
            } catch (Exception e) {

            }

            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText(aDouble.toString()+"°C");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        loc=LocationServices.FusedLocationApi.getLastLocation(gac);//get last location of client
        if(loc!=null)
        {
            double lat=loc.getLatitude();
            double lon=loc.getLongitude();

            Geocoder g=new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> la=g.getFromLocation(lat,lon,1);
                Address add=la.get(0);
                 msg= add.getLocality();
                tvCityName.setText(msg);
                String url="http://api.openweathermap.org/";
                String sp1="data/2.5/weather?units=metric";
                String qu="&q="+msg;
                String id="b0bd5673322b460f4db21359eed55f32";
                String m=url+sp1+qu+"&appid="+id;
                MyTask t=new MyTask();
                t.execute(m);//temp ends


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(this, "Please check network connection and enable GPS", LENGTH_SHORT).show();

    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        gac.connect();
        }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        gac.disconnect();
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
