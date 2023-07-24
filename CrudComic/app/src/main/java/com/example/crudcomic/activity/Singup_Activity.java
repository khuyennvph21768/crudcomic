package com.example.crudcomic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudcomic.Interface.Interface;
import com.example.crudcomic.MainActivity;
import com.example.crudcomic.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singup_Activity extends AppCompatActivity {
EditText edfullname,edemail,eduser,edpass;
Button btnsingup;
TextView tvlogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        edfullname = findViewById(R.id.edt_fullname);
        edemail=findViewById(R.id.edt_email);
        eduser = findViewById(R.id.edt_usernamesingup);
        edpass = findViewById(R.id.edt_passsingup);
        btnsingup = findViewById(R.id.btn_registerRegister);
        tvlogin = findViewById(R.id.tv_CoTk);
        tvlogin.setOnClickListener(view -> {
            Intent intent = new Intent(Singup_Activity.this,Login_Activity.class);
            startActivity(intent);
        });
        btnsingup.setOnClickListener(view -> {
            Sinup(eduser.getText().toString(),
                    edpass.getText().toString(),
                    edemail.getText().toString(),
                    edfullname.getText().toString());
        });
    }

    private void Sinup(String user,String pass, String email ,String fullname ) {
        String SINGUP_URL = "http://10.0.2.2:3000/users/user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user);
            jsonObject.put("password", pass);
            jsonObject.put("email", email);
            jsonObject.put("fullname", fullname);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SINGUP_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean exists = response.getBoolean("exists");
                    Log.i("zzzz",String.valueOf(exists));
                    if(exists){
                        Toast.makeText(Singup_Activity.this, "tai khoan da ton tai", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Singup_Activity.this, "dang ky thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Singup_Activity.this, Login_Activity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}