package ru.gdgkazan.githubmosby.screen.repositories;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdgkazan.githubmosby.content.Repository;
import ru.gdgkazan.githubmosby.screen.general.LoadingView;

/**
 * @author Artur Vasilov
 */
public interface RepositoriesView extends LoadingView {

    void showRepositories(@NonNull List<Repository> repositories);

    void showCommits(@NonNull Repository repository);

    void showError();
}
