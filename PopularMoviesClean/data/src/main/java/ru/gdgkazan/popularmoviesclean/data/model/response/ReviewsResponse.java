package ru.gdgkazan.popularmoviesclean.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.popularmoviesclean.data.model.content.Review;

/**
 * @author Artur Vasilov
 */
public class ReviewsResponse {

    @SerializedName("results")
    private List<Review> mReviews;

    public List<Review> getReviews() {
        if (mReviews == null) {
            mReviews = new ArrayList<>();
        }
        return mReviews;
    }
}
