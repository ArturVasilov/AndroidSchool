package ru.gdgkazan.pictureofdaymvvm.content;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class DayPicture extends BaseObservable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("explanation")
    private String mExplanation;

    @SerializedName("url")
    private String mUrl;

    @Bindable
    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    @Bindable
    @NonNull
    public String getExplanation() {
        return mExplanation;
    }

    public void setExplanation(@NonNull String explanation) {
        mExplanation = explanation;
    }

    @Bindable
    @NonNull
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(@NonNull String url) {
        mUrl = url;
    }
}
