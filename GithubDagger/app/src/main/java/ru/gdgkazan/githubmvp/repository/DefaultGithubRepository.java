package ru.gdgkazan.githubmvp.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.RxUtils;
import ru.gdgkazan.githubmvp.api.GithubService;
import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.utils.AuthorizationUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class DefaultGithubRepository implements GithubRepository {

    private final GithubService mGithubService;
    private final KeyValueStorage mKeyValueStorage;

    public DefaultGithubRepository(@NonNull GithubService githubService, @NonNull KeyValueStorage keyValueStorage) {
        mGithubService = githubService;
        mKeyValueStorage = keyValueStorage;
    }

    @NonNull
    @Override
    public Observable<List<Repository>> repositories() {
        return mGithubService.repositories()
                .flatMap(repositories -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(Repository.class);
                        realm.insert(repositories);
                    });
                    return Observable.just(repositories);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Repository> repositories = realm.where(Repository.class).findAll();
                    return Observable.just(realm.copyFromRealm(repositories));
                })
                .compose(RxUtils.async());
    }

    @NonNull
    public Observable<Authorization> auth(@NonNull String login, @NonNull String password) {
        String authorizationString = AuthorizationUtils.createAuthorizationString(login, password);
        return mGithubService.authorize(authorizationString, AuthorizationUtils.createAuthorizationParam())
                .flatMap(authorization -> {
                    mKeyValueStorage.saveToken(authorization.getToken());
                    mKeyValueStorage.saveUserName(login);
                    return Observable.just(authorization);
                })
                .doOnError(throwable -> mKeyValueStorage.clear())
                .compose(RxUtils.async());
    }
}
