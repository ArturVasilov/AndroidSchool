package ru.arturvasilov.githubmvp.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class RxSchedulersTestRule implements TestRule {

    @SuppressWarnings("Convert2Lambda")
    private final Func1<Scheduler, Scheduler> mMockSchedulerFunc = new Func1<Scheduler, Scheduler>() {
        @Override
        public Scheduler call(Scheduler scheduler) {
            return Schedulers.immediate();
        }
    };

    private final RxAndroidSchedulersHook mRxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
        @Override
        public Scheduler getMainThreadScheduler() {
            return Schedulers.immediate();
        }
    };

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaHooks.reset();
                RxJavaHooks.setOnIOScheduler(mMockSchedulerFunc);
                RxJavaHooks.setOnNewThreadScheduler(mMockSchedulerFunc);

                RxAndroidPlugins.getInstance().reset();
                RxAndroidPlugins.getInstance().registerSchedulersHook(mRxAndroidSchedulersHook);

                base.evaluate();

                RxJavaHooks.reset();
                RxAndroidPlugins.getInstance().reset();
            }
        };
    }
}
