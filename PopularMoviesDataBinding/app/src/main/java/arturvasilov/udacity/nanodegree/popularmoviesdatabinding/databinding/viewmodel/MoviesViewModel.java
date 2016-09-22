package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.BR;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.RepositoryProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.app.Preferences;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.MoviesRouter;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.MoviesAdapter;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class MoviesViewModel extends BaseObservable {

    private final Context mContext;
    private final LifecycleHandler mLifecycleHandler;
    private final MoviesRouter mRouter;

    private final List<Movie> mMovies;

    private boolean mIsRefreshing;

    private MoviesProvider.Type mType;

    public MoviesViewModel(@NonNull Context context, @NonNull LifecycleHandler handler,
                           @NonNull MoviesRouter router) {
        mContext = context;
        mLifecycleHandler = handler;
        mRouter = router;

        mIsRefreshing = false;
        mMovies = new ArrayList<>();
    }

    public void init() {
        mType = Preferences.getMoviesType();
        load(false);
    }

    public void onResume() {
        MoviesProvider.Type type = Preferences.getMoviesType();
        //reload if changed or may be favourite movie was deleted
        if (mType != type || mType == MoviesProvider.Type.FAVOURITE) {
            mType = type;
            load(true);
        }
    }

    @Bindable
    public boolean isRefreshing() {
        return mIsRefreshing;
    }

    @NonNull
    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        int columns = mContext.getResources().getInteger(R.integer.columns_count);
        return new GridLayoutManager(mContext, columns);
    }

    @NonNull
    @Bindable
    public MoviesAdapter getAdapter() {
        int columns = mContext.getResources().getInteger(R.integer.columns_count);
        int imageWidth = mContext.getResources().getDisplayMetrics().widthPixels / columns;

        TypedValue typedValue = new TypedValue();
        mContext.getResources().getValue(R.dimen.rows_count, typedValue, true);
        float rowsCount = typedValue.getFloat();
        int actionBarHeight = mContext.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)
                              ? TypedValue.complexToDimensionPixelSize(typedValue.data, mContext.getResources().getDisplayMetrics())
                              : 0;
        int imageHeight = (int) ((mContext.getResources().getDisplayMetrics().heightPixels - actionBarHeight) / rowsCount);

        return new MoviesAdapter(new ArrayList<>(), imageWidth, imageHeight);
    }

    @NonNull
    @Bindable
    public List<Movie> getMovies() {
        return mMovies;
    }

    public void onItemClick(@NonNull View view, @NonNull Object obj) {
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Movie movie = (Movie) obj;
        mRouter.navigateToMovieScreen(imageView, movie);
    }

    private void load(boolean restart) {
        Observable.Transformer<List<Movie>, List<Movie>> lifecycleTransformer =
                restart
                ? mLifecycleHandler.reload(R.id.movies_loader_request)
                : mLifecycleHandler.load(R.id.movies_loader_request);

        RepositoryProvider.getRepository().loadMovies(mType)
                .doOnSubscribe(() -> setRefreshing(true))
                .doAfterTerminate(() -> setRefreshing(false))
                .compose(lifecycleTransformer)
                .subscribe(this::handleMovies, this::handleError);
    }

    private void setRefreshing(boolean refreshing) {
        mIsRefreshing = refreshing;
        notifyPropertyChanged(BR.refreshing);
    }

    @VisibleForTesting
    void handleMovies(@NonNull List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyPropertyChanged(BR.movies);
    }

    @VisibleForTesting
    void handleError(@NonNull Throwable throwable) {
        mMovies.clear();
        notifyPropertyChanged(BR.movies);
    }

}
