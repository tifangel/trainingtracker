package com.example.workout.Api;

import com.example.workout.Model.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface Api {
    @GET("top-headlines")
    Call<DefaultResponse> getAllNews(
            @Field("country") String country,
            @Field("category") String category,
            @Field("apiKey") String apiKey
    );
}
