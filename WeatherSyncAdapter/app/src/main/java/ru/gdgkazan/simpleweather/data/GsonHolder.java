package ru.gdgkazan.simpleweather.data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

/**
 * @author Artur Vasilov
 */
public class GsonHolder {

    private static final Gson GSON = new Gson();

    @NonNull
    public static Gson getGson() {
        return GSON;
    }
}
