package com.example.romabulani.bmicalculator;

import android.content.Intent;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE=1;
    FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_chat2;
    FloatingActionButton fabSend;
    EditText tiet;
    ListView lvData;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGN_IN_REQUEST_CODE)
            if(resultCode==RESULT_OK)
            {
                Snackbar.make(activity_chat2,"Successfully signed in.Welcome",Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }
        else {
                Snackbar.make(activity_chat2, "You couldn't sign in.Please try again",Snackbar.LENGTH_SHORT).show();
                finish();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m2,menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.signOut)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_chat2, "You have been signed out",Snackbar.LENGTH_SHORT).show();
                    finish();


                }
            });
            finish();
        }
        return  true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        activity_chat2=(RelativeLayout)findViewById(R.id.activity_chat2);
        fabSend=(FloatingActionButton)findViewById(R.id.fabSend);
         tiet=(EditText)findViewById(R.id.tiet);


        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(tiet.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                tiet.setText("");
                }
        });

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);

        }
        else
        {
            Snackbar.make(activity_chat2,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            displayChatMessage();

        }
        }

    private void displayChatMessage() {
        lvData=(ListView)findViewById(R.id.lvData);

        adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,
                R.layout.list_item,FirebaseDatabase.getInstance().getReference())
        {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText,messageUser,messageTime;
                messageText=(TextView)v.findViewById(R.id.message_text);
                messageUser=(TextView)v.findViewById(R.id.message_user);
                messageTime=(TextView)v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));


            }
        };
        lvData.setAdapter(adapter);

    }
}
