package com.sp.vigour.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.sp.vigour.R;

public class AboutWebview extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_webview);

        webview = findViewById(R.id.webview);

        String url = getIntent().getStringExtra("userurl");

        webview.setWebViewClient(new WebViewClient());

        webview.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }else {

            super.onBackPressed();
        }
    }
}