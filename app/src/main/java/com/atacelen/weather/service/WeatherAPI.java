package com.atacelen.weather.service;

import com.atacelen.weather.model.WeatherModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPI {


    @GET("data/2.5/weather?units=metric&appid=3a6c0733f5817ec1771722c473f775b1")
    Call<WeatherModel> getData(@Query("q") String location);

}
