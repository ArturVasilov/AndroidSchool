package ru.gdgkazan.githubmvp.screen.auth;

import ru.gdgkazan.githubmvp.screen.general.LoadingView;

/**
 * @author Artur Vasilov
 */
public interface AuthView extends LoadingView {

    void openRepositoriesScreen();

    void showLoginError();

    void showPasswordError();

}