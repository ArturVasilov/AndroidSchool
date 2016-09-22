package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api;

import android.support.annotation.NonNull;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Review;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface MoviesRepository {

    @NonNull
    Observable<List<Movie>> loadMovies(@NonNull MoviesProvider.Type type);

    @NonNull
    Observable<List<Review>> reviews(@NonNull Movie movie);

    @NonNull
    Observable<List<Video>> videos(@NonNull Movie movie);

    @NonNull
    Observable<Boolean> addToFavourite(@NonNull Movie movie);

    @NonNull
    Observable<Boolean> removeFromFavourite(@NonNull Movie movie);
}
