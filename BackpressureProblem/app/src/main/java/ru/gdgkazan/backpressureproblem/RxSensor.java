package ru.gdgkazan.backpressureproblem;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

import rx.AsyncEmitter;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public class RxSensor {

    /**
     * @deprecated use {@link RxSensor#observeSensorChanged(SensorManager, Sensor, int)}
     * which is free from Backpressure problem
     */
    @Deprecated
    @NonNull
    private static Observable<SensorEvent> simpleSensorObservable(
            final SensorManager sensorManager, final Sensor sensor,
            final int samplingPeriodUs) {
        return Observable.create(new Observable.OnSubscribe<SensorEvent>() {
            @Override
            public void call(final Subscriber<? super SensorEvent> subscriber) {
                SensorEventListener sensorEventListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        if (subscriber != null && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(event);
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        // do nothing
                    }
                };
                sensorManager.registerListener(sensorEventListener, sensor, samplingPeriodUs);
                subscriber.add(new MainThreadSubscription() {
                    @Override
                    protected void onUnsubscribe() {
                        sensorManager.unregisterListener(sensorEventListener);
                    }
                });
            }
        });
    }

    @NonNull
    public static Observable<SensorEvent> observeSensorChanged(final SensorManager sensorManager,
                                                               final Sensor sensor, final int samplingPeriodUs) {
        return Observable.fromEmitter(new Action1<AsyncEmitter<SensorEvent>>() {
            @Override
            public void call(final AsyncEmitter<SensorEvent> sensorEventAsyncEmitter) {
                final SensorEventListener sensorListener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        sensorEventAsyncEmitter.onNext(sensorEvent);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor originSensor, int i) {
                        // do nothing
                    }
                };

                sensorEventAsyncEmitter.setCancellation(() -> sensorManager.unregisterListener(sensorListener, sensor));
                sensorManager.registerListener(sensorListener, sensor, samplingPeriodUs);
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }

}
