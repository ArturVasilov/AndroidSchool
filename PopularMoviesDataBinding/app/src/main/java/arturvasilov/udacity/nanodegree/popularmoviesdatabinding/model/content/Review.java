package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Review {

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("content")
    private String mContent;

    @NonNull
    public String getAuthor() {
        return mAuthor;
    }

    @NonNull
    public String getContent() {
        return mContent;
    }
}
