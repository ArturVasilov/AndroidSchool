package ru.gdgkazan.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * @author Artur Vasilov
 */
public class Author extends RealmObject {

    @SerializedName("name")
    private String mAuthorName;

    @NonNull
    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(@NonNull String authorName) {
        mAuthorName = authorName;
    }
}
