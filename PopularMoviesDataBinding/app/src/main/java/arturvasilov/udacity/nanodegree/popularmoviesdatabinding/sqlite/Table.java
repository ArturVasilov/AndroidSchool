package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public abstract class Table {

    public abstract void onCreateTable(@NonNull SQLiteDatabase database);

    protected abstract int getLastUpdatedVersion();

    public void onDestroyTable(@NonNull SQLiteDatabase database) {
        String drop = "DROP TABLE IF EXISTS " + getTableName();
        database.execSQL(drop);
    }

    public void onUpgradeTable(@NonNull SQLiteDatabase database, int newVersion) {
        if (getLastUpdatedVersion() >= newVersion) {
            upgradeTable(database);
        }
    }

    @NonNull
    public String getTableName() {
        return getClass().getSimpleName();
    }

    protected void upgradeTable(@NonNull SQLiteDatabase database) {
        onDestroyTable(database);
        onCreateTable(database);
    }

}
