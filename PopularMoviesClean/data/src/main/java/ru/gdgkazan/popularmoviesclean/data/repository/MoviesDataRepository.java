package ru.gdgkazan.popularmoviesclean.data.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.gdgkazan.popularmoviesclean.data.cache.MoviesCacheTransformer;
import ru.gdgkazan.popularmoviesclean.data.mapper.MoviesMapper;
import ru.gdgkazan.popularmoviesclean.data.model.response.MoviesResponse;
import ru.gdgkazan.popularmoviesclean.data.network.ApiFactory;
import ru.gdgkazan.popularmoviesclean.domain.MoviesRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Movie;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class MoviesDataRepository implements MoviesRepository {

    @Override
    public Observable<List<Movie>> popularMovies() {
        return ApiFactory.getMoviesService()
                .popularMovies()
                .map(MoviesResponse::getMovies)
                .compose(new MoviesCacheTransformer())
                .flatMap(Observable::from)
                .map(new MoviesMapper())
                .toList();
    }
}

