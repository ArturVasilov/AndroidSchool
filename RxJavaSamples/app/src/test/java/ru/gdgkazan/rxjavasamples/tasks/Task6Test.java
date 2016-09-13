package ru.gdgkazan.rxjavasamples.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import rx.Observable;
import rx.Subscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class Task6Test {

    private BigInteger expectedResult;

    @Before
    public void setUp() throws Exception {
        BigInteger result = BigInteger.ONE;
        for (int value = 80_002; value <= 120_000; value += 2) {
            if (value % 3 == 0) {
                result = result.multiply(BigInteger.valueOf(value));
            }
        }
        expectedResult = result;
    }

    @Test
    public void testNotNull() throws Exception {
        Observable<BigInteger> observable = RxJavaTask6.task2Observable();
        assertNotNull(observable);
    }

    @Test
    public void testTask2() throws Exception {
        final Observable<BigInteger> observable = RxJavaTask6.task2Observable();

        final AtomicLong startedTime = new AtomicLong(System.currentTimeMillis());
        final AtomicLong differenceTime = new AtomicLong();
        Subscriber<BigInteger> subscriber = new Subscriber<BigInteger>() {
            @Override
            public void onCompleted() {
                startedTime.set(System.currentTimeMillis());

                observable.subscribe(bigInteger -> {
                    long newDifference = System.currentTimeMillis() - startedTime.get();
                    long oldDifference = differenceTime.get();
                    //cached response is much faster
                    assertTrue("Second request to observable is too slow, probably you haven't cached it",
                            newDifference < oldDifference / 5);
                });
            }

            @Override
            public void onError(Throwable e) {
                fail();
            }

            @Override
            public void onNext(BigInteger bigInteger) {
                differenceTime.set(System.currentTimeMillis() - startedTime.get());
                assertEquals(bigInteger.divide(expectedResult).intValue(), 1);
            }
        };

        observable.subscribe(subscriber);
    }

    @After
    public void tearDown() throws Exception {
        expectedResult = null;
    }
}
