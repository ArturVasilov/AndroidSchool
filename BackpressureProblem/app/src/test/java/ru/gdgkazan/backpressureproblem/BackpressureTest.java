package ru.gdgkazan.backpressureproblem;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class BackpressureTest {

    @Test
    public void testBackpressureCrash() throws Exception {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 100000; i++) {
                    subscriber.onNext(i + "");
                }
            }
        });

        observable.observeOn(Schedulers.computation())
                .subscribe(System.out::println, throwable -> {
                    System.out.println("error: " + throwable);
                });
    }

    @Test
    public void testSample() throws Exception {
        createBackpressureObservable()
                .sample(10, TimeUnit.MICROSECONDS)
                .observeOn(Schedulers.computation())
                .subscribe(System.out::println, throwable -> {
                    System.out.println("error: " + throwable);
                });
    }

    @Test
    public void testBuffer() throws Exception {
        createBackpressureObservable()
                .buffer(100)
                .observeOn(Schedulers.computation())
                .subscribe(System.out::println, throwable -> {
                    System.out.println("error: " + throwable);
                });
    }

    @NonNull
    private Observable<String> createBackpressureObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 1000; i++) {
                    subscriber.onNext(i + "");
                }
            }
        });
    }

    @SuppressWarnings("Anonymous2MethodRef,Convert2Lambda")
    @Test
    public void testFromCallable() throws Exception {
        Observable.fromCallable(new Callable<List<Integer>>() {
            @Override
            public List<Integer> call() throws Exception {
                //some long-running operation
                return getUserIdsFromDatabase();
            }
        });
    }

    @NonNull
    private List<Integer> getUserIdsFromDatabase() {
        return new ArrayList<>();
    }
}

