package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.AppDelegate;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite.DatabaseProvider;

import static arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite.DatabaseUtils.safeIntFromCursor;
import static arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite.DatabaseUtils.safeStringFromCursor;

/**
 * @author Artur Vasilov
 */
public final class MoviesProvider {

    private MoviesProvider() {
    }

    public static final Uri URI = DatabaseProvider.BASE_CONTENT_URI
            .buildUpon()
            .appendPath(MovieTable.getTable().getTableName())
            .build();

    public static void save(@NonNull Movie movie, @NonNull Type type) {
        AppDelegate.getDb().insert(URI, toContentValues(movie, type));
    }

    public static void delete(@NonNull Movie movie) {
        String where = MovieTable.Columns._ID + "=?";
        AppDelegate.getDb().delete(URI, where, new String[]{String.valueOf(movie.getId())});
    }

    public static void save(@NonNull List<Movie> movies, @NonNull Type type) {
        String where = MovieTable.Columns.TYPE + "=?";
        AppDelegate.getDb().delete(URI, where, new String[]{type.name()});

        ContentValues[] values = new ContentValues[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            values[i] = toContentValues(movies.get(i), type);
        }
        AppDelegate.getDb().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Movie movie, @NonNull Type type) {
        ContentValues values = new ContentValues();
        values.put(MovieTable.Columns._ID, movie.getId());
        values.put(MovieTable.Columns.POSTER_PATH, movie.getPosterPath());
        values.put(MovieTable.Columns.OVERVIEW, movie.getOverview());
        values.put(MovieTable.Columns.TITLE, movie.getTitle());
        values.put(MovieTable.Columns.RELEASED_DATE, movie.getReleasedDate());
        values.put(MovieTable.Columns.VOTE_AVERAGE, Double.toString(movie.getVoteAverage()));
        values.put(MovieTable.Columns.TYPE, type.name());
        return values;
    }

    @NonNull
    public static Movie fromCursor(@NonNull Cursor cursor) {
        int id = safeIntFromCursor(cursor, MovieTable.Columns._ID);
        String posterPath = safeStringFromCursor(cursor, MovieTable.Columns.POSTER_PATH);
        String overview = safeStringFromCursor(cursor, MovieTable.Columns.OVERVIEW);
        String title = safeStringFromCursor(cursor, MovieTable.Columns.TITLE);
        String releasedDate = safeStringFromCursor(cursor, MovieTable.Columns.RELEASED_DATE);
        double voteAverage = Double.parseDouble(safeStringFromCursor(cursor, MovieTable.Columns.VOTE_AVERAGE));
        return new Movie(id, posterPath, overview, title, releasedDate, voteAverage);
    }

    @NonNull
    public static Cursor movies(@NonNull Type type) {
        String where = MovieTable.Columns.TYPE + "=?";
        //noinspection ConstantConditions
        return AppDelegate.getDb().query(URI, null, where, new String[]{type.name()}, null);
    }

    public enum Type {
        TOP_RATED,
        POPULAR,
        FAVOURITE
    }


}
