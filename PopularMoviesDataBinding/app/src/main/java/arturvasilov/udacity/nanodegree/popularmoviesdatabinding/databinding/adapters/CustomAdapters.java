package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.RefreshingDialogView;

/**
 * @author Artur Vasilov
 */
public class CustomAdapters {

    @BindingAdapter("app:refreshing")
    public static void setRefreshing(@NonNull RefreshingDialogView view, boolean refreshing) {
        view.setRefreshing(refreshing);
    }
}
