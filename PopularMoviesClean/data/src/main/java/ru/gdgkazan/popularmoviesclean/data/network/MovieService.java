package ru.gdgkazan.popularmoviesclean.data.network;

import retrofit2.http.GET;
import ru.gdgkazan.popularmoviesclean.data.model.response.MoviesResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface MovieService {

    @GET("popular/")
    Observable<MoviesResponse> popularMovies();

}
