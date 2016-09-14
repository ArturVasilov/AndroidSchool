package ru.gdgkazan.rxjavasamples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import rx.Subscription;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RxJavaCreateTest {

    @Test
    public void testObservableWithCreate() throws Exception {
        RxJavaCreate.observableWithCreate().subscribe(System.out::println);
    }

    @Test
    public void testCreateUnsubscribed() throws Exception {
        Subscription subscription = RxJavaCreate.observableWithCreate()
                .subscribeOn(Schedulers.newThread())
                .subscribe(System.out::println);
        subscription.unsubscribe();
    }

    @Test
    public void testFrom() throws Exception {
        assertNotNull(RxJavaCreate.from());
    }

    @Test
    public void testAsync() throws Exception {
        assertNotNull(RxJavaCreate.async());
    }
}