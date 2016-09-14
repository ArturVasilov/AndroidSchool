package ru.gdgkazan.rxjavasamples.tasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

import rx.Observable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class Task6Test {

    private static final BigInteger EXPECTED_RESULT;

    static {
        BigInteger result = BigInteger.ONE;
        for (int value = 80_002; value <= 120_000; value += 2) {
            if (value % 3 == 0) {
                result = result.multiply(BigInteger.valueOf(value));
            }
        }
        EXPECTED_RESULT = result;
    }

    @Test
    public void testNotNull() throws Exception {
        Observable<BigInteger> observable = RxJavaTask6.task6Observable();
        assertNotNull(observable);
    }

    @Test
    public void testResultCorrect() throws Exception {
        RxJavaTask6.task6Observable().subscribe(result -> assertEquals(EXPECTED_RESULT, result));
    }

    @Test
    public void testTask2() throws Exception {
        final Observable<BigInteger> observable = RxJavaTask6.task6Observable();

        final long firstStart = System.nanoTime();

        observable
                .doAfterTerminate(() -> {
                    final long firstTime = System.nanoTime() - firstStart;

                    final long secondStart = System.nanoTime();
                    observable.subscribe(bigInteger -> {
                        final long secondTime = System.nanoTime() - secondStart;
                        assertTrue("Second request to observable is too slow, probably you haven't cached it",
                                secondTime < firstTime / 2);
                    });
                })
                .subscribe(value -> {
                    assertEquals(EXPECTED_RESULT, value);
                }, throwable -> fail());
    }

}
