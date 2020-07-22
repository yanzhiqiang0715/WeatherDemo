package com.example.administrator.coolweather;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.coolweather.gson.AQI;
import com.example.administrator.coolweather.gson.Lifestyle;
import com.example.administrator.coolweather.gson.SearchCity;
import com.example.administrator.coolweather.gson.WeatherForecast;
import com.example.administrator.coolweather.gson.WeatherNow;
import com.example.administrator.coolweather.service.AutoUpdateService;
import com.example.administrator.coolweather.util.HttpUtil;
import com.example.administrator.coolweather.util.Utility;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.administrator.coolweather.util.Utility.handleAQIResponse;
import static com.example.administrator.coolweather.util.Utility.handleLifestyleResponse;
import static com.example.administrator.coolweather.util.Utility.handleSearchCityResponse;
import static com.example.administrator.coolweather.util.Utility.handleWeatherForecastResponse;
import static com.example.administrator.coolweather.util.Utility.handleWeatherNowResponse;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;
    private EditText cityEditText;
    private Button searchCityButton;

    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;

    public DrawerLayout drawerLayout;
    private Button navButton;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
        {
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        //初始化各控件
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        weatherLayout=(ScrollView) findViewById(R.id.weather_layout);
        titleCity=(TextView)findViewById(R.id.title_city);
        titleUpdateTime=(TextView)findViewById(R.id.title_update_time);
        degreeText=(TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_layout);
        aqiText=(TextView)findViewById(R.id.aqi_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carWashText=(TextView)findViewById(R.id.car_wash_text);
        sportText=(TextView)findViewById(R.id.sport_text);
        bingPicImg=(ImageView)findViewById(R.id.bing_pic_img);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navButton=(Button)findViewById(R.id.nav_button);
        cityEditText=findViewById(R.id.search_city);
        searchCityButton=(Button)findViewById(R.id.search_button);

        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherNowString=prefs.getString("weatherNow",null);
        String aqiString=prefs.getString("aqi",null);
        String weatherForecastString=prefs.getString("weatherForecast",null);
        String lifeStyleString=prefs.getString("lifeStyle",null);
        String searchCityString=prefs.getString("searchCity",null);

        if(weatherNowString!=null&&aqiString!=null &&
                weatherForecastString!=null &&lifeStyleString!=null && searchCityString!=null)
        {
            //有缓存时直接解析天气数据
            WeatherNow weatherNow= (WeatherNow) handleWeatherNowResponse(weatherNowString);
            AQI aqi=(AQI) handleAQIResponse(aqiString);
            WeatherForecast weatherForecast=(WeatherForecast) handleWeatherForecastResponse(weatherForecastString);
            Lifestyle lifestyle=(Lifestyle) handleLifestyleResponse(lifeStyleString);
            SearchCity searchCity=(SearchCity) handleSearchCityResponse(searchCityString);
            mWeatherId=searchCity.getLocation().get(0).getId();
            System.out.println(mWeatherId);
            showWeatherNowInfo(weatherNow);
            showAQIInfo(aqi);
            showCityNameInfo(searchCity);
            showWeatherForecastInfo(weatherForecast);
            showLifeStyleInfo(lifestyle);

        }else
        {   //无缓存时,去服务器查询天气
            mWeatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println(mWeatherId);
                requestWeather(mWeatherId);
            }
        });

        /**
         * 读取缓存在SharedPreference的pic数据,
         */
        String bingPic=prefs.getString("bing_pic",null);
        if(bingPic!=null)
        {
            Glide.with(this).load(bingPic).into(bingPicImg);
        }
        else
        {
            loadBingPic();
        }
