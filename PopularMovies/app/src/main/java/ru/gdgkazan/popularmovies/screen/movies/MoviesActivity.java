package ru.gdgkazan.popularmovies.screen.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.gdgkazan.popularmovies.R;
import ru.gdgkazan.popularmovies.model.content.Movie;
import ru.gdgkazan.popularmovies.model.response.MoviesResponse;
import ru.gdgkazan.popularmovies.network.ApiFactory;
import ru.gdgkazan.popularmovies.screen.details.MovieDetailsActivity;
import ru.gdgkazan.popularmovies.screen.loading.LoadingDialog;
import ru.gdgkazan.popularmovies.screen.loading.LoadingView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mMoviesRecycler;

    @BindView(R.id.empty)
    View mEmptyView;

    private MoviesAdapter mAdapter;

    @Nullable
    private Subscription mMoviesSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        int columns = getResources().getInteger(R.integer.columns_count);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), columns);
        mMoviesRecycler.setLayoutManager(layoutManager);
        mAdapter = createAdapter();
        mMoviesRecycler.setAdapter(mAdapter);

        LoadingView loadingView = LoadingDialog.view(getSupportFragmentManager());
        mMoviesSubscription = ApiFactory.getMoviesService()
                .popularMovies()
                .map(MoviesResponse::getMovies)
                .flatMap(movies -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(Movie.class);
                        realm.insert(movies);
                    });
                    return Observable.just(movies);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Movie> results = realm.where(Movie.class).findAll();
                    return Observable.just(realm.copyFromRealm(results));
                })
                .doOnSubscribe(loadingView::showLoadingIndicator)
                .doAfterTerminate(loadingView::hideLoadingIndicator)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMovies, throwable -> showError());
    }

    @Override
    protected void onPause() {
        if (mMoviesSubscription != null) {
            mMoviesSubscription.unsubscribe();
        }
        super.onPause();
    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull Movie movie) {
        ImageView imageView = ButterKnife.findById(view, R.id.image);
        MovieDetailsActivity.navigate(this, imageView, movie);
    }

    private void showError() {
        mMoviesRecycler.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void showMovies(@NonNull List<Movie> movies) {
        mAdapter.changeDataSet(movies);
        mMoviesRecycler.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @NonNull
    private MoviesAdapter createAdapter() {
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.rows_count, typedValue, true);
        float rowsCount = typedValue.getFloat();
        int actionBarHeight = getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)
                ? TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics())
                : 0;
        int imageHeight = (int) ((getResources().getDisplayMetrics().heightPixels - actionBarHeight) / rowsCount);

        int columns = getResources().getInteger(R.integer.columns_count);
        int imageWidth = getResources().getDisplayMetrics().widthPixels / columns;

        return new MoviesAdapter(imageHeight, imageWidth, this);
    }
}
