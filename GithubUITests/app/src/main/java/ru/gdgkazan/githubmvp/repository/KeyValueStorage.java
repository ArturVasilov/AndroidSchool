package ru.gdgkazan.githubmvp.repository;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface KeyValueStorage {

    String TOKEN_KEY = "github_token";
    String USER_NAME_KEY = "user_name";
    String WALKTHROUGH_PASSED_KEY = "walkthrough_passed";

    void saveToken(@NonNull String token);

    @NonNull
    String getToken();

    void saveUserName(@NonNull String userName);

    @NonNull
    Observable<String> getUserName();

    void saveWalkthroughPassed();

    boolean isWalkthroughPassed();

    void clear();
}
