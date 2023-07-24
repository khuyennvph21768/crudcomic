package com.example.crudcomic.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudcomic.MainActivity;
import com.example.crudcomic.R;
import com.example.crudcomic.models.Comic;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailComic_Activity extends AppCompatActivity {
    ImageView imageView;
    Button btn_xoa,btn_update;
    TextView tv_tentitle,tv_dedecristion,tv_deathor,tv_deyear;
    EditText edt_title,edt_description,edt_author,edt_year,edt_coverImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        anhxa();
        getData();
        btn_xoa.setOnClickListener(view -> onpedialog_delete());
        btn_update.setOnClickListener(view -> {
          showDialogSua();
        });
    }

    private void getData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            return;
        }
        final Comic comic = (Comic) bundle.get("object_comic");
        Picasso.get().load(comic.getCoverImage()).into(imageView);
        Log.i("aaaaaaa",comic.get_id());
        Log.i("zzzz",comic.getAuthor());
        tv_tentitle.setText("Tên truyện : " + comic.getTitle());
        tv_dedecristion.setText("Mô tả : " + comic.getDescription());
        tv_deathor.setText("tác giả : " + comic.getAuthor());
        tv_deyear.setText("Năm : " + comic.getYear());
    }

    private void onpedialog_delete() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Bạn có chăc muốn xóa");
        alertDialog.setPositiveButton("YES", (dialog, which) -> {
            Bundle bundle = getIntent().getExtras();
            final Comic comic = (Comic) bundle.get("object_comic");
            DeleteComic(comic.get_id());
            Toast.makeText(this, "xóa thành công", Toast.LENGTH_SHORT).show();
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void DeleteComic(String id) {
        String URL = "http://10.0.2.2:3000/comics/"+id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest  jsonObjectRequest  = new JsonObjectRequest(Request.Method.DELETE, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent  intent = new Intent(DetailComic_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void anhxa(){
        imageView = findViewById(R.id.img_detail);
        tv_tentitle = findViewById(R.id.tv_detitle);
        tv_dedecristion=findViewById(R.id.tv_dedecristion);
        tv_deathor=findViewById(R.id.tv_deathor);
        tv_deyear=findViewById(R.id.tv_deyear);
        btn_xoa = findViewById(R.id.btn_xoa);
        btn_update =findViewById(R.id.btn_update);
    }
    private void showDialogSua() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailComic_Activity.this);
        alertDialog.setTitle("Update Truyện");
        alertDialog.setMessage("Vui lòng điền đủ thông tin");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.dialog_add_comic, null);
        edt_title = add_menu_layout.findViewById(R.id.edt_title);
        edt_description = add_menu_layout.findViewById(R.id.edt_description);
        edt_author = add_menu_layout.findViewById(R.id.edt_author);
        edt_year = add_menu_layout.findViewById(R.id.edt_year);
        edt_coverImage = add_menu_layout.findViewById(R.id.edt_coverImage);
        Bundle bundle = getIntent().getExtras();
        final Comic comic = (Comic) bundle.get("object_comic");
        edt_title.setText( comic.getTitle());
        edt_description.setText(comic.getDescription());
        edt_author.setText(comic.getAuthor());
        edt_year.setText( comic.getYear());
        edt_coverImage.setText( comic.getCoverImage());
        alertDialog.setView(add_menu_layout);

        alertDialog.setPositiveButton("YES", (dialog, which) -> {
            String title=edt_title.getText().toString();
            String description=edt_description.getText().toString();
            String author=edt_author.getText().toString();
            String year =edt_year.getText().toString();
            String coverImage=edt_coverImage.getText().toString();
            Bundle bundle1 = getIntent().getExtras();
            final Comic comic1 = (Comic) bundle1.get("object_comic");

            update_Comic(comic1.get_id(),title,description,author,year,coverImage);
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void update_Comic(String id ,String title,String description,String author,String year,String cover_image){
        String URL = "http://10.0.2.2:3000/comics/"+id;
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, URL, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(DetailComic_Activity.this, "Sua tryen thanh cong ", Toast.LENGTH_SHORT).show();
                Intent  intent = new Intent(DetailComic_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}