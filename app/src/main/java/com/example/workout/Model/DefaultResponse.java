package com.example.workout.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private List<News> articles;
}
