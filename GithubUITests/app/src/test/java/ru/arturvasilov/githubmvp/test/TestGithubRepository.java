package ru.arturvasilov.githubmvp.test;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TestGithubRepository implements GithubRepository {

    @NonNull
    @Override
    public Observable<List<Repository>> repositories() {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<Authorization> auth(@NonNull String login, @NonNull String password) {
        return Observable.empty();
    }
}

