package com.polskiedev.android_webapp_api_connect;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private Map<String, String> params;

    public LoginRequest(String URL, String API_KEY, String username, String password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("apikey", API_KEY);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}