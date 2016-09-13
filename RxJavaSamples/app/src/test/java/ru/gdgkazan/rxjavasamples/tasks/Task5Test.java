package ru.gdgkazan.rxjavasamples.tasks;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import rx.Observable;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class Task5Test {

    @Test
    public void testOneElement() throws Exception {
        testResult(
                Observable.just(5),
                Observable.just(15),
                Observable.just(25)
        );
    }

    @Test
    public void testOneElementCoPrime() throws Exception {
        testResult(
                Observable.just(1),
                Observable.just(16),
                Observable.just(27)
        );
    }

    @Test
    public void testManyElements() throws Exception {
        testResult(
                Observable.just(2, 1, 15, 53),
                Observable.just(32, 19, 225, 53),
                Observable.just(82, 18, 165, 0)
        );
    }

    private void testResult(@NonNull Observable<Integer> expected,
                            @NonNull Observable<Integer> first, @NonNull Observable<Integer> second) {
        List<Integer> expectedGcds = expected.toList().toBlocking().first();
        List<Integer> gcds = RxJavaTask5.gcdsObservable(first, second).toList().toBlocking().first();
        for (int i = 0; i < expectedGcds.size(); i++) {
            assertEquals(expectedGcds.get(i), gcds.get(i));
        }
    }
}
