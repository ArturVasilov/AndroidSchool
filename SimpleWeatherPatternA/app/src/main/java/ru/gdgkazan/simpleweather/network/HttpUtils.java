package ru.gdgkazan.simpleweather.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.gdgkazan.simpleweather.data.model.WeatherCity;
import ru.gdgkazan.simpleweather.data.tables.WeatherCityTable;

/**
 * @author Artur Vasilov
 */
public class HttpUtils {

    //TODO : remove this before present to students

    public static void saveCities() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(new Request.Builder().url("http://openweathermap.org/help/city_list.txt")
                .build())
                .execute();

        InputStream in = response.body().byteStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        reader.readLine();

        List<WeatherCity> cities = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null) {
            String[] cityParams = line.split("\t");
            int id = Integer.parseInt(cityParams[0]);
            String name = cityParams[1];
            cities.add(new WeatherCity(id, name));
            if (cities.size() > 10000) {
                SQLite.get().insert(WeatherCityTable.TABLE, cities);
                cities.clear();
            }
        }
    }

}
