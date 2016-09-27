package ru.gdgkazan.githubmvp.screen.auth;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public class AuthPresenter {

    private final GithubRepository mRepository;
    private final KeyValueStorage mKeyValueStorage;
    private final LifecycleHandler mLifecycleHandler;
    private final AuthView mAuthView;

    public AuthPresenter(@NonNull GithubRepository repository, @NonNull KeyValueStorage keyValueStorage,
                         @NonNull LifecycleHandler lifecycleHandler, @NonNull AuthView authView) {
        mRepository = repository;
        mKeyValueStorage = keyValueStorage;
        mLifecycleHandler = lifecycleHandler;
        mAuthView = authView;
    }

    public void init() {
        String token = mKeyValueStorage.getToken();
        if (!TextUtils.isEmpty(token)) {
            mAuthView.openRepositoriesScreen();
        }
    }

    public void tryLogIn(@NonNull String login, @NonNull String password) {
        if (TextUtils.isEmpty(login)) {
            mAuthView.showLoginError();
        } else if (TextUtils.isEmpty(password)) {
            mAuthView.showPasswordError();
        } else {
            mRepository.auth(login, password)
                    .doOnSubscribe(mAuthView::showLoading)
                    .doOnTerminate(mAuthView::hideLoading)
                    .compose(mLifecycleHandler.reload(R.id.auth_request))
                    .subscribe(authorization -> mAuthView.openRepositoriesScreen(),
                            throwable -> mAuthView.showLoginError());
        }
    }
}
