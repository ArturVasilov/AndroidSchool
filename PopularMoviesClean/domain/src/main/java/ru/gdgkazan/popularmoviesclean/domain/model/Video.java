package ru.gdgkazan.popularmoviesclean.domain.model;

/**
 * @author Artur Vasilov
 */
public class Video {

    private String mKey;
    private String mName;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
