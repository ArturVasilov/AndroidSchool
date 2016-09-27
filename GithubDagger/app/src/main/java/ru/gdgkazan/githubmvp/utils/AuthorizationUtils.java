package ru.gdgkazan.githubmvp.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.JsonObject;

import ru.gdgkazan.githubmvp.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class AuthorizationUtils {

    private static final String BASIC_AUTHORIZATION = "Basic ";
    private static final String CLIENT_ID_PROPERTY = "client_id";
    private static final String CLIENT_SECRET_PROPERTY = "client_secret";

    private AuthorizationUtils() {
    }

    @NonNull
    public static String createAuthorizationString(@NonNull String login, @NonNull String password) {
        String combinedStr = String.format("%1$s:%2$s", login, password);
        String authorizationString = BASIC_AUTHORIZATION + Base64.encodeToString(combinedStr.getBytes(), Base64.DEFAULT);

        return authorizationString.trim();
    }

    @NonNull
    public static JsonObject createAuthorizationParam() {
        JsonObject param = new JsonObject();
        param.addProperty(CLIENT_ID_PROPERTY, BuildConfig.CLIENT_ID);
        param.addProperty(CLIENT_SECRET_PROPERTY, BuildConfig.CLIENT_SECRET);
        return param;
    }
}
