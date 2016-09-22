package ru.gdgkazan.pictureofdaymvvm.screen.day;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.pictureofdaymvvm.BR;
import ru.gdgkazan.pictureofdaymvvm.content.DayPicture;
import ru.gdgkazan.pictureofdaymvvm.repository.NasaRepository;
import ru.gdgkazan.pictureofdaymvvm.repository.RepositoryProvider;
import ru.gdgkazan.pictureofdaymvvm.test.MockLifecycleHandler;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class DayViewModelTest {

    private DayViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        LifecycleHandler lifecycleHandler = new MockLifecycleHandler();
        mViewModel = Mockito.spy(new DayViewModel(lifecycleHandler));
    }

    @Test
    public void testDataLoaded() throws Exception {
        NasaRepository repository = Mockito.mock(NasaRepository.class);
        Observable<DayPicture> observable = Observable.just(new DayPicture());
        Mockito.when(repository.dayPicture()).thenReturn(observable);
        RepositoryProvider.setNasaRepository(repository);

        mViewModel.init();
        Mockito.verify(mViewModel).notifyPropertyChanged(BR.title);
        Mockito.verify(mViewModel).notifyPropertyChanged(BR.explanation);
        Mockito.verify(mViewModel).notifyPropertyChanged(BR.url);
    }

    @Test
    public void testCorrectElementsDisplayed() throws Exception {
        NasaRepository repository = Mockito.mock(NasaRepository.class);
        DayPicture dayPicture = new DayPicture();
        dayPicture.setTitle("Title");
        dayPicture.setExplanation("Explanation");
        dayPicture.setUrl("Url");
        Mockito.when(repository.dayPicture()).thenReturn(Observable.just(dayPicture));
        RepositoryProvider.setNasaRepository(repository);

        mViewModel.init();
        assertEquals("Title", mViewModel.getTitle());
        assertEquals("Explanation", mViewModel.getExplanation());
        assertEquals("Url", mViewModel.getUrl());
    }

    @Test
    public void testLoadingShown() throws Exception {
        NasaRepository repository = Mockito.mock(NasaRepository.class);
        Observable<DayPicture> observable = Observable.just(new DayPicture());
        Mockito.when(repository.dayPicture()).thenReturn(observable);
        RepositoryProvider.setNasaRepository(repository);

        mViewModel.init();
        Mockito.verify(mViewModel).setLoading(true);
        Mockito.verify(mViewModel).setLoading(false);
        Mockito.verify(mViewModel, times(2)).notifyPropertyChanged(BR.loading);
    }
}
