package ru.arturvasilov.githubmvp.test.idling;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

/**
 * @author Artur Vasilov
 */
public class TimeIdlingResource implements IdlingResource {

    private final long waitTimeMs;
    private final long startTime;

    @Nullable
    private volatile ResourceCallback mCallback;

    private TimeIdlingResource(long waitTimeMs) {
        this.waitTimeMs = waitTimeMs;
        this.startTime = System.currentTimeMillis();
    }

    @NonNull
    public static IdlingResource timeout(long waitTimeMs) {
        IdlingResource idlingResource = new TimeIdlingResource(waitTimeMs);
        Espresso.registerIdlingResources(idlingResource);
        return idlingResource;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean isIdle = System.currentTimeMillis() - startTime >= waitTimeMs;
        if (isIdle && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }
}