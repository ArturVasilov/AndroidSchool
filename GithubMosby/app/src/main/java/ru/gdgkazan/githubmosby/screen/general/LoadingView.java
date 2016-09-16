package ru.gdgkazan.githubmosby.screen.general;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * @author Artur Vasilov
 */
public interface LoadingView extends MvpView {

    void showLoading();

    void hideLoading();

}

