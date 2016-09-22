package ru.gdgkazan.pictureofdaymvvm;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import ru.gdgkazan.pictureofdaymvvm.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class DayPictureApp extends Application {

    private static DayPictureApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        RepositoryProvider.init();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    @NonNull
    public static DayPictureApp getAppContext() {
        return sInstance;
    }
}
