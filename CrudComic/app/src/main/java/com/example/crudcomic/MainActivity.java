package com.example.crudcomic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudcomic.Interface.Interface;
import com.example.crudcomic.Interface.ItemClick;
import com.example.crudcomic.activity.DetailComic_Activity;
import com.example.crudcomic.adapter.ComicAdapter;
import com.example.crudcomic.models.Comic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rc_truyen;
    FloatingActionButton btnadd;
    ComicAdapter adapter;
    List<Comic>list ;

    EditText edt_title,edt_description,edt_author,edt_year,edt_coverImage;
    public static final String  BaseUrl = "http://192.168.31.2:3000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rc_truyen = findViewById(R.id.re_truyen);
        btnadd = findViewById(R.id.float_addcomic);
        list = new ArrayList<>();
        btnadd.setOnClickListener(view -> {
            showDialogThem();
        });
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        rc_truyen.setLayoutManager(manager);
//        getComic();
        fetchComicDataFromServer();
    }

    private  void getComic(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl) // Định nghĩa base URL của API
                .addConverterFactory(GsonConverterFactory.create()) // (sử dụng Gson Converter)
                .build();
        Interface apiService = retrofit.create(Interface.class);
        Call<List<Comic>> call = apiService.getComic();
        call.enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                if (response.isSuccessful()) {
                    list = response.body();

                    adapter = new ComicAdapter(list, new ItemClick() {
                        @Override
                        public void itemClick(Comic comic) {
                            onclickgoDetail(comic);
                        }
                    });
                    rc_truyen.setAdapter(adapter);
                } else {
                    Log.i("zzzzzzzzzzz", "API error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });
    }
    private void showDialogThem() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Thêm mới Truyện");
        alertDialog.setMessage("Vui lòng điền đủ thông tin");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.dialog_add_comic, null);
        edt_title = add_menu_layout.findViewById(R.id.edt_title);
        edt_description = add_menu_layout.findViewById(R.id.edt_description);
        edt_author = add_menu_layout.findViewById(R.id.edt_author);
        edt_year = add_menu_layout.findViewById(R.id.edt_year);
        edt_coverImage = add_menu_layout.findViewById(R.id.edt_coverImage);
        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("YES", (dialog, which) -> {
             String title=edt_title.getText().toString();
             String description=edt_description.getText().toString();
             String author=edt_author.getText().toString();
             String year =edt_year.getText().toString();
              String coverImage=edt_coverImage.getText().toString();
//             add_Comic(title,description,author,year,coverImage);
            addLSP(title,description,author,year,coverImage);
            getComic();
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private void add_Comic(String title,String description,String author,String year,String cover_image){
        String URL = "http://10.0.2.2:3000/comics";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("author", author);
            jsonObject.put("year", year);
            jsonObject.put("coverImage",cover_image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Them tryen thanh cong  ", Toast.LENGTH_SHORT).show();
                getComic();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

        private void onclickgoDetail(Comic comic){
        Intent intent = new Intent(this, DetailComic_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_comic",comic);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void fetchComicDataFromServer() {
        RequestQueue  requestQueue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:3000/comics"; // Replace with your Node.js server URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonResponse(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void parseJsonResponse(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Comic comic = new Comic();
                comic.set_id(jsonObject.getString("_id"));
                comic.setTitle(jsonObject.getString("title"));
                comic.setDescription(jsonObject.getString("description"));
                comic.setAuthor(jsonObject.getString("author"));
                comic.setYear(String.valueOf(jsonObject.getInt("year")));
                comic.setCoverImage(jsonObject.getString("coverImage"));
                // ... Parse other properties if needed
               list.add(comic);
               Log.i("zzzzzzzzzzzzzz",jsonObject.getString("_id"));
            }
            adapter = new ComicAdapter(list, new ItemClick() {
                @Override
                public void itemClick(Comic comic) {
                    onclickgoDetail(comic);
                }
            });
            rc_truyen.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addLSP(String a ,String b ,String c,String d,String e){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // Định nghĩa base URL của API
                .addConverterFactory(GsonConverterFactory.create()) // (sử dụng Gson Converter)
                .build();
// Tạo đối tượng API từ interface MyApiService
        Interface apiService = retrofit.create(Interface.class);

        Comic comic =new Comic(a ,b,c,d,e);
        Call<Comic> callAdd = apiService.addComic(comic);
        callAdd.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                Toast.makeText(MainActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Toast.makeText(MainActivity.this, "that bai", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


