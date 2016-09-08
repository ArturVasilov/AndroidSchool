package ru.gdgkazan.simpleweather.data.sqlite;

import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.SQLiteSchema;
import ru.gdgkazan.simpleweather.data.tables.CityTable;

/**
 * @author Artur Vasilov
 */
public class WeatherContentProvider extends SQLiteContentProvider {

    private static final String DATABASE_NAME = "simpleweather.db";
    private static final String CONTENT_AUTHORITY = "ru.gdgkazan.simpleweather";

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);
    }

    @Override
    protected void prepareSchema(@NonNull SQLiteSchema schema) {
        schema.register(CityTable.TABLE);
    }
}
