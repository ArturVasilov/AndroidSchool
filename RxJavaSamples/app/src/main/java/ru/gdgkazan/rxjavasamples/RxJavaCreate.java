package ru.gdgkazan.rxjavasamples;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class RxJavaCreate {

    @NonNull
    public static Observable<Integer> observableWithCreate() {
        return Observable.create(subscriber -> {
            subscriber.onNext(5);
            subscriber.onNext(10);
            try {
                //stub long-running operation
                Thread.sleep(300);
            } catch (InterruptedException e) {
                subscriber.onError(e);
                return;
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(15);
            }
            subscriber.onCompleted();
        });
    }

    @NonNull
    public static Observable<Integer> from() {
        List<Integer> values = new ArrayList<>();
        values.add(5);
        values.add(10);
        values.add(15);
        values.add(20);
        return Observable.from(values);
    }

    @NonNull
    public static Observable<Integer> async() {
        return Observable.just(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
