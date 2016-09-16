package ru.gdgkazan.githubmosby.screen.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.screen.general.LoadingDialog;
import ru.gdgkazan.githubmosby.screen.general.LoadingView;
import ru.gdgkazan.githubmosby.screen.repositories.RepositoriesActivity;

public class AuthActivity extends MvpActivity<AuthView, AuthPresenter> implements AuthView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loginEdit)
    EditText mLoginEdit;

    @BindView(R.id.passwordEdit)
    EditText mPasswordEdit;

    @BindView(R.id.loginInputLayout)
    TextInputLayout mLoginInputLayout;

    @BindView(R.id.passwordInputLayout)
    TextInputLayout mPasswordInputLayout;

    private LoadingView mLoadingView;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        getPresenter().init();
    }

    @NonNull
    @Override
    public AuthPresenter createPresenter() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        return new AuthPresenter(lifecycleHandler);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.logInButton)
    public void onLogInButtonClick() {
        String login = mLoginEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        getPresenter().tryLogIn(login, password);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void showLoginError() {
        mLoginInputLayout.setError(getString(R.string.error));
    }

    @Override
    public void showPasswordError() {
        mPasswordInputLayout.setError(getString(R.string.error));
    }

    @Override
    public void openRepositoriesScreen() {
        RepositoriesActivity.start(this);
        finish();
    }
}
