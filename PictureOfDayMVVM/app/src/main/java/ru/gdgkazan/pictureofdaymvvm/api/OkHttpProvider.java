package ru.gdgkazan.pictureofdaymvvm.api;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;

/**
 * @author Artur Vasilov
 */
public final class OkHttpProvider {

    private OkHttpProvider() {
    }

    private static volatile OkHttpClient sClient;

    @NonNull
    public static OkHttpClient provideClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    public static void recreate() {
        sClient = null;
        sClient = buildClient();
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.create())
                .addInterceptor(ApiKeyInterceptor.create())
                .build();
    }

}
