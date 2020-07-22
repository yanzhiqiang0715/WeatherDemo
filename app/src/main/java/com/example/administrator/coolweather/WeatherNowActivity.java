package com.example.administrator.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.coolweather.gson.WeatherNow;
import com.example.administrator.coolweather.util.Utility;

public class WeatherNowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_now);
        WebView webView=(WebView) findViewById(R.id.weather_now_webview);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherNowString=prefs.getString("weatherNow",null);
        WeatherNow weatherNow=(WeatherNow) Utility.handleWeatherNowResponse(weatherNowString);
        String Url=weatherNow.getFxLink();
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