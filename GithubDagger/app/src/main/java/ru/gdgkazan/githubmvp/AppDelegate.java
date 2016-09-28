package ru.gdgkazan.githubmvp;

import android.app.Application;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;
import ru.gdgkazan.githubmvp.di.AppComponent;
import ru.gdgkazan.githubmvp.di.DaggerAppComponent;
import ru.gdgkazan.githubmvp.di.DataModule;

/**
 * @author Artur Vasilov
 */
public class AppDelegate extends Application {

    /**
     * TODO : task
     *
     * Create modules and subcomponents for each screen
     * Inject Presenters for each screen
     * Use test modules to test your app
     */

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();

        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .rxFactory(new RealmObservableFactory())
                .build();
        Realm.setDefaultConfiguration(configuration);

        sAppComponent = DaggerAppComponent.builder()
                .dataModule(new DataModule())
                .build();
    }

    @NonNull
    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
