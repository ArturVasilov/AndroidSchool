package ru.arturvasilov.githubmvp.screen.auth;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import ru.arturvasilov.githubmvp.test.MockLifecycleHandler;
import ru.arturvasilov.githubmvp.test.TestGithubRepository;
import ru.arturvasilov.githubmvp.test.TestKeyValueStorage;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.auth.AuthPresenter;
import ru.gdgkazan.githubmvp.screen.auth.AuthView;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class AuthPresenterTest {

    private AuthView mAuthView;
    private AuthPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        LifecycleHandler lifecycleHandler = new MockLifecycleHandler();
        mAuthView = Mockito.mock(AuthView.class);

        mPresenter = new AuthPresenter(lifecycleHandler, mAuthView);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(mAuthView);
    }

    @Test
    public void testEmptyToken() throws Exception {
        KeyValueStorage storage = new TokenKeyValueStorage("");
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();

        Mockito.verifyNoMoreInteractions(mAuthView);
    }

    @Test
    public void testMainScreenOpened() throws Exception {
        KeyValueStorage storage = new TokenKeyValueStorage("ac781f9d0eb890d1e107d2898db9");
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();

        Mockito.verify(mAuthView).openRepositoriesScreen();
        Mockito.verify(mAuthView, times(0)).showLoading();
    }

    @Test
    public void testEmptyLogin() throws Exception {
        mPresenter.tryLogIn("", "password");
        Mockito.verify(mAuthView).showLoginError();
    }

    @Test
    public void testEmptyPassword() throws Exception {
        mPresenter.tryLogIn("login", "");
        Mockito.verify(mAuthView).showPasswordError();
    }

    @Test
    public void testLoginAndPasswordEmpty() throws Exception {
        mPresenter.tryLogIn("", "");
        Mockito.verify(mAuthView).showLoginError();
    }

    @Test
    public void testSuccessAuth() throws Exception {
        RepositoryProvider.setGithubRepository(new AuthTestRepository());

        mPresenter.tryLogIn("alice", "qwerty");
        Mockito.verify(mAuthView).openRepositoriesScreen();
    }

    @Test
    public void testErrorAuth() throws Exception {
        RepositoryProvider.setGithubRepository(new AuthTestRepository());

        mPresenter.tryLogIn("bob", "123456");
        Mockito.verify(mAuthView).showLoginError();
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setKeyValueStorage(null);
        RepositoryProvider.setGithubRepository(null);
    }

    @Test
    public void testScreenScenario() throws Exception {
        KeyValueStorage storage = new TokenKeyValueStorage("");
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        Mockito.verifyNoMoreInteractions(mAuthView);

        RepositoryProvider.setGithubRepository(new AuthTestRepository());
        mPresenter.tryLogIn("abc", "xzy");
        Mockito.verify(mAuthView).showLoading();
        Mockito.verify(mAuthView).hideLoading();
        Mockito.verify(mAuthView).showLoginError();

        mPresenter.tryLogIn("alice", "qwerty");
        Mockito.verify(mAuthView, times(2)).showLoading();
        Mockito.verify(mAuthView, times(2)).hideLoading();
        Mockito.verify(mAuthView).openRepositoriesScreen();
    }

    private class TokenKeyValueStorage extends TestKeyValueStorage {

        private final String mToken;

        public TokenKeyValueStorage(@NonNull String token) {
            mToken = token;
        }

        @NonNull
        @Override
        public String getToken() {
            return mToken;
        }
    }

    private class AuthTestRepository extends TestGithubRepository {

        @NonNull
        @Override
        public Observable<Authorization> auth(@NonNull String login, @NonNull String password) {
            if ("alice".equals(login) && "qwerty".equals(password)) {
                return Observable.just(new Authorization());
            }
            return Observable.error(new IOException());
        }
    }
}

