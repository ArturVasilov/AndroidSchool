package ru.gdgkazan.githubmvp.screen.auth;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public class AuthPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final AuthView mAuthView;

    public AuthPresenter(@NonNull LifecycleHandler lifecycleHandler,
                         @NonNull AuthView authView) {
        mLifecycleHandler = lifecycleHandler;
        mAuthView = authView;
    }

    public void init() {
        String token = RepositoryProvider.provideKeyValueStorage().getToken();
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
            RepositoryProvider.provideGithubRepository()
                    .auth(login, password)
                    .doOnSubscribe(mAuthView::showLoading)
                    .doOnTerminate(mAuthView::hideLoading)
                    .compose(mLifecycleHandler.reload(R.id.auth_request))
                    .subscribe(authorization -> mAuthView.openRepositoriesScreen(),
                            throwable -> mAuthView.showLoginError());
        }
    }
}
