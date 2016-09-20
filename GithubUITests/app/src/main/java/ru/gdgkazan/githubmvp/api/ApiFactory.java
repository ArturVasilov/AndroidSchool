package ru.gdgkazan.githubmvp.api;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.gdgkazan.githubmvp.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class ApiFactory {

    private static volatile GithubService sService;

    private ApiFactory() {
    }

    @NonNull
    public static GithubService getGithubService() {
        GithubService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(GithubService.class);
                }
            }
        }
        return service;
    }

    public static void setGithubService(@NonNull GithubService service) {
        sService = service;
    }

    public static void recreate() {
        OkHttpProvider.recreate();
        sService = buildRetrofit().create(GithubService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(OkHttpProvider.provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
