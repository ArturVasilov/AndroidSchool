package ru.gdgkazan.pictureofdaymvvm.repository;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.RxUtils;
import ru.gdgkazan.pictureofdaymvvm.api.ApiFactory;
import ru.gdgkazan.pictureofdaymvvm.content.DayPicture;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class DefaultNasaRepository implements NasaRepository {

    @NonNull
    @Override
    public Observable<DayPicture> dayPicture() {
        return ApiFactory.getNasaService()
                .todayPicture()
                .compose(RxUtils.async());
    }
}
