package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.BR;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.RepositoryProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.app.Preferences;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.MoviesRouter;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils.MockLifecycleHandler;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils.RxUtils;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils.TestMoviesRepository;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils.prefs.SharedPreferencesMapImpl;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Preferences.class})
public class MoviesViewModelTest {

    private MoviesViewModel mViewModel;
    private MoviesRouter mRouter;

    private TestMoviesRepository mTestRepository;

    private SharedPreferences mPreferences;

    static {
        RxUtils.setupTestSchedulers();
    }

    @Before
    public void setUp() throws Exception {
        mPreferences = new SharedPreferencesMapImpl();
        PowerMockito.stub(PowerMockito.method(Preferences.class, "getPrefs")).toReturn(mPreferences);

        mTestRepository = new TestMoviesRepository();
        RepositoryProvider.setRepository(mTestRepository);

        mRouter = mock(MoviesRouter.class);
        mViewModel = spy(new MoviesViewModel(Mockito.mock(Context.class), new MockLifecycleHandler(), mRouter));
    }

    @Test
    public void testLoadMovies() throws Exception {
        List<Movie> movies = testMovies();
        mTestRepository.setMovies(movies);
        mViewModel.init();

        verify(mViewModel).handleMovies(movies);
        verify(mViewModel).notifyPropertyChanged(BR.movies);
        assertEquals(testMovies().size(), mViewModel.getMovies().size());
    }

    @Test
    public void testLoadFailed() throws Exception {
        RepositoryProvider.setRepository(new ErrorMoviesRepository());
        mViewModel.init();

        verify(mViewModel).handleError(any(IOException.class));
        verify(mViewModel).notifyPropertyChanged(BR.movies);
        assertEquals(0, mViewModel.getMovies().size());
    }

    @Test
    public void testReloadDifferentItems() throws Exception {
        List<Movie> popular = testMovies();
        mTestRepository.setMovies(popular);
        mPreferences.edit().putString("movies_type_key", "popular").apply();

        mViewModel.init();
        mViewModel.onResume();

        verify(mViewModel, times(1)).handleMovies(popular);

        List<Movie> topRated = testMovies();
        mTestRepository.setMovies(topRated);
        mPreferences.edit().putString("movies_type_key", "top_rated").apply();
        mViewModel.onResume();

        verify(mViewModel, times(2)).handleMovies(topRated);
    }

    @Test
    public void testOpenMovieDetails() throws Exception {
        List<Movie> movies = testMovies();
        mTestRepository.setMovies(movies);
        mViewModel.init();

        View view = mock(View.class);
        ImageView imageView = mock(ImageView.class);
        when(view.findViewById(anyInt())).thenReturn(imageView);
        mViewModel.onItemClick(view, movies.get(0));

        verify(mRouter).navigateToMovieScreen(imageView, movies.get(0));
    }

    @NonNull
    private List<Movie> testMovies() {
        return new ArrayList<Movie>() {
            {
                add(new Movie(1, "", "", "", "", 5.6));
                add(new Movie(2, "", "", "", "", 5.6));
            }
        };
    }

    private class ErrorMoviesRepository extends TestMoviesRepository {

        @NonNull
        @Override
        public Observable<List<Movie>> loadMovies(@NonNull MoviesProvider.Type type) {
            return Observable.error(new IOException());
        }
    }
}
