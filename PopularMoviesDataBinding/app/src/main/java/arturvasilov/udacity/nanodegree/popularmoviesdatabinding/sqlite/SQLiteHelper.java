package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MovieTable;

/**
 * @author Artur Vasilov
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "android_training_database";

    private static final List<Table> TABLES = new ArrayList<>();

    static {
        TABLES.add(MovieTable.getTable());
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseProvider.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Table table : TABLES) {
            table.onCreateTable(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table table : TABLES) {
            table.onUpgradeTable(db, newVersion);
        }
    }
}
