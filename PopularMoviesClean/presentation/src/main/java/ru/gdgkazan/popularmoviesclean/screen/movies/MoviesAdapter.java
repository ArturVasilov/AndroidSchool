package ru.gdgkazan.popularmoviesclean.screen.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.popularmoviesclean.domain.model.Movie;

/**
 * @author Artur Vasilov
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

    private final List<Movie> mMovies;
    private final int mImageHeight;
    private final int mImageWidth;

    private final OnItemClickListener mOnItemClickListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Movie movie = (Movie) view.getTag();
            mOnItemClickListener.onItemClick(view, movie);
        }
    };

    public MoviesAdapter(int imageHeight, int imageWidth, @NonNull OnItemClickListener onItemClickListener) {
        mMovies = new ArrayList<>();
        mImageHeight = imageHeight;
        mImageWidth = imageWidth;
        mOnItemClickListener = onItemClickListener;
    }

    public void changeDataSet(@NonNull List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MovieHolder.create(parent.getContext(), mImageHeight, mImageWidth);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bind(movie);

        holder.itemView.setOnClickListener(mInternalListener);
        holder.itemView.setTag(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public interface OnItemClickListener {

        void onItemClick(@NonNull View view, @NonNull Movie movie);

    }
}
