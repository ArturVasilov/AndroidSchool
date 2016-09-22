package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;

/**
 * @author Artur Vasilov
 */
public interface MoviesRouter {

    void navigateToMovieScreen(@NonNull ImageView imageView, @NonNull Movie movie);

    void navigateToSettingsActivity();

}
