package ru.gdgkazan.githubmosby.screen.repositories;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.content.Repository;
import ru.gdgkazan.githubmosby.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class RepositoriesPresenter extends MvpBasePresenter<RepositoriesView> {

    private final LifecycleHandler mLifecycleHandler;

    public RepositoriesPresenter(@NonNull LifecycleHandler lifecycleHandler) {
        mLifecycleHandler = lifecycleHandler;
    }

    public void init() {
        if (!isViewAttached() || getView() == null) {
            return;
        }

        RepositoryProvider.provideGithubRepository()
                .repositories()
                .doOnSubscribe(getView()::showLoading)
                .doOnTerminate(getView()::hideLoading)
                .compose(mLifecycleHandler.load(R.id.repositories_request))
                .subscribe(getView()::showRepositories, throwable -> getView().showError());
    }

    public void onItemClick(@NonNull Repository repository) {
        if (isViewAttached() && getView() != null) {
            getView().showCommits(repository);
        }
    }
}
