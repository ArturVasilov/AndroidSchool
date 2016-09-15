package ru.gdgkazan.popularmoviesclean.data.model.content;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * @author Artur Vasilov
 */
public class Review extends RealmObject {

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("content")
    private String mContent;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
