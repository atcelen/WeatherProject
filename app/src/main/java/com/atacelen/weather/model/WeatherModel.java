package com.atacelen.weather.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class WeatherModel {

        @SerializedName("name")
        public String name;
        @SerializedName("weather")
        public JsonArray weather;
        @SerializedName("main")
        public JsonObject main_weather;
        @SerializedName("wind")
        public JsonObject wind;

        String description;
        String icon;
        String temp;
        String min_temp;
        String max_temp;
        String humidity;
        String wind_speed;

        public void initiate() {
                description = this.weather.get(0).getAsJsonObject().get("description").getAsString();
                icon = this.weather.get(0).getAsJsonObject().get("icon").getAsString();
                temp = this.main_weather.get("temp").getAsString();
                min_temp = this.main_weather.get("temp_min").getAsString();
                max_temp = this.main_weather.get("temp_max").getAsString();
                humidity = this.main_weather.get("humidity").getAsString();
                wind_speed = this.wind.get("speed").getAsString();
        }

        public String getDescription() {
                return description;
        }

        public String getIcon() {
                return icon;
        }

        public String getTemp() {
                return temp;
        }

        public String getMin_temp() {
                return min_temp;
        }

        public String getMax_temp() {
                return max_temp;
        }

        public String getHumidity() {
                return humidity;
        }

        public String getWind_speed() {
                return wind_speed;
        }
}
