package ru.gdgkazan.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

/**
 * @author Artur Vasilov
 */
public final class GsonHolder {

    private GsonHolder() {
    }

    @NonNull
    public static Gson getGson() {
        return Holder.GSON;
    }

    public static final class Holder {
        private static final Gson GSON = new Gson();
    }

}
