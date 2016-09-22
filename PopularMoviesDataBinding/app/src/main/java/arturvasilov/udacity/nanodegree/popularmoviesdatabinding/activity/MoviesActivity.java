package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.ActivityMoviesBinding;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.viewmodel.MoviesViewModel;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.MoviesRouter;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.impl.MoviesRouterImpl;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class MoviesActivity extends AppCompatActivity {

    private MoviesViewModel mViewModel;
    private MoviesRouter mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mRouter = new MoviesRouterImpl(this);
        ActivityMoviesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mViewModel = new MoviesViewModel(this, lifecycleHandler, mRouter);
        binding.setModel(mViewModel);
        //loader handles activity restart and returns values to fast, so we need our binding (adapter) to be ready for it
        binding.executePendingBindings();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewModel.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            mRouter.navigateToSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onResume();
    }
}
