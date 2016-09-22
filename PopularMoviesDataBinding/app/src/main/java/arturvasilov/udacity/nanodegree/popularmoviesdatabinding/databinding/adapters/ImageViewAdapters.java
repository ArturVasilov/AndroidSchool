package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.Images;

/**
 * @author Artur Vasilov
 */
public class ImageViewAdapters {

    @BindingAdapter({"app:posterPath", "app:imageWidth"})
    public static void setImageUrl(@NonNull ImageView image, @NonNull String url, @NonNull String size) {
        Images.loadMovie(image, url, size);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(@NonNull ImageView imageView, @DrawableRes int resource) {
        imageView.setImageResource(resource);
    }

}
