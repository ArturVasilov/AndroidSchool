package ru.gdgkazan.rxjavasamples.model;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class Person {

    private final String mName;
    private final int mAge;

    public Person(@NonNull String name, int age) {
        mName = name;
        mAge = age;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }
}
