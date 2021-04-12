package com.example.workout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.workout.Model.News;
import com.example.workout.R;

public class SportsNewsWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private News selected_news;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news_web_view);

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
}