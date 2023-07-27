package com.example.crudcomic.Interface;


import com.example.crudcomic.models.Comic;
import com.example.crudcomic.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Interface {
    // api user
    @FormUrlEncoded
    @GET("/users")
    Call<List<User>> getuser();
    @GET("/comics")
    Call<List<Comic>> getComic();

    @POST("/comics")
    Call<Comic> addComic(@Body Comic comic);

    @DELETE("comics/{id}")
    Call<Void> deleteLsp(@Path("id") String id);


    @PUT("comics/{id}")
    Call<Comic> update(@Path("id") String id,@Body Comic comic);
}

