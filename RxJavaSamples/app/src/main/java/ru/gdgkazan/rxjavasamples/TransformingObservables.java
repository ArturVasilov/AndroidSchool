package ru.gdgkazan.rxjavasamples;

import android.support.annotation.NonNull;

import ru.gdgkazan.rxjavasamples.model.Person;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TransformingObservables {

    @NonNull
    public static Observable<Integer> sequential() {
        Observable<Integer> first = Observable.just(1, 4, 8);
        Observable<Integer> second = Observable.just(2, 6, 9);
        Observable<Integer> third = Observable.just("Red", "Hello").map(String::length);
        return Observable.concat(first, second, third);
    }

    @NonNull
    public static Observable<Integer> parallel() {
        Observable<Integer> first = Observable.just(1, 4, 8);
        Observable<Integer> second = Observable.just(2, 6, 9);
        Observable<Integer> third = Observable.just("Red", "Hello").map(String::length);
        return Observable.merge(first, second, third);
    }

    @NonNull
    public static Observable<Person> zipPersons() {
        Observable<String> names = Observable.just("John", "Jack");
        Observable<Integer> ages = Observable.just(28, 17);
        return Observable.zip(names, ages, Person::new);
    }

}
