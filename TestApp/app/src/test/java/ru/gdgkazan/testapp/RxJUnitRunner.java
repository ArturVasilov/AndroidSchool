package ru.gdgkazan.testapp;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class RxJUnitRunner extends BlockJUnit4ClassRunner {

    public RxJUnitRunner(Class<?> klass) throws InitializationError {
        super(klass);
        setupTestSchedulers();
    }

    private void setupTestSchedulers() {
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
        RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

        try {
            RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                @Override
                public Scheduler getMainThreadScheduler() {
                    return Schedulers.immediate();
                }
            });
        } catch (IllegalStateException ignored) {
            // already registered
        }
    }
}
