package ru.gdgkazan.rxjavasamples.tasks;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import rx.Observable;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class Task1Test {

    @Test
    public void testInput1() throws Exception {
        List<String> list = createList("Hello", "Reactive", "World");

        Observable<Integer> observable = RxJavaTask1.task1(list);
        testObservable(Observable.just(8, 5), observable);
    }

    @Test
    public void testInput2() throws Exception {
        List<String> list = createList("Rrrr", "r", "R", "aRRRR", "aA", "");

        Observable<Integer> observable = RxJavaTask1.task1(list);
        testObservable(Observable.just(4, 1, 1, 5), observable);
    }

    @Test
    public void testInput3() throws Exception {
        List<String> list = createList("Hele is nothing", "This is input with no such lettel",
                "AAAaa", "Enough", "Stop it", "done",
                "If youl obselvable will emit some items, you should know - you've failed");

        Observable<Integer> observable = RxJavaTask1.task1(list);
        testObservable(Observable.empty(), observable);
    }

    @Test
    public void testInputRandom() throws Exception {
        List<String> list = randomStringList();
        List<Integer> expected = new ArrayList<>();
        for (String s : list) {
            if (s.contains("r") || s.contains("R")) {
                expected.add(s.length());
            }
        }

        Observable<Integer> observable = RxJavaTask1.task1(list);
        testObservable(Observable.from(expected), observable);
    }

    private void testObservable(@NonNull Observable<Integer> expected, @NonNull Observable<Integer> observable) {
        List<Integer> expectedList = expected.toList().toBlocking().first();
        List<Integer> resultList = observable.toList().toBlocking().first();
        assertEquals(expectedList.size(), resultList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), resultList.get(i));
        }
    }

    @SafeVarargs
    @NonNull
    private final <T> List<T> createList(T... ts) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, ts);
        return list;
    }

    @NonNull
    private String randomString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    @NonNull
    private List<String> randomStringList() {
        final String template = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        Random random = new SecureRandom();

        int size = random.nextInt(10_000) + 500;
        List<String> stringList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int length = random.nextInt(10) + 5;
            stringList.add(randomString(random, template, length));
        }
        return stringList;
    }

}
