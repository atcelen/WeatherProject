package com.atacelen.weather.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.atacelen.weather.R;
import com.atacelen.weather.adapter.WeatherRecyclerAdapter;
import com.atacelen.weather.model.WeatherModel;
import com.atacelen.weather.service.WeatherAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=3a6c0733f5817ec1771722c473f775b1

public class MainActivity extends AppCompatActivity {

    private ArrayList<WeatherModel> weatherModelsList = new ArrayList<>();
    private String BASE_URL = "https://api.openweathermap.org/";

    Retrofit retrofit;
    WeatherRecyclerAdapter weatherRecyclerAdapter;
    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerAdapter = new WeatherRecyclerAdapter(weatherModelsList);
        recyclerView.setAdapter(weatherRecyclerAdapter);

        getData();
    }

    private void loadData(String location) {
        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<WeatherModel> call = weatherAPI.getData(location);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if(response.isSuccessful()) {
                    WeatherModel weatherModels = response.body();
                    weatherModels.initiate();
                    weatherModelsList.add(weatherModels);
                    weatherRecyclerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    /*

    private void loadData(String location) {
        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(weatherAPI.getData(location)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse));
    }

    private void handleResponse(WeatherModel weatherModels) {
        weatherModels.initiate();
        weatherModelsList.add(weatherModels);
        weatherRecyclerAdapter.notifyDataSetChanged();
    }

    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

     */




    public void getData(){
        try{
            SQLiteDatabase database = this.openOrCreateDatabase("WeatherReports", MODE_PRIVATE, null);

            Cursor cursor = database.rawQuery("SELECT * FROM weatherReports", null);
            int nameIx = cursor.getColumnIndex("location");

            while(cursor.moveToNext()){
                loadData(cursor.getString(nameIx));
            }

            cursor.close();

        } catch (Exception e) {
            System.out.println("catch");
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_weather_report) {
            Intent intentToItem = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intentToItem);
        }

        return super.onOptionsItemSelected(item);
    }
}