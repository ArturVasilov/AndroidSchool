package arturvasilov.udacity.nanodegree.popularmoviesdatabinding;

import android.app.Application;
import android.content.ContentResolver;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.ApiFactory;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.MoviesRepository;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.MoviesRepositoryImpl;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class AppDelegate extends Application {

    private static AppDelegate sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        MoviesRepository repository = new MoviesRepositoryImpl(ApiFactory.getMoviesService());
        RepositoryProvider.setRepository(repository);
    }

    @NonNull
    public static AppDelegate getAppContext() {
        return sInstance;
    }

    @NonNull
    public static ContentResolver getDb() {
        return sInstance.getContentResolver();
    }
}
