package com.example.crudcomic.Interface;


import com.example.crudcomic.models.Comic;
import com.example.crudcomic.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Interface {
    // api user
    @FormUrlEncoded
    @GET("/users")
    Call<List<User>> getuser();
    @GET("/comics")
    Call<List<Comic>> getComic();

}

