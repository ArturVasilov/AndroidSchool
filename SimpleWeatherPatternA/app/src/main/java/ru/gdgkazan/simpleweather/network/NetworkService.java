package ru.gdgkazan.simpleweather.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;

import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;
import ru.gdgkazan.simpleweather.data.GsonHolder;
import ru.gdgkazan.simpleweather.data.model.City;
import ru.gdgkazan.simpleweather.data.tables.CityTable;
import ru.gdgkazan.simpleweather.data.tables.RequestTable;
import ru.gdgkazan.simpleweather.network.model.NetworkRequest;
import ru.gdgkazan.simpleweather.network.model.Request;
import ru.gdgkazan.simpleweather.network.model.RequestStatus;

/**
 * @author Artur Vasilov
 */
public class NetworkService extends IntentService {

    private static final String REQUEST_KEY = "request";
    private static final String CITY_NAME_KEY = "city_name";

    public static void start(@NonNull Context context, @NonNull Request request, @NonNull String cityName) {
        Intent intent = new Intent(context, NetworkService.class);
        intent.putExtra(REQUEST_KEY, GsonHolder.getGson().toJson(request));
        intent.putExtra(CITY_NAME_KEY, cityName);
        context.startService(intent);
    }

    @SuppressWarnings("unused")
    public NetworkService() {
        super(NetworkService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Request request = GsonHolder.getGson().fromJson(intent.getStringExtra(REQUEST_KEY), Request.class);
        Request savedRequest = SQLite.get().querySingle(RequestTable.TABLE,
                Where.create().equalTo(RequestTable.REQUEST, request.getRequest()));

        if (savedRequest != null && request.getStatus() == RequestStatus.IN_PROGRESS) {
            return;
        }
        request.setStatus(RequestStatus.IN_PROGRESS);
        SQLite.get().insert(RequestTable.TABLE, request);
        SQLite.get().notifyTableChanged(RequestTable.TABLE);

        if (TextUtils.equals(NetworkRequest.CITY_WEATHER, request.getRequest())) {
            String cityName = intent.getStringExtra(CITY_NAME_KEY);
            executeCityRequest(request, cityName);
        }
    }

    private void executeCityRequest(@NonNull Request request, @NonNull String cityName) {
        try {
            City city = ApiFactory.getWeatherService()
                    .getWeather(cityName)
                    .execute()
                    .body();
            SQLite.get().delete(CityTable.TABLE);
            SQLite.get().insert(CityTable.TABLE, city);
            request.setStatus(RequestStatus.SUCCESS);
        } catch (IOException e) {
            request.setStatus(RequestStatus.ERROR);
            request.setError(e.getMessage());
        } finally {
            SQLite.get().insert(RequestTable.TABLE, request);
            SQLite.get().notifyTableChanged(RequestTable.TABLE);
        }
    }
}

