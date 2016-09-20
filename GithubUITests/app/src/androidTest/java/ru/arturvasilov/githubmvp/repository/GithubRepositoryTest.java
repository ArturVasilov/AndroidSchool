package ru.arturvasilov.githubmvp.repository;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import retrofit2.adapter.rxjava.HttpException;
import ru.arturvasilov.githubmvp.test.RxSchedulersTestRule;
import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.DefaultGithubRepository;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class GithubRepositoryTest {

    private static final String TOKEN = "acd68de0abc2da3e89da0da";
    private static final String ERROR = "error";

    @Rule
    public RxSchedulersTestRule mRule = new RxSchedulersTestRule();

    private GithubRepository mRepository;

    @Before
    public void setUp() throws Exception {
        mRepository = new DefaultGithubRepository();
    }

    @Test
    public void testSuccessAuth() throws Exception {
        Authorization authorization = mRepository.auth("root", "12345").toBlocking().first();
        assertEquals(TOKEN, authorization.getToken());

        KeyValueStorage storage = RepositoryProvider.provideKeyValueStorage();
        assertEquals(TOKEN, storage.getToken());
        assertEquals("root", storage.getUserName().toBlocking().first());
    }

    @Test
    public void testErrorAuth() throws Exception {
        RepositoryProvider.provideKeyValueStorage().saveToken(ERROR);
        TestSubscriber<Authorization> testSubscriber = new TestSubscriber<>();
        mRepository.auth("error", "12").subscribe(testSubscriber);

        testSubscriber.assertError(HttpException.class);

        KeyValueStorage storage = RepositoryProvider.provideKeyValueStorage();
        assertEquals("", storage.getToken());
        assertEquals("", storage.getUserName().toBlocking().first());
    }

    @Test
    public void testLoadRepositories() throws Exception {
        TestSubscriber<Repository> testSubscriber = new TestSubscriber<>();
        mRepository.repositories().flatMap(Observable::from).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(22);
    }

    @Test
    public void testRepositoriesSaved() throws Exception {
        mRepository.repositories().subscribe();

        int savedCount = Realm.getDefaultInstance()
                .where(Repository.class)
                .findAll()
                .size();
        assertEquals(22, savedCount);
    }

    @Test
    public void testRepositoriesRestoredFromCache() throws Exception {
        //load all repositories
        mRepository.repositories().subscribe();

        //force error for loading
        RepositoryProvider.provideKeyValueStorage().saveToken(ERROR);

        TestSubscriber<Repository> testSubscriber = new TestSubscriber<>();
        mRepository.repositories().flatMap(Observable::from).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(22);
    }

    @After
    public void tearDown() throws Exception {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.delete(Repository.class));
        RepositoryProvider.provideKeyValueStorage().clear();
    }
}

