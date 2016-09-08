package ru.gdgkazan.simpleweather.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.sqlite.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.gdgkazan.simpleweather.data.GsonHolder;
import ru.gdgkazan.simpleweather.data.model.City;
import ru.gdgkazan.simpleweather.data.model.Main;
import ru.gdgkazan.simpleweather.data.model.Weather;
import ru.gdgkazan.simpleweather.data.model.Wind;

/**
 * @author Artur Vasilov
 */
public class CityTable extends BaseTable<City> {

    public static final Table<City> TABLE = new CityTable();

    public static final String CITY_NAME = "city_name";
    public static final String WEATHER = "weather";
    public static final String MAIN = "main";
    public static final String WIND = "wind";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(CITY_NAME)
                .textColumn(WEATHER)
                .textColumn(MAIN)
                .textColumn(WIND)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull City city) {
        ContentValues values = new ContentValues();
        values.put(CITY_NAME, city.getName());
        values.put(WEATHER, GsonHolder.getGson().toJson(city.getWeather()));
        values.put(MAIN, GsonHolder.getGson().toJson(city.getMain()));
        values.put(WIND, GsonHolder.getGson().toJson(city.getWind()));
        return values;
    }

    @NonNull
    @Override
    public City fromCursor(@NonNull Cursor cursor) {
        City city = new City();
        city.setName(cursor.getString(cursor.getColumnIndex(CITY_NAME)));

        Weather weather = GsonHolder.getGson().fromJson(cursor.getString(cursor.getColumnIndex(WEATHER)), Weather.class);
        List<Weather> weathers = new ArrayList<>();
        weathers.add(weather);
        city.setWeathers(weathers);

        city.setMain(GsonHolder.getGson().fromJson(cursor.getString(cursor.getColumnIndex(MAIN)), Main.class));
        city.setWind(GsonHolder.getGson().fromJson(cursor.getString(cursor.getColumnIndex(WIND)), Wind.class));

        return city;
    }
}
