package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Review;

/**
 * @author Artur Vasilov
 */
public class ReviewsResponse {

    @SerializedName("results")
    private List<Review> mReviews;

    @NonNull
    public List<Review> getReviews() {
        return mReviews;
    }
}
