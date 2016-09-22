package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.Images;

/**
 * @author Artur Vasilov
 */
public class MoviesAdapter extends BaseAdapter<MoviesAdapter.ViewHolder, Movie> {

    private final int mImageWidth;
    private final int mImageHeight;

    public MoviesAdapter(@NonNull List<Movie> items, int imageWidth, int imageHeight) {
        super(items);
        mImageWidth = imageWidth;
        mImageHeight = imageHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.image_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = mImageHeight;
        layoutParams.width = mImageWidth;
        imageView.requestLayout();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Movie movie = getItem(position);
        Images.loadMovie(holder.mImageView, movie, Images.WIDTH_185);
        Images.fetch(movie.getPosterPath(), Images.WIDTH_780);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
