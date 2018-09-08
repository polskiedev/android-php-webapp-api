package com.polskiedev.android_webapp_api_connect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final TextView tvName = (TextView) findViewById(R.id.tvName);
        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String message  = "Welcome " + username + "!";

        welcomeMessage.setText(message);
        tvName.setText(name);
        tvUsername.setText(username);
    }
}
