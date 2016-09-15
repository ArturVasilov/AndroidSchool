package ru.gdgkazan.popularmoviesclean.data.cache;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.gdgkazan.popularmoviesclean.data.model.content.Movie;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Artur Vasilov
 */
public class MoviesCacheTransformer implements Observable.Transformer<List<Movie>, List<Movie>> {

    private final Func1<List<Movie>, Observable<List<Movie>>> mSaveFunc = movies -> {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            realm.delete(Movie.class);
            realm.insert(movies);
        });
        return Observable.just(movies);
    };

    private final Func1<Throwable, Observable<List<Movie>>> mCacheErrorHandler = throwable -> {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> results = realm.where(Movie.class).findAll();
        return Observable.just(realm.copyFromRealm(results));
    };

    @Override
    public Observable<List<Movie>> call(Observable<List<Movie>> moviesObservable) {
        return moviesObservable
                .flatMap(mSaveFunc)
                .onErrorResumeNext(mCacheErrorHandler);
    }
}
