package ru.gdgkazan.githubmvp.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.gdgkazan.githubmvp.AppDelegate;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;

/**
 * @author Artur Vasilov
 */
public final class ApiKeyInterceptor implements Interceptor {

    private final KeyValueStorage mKeyValueStorage;

    private ApiKeyInterceptor() {
        mKeyValueStorage = AppDelegate.getAppComponent().keyValueStorage();
    }

    @NonNull
    public static Interceptor create() {
        return new ApiKeyInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = mKeyValueStorage.getToken();
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(chain.request());
        }
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", String.format("%s %s", "token", token))
                .build();
        return chain.proceed(request);
    }
}
