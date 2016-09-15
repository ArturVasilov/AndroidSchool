package ru.gdgkazan.popularmoviesclean.screen.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.popularmoviesclean.R;
import ru.gdgkazan.popularmoviesclean.domain.model.Movie;
import ru.gdgkazan.popularmoviesclean.utils.Images;

/**
 * @author Artur Vasilov
 */
public class MovieHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    ImageView mImageView;

    @NonNull
    public static MovieHolder create(@NonNull Context context, int imageHeight, int imageWidth) {
        View view = View.inflate(context, R.layout.image_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = imageHeight;
        layoutParams.width = imageWidth;
        imageView.requestLayout();
        return new MovieHolder(view);
    }

    private MovieHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Movie movie) {
        Images.loadMovie(mImageView, movie, Images.WIDTH_185);
    }
}
