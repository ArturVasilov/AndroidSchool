package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite.Table;

/**
 * @author Artur Vasilov
 */
public class MovieTable extends Table {

    private static MovieTable sTable;

    public static MovieTable getTable() {
        MovieTable table = sTable;
        if (table == null) {
            synchronized (MovieTable.class) {
                table = sTable;
                if (table == null) {
                    table = sTable = new MovieTable();
                }
            }
        }
        return table;
    }

    @Override
    public void onCreateTable(@NonNull SQLiteDatabase database) {
        String create = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (" +
                Columns._ID + " INTEGER, " +
                Columns.POSTER_PATH + " VARCHAR(50), " +
                Columns.OVERVIEW + " TEXT, " +
                Columns.TITLE + " VARCHAR(50), " +
                Columns.RELEASED_DATE + " VARCHAR(20), " +
                Columns.VOTE_AVERAGE + " VARCHAR(6), " +
                Columns.TYPE + " VARCHAR(20), " +
                "PRIMARY KEY (" + Columns._ID + ", " + Columns.TYPE + "));";
        database.execSQL(create);
    }

    @Override
    protected int getLastUpdatedVersion() {
        return 0;
    }

    public interface Columns extends BaseColumns {
        String POSTER_PATH = "poster_path";
        String OVERVIEW = "overview";
        String TITLE = "title";
        String RELEASED_DATE = "released_date";
        String VOTE_AVERAGE = "vote_average";
        String TYPE = "type";
    }

}
