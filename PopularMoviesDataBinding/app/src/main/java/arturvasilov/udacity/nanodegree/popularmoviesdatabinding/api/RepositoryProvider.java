package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public final class RepositoryProvider {

    private static MoviesRepository sRepository;

    private RepositoryProvider() {
    }

    @NonNull
    public static MoviesRepository getRepository() {
        return sRepository;
    }

    public static void setRepository(@NonNull MoviesRepository repository) {
        sRepository = repository;
    }
}
