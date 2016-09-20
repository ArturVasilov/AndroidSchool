package ru.arturvasilov.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughView;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {

    private WalkthroughView mView;
    private WalkthroughPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(WalkthroughView.class);
        mPresenter = new WalkthroughPresenter(mView);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
        Mockito.verifyNoMoreInteractions(mView);
    }

    @Test
    public void testInitWalkthroughPassed() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(true);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        Mockito.verify(mView).startAuth();
    }

    @Test
    public void testInitWalkthroughNotPassed() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        Mockito.verify(mView).showBenefits(getBenefits());
        Mockito.verify(mView).showActionButtonText(R.string.next_uppercase);
    }

    @Test
    public void testNextButtonClickNotLast() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        mPresenter.onActionButtonClick();

        Mockito.verify(mView, times(2)).showActionButtonText(R.string.next_uppercase);
        Mockito.verify(mView).scrollToNextBenefit();
    }

    @Test
    public void testNextButtonClickToLast() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();

        Mockito.verify(mView).showActionButtonText(R.string.finish_uppercase);
        Mockito.verify(mView, times(2)).scrollToNextBenefit();
    }

    @Test
    public void testFinishWalkthrough() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();

        Mockito.verify(storage).saveWalkthroughPassed();
        Mockito.verify(mView).startAuth();
    }

    @Test
    public void testUserChangedPageNotToLast() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        mPresenter.onPageChanged(1, true);

        Mockito.verify(mView, times(2)).showActionButtonText(R.string.next_uppercase);
    }

    @Test
    public void testUserChangedPageToLast() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        mPresenter.onPageChanged(2, true);

        Mockito.verify(mView).showActionButtonText(R.string.finish_uppercase);
    }

    @Test
    public void testPageChangedNotFromUser() throws Exception {
        mPresenter.onPageChanged(2, false);
        Mockito.verifyNoMoreInteractions(mView);
    }

    @Test
    public void testUserScenario() throws Exception {
        KeyValueStorage storage = Mockito.mock(KeyValueStorage.class);
        Mockito.when(storage.isWalkthroughPassed()).thenReturn(false);
        RepositoryProvider.setKeyValueStorage(storage);

        mPresenter.init();
        Mockito.verify(mView).showBenefits(getBenefits());
        Mockito.verify(mView).showActionButtonText(R.string.next_uppercase);

        mPresenter.onActionButtonClick();
        Mockito.verify(mView, times(2)).showActionButtonText(R.string.next_uppercase);
        Mockito.verify(mView).scrollToNextBenefit();

        mPresenter.onPageChanged(0, true);
        Mockito.verify(mView, times(3)).showActionButtonText(R.string.next_uppercase);

        mPresenter.onActionButtonClick();
        Mockito.verify(mView, times(4)).showActionButtonText(R.string.next_uppercase);
        Mockito.verify(mView, times(2)).scrollToNextBenefit();

        mPresenter.onActionButtonClick();
        Mockito.verify(mView).showActionButtonText(R.string.finish_uppercase);
        Mockito.verify(mView, times(3)).scrollToNextBenefit();

        mPresenter.onActionButtonClick();
        Mockito.verify(mView).startAuth();
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setKeyValueStorage(null);
    }

    @NonNull
    private List<Benefit> getBenefits() {
        return new ArrayList<Benefit>() {
            {
                add(Benefit.WORK_TOGETHER);
                add(Benefit.CODE_HISTORY);
                add(Benefit.PUBLISH_SOURCE);
            }
        };
    }

}
