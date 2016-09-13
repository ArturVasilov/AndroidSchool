package ru.gdgkazan.rxjavasamples.tasks;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class RxJavaTask3 {

    /**
     * TODO :
     * Sum all elements from observable and return observable with this single sum
     * <p>
     * Example:
     * Input stream (1, 2, 3, 4, 5)
     * Result stream (15)
     * <p>
     * Find suitable operator for this task using Google
     */
    @NonNull
    public static Observable<Integer> sum(@NonNull Observable<Integer> observable) {
        return Observable.just(0);
    }

}
