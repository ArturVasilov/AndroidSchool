package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.impl;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.activity.MovieDetailsActivity;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.activity.SettingsActivity;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.MoviesRouter;

/**
 * @author Artur Vasilov
 */
public class MoviesRouterImpl implements MoviesRouter {

    private final Activity mActivity;

    public MoviesRouterImpl(@NonNull Activity activity) {
        mActivity = activity;
    }

    @Override
    public void navigateToMovieScreen(@NonNull ImageView imageView, @NonNull Movie movie) {
        AppCompatActivity compatActivity = (AppCompatActivity) mActivity;
        MovieDetailsActivity.navigate(compatActivity, imageView, movie);
    }

    @Override
    public void navigateToSettingsActivity() {
        Intent intent = new Intent(mActivity, SettingsActivity.class);
        mActivity.startActivity(intent);
    }

}
