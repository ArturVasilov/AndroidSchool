package ru.gdgkazan.githubmosby.repository;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdgkazan.githubmosby.content.Authorization;
import ru.gdgkazan.githubmosby.content.Repository;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface GithubRepository {

    @NonNull
    Observable<List<Repository>> repositories();

    @NonNull
    Observable<Authorization> auth(@NonNull String login, @NonNull String password);
}
