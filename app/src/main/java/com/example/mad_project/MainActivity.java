package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchWeatherData();
    }

    private void fetchWeatherData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);


        Call<WeatherApp> call = api.getWeatherData(
                "mumbai",
                "2da88a9816ec53d6f29917f2c2996255",
                "metric"
        );


        call.enqueue(new Callback<WeatherApp>() {
            @Override
            public void onResponse(Call<WeatherApp> call, Response<WeatherApp> response) {
                if (response.isSuccessful() && response.body() != null) {

                    WeatherApp data = response.body();



                    double temperature = data.getMain().getTemp();


                    Log.d(TAG, "onResponse: Temp = " + temperature);
                } else {
                    Log.d(TAG, "onResponse: Error -> " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherApp> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}





