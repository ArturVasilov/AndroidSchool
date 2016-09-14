package ru.gdgkazan.rxjavasamples.tasks;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class RxJavaTask1 {

    /**
     * TODO : implement this method
     * <p>
     * This method takes list of strings and creates an observable of integers,
     * that represents length of strings which contains letter 'r' (or 'R')
     * <p>
     * Example:
     * Input list: ("Vasya", "Dima", "Artur", "Petya", "Roma")
     * Result stream: (5, 4)
     */
    @NonNull
    public static Observable<Integer> task1(@NonNull List<String> list) {
        return Observable.just(0);
    }

}