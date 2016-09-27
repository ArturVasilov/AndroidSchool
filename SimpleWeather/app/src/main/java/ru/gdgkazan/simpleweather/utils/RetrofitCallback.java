package ru.gdgkazan.simpleweather.utils;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public final class RetrofitCallback {

    private RetrofitCallback() {
    }

    public static <T> void execute(@NonNull Call<T> call, @NonNull Action1<T> onSuccess,
                                   @NonNull Action1<Throwable> onError, @NonNull Action0 onComplete) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onSuccess.call(response.body());
                onComplete.call();
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                onError.call(t);
                //in fact it breaks Rx contract a little bit but it's OK
                onComplete.call();
            }
        });
    }

}
