package ru.gdgkazan.popularmoviesagera.network;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.gdgkazan.popularmoviesagera.model.response.MoviesResponse;

/**
 * @author Artur Vasilov
 */
public interface MovieService {

    @GET("popular/")
    Call<MoviesResponse> popularMovies();

}
