package ru.gdgkazan.simpleweather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Artur Vasilov
 */
public class Wind implements Serializable {

    @SerializedName("speed")
    private double mSpeed;

    public int getSpeed() {
        return (int) mSpeed;
    }
}
