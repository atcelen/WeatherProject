package com.atacelen.weather.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atacelen.weather.R;
import com.atacelen.weather.model.WeatherModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherItemHolder> {

    private ArrayList<WeatherModel> weatherModels;

    public WeatherRecyclerAdapter(ArrayList<WeatherModel> weatherModels) {
        this.weatherModels = weatherModels;
    }

    @NonNull
    @Override
    public WeatherItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new WeatherItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherItemHolder holder, int position) {
        holder.cityName.setText(weatherModels.get(position).name);
        holder.cityDescription.setText(weatherModels.get(position).getDescription());
        holder.cityTemperature.setText("Temperature:    "+weatherModels.get(position).getTemp()+"℃");
        holder.cityMinTemperature.setText("Minimum Temperature: "+weatherModels.get(position).getMin_temp()+"℃");
        holder.cityMaxTemperature.setText("Maximum Temperature: "+weatherModels.get(position).getMax_temp()+ "℃");
        holder.cityHumidity.setText("Humidity:  "+weatherModels.get(position).getHumidity()+"%");
        holder.cityWind.setText("Wind Speed:    "+weatherModels.get(position).getWind_speed()+" km per hour");

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.icon.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                System.out.println("failed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                System.out.println("loading");
            }
        };
        holder.icon.setTag(target);
        String url = "https://openweathermap.org/img/wn/"+weatherModels.get(position).getIcon()+"@2x.png";
        System.out.println(url);
        Picasso.get().load(url).into(target);

    }

    @Override
    public int getItemCount() {
        return weatherModels.size();
    }

    public class WeatherItemHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView cityName, cityDescription, cityTemperature, cityMinTemperature, cityMaxTemperature, cityHumidity, cityWind;


        public WeatherItemHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityDescription = itemView.findViewById(R.id.cityDescription);
            cityTemperature = itemView.findViewById(R.id.cityTemperature);
            cityMinTemperature = itemView.findViewById(R.id.cityMinTemperature);
            cityMaxTemperature = itemView.findViewById(R.id.cityMaxTemperature);
            cityHumidity = itemView.findViewById(R.id.cityHumidity);
            cityWind = itemView.findViewById(R.id.cityWindSpeed);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
