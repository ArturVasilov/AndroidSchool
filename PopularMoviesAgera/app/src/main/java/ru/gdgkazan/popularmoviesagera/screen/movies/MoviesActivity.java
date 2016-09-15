package ru.gdgkazan.popularmoviesagera.screen.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.popularmoviesagera.R;
import ru.gdgkazan.popularmoviesagera.model.content.Movie;
import ru.gdgkazan.popularmoviesagera.model.response.MoviesResponse;
import ru.gdgkazan.popularmoviesagera.network.ApiFactory;
import ru.gdgkazan.popularmoviesagera.screen.details.MovieDetailsActivity;
import ru.gdgkazan.popularmoviesagera.utils.AsyncExecutor;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickListener, Updatable {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mMoviesRecycler;

    @BindView(R.id.empty)
    View mEmptyView;

    private MoviesAdapter mAdapter;

    private Repository<Result<List<Movie>>> mMoviesRepository;

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

        mMoviesRepository = Repositories.repositoryWithInitialValue(Result.<List<Movie>>absent())
                .observe()
                .onUpdatesPerLoop()
                .goTo(AsyncExecutor.EXECUTOR)
                .getFrom(() -> {
                    try {
                        return ApiFactory.getMoviesService().popularMovies().execute().body();
                    } catch (IOException e) {
                        return new MoviesResponse();
                    }
                })
                .thenTransform(input -> Result.absentIfNull(input.getMovies()))
                .compile();
    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull Movie movie) {
        ImageView imageView = ButterKnife.findById(view, R.id.image);
        MovieDetailsActivity.navigate(this, imageView, movie);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoviesRepository.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        mMoviesRepository.removeUpdatable(this);
        super.onPause();
    }

    @Override
    public void update() {
        mMoviesRepository.get()
                .ifSucceededSendTo(this::showMovies)
                .ifFailedSendTo(value -> showError());
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
