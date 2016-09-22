package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api;

import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response.MoviesResponse;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response.ReviewsResponse;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response.VideosResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface MovieService {

    @GET("popular/")
    Observable<MoviesResponse> popularMovies();

    @GET("top_rated/")
    Observable<MoviesResponse> topRatedMovies();

    @GET("{id}/reviews")
    Observable<ReviewsResponse> reviews(@Path("id") @NonNull String id);

    @GET("{id}/videos")
    Observable<VideosResponse> video(@Path("id") @NonNull String id);

}