//yt
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
    /**
     * 查找城市
     */
    public void searchOnClick(View view){
        String searchName=cityEditText.getText().toString().trim();
        searchCityId(searchName);
        cityEditText.setText("");
    }
    private void searchCityId(String searchName){
        String cityUrl="https://geoapi.heweather.net/v2/city/lookup?location="+searchName.toString()+
                "&key=616d9568e63a4fadaec74888f36fc1a4";
        HttpUtil.sendOkHttpRequest(cityUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(WeatherActivity.this,"搜索失败onFailure",Toast.LENGTH_LONG).show();
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final SearchCity searchCity=handleSearchCityResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(searchCity.getStatus());
                        if((searchCity!=null)&& "200".equals(searchCity.getStatus())){
                            String searchedWeatherId=searchCity.getLocation().get(0).getId();
                            requestWeather(searchedWeatherId);
                        }
                        else if((searchCity!=null)&& "404".equals(searchCity.getStatus())){
                            Toast.makeText(WeatherActivity.this,"无此城市",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 天气点击事件
     */
    public void nowOnClick(View view){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherNowString=prefs.getString("weatherNow",null);
        if (weatherNowString!=null){
            Intent intent=new Intent(this,WeatherNowActivity.class);
            startActivity(intent);
        }
    }
    public void forecastOnClick(View view){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherForecastString=prefs.getString("weatherForecast",null);
        if (weatherForecastString!=null){
            Intent intent=new Intent(this,WeatherNowActivity.class);
            startActivity(intent);
        }
    }
    /**
     * aqi点击事件
     */
    public void aqiOnClick(View view){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String aqiString=prefs.getString("aqi",null);
        if (aqiString!=null){
            Intent intent=new Intent(this,AqiActivity.class);
            startActivity(intent);
        }
    }
    /**
     * 生活指数点击事件
     */
    public void lifeStyleOnClick(View view){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String lifeStyleString=prefs.getString("lifeStyle",null);
        if (lifeStyleString!=null){
            Intent intent=new Intent(this,LifeStyleActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingpic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingpic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingpic).into(bingPicImg);
                    }
                });
            }
        });
    }


    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(String weatherId)
    {
        final String weatherUrl="https://devapi.heweather.net/v7/weather/now?location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
        final String aqiUrl="https://devapi.heweather.net/v7/air/now?location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
        final String weatherForecastUrl="https://devapi.heweather.net/v7/weather/3d?location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
        final String lifeStyleUrl="https://devapi.heweather.net/v7/indices/1d?type=0&location="+weatherId.toString()+"&key=616d9568e63a4fadaec74888f36fc1a4";
        String cityUrl="https://geoapi.heweather.net/v2/city/lookup?location="+weatherId.toString()+
                "&key=616d9568e63a4fadaec74888f36fc1a4";
        HttpUtil.sendOkHttpRequest(cityUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(WeatherActivity.this,"搜索失败onFailure",Toast.LENGTH_LONG).show();
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final SearchCity searchCity=handleSearchCityResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("searchCity:"+searchCity.getStatus());
                        if((searchCity!=null)&& "200".equals(searchCity.getStatus())){
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("searchCity",responseText);
                            editor.apply();
                            mWeatherId=searchCity.getLocation().get(0).getId();
                            showCityNameInfo(searchCity);
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        /**
         * 这是对基本天气的访问,但是缺了aqi这一项
         */
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败onFailure",Toast.LENGTH_LONG).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final WeatherNow weatherNow=handleWeatherNowResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("weatherNow:"+weatherNow.getCode());
                        if((weatherNow != null) && "200".equals(weatherNow.getCode()))
                        {
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weatherNow",responseText);
                            editor.apply();
                            showWeatherNowInfo(weatherNow);

                        }else
                        {
                            Toast.makeText(WeatherActivity.this, responseText, Toast.LENGTH_SHORT).show();

                        }
                        swipeRefresh.setRefreshing(false);
                    }


                });

            }
        });

        HttpUtil.sendOkHttpRequest(weatherForecastUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败onFailure",Toast.LENGTH_LONG).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final WeatherForecast weatherForecast=handleWeatherForecastResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("weatherForecast："+weatherForecast.getCode());
                        if((weatherForecast != null) && "200".equals(weatherForecast.getCode()))
                        {
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weatherForecast",responseText);
                            editor.apply();
                            showWeatherForecastInfo(weatherForecast);

                        }else
                        {
                            Toast.makeText(WeatherActivity.this, responseText, Toast.LENGTH_SHORT).show();

                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        HttpUtil.sendOkHttpRequest(lifeStyleUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败onFailure",Toast.LENGTH_LONG).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final Lifestyle lifestyle=handleLifestyleResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("lifeStyle:"+lifestyle.getCode());
                        if((lifestyle != null) && "200".equals(lifestyle.getCode()))
                        {
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("lifeStyle",responseText);
                            editor.apply();
                            showLifeStyleInfo(lifestyle);

                        }else if ((lifestyle!=null)&& "403".equals(lifestyle.getCode())){
                            comfortText.setText("舒适度：无");
                            carWashText.setText("洗车指数：无");
                            sportText.setText("运动指数：无");
                        }
                        else
                        {
                            Toast.makeText(WeatherActivity.this, responseText, Toast.LENGTH_SHORT).show();

                        }
                        swipeRefresh.setRefreshing(false);
                    }


                });

            }
        });

        HttpUtil.sendOkHttpRequest(aqiUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败onFailure",Toast.LENGTH_LONG).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException{
                final String responseText=response.body().string();
                final AQI aqi=Utility.handleAQIResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("aqi:"+aqi.getCode());
                        if((aqi!=null)&&"200".equals(aqi.getCode()) ){
                            SharedPreferences.Editor editor=PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("aqi",responseText);
                            editor.apply();
                            showAQIInfo(aqi);
                        }else if ((aqi!=null)&& "403".equals(aqi.getCode())){
                            aqiText.setText("无");
                            pm25Text.setText("无");
                        }

                        else{
                            Toast.makeText(WeatherActivity.this,responseText,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void showCityNameInfo(SearchCity searchCity) {
        titleCity.setText(searchCity.getLocation().get(0).getName());
    }

    private void showLifeStyleInfo(Lifestyle lifestyle) {
        comfortText.setText("舒适度："+lifestyle.getDaily().get(12).getText());
        carWashText.setText("洗车指数："+lifestyle.getDaily().get(14).getText());
        sportText.setText("运动指数："+lifestyle.getDaily().get(15).getText());
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void showAQIInfo(AQI aqi) {

        if(aqi!=null)
        {
            aqiText.setText(aqi.getNow().getAqi());
            pm25Text.setText(aqi.getNow().getPm2p5());
        }

    }


    private void showWeatherNowInfo(WeatherNow weatherNow) {

        String degree=weatherNow.getNow().getTemp()+"℃";
        String weatherInfo=weatherNow.getNow().getText();
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        titleUpdateTime.setText(weatherNow.getUpdateTime());
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent=new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    private void showWeatherForecastInfo(WeatherForecast weatherForecast){
        forecastLayout.removeAllViews();

        for(int i=0;i<3;i++ )
        {View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dataText=(TextView) view.findViewById(R.id.data_text);
            TextView infoText=(TextView) view.findViewById(R.id.info_text);
            TextView maxText=(TextView)view.findViewById(R.id.max_text);
            TextView minText=(TextView)view.findViewById(R.id.min_text);

            dataText.setText(weatherForecast.getDaily().get(i).getFxDate());
            infoText.setText(weatherForecast.getDaily().get(i).getTextDay());
            maxText.setText(weatherForecast.getDaily().get(i).getTempMax()+"℃");
            minText.setText(weatherForecast.getDaily().get(i).getTempMin()+"℃");
            forecastLayout.addView(view);
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
