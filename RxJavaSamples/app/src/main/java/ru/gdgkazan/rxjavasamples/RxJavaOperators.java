package ru.gdgkazan.rxjavasamples;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
@SuppressWarnings("unused")
public class RxJavaOperators {

    @NonNull
    public static Observable<Integer> mapSimple(@NonNull Observable<Integer> observable) {
        return observable
                .map(integer -> {
                    int value = integer * 2;
                    String text = String.valueOf(value);
                    return text.hashCode();
                });
    }

    @NonNull
    public static Observable<Integer> mapMany(@NonNull Observable<Integer> observable) {
        return observable
                .map(integer -> integer * 2)
                .map(String::valueOf)
                .map(String::hashCode);
    }


    @NonNull
    public static Observable<Integer> async() {
        return Observable.just(1)
                .compose(new AsyncTransformer<>());
    }

}
