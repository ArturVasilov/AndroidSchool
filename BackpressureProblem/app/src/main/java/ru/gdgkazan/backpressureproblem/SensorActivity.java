package ru.gdgkazan.backpressureproblem;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Subscription;

public class SensorActivity extends AppCompatActivity {

    private static final String TAG = SensorActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Nullable
    private Subscription mSensorSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorSubscription = RxSensor.observeSensorChanged(mSensorManager, mAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST)
                .subscribe(sensorEvent -> {
                    Log.i(TAG, "SensorEvent");
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorSubscription != null) {
            mSensorSubscription.unsubscribe();
        }
    }
}
