package com.example.administrator.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import com.example.administrator.coolweather.gson.AQI;
import com.example.administrator.coolweather.gson.Lifestyle;
import com.example.administrator.coolweather.gson.SearchCity;
import com.example.administrator.coolweather.gson.WeatherForecast;
import com.example.administrator.coolweather.gson.WeatherNow;
import com.example.administrator.coolweather.util.HttpUtil;
import com.example.administrator.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
     return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour=4*60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherNowString=prefs.getString("weatherNow",null);
        String weatherForecastString=prefs.getString("weatherForecast",null);
        String aqiString=prefs.getString("aqi",null);
        String lifeStyleString=prefs.getString("lifeStyle",null);
        String searchCityString=prefs.getString("searchCity",null);
        if(weatherNowString!=null&&aqiString!=null &&
                weatherForecastString!=null &&lifeStyleString!=null && searchCityString!=null )
        {
            WeatherNow weatherNow= Utility.handleWeatherNowResponse(weatherNowString);
            final WeatherForecast weatherForecast=Utility.handleWeatherForecastResponse(weatherForecastString);
            AQI aqi =Utility.handleAQIResponse(aqiString);
            final Lifestyle lifestyle=Utility.handleLifestyleResponse(lifeStyleString);
            SearchCity searchCity=Utility.handleSearchCityResponse(searchCityString);
            String weatherId=searchCity.getLocation().get(0).getId();

            final String weatherUrl="https://devapi.heweather.net/v7/weather/now?location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
            final String aqiUrl="https://devapi.heweather.net/v7/air/now?location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
            final String weatherForecastUrl="https://devapi.heweather.net/v7/weather/3d?location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
            final String lifeStyleUrl="https://devapi.heweather.net/v7/indices/1d?type=0&location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
            String cityUrl="https://geoapi.heweather.net/v2/city/lookup?location="+weatherId.toString()+
                    "&key=616d9568e63a4fadaec74888f36fc1a4";

            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();

                WeatherNow weatherNow1=Utility.handleWeatherNowResponse(responseText);

                if((weatherNow1 != null) && "200".equals(weatherNow1.getCode()))
                {
                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                    editor.putString("weatherNow",responseText);
                    editor.apply();
                }
            }
        });
            HttpUtil.sendOkHttpRequest(weatherForecastUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body().string();

                    WeatherForecast weatherForecast1=Utility.handleWeatherForecastResponse(responseText);

                    if((weatherForecast1 != null) && "200".equals(weatherForecast1.getCode()))
                    {
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weatherForecast",responseText);
                        editor.apply();
                    }
                }
            });
            HttpUtil.sendOkHttpRequest(aqiUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body().string();

                    AQI aqi1=Utility.handleAQIResponse(responseText);

                    if((aqi1 != null) && "200".equals(aqi1.getCode()))
                    {
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("aqi",responseText);
                        editor.apply();
                    }
                }
            });

            HttpUtil.sendOkHttpRequest(lifeStyleUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body().string();

                    Lifestyle lifestyle1=Utility.handleLifestyleResponse(responseText);

                    if((lifestyle1 != null) && "200".equals(lifestyle1.getCode()))
                    {
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("lifeStyle",responseText);
                        editor.apply();
                    }
                }
            });
            HttpUtil.sendOkHttpRequest(cityUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body().string();

                    SearchCity searchCity1=Utility.handleSearchCityResponse(responseText);

                    if((searchCity1 != null) && "200".equals(searchCity1.getStatus()))
                    {
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("searchCity",responseText);
                        editor.apply();
                    }
                }
            });
    }
    }
    private void updateBingPic() {
        final String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
            }
        });

    }
}
