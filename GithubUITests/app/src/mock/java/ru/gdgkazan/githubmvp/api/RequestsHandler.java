package ru.gdgkazan.githubmvp.api;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.Response;
import ru.gdgkazan.githubmvp.AppDelegate;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class RequestsHandler {

    private final Map<String, String> mResponsesMap = new HashMap<>();

    public RequestsHandler() {
        mResponsesMap.put("/authorizations", "auth.json");
        mResponsesMap.put("/user/repos", "repositories.json");
    }

    public boolean shouldIntercept(@NonNull String path) {
        Set<String> keys = mResponsesMap.keySet();
        for (String interceptUrl : keys) {
            if (path.contains(interceptUrl)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public Response proceed(@NonNull Request request, @NonNull String path) {
        String token = RepositoryProvider.provideKeyValueStorage().getToken();
        if ("error".equals(token)) {
            return OkHttpResponse.error(request, 400, "Error for path " + path);
        }

        Set<String> keys = mResponsesMap.keySet();
        for (String interceptUrl : keys) {
            if (path.contains(interceptUrl)) {
                String mockResponsePath = mResponsesMap.get(interceptUrl);
                return createResponseFromAssets(request, mockResponsePath);
            }
        }

        return OkHttpResponse.error(request, 500, "Incorrectly intercepted request");
    }

    @NonNull
    private Response createResponseFromAssets(@NonNull Request request, @NonNull String assetPath) {
        Context context = AppDelegate.getContext();
        try {
            final InputStream stream = context.getAssets().open(assetPath);
            //noinspection TryFinallyCanBeTryWithResources
            try {
                return OkHttpResponse.success(request, stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            return OkHttpResponse.error(request, 500, e.getMessage());
        }
    }
}
