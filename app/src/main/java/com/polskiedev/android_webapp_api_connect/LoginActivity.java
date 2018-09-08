package com.polskiedev.android_webapp_api_connect;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView)   findViewById(R.id.tvRegisterHere);

        final String APP_ENVIRONMENT = getString(R.string.APP_ENVIRONMENT).toUpperCase();
        final String CONFIG_API_KEY = getString(R.string.CONFIG_API_KEY);

        registerLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String API_URL = "";

                if(APP_ENVIRONMENT.equals("TESTING")) {
                    API_URL = getString(R.string.LOCAL_URL_LOGIN);
                } else if (APP_ENVIRONMENT.equals("PRODUCTION")) {
                    API_URL = getString(R.string.URL_LOGIN);
                } else {
                    //URL_REGISTER = "google.com";
                }

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int success = jsonResponse.getInt("success");
                            String msg = jsonResponse.getString("message");

                            if(success == 1) {
                                String sUserData = jsonResponse.getString("user_data");
                                JSONObject userData = new JSONObject(sUserData);

                                String name = userData.getString("name");
                                String username = userData.getString("username");

                                Intent intent = new Intent(getApplicationContext(), UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);

                                LoginActivity.this.startActivity(intent);
                            } else if(success == 0){
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg)
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Error!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(API_URL, CONFIG_API_KEY, username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}
