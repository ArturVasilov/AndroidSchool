package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;

/**
 * @author Artur Vasilov
 */
public class MoviesResponse {

    @SerializedName("results")
    private List<Movie> mMovies;

    @NonNull
    public List<Movie> getMovies() {
        if (mMovies == null) {
            return new ArrayList<>();
        }
        return mMovies;
    }

}
