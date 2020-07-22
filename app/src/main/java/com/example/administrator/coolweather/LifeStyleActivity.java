package com.example.administrator.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.coolweather.gson.Lifestyle;
import com.example.administrator.coolweather.util.Utility;

public class LifeStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);
        WebView webView=(WebView) findViewById(R.id.lifestyle_webview);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String lifeStyleString=prefs.getString("lifeStyle",null);
        Lifestyle lifestyle=(Lifestyle) Utility.handleLifestyleResponse(lifeStyleString);
        String Url=lifestyle.getFxLink();
        webView.loadUrl(Url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });
    }
}