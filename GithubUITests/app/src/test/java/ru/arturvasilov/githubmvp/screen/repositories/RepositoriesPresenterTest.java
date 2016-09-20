package ru.arturvasilov.githubmvp.screen.repositories;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.githubmvp.test.MockLifecycleHandler;
import ru.arturvasilov.githubmvp.test.TestGithubRepository;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesView;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RepositoriesPresenterTest {

    private RepositoriesView mView;
    private RepositoriesPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        LifecycleHandler lifecycleHandler = new MockLifecycleHandler();
        mView = Mockito.mock(RepositoriesView.class);
        mPresenter = new RepositoriesPresenter(lifecycleHandler, mView);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
        Mockito.verifyNoMoreInteractions(mView);
    }

    @Test
    public void testProgressShowingDuringLoading() throws Exception {
        GithubRepository repository = new TestRepository(new ArrayList<>(), false);
        RepositoryProvider.setGithubRepository(repository);

        mPresenter.init();
        Mockito.verify(mView).showLoading();
        Mockito.verify(mView).hideLoading();
    }

    @Test
    public void testRepositoriesLoaded() throws Exception {
        List<Repository> repositories = new ArrayList<>();
        repositories.add(new Repository());
        repositories.add(new Repository());
        GithubRepository repository = new TestRepository(repositories, false);
        RepositoryProvider.setGithubRepository(repository);

        mPresenter.init();
        Mockito.verify(mView).showRepositories(repositories);
    }

    @Test
    public void testErrorHandled() throws Exception {
        GithubRepository repository = new TestRepository(new ArrayList<>(), true);
        RepositoryProvider.setGithubRepository(repository);

        mPresenter.init();
        Mockito.verify(mView).showError();
    }

    @Test
    public void testShowCommitsOnClick() throws Exception {
        Repository repository = new Repository();
        mPresenter.onItemClick(repository);
        Mockito.verify(mView).showCommits(repository);
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setGithubRepository(null);
    }

    private class TestRepository extends TestGithubRepository {

        private final List<Repository> mRepositories;
        private final boolean mIsError;

        public TestRepository(@NonNull List<Repository> repositories, boolean isError) {
            mRepositories = repositories;
            mIsError = isError;
        }

        @NonNull
        @Override
        public Observable<List<Repository>> repositories() {
            if (mIsError) {
                return Observable.error(new IOException());
            }
            return Observable.just(mRepositories);
        }
    }
}
