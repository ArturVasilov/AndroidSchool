package ru.gdgkazan.simpleweather.screen.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import ru.gdgkazan.simpleweather.R;
import ru.gdgkazan.simpleweather.model.City;
import ru.gdgkazan.simpleweather.network.ApiFactory;
import ru.gdgkazan.simpleweather.screen.loading.LoadingDialog;
import ru.gdgkazan.simpleweather.screen.loading.LoadingView;
import ru.gdgkazan.simpleweather.utils.RetrofitCallback;

public class WeatherActivity extends AppCompatActivity {

    private static final String WEATHER_KEY = "weather";

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
        mToolbarTitle.setText(getString(R.string.default_city));

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        if (savedInstanceState == null || !savedInstanceState.containsKey(WEATHER_KEY)) {
            loadWeather();
        } else {
            mCity = (City) savedInstanceState.getSerializable(WEATHER_KEY);
            showWeather();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCity != null) {
            outState.putSerializable(WEATHER_KEY, mCity);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.error_layout)
    public void onErrorLayoutClick() {
        loadWeather();
    }

    private void loadWeather() {
        mWeatherLayout.setVisibility(View.INVISIBLE);
        mErrorLayout.setVisibility(View.GONE);

        mLoadingView.showLoadingIndicator();
        Call<City> call = ApiFactory.getWeatherService().getWeather(getString(R.string.default_city));
        RetrofitCallback.execute(call,
                city -> {
                    mCity = city;
                    showWeather();
                },
                throwable -> showError(),
                mLoadingView::hideLoadingIndicator);
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
