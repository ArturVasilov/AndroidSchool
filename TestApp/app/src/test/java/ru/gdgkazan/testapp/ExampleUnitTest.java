package ru.gdgkazan.testapp;

import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
@RunWith(RxJUnitRunner.class)
public class ExampleUnitTest {

    @Test
    public void testSimpleObservable() throws Exception {
        Observable.just(5, 6, 7)
                .reduce((integer, integer2) -> integer + integer2)
                .first()
                .subscribe(integer -> assertEquals(18, integer.intValue()));
    }

    @Test
    public void testSchedulers() throws Exception {
        Observable.range(0, 100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {});
    }
}