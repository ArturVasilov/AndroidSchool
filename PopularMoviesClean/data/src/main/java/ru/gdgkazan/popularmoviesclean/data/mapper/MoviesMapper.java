package ru.gdgkazan.popularmoviesclean.data.mapper;

import ru.gdgkazan.popularmoviesclean.domain.model.Movie;
import rx.functions.Func1;

/**
 * @author Artur Vasilov
 */
public class MoviesMapper implements Func1<ru.gdgkazan.popularmoviesclean.data.model.content.Movie, Movie> {

    @Override
    public Movie call(ru.gdgkazan.popularmoviesclean.data.model.content.Movie movie) {
        return new Movie(movie.getPosterPath(), movie.getOverview(),
                movie.getTitle(), movie.getReleasedDate(), movie.getVoteAverage());
    }
}
