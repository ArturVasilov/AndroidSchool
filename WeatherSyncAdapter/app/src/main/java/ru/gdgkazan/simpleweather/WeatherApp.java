package ru.gdgkazan.simpleweather;

import android.app.Application;

import ru.arturvasilov.sqlite.core.SQLite;

/**
 * @author Artur Vasilov
 */
public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLite.initialize(this);
    }
}
