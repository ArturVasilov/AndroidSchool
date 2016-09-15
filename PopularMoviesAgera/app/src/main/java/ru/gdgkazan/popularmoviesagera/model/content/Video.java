package ru.gdgkazan.popularmoviesagera.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Video {

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @NonNull
    public String getKey() {
        return mKey;
    }

    public void setKey(@NonNull String key) {
        mKey = key;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }
}
