package ru.gdgkazan.githubmvp.repository;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class HawkKeyValueStorage implements KeyValueStorage {

    public void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
    }

    @NonNull
    public String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    public void saveUserName(@NonNull String userName) {
        Hawk.put(USER_NAME_KEY, userName);
    }

    @NonNull
    public Observable<String> getUserName() {
        return Hawk.getObservable(USER_NAME_KEY, "");
    }

    public void saveWalkthroughPassed() {
        Hawk.put(WALKTHROUGH_PASSED_KEY, true);
    }

    public boolean isWalkthroughPassed() {
        return Hawk.get(WALKTHROUGH_PASSED_KEY, false);
    }

}
