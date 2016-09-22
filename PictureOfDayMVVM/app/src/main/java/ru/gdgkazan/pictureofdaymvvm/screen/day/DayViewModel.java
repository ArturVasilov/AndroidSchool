package ru.gdgkazan.pictureofdaymvvm.screen.day;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.pictureofdaymvvm.R;
import ru.gdgkazan.pictureofdaymvvm.content.DayPicture;
import ru.gdgkazan.pictureofdaymvvm.repository.RepositoryProvider;

import ru.gdgkazan.pictureofdaymvvm.BR;

/**
 * @author Artur Vasilov
 */
public class DayViewModel extends BaseObservable {

    @Nullable
    private DayPicture mDayPicture;

    private final LifecycleHandler mLifecycleHandler;

    private boolean mIsLoading = false;

    public DayViewModel(@NonNull LifecycleHandler lifecycleHandler) {
        mLifecycleHandler = lifecycleHandler;
    }

    public void init() {
        RepositoryProvider.provideNasaRepository()
                .dayPicture()
                .doOnSubscribe(() -> setLoading(true))
                .doOnTerminate(() -> setLoading(false))
                .compose(mLifecycleHandler.load(R.id.day_picture_request))
                .subscribe(dayPicture -> {
                    mDayPicture = dayPicture;
                    notifyPropertyChanged(BR.title);
                    notifyPropertyChanged(BR.explanation);
                    notifyPropertyChanged(BR.url);
                }, throwable -> {
                    //TODO : handle error
                });
    }

    @Bindable
    @NonNull
    public String getTitle() {
        if (mDayPicture == null) {
            return "";
        }
        return mDayPicture.getTitle();
    }

    @Bindable
    @NonNull
    public String getExplanation() {
        if (mDayPicture == null) {
            return "";
        }
        return mDayPicture.getExplanation();
    }

    @Bindable
    @NonNull
    public String getUrl() {
        if (mDayPicture == null) {
            return "";
        }
        return mDayPicture.getUrl();
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    @VisibleForTesting
    void setLoading(boolean isLoading) {
        mIsLoading = isLoading;
        notifyPropertyChanged(BR.loading);
    }
}
