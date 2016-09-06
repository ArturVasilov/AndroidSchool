package ru.gdgkazan.simpleweather.screen.retainweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.gdgkazan.simpleweather.R;

/**
 * @author Artur Vasilov
 */
public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_retain);
        if (savedInstanceState == null) {
            WeatherFragment fragment = new WeatherFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
