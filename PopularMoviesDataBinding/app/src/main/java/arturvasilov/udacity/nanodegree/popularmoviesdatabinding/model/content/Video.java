package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Video {

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @NonNull
    public String getKey() {
        return mKey;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}
