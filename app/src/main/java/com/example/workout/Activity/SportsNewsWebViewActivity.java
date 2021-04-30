package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.workout.Model.News;
import com.example.workout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SportsNewsWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private News selected_news;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news_web_view);

        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        selected_news = getIntent().getParcelableExtra("selected_news");
        Log.d("Keberadaan Berita", "Berita termuat: " + selected_news.getTitle());
        url = selected_news.getUrl();

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
                    case R.id.sports_news:
                        Intent intent_sport_news = new Intent(SportsNewsWebViewActivity.this, SportsNewsActivity.class);
                        startActivity(intent_sport_news);
                        return true;

                    case R.id.training_tracker:
                        Intent intent_training_tracker = new Intent(SportsNewsWebViewActivity.this, TrainingTrackerActivity.class);
                        startActivity(intent_training_tracker);
                        return true;
//
                    case R.id.training_history:
                        Intent intent_training_history = new Intent(SportsNewsWebViewActivity.this, TrainingHistoryActivity.class);
                        startActivity(intent_training_history);
                        return true;

                    case R.id.training_scheduler:
                        Intent intent_training_schedule = new Intent(SportsNewsWebViewActivity.this, TrainingSchedulerActivity.class);
                        startActivity(intent_training_schedule);
                        return true;
                }
                return false;
            };
}