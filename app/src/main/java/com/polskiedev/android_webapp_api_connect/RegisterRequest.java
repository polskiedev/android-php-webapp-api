package com.polskiedev.android_webapp_api_connect;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
//    private static final String REGISTER_REQUEST_URL = "https://api-dev.000webhostapp.com/";
//    private static final String REGISTER_REQUEST_URL = "http://10.0.2.2/android-api/create_user.php";
    //String url = "http://10.0.2.2/android-api/get_all_products.php";

    private Map<String, String> params;

    public RegisterRequest(String URL, String API_KEY, String name, String username, String password, String confirm_password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("confirm_password", confirm_password);
        params.put("apikey", API_KEY);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
