package ru.gdgkazan.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class CommitResponse {

    @SerializedName("commit")
    private Commit mCommit;

    @NonNull
    public Commit getCommit() {
        return mCommit;
    }
}