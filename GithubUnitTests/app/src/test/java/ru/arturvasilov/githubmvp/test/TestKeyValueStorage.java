package ru.arturvasilov.githubmvp.test;

import android.support.annotation.NonNull;

import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TestKeyValueStorage implements KeyValueStorage {

    @Override
    public void saveToken(@NonNull String token) {
        // Do nothing
    }

    @NonNull
    @Override
    public String getToken() {
        return "";
    }

    @Override
    public void saveUserName(@NonNull String userName) {
        // Do nothing
    }

    @NonNull
    @Override
    public Observable<String> getUserName() {
        return Observable.empty();
    }

    @Override
    public void saveWalkthroughPassed() {
        // Do nothing
    }

    @Override
    public boolean isWalkthroughPassed() {
        return false;
    }
}
