package ru.gdgkazan.simpleweather.network.model;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class Request {

    @NetworkRequest
    private final String mRequest;

    private RequestStatus mStatus;

    private String mError;

    public Request(@NonNull @NetworkRequest String request) {
        mRequest = request;
    }

    public Request(@NonNull @NetworkRequest String request,
                   @NonNull RequestStatus status, @NonNull String error) {
        mRequest = request;
        mStatus = status;
        mError = error;
    }

    @NetworkRequest
    @NonNull
    public String getRequest() {
        return mRequest;
    }

    @NonNull
    public RequestStatus getStatus() {
        return mStatus;
    }

    public void setStatus(@NonNull RequestStatus status) {
        mStatus = status;
    }

    @NonNull
    public String getError() {
        return mError;
    }

    public void setError(@NonNull String error) {
        mError = error;
    }
}
