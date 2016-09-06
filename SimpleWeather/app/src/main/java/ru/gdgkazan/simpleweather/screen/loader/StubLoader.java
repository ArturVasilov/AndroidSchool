package ru.gdgkazan.simpleweather.screen.loader;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader;

/**
 * @author Artur Vasilov
 */
public class StubLoader extends AsyncTaskLoader<Integer> {

    public StubLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Integer loadInBackground() {
        // emulate long-running operation
        SystemClock.sleep(2000);
        return 5;
    }
}


