package com.example.administrator.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.coolweather.gson.AQI;
import com.example.administrator.coolweather.util.Utility;

public class AqiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqi);
        WebView webView=(WebView) findViewById(R.id.aqi_webview);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String aqiString=prefs.getString("aqi",null);
        AQI aqi=(AQI) Utility.handleAQIResponse(aqiString);
        String Url=aqi.getFxLink();
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