package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.testutils;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public final class RxUtils {

    public static void setupTestSchedulers() {
        try {
            RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
            RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

            RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
                @Override
                public Scheduler getMainThreadScheduler() {
                    return Schedulers.immediate();
                }
            });
        } catch (IllegalStateException ignored) {
        }
    }

}
