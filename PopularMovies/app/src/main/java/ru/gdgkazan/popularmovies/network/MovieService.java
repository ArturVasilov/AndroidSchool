package ru.gdgkazan.popularmovies.network;

import retrofit2.http.GET;
import ru.gdgkazan.popularmovies.model.response.MoviesResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface MovieService {

    @GET("popular/")
    Observable<MoviesResponse> popularMovies();

}
