package ru.gdgkazan.popularmoviesclean.domain.usecase;

import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.MoviesRepository;
import ru.gdgkazan.popularmoviesclean.domain.model.Movie;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class MoviesUseCase {

    private final MoviesRepository mRepository;
    private final Observable.Transformer<List<Movie>, List<Movie>> mAsyncTransformer;

    public MoviesUseCase(MoviesRepository repository,
                         Observable.Transformer<List<Movie>, List<Movie>> asyncTransformer) {
        mRepository = repository;
        mAsyncTransformer = asyncTransformer;
    }

    public Observable<List<Movie>> popularMovies() {
        return mRepository.popularMovies()
                .compose(mAsyncTransformer);
    }
}


