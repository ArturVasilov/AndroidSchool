package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MovieTable;

/**
 * @author Artur Vasilov
 */
public class DatabaseProvider extends ContentProvider {

    public static final int DATABASE_VERSION = 1;

    public static final String CONTENT_AUTHORITY = "arturvasilov.udacity.nanodegree.popularmoviesdatabinding";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int MOVIE_TABLE = 1;

    private static final UriMatcher URI_MATCHER;

    private SQLiteOpenHelper mSQLiteHelper;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(CONTENT_AUTHORITY, MovieTable.getTable().getTableName(), MOVIE_TABLE);
    }

    @Override
    public boolean onCreate() {
        mSQLiteHelper = new SQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        } else {
            return database.query(table,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case MOVIE_TABLE:
                return MovieTable.getTable().getTableName();
            default:
                return "";
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            long id = database.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            return ContentUris.withAppendedId(uri, id);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            int numInserted = 0;
            database.beginTransaction();
            try {
                for (ContentValues contentValues : values) {
                    long id = database.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    if (id > 0) {
                        numInserted++;
                    }
                }
                database.setTransactionSuccessful();
            }
            finally {
                database.endTransaction();
            }
            return numInserted;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            return database.delete(table, selection, selectionArgs);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
        String table = getType(uri);
        if (TextUtils.isEmpty(table)) {
            throw new UnsupportedOperationException("No such table to query");
        }
        else {
            return database.update(table, values, selection, selectionArgs);
        }
    }
}
