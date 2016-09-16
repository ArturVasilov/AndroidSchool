package ru.gdgkazan.githubmvp.utils;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public final class PreferenceUtils {

    private static final String TOKEN_KEY = "github_token";
    private static final String USER_NAME_KEY = "user_name";
    private static final String WALKTHROUGH_PASSED_KEY = "walkthrough_passed";

    private PreferenceUtils() {
    }

    public static void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
    }

    @NonNull
    public static String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    public static void saveUserName(@NonNull String userName) {
        Hawk.put(USER_NAME_KEY, userName);
    }

    @NonNull
    public static Observable<String> getUserName() {
        return Hawk.getObservable(USER_NAME_KEY, "");
    }

    public static void saveWalkthroughPassed() {
        Hawk.put(WALKTHROUGH_PASSED_KEY, true);
    }

    public static boolean isWalkthroughPassed() {
        return Hawk.get(WALKTHROUGH_PASSED_KEY, false);
    }
}
