package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;

/**
 * @author Artur Vasilov
 */
public class SupportAdapters {

    @BindingAdapter("app:expandedTitleColor")
    public static void setExpandedTitleColor(@NonNull CollapsingToolbarLayout layout,
                                             @ColorRes int colorRes) {
        layout.setExpandedTitleColor(ContextCompat.getColor(layout.getContext(), colorRes));
    }

}
