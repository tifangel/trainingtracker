package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.workout.Adapter.SportsNewsAdapter;
import com.example.workout.Model.News;
import com.example.workout.Model.RecyclerItemClickListener;
import com.example.workout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SportsNewsActivity extends AppCompatActivity {
    private RecyclerView recyclerNews;
    private List<News> newsList;
    private final String TOKEN = "571be0fed4944a4090d63fd0c45da227";
    private SportsNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView

        addData();

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

    private void addData() {
        newsList = new ArrayList<>();
        newsList.add(new News("Tribun1", "Duar HEadshot", "Rakha hamil", "google.com"));
        newsList.add(new News("Tribun2", "Duar HEadshott", "Tifany ultah", "google.com"));
        newsList.add(new News("Tribun3", "Duar HEadshottt", "Tinky hamil", "google.com"));
        newsList.add(new News("Tribun4", "Duar HEadshotttt", "Winky hamil", "google.com"));
        newsList.add(new News("Tribun5", "Duar HEadshottttt", "Dipsi hamil", "google.com"));
        newsList.add(new News("Tribun6", "Duar HEadshottttttt", "Lala hamil", "google.com"));
        newsList.add(new News("Tribun7", "Duar HEadshotttttttt", "Po hamil", "google.com"));
        newsList.add(new News("Tribun8", "Duar HEadshottttttttt", "Duar hamil", "google.com"));
        newsList.add(new News("Tribun9", "Duar HEadshotttttttttt", "HIya hamil", "google.com"));
    }
}