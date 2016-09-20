package ru.gdgkazan.githubmvp.api;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Artur Vasilov
 */
public class MockingInterceptor implements Interceptor {

    private final RequestsHandler mHandlers;

    private final Random mRandom;

    private MockingInterceptor() {
        mHandlers = new RequestsHandler();
        mRandom = new SecureRandom();
    }

    @NonNull
    public static Interceptor create() {
        return new MockingInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String path = request.url().encodedPath();
        if (mHandlers.shouldIntercept(path)) {
            Response response = mHandlers.proceed(request, path);
            int stubDelay = 500 + mRandom.nextInt(2500);
            SystemClock.sleep(stubDelay);
            return response;
        }
        return chain.proceed(request);
    }
}

