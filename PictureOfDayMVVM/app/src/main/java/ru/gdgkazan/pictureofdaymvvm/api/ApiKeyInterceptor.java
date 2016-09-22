package ru.gdgkazan.pictureofdaymvvm.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.gdgkazan.pictureofdaymvvm.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class ApiKeyInterceptor implements Interceptor {

    @NonNull
    public static Interceptor create() {
        return new ApiKeyInterceptor();
    }

    private ApiKeyInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
