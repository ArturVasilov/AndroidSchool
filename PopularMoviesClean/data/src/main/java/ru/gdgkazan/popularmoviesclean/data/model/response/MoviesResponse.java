package ru.gdgkazan.popularmoviesclean.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.popularmoviesclean.data.model.content.Movie;

/**
 * @author Artur Vasilov
 */
public class MoviesResponse {

    @SerializedName("results")
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        if (mMovies == null) {
            return new ArrayList<>();
        }
        return mMovies;
    }

}
