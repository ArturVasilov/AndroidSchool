package ru.gdgkazan.githubmosby.screen.auth;

import ru.gdgkazan.githubmosby.screen.general.LoadingView;

/**
 * @author Artur Vasilov
 */
public interface AuthView extends LoadingView {

    void openRepositoriesScreen();

    void showLoginError();

    void showPasswordError();

}