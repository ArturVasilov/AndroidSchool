package ru.gdgkazan.githubmvp.screen.repositories;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class RepositoriesPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final RepositoriesView mView;

    public RepositoriesPresenter(@NonNull LifecycleHandler lifecycleHandler,
                                 @NonNull RepositoriesView view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    public void init() {
        RepositoryProvider.provideGithubRepository()
                .repositories()
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.repositories_request))
                .subscribe(mView::showRepositories, throwable -> mView.showError());
    }

    public void onItemClick(@NonNull Repository repository) {
        mView.showCommits(repository);
    }
}
