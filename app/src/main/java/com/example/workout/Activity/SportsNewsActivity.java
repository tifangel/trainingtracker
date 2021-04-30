package com.example.workout.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.workout.Adapter.SportsNewsAdapter;
import com.example.workout.Model.DefaultResponse;
import com.example.workout.Model.News;
import com.example.workout.Model.RecyclerItemClickListener;
import com.example.workout.Model.RetrofitClient;
import com.example.workout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsNewsActivity extends AppCompatActivity {
    private RecyclerView recyclerNews;
    private List<News> newsList;
    private final String cID = "id";
    private final String cat = "sports";
    private final String TOKEN = "571be0fed4944a4090d63fd0c45da227";
    private SportsNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news);

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        addData(cID, cat, TOKEN);
    }

    private void pasangAdapter(List<News> newsList){
        recyclerNews = (RecyclerView) findViewById(R.id.recyclerNews);
        adapter = new SportsNewsAdapter(newsList);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SportsNewsActivity.this, 3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SportsNewsActivity.this);
        recyclerNews.setLayoutManager(layoutManager);
        recyclerNews.setAdapter(adapter);
        recyclerNews.addOnItemTouchListener(
                new RecyclerItemClickListener(SportsNewsActivity.this, recyclerNews ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        News selected_news = newsList.get(position);
                        Intent i = new Intent(SportsNewsActivity.this, SportsNewsWebViewActivity.class);
                        i.putExtra("selected_news", selected_news);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void addData(String country, String category, String API_key) {
        newsList = new ArrayList<>();
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllNews(country, category, API_key);
        call.enqueue(new Callback<DefaultResponse>(){

            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.d("Masuk onResponse", "Masuk");
                List<News> listOfNews = response.body().getArticles();
                Log.d("Jumlah isi listnya", String.valueOf(newsList.size()));
                for (int i = 0; i < listOfNews.size(); i++){
                    newsList.add(new News(listOfNews.get(i).getAuthor(), listOfNews.get(i).getTitle(), listOfNews.get(i).getDescription(), listOfNews.get(i).getUrl(), listOfNews.get(i).getUrlToImage()));
                }
                pasangAdapter(newsList);
                for (int i = 0; i < newsList.size(); i++){
//                    Log.d("Judul berita", "Berita ke-" + String.valueOf(i) + ": " + newsList.get(i).getTitle());
                    Log.d("Gambar berita", "Link gambar berita ke-" + String.valueOf(i) + ": " + newsList.get(i).getUrlToImage());
                }

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d("Error ngambil berita", t.getMessage());
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
//                    case R.id.sports_news:
//                        Intent intent_sport_news = new Intent(SportsNewsActivity.this, SportsNewsActivity.class);
//                        startActivity(intent_sport_news);
//                        return true;

                    case R.id.training_tracker:
                        Intent intent_training_tracker = new Intent(SportsNewsActivity.this, TrainingTrackerActivity.class);
                        startActivity(intent_training_tracker);
                        overridePendingTransition(0, 0);
                        return true;
//
                    case R.id.training_history:
                        Intent intent_training_history = new Intent(SportsNewsActivity.this, TrainingHistoryActivity.class);
                        startActivity(intent_training_history);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.training_scheduler:
                        Intent intent_training_schedule = new Intent(SportsNewsActivity.this, TrainingSchedulerActivity.class);
                        startActivity(intent_training_schedule);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            };
}
