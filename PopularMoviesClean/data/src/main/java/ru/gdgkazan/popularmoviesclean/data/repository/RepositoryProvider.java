package ru.gdgkazan.popularmoviesclean.data.repository;

import ru.gdgkazan.popularmoviesclean.domain.MoviesRepository;

/**
 * @author Artur Vasilov
 */
public class RepositoryProvider {

    private static MoviesRepository sMoviesRepository;

    public static MoviesRepository getMoviesRepository() {
        if (sMoviesRepository == null) {
            sMoviesRepository = new MoviesDataRepository();
        }
        return sMoviesRepository;
    }

    public static void setMoviesRepository(MoviesRepository moviesRepository) {
        sMoviesRepository = moviesRepository;
    }
}
