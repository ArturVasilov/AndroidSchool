package ru.gdgkazan.popularmoviesclean.screen.movies;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.model.Movie;
import ru.gdgkazan.popularmoviesclean.screen.general.LoadingView;

/**
 * @author Artur Vasilov
 */
public interface MoviesView extends LoadingView {

    void showMovies(@NonNull List<Movie> movies);

    void showError();

}


