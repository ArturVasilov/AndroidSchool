package ru.gdgkazan.simpleweather.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.gdgkazan.simpleweather.data.model.WeatherCity;

/**
 * @author Artur Vasilov
 */
public class WeatherCityTable extends BaseTable<WeatherCity> {

    public static final Table<WeatherCity> TABLE = new WeatherCityTable();

    public static final String CITY_ID = "id";
    public static final String CITY_NAME = "city_name";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(CITY_ID)
                .textColumn(CITY_NAME)
                .primaryKey(CITY_ID)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull WeatherCity weatherCity) {
        ContentValues values = new ContentValues();
        values.put(CITY_ID, weatherCity.getCityId());
        values.put(CITY_NAME, weatherCity.getCityName());
        return values;
    }

    @NonNull
    @Override
    public WeatherCity fromCursor(@NonNull Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(CITY_ID));
        String cityName = cursor.getString(cursor.getColumnIndex(CITY_NAME));
        return new WeatherCity(id, cityName);
    }
}
