package ru.gdgkazan.simpleweather.utils;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public final class RxSchedulers {

    private RxSchedulers() {
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> async() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
