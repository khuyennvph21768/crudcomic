package com.example.crudcomic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudcomic.MainActivity;
import com.example.crudcomic.R;
import com.example.crudcomic.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login_Activity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin;
    TextView btnSingup;

    List<User> list = new ArrayList<>();
    public static final String  BaseUrl = "http://192.168.31.2:3000/";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        btnSingup = findViewById(R.id.tv_loginSingup);
        btnLogin = findViewById(R.id.btn_loginLogin);

        btnSingup.setOnClickListener(view -> {
            Intent intent = new Intent(Login_Activity.this, Singup_Activity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            loginUser(username.getText().toString(), password.getText().toString());
        });
    }




    private void loginUser(String username, String password) {
        String LOGIN_URL = "http://10.0.2.2:3000/users/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean exists = response.getBoolean("exists");
                    if(exists){
                        String meesage = response.getString("message");
                        if(meesage.equals("Đăng nhập thành công")){
                            int role = response.getInt("role");
                            String fullname = response.getString("fullname");
                            String email = response.getString("email");
                            if(role==2){
                                User user = new User(username,password,email,fullname);
                                Toast.makeText(Login_Activity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                startActivity(intent);
                            }else if(role==1){
                                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(Login_Activity.this, "user dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Login_Activity.this, "that bai", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Login_Activity.this, "mk ko dung", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Login_Activity.this, "tk ko ton tai", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }


        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}


