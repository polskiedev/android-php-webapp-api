package com.polskiedev.android_webapp_api_connect;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        final String APP_ENVIRONMENT = getString(R.string.APP_ENVIRONMENT).toUpperCase();
        final String CONFIG_API_KEY = getString(R.string.CONFIG_API_KEY);

//        String[] recourseList=this.getResources().getStringArray(R.array.TestList);

        bRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String confirm_password = etConfirmPassword.getText().toString();

//                final int age = Integer.parseInt(etAge.getText().toString());

                String API_URL = "";

                if(APP_ENVIRONMENT.equals("TESTING")) {
                    API_URL = getString(R.string.LOCAL_URL_REGISTER);
                } else if (APP_ENVIRONMENT.equals("PRODUCTION")) {
                    API_URL = getString(R.string.URL_REGISTER);
                } else {
                    //API_URL = "google.com";
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
                            int success = jsonResponse.getInt("success");
                            String msg = jsonResponse.getString("message");

                            if(success == 1) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else if(success == 0){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(msg)
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

                RegisterRequest registerRequest = new RegisterRequest(API_URL, CONFIG_API_KEY, name, username, password, confirm_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
