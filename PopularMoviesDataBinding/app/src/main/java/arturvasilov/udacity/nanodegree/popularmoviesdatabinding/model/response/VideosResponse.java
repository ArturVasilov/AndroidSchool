package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;

/**
 * @author Artur Vasilov
 */
public class VideosResponse {

    @SerializedName("results")
    private List<Video> mVideos;

    @NonNull
    public List<Video> getVideos() {
        return mVideos;
    }
}
