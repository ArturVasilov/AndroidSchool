package ru.gdgkazan.pictureofdaymvvm.api;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.gdgkazan.pictureofdaymvvm.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class ApiFactory {

    private static volatile NasaService sService;

    private ApiFactory() {
    }

    @NonNull
    public static NasaService getNasaService() {
        NasaService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(NasaService.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        OkHttpProvider.recreate();
        sService = buildRetrofit().create(NasaService.class);
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
