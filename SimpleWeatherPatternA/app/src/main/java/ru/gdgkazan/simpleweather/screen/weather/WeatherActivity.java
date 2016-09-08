package ru.gdgkazan.simpleweather.screen.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.arturvasilov.sqlite.core.BasicTableObserver;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;
import ru.arturvasilov.sqlite.rx.RxSQLite;
import ru.gdgkazan.simpleweather.R;
import ru.gdgkazan.simpleweather.data.model.City;
import ru.gdgkazan.simpleweather.data.tables.CityTable;
import ru.gdgkazan.simpleweather.data.tables.RequestTable;
import ru.gdgkazan.simpleweather.network.NetworkService;
import ru.gdgkazan.simpleweather.network.model.NetworkRequest;
import ru.gdgkazan.simpleweather.network.model.Request;
import ru.gdgkazan.simpleweather.network.model.RequestStatus;
import ru.gdgkazan.simpleweather.screen.general.LoadingDialog;
import ru.gdgkazan.simpleweather.screen.general.LoadingView;
import ru.gdgkazan.simpleweather.utils.RxSchedulers;
import rx.Observable;

public class WeatherActivity extends AppCompatActivity implements BasicTableObserver {

    private static final String WEATHER_KEY = "weather";
    private static final String CITY_NAME_KEY = "city_name";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.weather_layout)
    View mWeatherLayout;

    @BindView(R.id.weather_main)
    TextView mWeatherMain;

    @BindView(R.id.temperature)
    TextView mTemperature;

    @BindView(R.id.pressure)
    TextView mPressure;

    @BindView(R.id.humidity)
    TextView mHumidity;

    @BindView(R.id.wind_speed)
    TextView mWindSpeed;

    @BindView(R.id.error_layout)
    TextView mErrorLayout;

    private LoadingView mLoadingView;
    private String mCityName;

    @NonNull
    public static Intent makeIntent(@NonNull Activity activity, @NonNull String cityName) {
        Intent intent = new Intent(activity, WeatherActivity.class);
        intent.putExtra(CITY_NAME_KEY, cityName);
        return intent;
    }

    @Nullable
    private City mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        mCityName = getIntent().getStringExtra(CITY_NAME_KEY);
        mToolbarTitle.setText(mCityName);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        if (savedInstanceState == null || !savedInstanceState.containsKey(WEATHER_KEY)) {
            loadWeather();
        } else {
            mCity = (City) savedInstanceState.getSerializable(WEATHER_KEY);
            showWeather();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SQLite.get().unregisterObserver(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCity != null) {
            outState.putSerializable(WEATHER_KEY, mCity);
        }
    }

    @Override
    public void onTableChanged() {
        Where where = Where.create().equalTo(RequestTable.REQUEST, NetworkRequest.CITY_WEATHER);
        RxSQLite.get().querySingle(RequestTable.TABLE, where)
                .compose(RxSchedulers.async())
                .flatMap(request -> {
                    if (request.getStatus() == RequestStatus.IN_PROGRESS) {
                        mLoadingView.showLoadingIndicator();
                        return Observable.empty();
                    } else if (request.getStatus() == RequestStatus.ERROR) {
                        return Observable.error(new IOException(request.getError()));
                    }
                    return RxSQLite.get().querySingle(CityTable.TABLE).compose(RxSchedulers.async());
                })
                .subscribe(city -> {
                    mCity = city;
                    showWeather();
                    mLoadingView.hideLoadingIndicator();
                }, throwable -> {
                    showError();
                    mLoadingView.hideLoadingIndicator();
                });
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.error_layout)
    public void onErrorLayoutClick() {
        loadWeather();
    }

    private void loadWeather() {
        mWeatherLayout.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.GONE);

        SQLite.get().registerObserver(RequestTable.TABLE, this);
        Request request = new Request(NetworkRequest.CITY_WEATHER);
        NetworkService.start(this, request, mCityName);
    }

    private void showWeather() {
        if (mCity == null || mCity.getMain() == null || mCity.getWeather() == null
                || mCity.getWind() == null) {
            showError();
            return;
        }

        mWeatherLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);

        mToolbarTitle.setText(mCity.getName());
        mWeatherMain.setText(mCity.getWeather().getMain());
        mTemperature.setText(getString(R.string.f_temperature, mCity.getMain().getTemp()));
        mPressure.setText(getString(R.string.f_pressure, mCity.getMain().getPressure()));
        mHumidity.setText(getString(R.string.f_humidity, mCity.getMain().getHumidity()));
        mWindSpeed.setText(getString(R.string.f_wind_speed, mCity.getWind().getSpeed()));
    }

    private void showError() {
        mWeatherLayout.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }
}
