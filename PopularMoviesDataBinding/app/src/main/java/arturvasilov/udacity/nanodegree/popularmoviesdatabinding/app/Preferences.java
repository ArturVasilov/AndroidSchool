package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.AppDelegate;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public class Preferences {

    public static final String SETTINGS_NAME = "movies_prefs";

    private static final String TYPE_KEY = "movies_type_key";

    private static final String POPULAR_MOVIE_TYPE = "popular";
    private static final String TOP_RATED_MOVIE_TYPE = "top_rated";

    @NonNull
    public static MoviesProvider.Type getMoviesType() {
        SharedPreferences prefs = getPrefs();
        if (!prefs.contains(TYPE_KEY)) {
            prefs.edit().putString(TYPE_KEY, POPULAR_MOVIE_TYPE).apply();
            return MoviesProvider.Type.POPULAR;
        }

        String type = prefs.getString(TYPE_KEY, "");
        if (TextUtils.equals(type, POPULAR_MOVIE_TYPE)) {
            return MoviesProvider.Type.POPULAR;
        }
        if (TextUtils.equals(type, TOP_RATED_MOVIE_TYPE)) {
            return MoviesProvider.Type.TOP_RATED;
        }
        return MoviesProvider.Type.FAVOURITE;
    }

    @NonNull
    public static SharedPreferences getPrefs() {
        return AppDelegate.getAppContext()
                .getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

}
