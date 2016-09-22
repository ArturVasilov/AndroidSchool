package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.MoviesRepository;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Review;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TestMoviesRepository implements MoviesRepository {

    private final List<Movie> mMovies;

    public TestMoviesRepository() {
        mMovies = new ArrayList<>();
    }

    public void setMovies(@NonNull List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
    }

    @NonNull
    @Override
    public Observable<List<Movie>> loadMovies(@NonNull MoviesProvider.Type type) {
        return Observable.just(mMovies);
    }

    @NonNull
    @Override
    public Observable<List<Review>> reviews(@NonNull Movie movie) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<List<Video>> videos(@NonNull Movie movie) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<Boolean> addToFavourite(@NonNull Movie movie) {
        return Observable.just(true);
    }

    @NonNull
    @Override
    public Observable<Boolean> removeFromFavourite(@NonNull Movie movie) {
        return Observable.just(false);
    }
}
