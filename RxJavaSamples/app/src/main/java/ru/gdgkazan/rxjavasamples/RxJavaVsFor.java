package ru.gdgkazan.rxjavasamples;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
@SuppressWarnings("unused")
public class RxJavaVsFor {

    private static final String TAG = "RxJava";

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void rxJavaObserver() {
        Observable<Integer> observable = Observable.just(1, 2, 4);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                // do nothing
            }

            @Override
            public void onError(Throwable e) {
                // do nothing
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, String.valueOf(integer));
            }
        });
    }

    public static void rxJavaLambdas() {
        Observable<Integer> observable = Observable.just(1, 2, 4);
        observable.subscribe(
                integer -> Log.i(TAG, String.valueOf(integer)),
                throwable -> {/*handle error*/});
    }

    public static void rxJavaVersusFor() {
        //RxJava
        Observable.just(1, 2, 4, 8, 16, 32, 64)
                .filter(integer -> integer >= 13)
                .map(String::valueOf)
                .subscribe(value -> Log.i(TAG, value));

        //For
        int[] items = new int[]{1, 2, 4, 8, 16, 32, 64};
        for (int value : items) {
            if (value >= 13) {
                String s = String.valueOf(value);
                Log.i(TAG, s);
            }
        }
    }

    public static void rxJavaVersusFor2() {
        //RxJava
        Observable.just(1, 2, 4, 8, 16, 32, 64)
                .filter(integer -> integer >= 13)
                .map(String::valueOf)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> Log.i(TAG, value));

        //For
        EXECUTOR.execute(() -> {
            int[] items = new int[]{1, 2, 4, 8, 16, 32, 64};
            for (int value : items) {
                if (value >= 13) {
                    String s = String.valueOf(value);
                    HANDLER.post(() -> Log.i(TAG, s));
                }
            }
        });
    }

}
