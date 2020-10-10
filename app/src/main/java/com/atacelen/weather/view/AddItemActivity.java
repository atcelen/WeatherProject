package com.atacelen.weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atacelen.weather.R;
import com.atacelen.weather.model.WeatherModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ArrayList<WeatherModel> weatherModelsList;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.newItemName);

    }

    public void addItem(View view) {
        String location = editText.getText().toString();

        try {
            database = this.openOrCreateDatabase("WeatherReports", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS weatherReports (id INTEGER PRIMARY KEY, location VARCHAR)");

            String sqlString = "INSERT INTO weatherReports (location) VALUES (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, location);
            sqLiteStatement.execute();
        } catch (Exception e){
        }

        Intent intentToMainActivity = new Intent (AddItemActivity.this, MainActivity.class);
        //intentToMainActivity.putExtra("addedWeatherItem", editText.getText().toString());
        intentToMainActivity.putExtra("IntentID", "fromAddItemActivity");
        startActivity(intentToMainActivity);

    }
}