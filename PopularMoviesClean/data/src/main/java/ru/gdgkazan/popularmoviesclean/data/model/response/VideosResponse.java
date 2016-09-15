package ru.gdgkazan.popularmoviesclean.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.popularmoviesclean.data.model.content.Video;

/**
 * @author Artur Vasilov
 */
public class VideosResponse {

    @SerializedName("results")
    private List<Video> mVideos;

    public List<Video> getVideos() {
        if (mVideos == null) {
            return new ArrayList<>();
        }
        return mVideos;
    }
}
