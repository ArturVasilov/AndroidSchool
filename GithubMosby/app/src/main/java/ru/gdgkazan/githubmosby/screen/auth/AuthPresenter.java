package ru.gdgkazan.githubmosby.screen.auth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.repository.RepositoryProvider;
import ru.gdgkazan.githubmosby.utils.PreferenceUtils;

/**
 * @author Artur Vasilov
 */
public class AuthPresenter extends MvpBasePresenter<AuthView> {

    private final LifecycleHandler mLifecycleHandler;

    public AuthPresenter(@NonNull LifecycleHandler lifecycleHandler) {
        mLifecycleHandler = lifecycleHandler;
    }

    public void init() {
        String token = PreferenceUtils.getToken();
        if (!TextUtils.isEmpty(token) && isViewAttached() && getView() != null) {
            getView().openRepositoriesScreen();
        }
    }

    public void tryLogIn(@NonNull String login, @NonNull String password) {
        if (!isViewAttached() || getView() == null) {
            return;
        }

        if (TextUtils.isEmpty(login)) {
            getView().showLoginError();
        } else if (TextUtils.isEmpty(password)) {
            getView().showPasswordError();
        } else {
            RepositoryProvider.provideGithubRepository()
                    .auth(login, password)
                    .doOnSubscribe(getView()::showLoading)
                    .doOnTerminate(getView()::hideLoading)
                    .compose(mLifecycleHandler.reload(R.id.auth_request))
                    .subscribe(authorization -> getView().openRepositoriesScreen(),
                            throwable -> getView().showLoginError());
        }
    }
}
