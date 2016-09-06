package ru.gdgkazan.simpleweather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Artur Vasilov
 */
public class Main implements Serializable {

    @SerializedName("temp")
    private double mTemp;

    @SerializedName("pressure")
    private double mPressure;

    @SerializedName("humidity")
    private double mHumidity;

    public int getTemp() {
        return (int) mTemp;
    }

    public int getPressure() {
        return (int) mPressure;
    }

    public int getHumidity() {
        return (int) mHumidity;
    }
}
