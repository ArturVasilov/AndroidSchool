package ru.gdgkazan.simpleweather.screen.loader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.gdgkazan.simpleweather.R;

/**
 * @author Artur Vasilov
 */
public class StubLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportLoaderManager().initLoader(R.id.stub_loader_id, Bundle.EMPTY, new StubLoaderCallbacks());
    }

    private class StubLoaderCallbacks implements LoaderManager.LoaderCallbacks<Integer> {

        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            if (id == R.id.stub_loader_id) {
                return new StubLoader(StubLoaderActivity.this);
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer data) {
            if (loader.getId() == R.id.stub_loader_id) {
                Toast.makeText(StubLoaderActivity.this, R.string.load_finished, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {
            // Do nothing
        }
    }
}
