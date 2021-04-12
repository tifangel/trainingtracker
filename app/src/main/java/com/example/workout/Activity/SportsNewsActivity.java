package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.workout.Adapter.SportsNewsAdapter;
import com.example.workout.Model.DefaultResponse;
import com.example.workout.Model.News;
import com.example.workout.Model.NewsResponse;
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
//        bottomNavigationView

        addData(cID, cat, TOKEN);

//        recyclerNews = (RecyclerView) findViewById(R.id.recyclerNews);
//        adapter = new SportsNewsAdapter(newsList);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SportsNewsActivity.this, 3);
//        recyclerNews.setLayoutManager(layoutManager);
//        recyclerNews.setAdapter(adapter);
//        recyclerNews.addOnItemTouchListener(
//                new RecyclerItemClickListener(SportsNewsActivity.this, recyclerNews ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
////                        News selected_news = RecyclerView.ViewHolder.getPosition(position);
//                        News selected_news = new News("Tribun", "Duar HEadshot", "Rakha hamil", "google.com");
//                        Intent i = new Intent(SportsNewsActivity.this, SportsNewsWebViewActivity.class);
//                        i.putExtra("selected_news", selected_news);
//                        startActivity(i);
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );
    }
    private void pasangAdapter(){
        recyclerNews = (RecyclerView) findViewById(R.id.recyclerNews);
        adapter = new SportsNewsAdapter(newsList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SportsNewsActivity.this, 3);
        recyclerNews.setLayoutManager(layoutManager);
        recyclerNews.setAdapter(adapter);
        recyclerNews.addOnItemTouchListener(
                new RecyclerItemClickListener(SportsNewsActivity.this, recyclerNews ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
//                        News selected_news = RecyclerView.ViewHolder.getPosition(position);
                        News selected_news = new News("Tribun", "Duar HEadshot", "Rakha hamil", "google.com");
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
                    newsList.add(new News(listOfNews.get(i).getAuthor(), listOfNews.get(i).getTitle(), listOfNews.get(i).getDescription(), listOfNews.get(i).getUrl()));
//                    Log.d("Judul berita", "Berita ke-" + String.valueOf(i) + ": " + newsList.get(i).getTitle());
                }
                pasangAdapter();

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d("Error ngambil berita", t.getMessage());
            }
        });
        for (int i = 0; i < newsList.size(); i++){
//            newsList.add(new News(listOfNews.get(i).getAuthor(), listOfNews.get(i).getTitle(), listOfNews.get(i).getDescription(), listOfNews.get(i).getUrl()));
            Log.d("Judul berita", "Berita ke-" + String.valueOf(i) + ": " + newsList.get(i).getTitle());
        }
//        newsList.add(new News("Tribun1", "Sepakbola Jose Mourinho Puji Manchester United - detikSport", "Rakha hamil", "google.com"));
//        newsList.add(new News("Tribun2", "Duar HEadshott", "Tifany ultah", "google.com"));
//        newsList.add(new News("Tribun3", "Duar HEadshottt", "Tinky hamil", "google.com"));
//        newsList.add(new News("Tribun4", "Duar HEadshotttt", "Winky hamil", "google.com"));
//        newsList.add(new News("Tribun5", "Duar HEadshottttt", "Dipsi hamil", "google.com"));
//        newsList.add(new News("Tribun6", "Duar HEadshottttttt", "Lala hamil", "google.com"));
//        newsList.add(new News("Tribun7", "Duar HEadshotttttttt", "Po hamil", "google.com"));
//        newsList.add(new News("Tribun8", "Duar HEadshottttttttt", "Duar hamil", "google.com"));
//        newsList.add(new News("Tribun9", "Duar HEadshotttttttttt", "HIya hamil", "google.com"));
    }
}