package ru.gdgkazan.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * @author Artur Vasilov
 */
public class Commit extends RealmObject {

    private String mRepoName;

    @SerializedName("author")
    private Author mAuthor;

    @SerializedName("message")
    private String mMessage;

    @NonNull
    public String getRepoName() {
        return mRepoName;
    }

    public void setRepoName(@NonNull String repoName) {
        mRepoName = repoName;
    }

    @NonNull
    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(@NonNull Author author) {
        mAuthor = author;
    }

    @NonNull
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(@NonNull String message) {
        mMessage = message;
    }
}
