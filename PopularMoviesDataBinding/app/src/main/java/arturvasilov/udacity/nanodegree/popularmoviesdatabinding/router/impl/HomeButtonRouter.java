package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.router.impl;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Artur Vasilov
 */
public class HomeButtonRouter {

    private final AppCompatActivity mActivity;

    public HomeButtonRouter(@NonNull AppCompatActivity activity) {
        mActivity = activity;
    }

    public void onHomeButtonClicked() {
        mActivity.onBackPressed();
    }
}
