package ru.gdgkazan.pictureofdaymvvm.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public final class RepositoryProvider {

    private static NasaRepository sNasaRepository;

    private RepositoryProvider() {
    }

    @NonNull
    public static NasaRepository provideNasaRepository() {
        if (sNasaRepository == null) {
            sNasaRepository = new DefaultNasaRepository();
        }
        return sNasaRepository;
    }

    public static void setNasaRepository(@NonNull NasaRepository nasaRepository) {
        sNasaRepository = nasaRepository;
    }

    @MainThread
    public static void init() {
        sNasaRepository = new DefaultNasaRepository();
    }
}
