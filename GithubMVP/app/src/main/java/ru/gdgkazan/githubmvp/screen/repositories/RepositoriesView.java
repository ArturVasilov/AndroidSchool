package ru.gdgkazan.githubmvp.screen.repositories;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.screen.general.LoadingView;

/**
 * @author Artur Vasilov
 */
public interface RepositoriesView extends LoadingView {

    void showRepositories(@NonNull List<Repository> repositories);

    void showCommits(@NonNull Repository repository);

    void showError();
}
