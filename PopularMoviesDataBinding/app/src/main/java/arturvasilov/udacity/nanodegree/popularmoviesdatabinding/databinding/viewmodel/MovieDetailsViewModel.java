package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.BR;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api.RepositoryProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Review;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.Images;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.Videos;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.ReviewsAdapter;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.TrailersAdapter;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.RxUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class MovieDetailsViewModel extends BaseObservable {

    private static final String MAXIMUM_RATING = "10";

    private final Context mContext;
    private final LifecycleHandler mLifecycleHandler;

    private final Movie mMovie;

    private final List<Review> mReviews;
    private final List<Video> mTrailers;

    private boolean mIsLoading = false;

    public MovieDetailsViewModel(@NonNull Context context, @NonNull LifecycleHandler lifecycleHandler,
                                 @NonNull Movie movie) {
        mContext = context;
        mLifecycleHandler = lifecycleHandler;
        mMovie = movie;

        mReviews = new ArrayList<>();
        mTrailers = new ArrayList<>();
    }

    public void init() {
        Observable<List<Review>> reviews = RepositoryProvider.getRepository()
                .reviews(mMovie)
                .doOnNext(this::handleReviews);

        Observable<List<Video>> videos = RepositoryProvider.getRepository()
                .videos(mMovie)
                .doOnNext(this::handleVideos);

        Observable.zip(reviews, videos, this::isNoError)
                .flatMap(value -> {
                    if (value != null && value) {
                        return Observable.just(true);
                    }
                    return Observable.error(new IOException());
                })
                .doOnSubscribe(() -> setLoading(true))
                .doOnTerminate(() -> setLoading(false))
                .compose(RxUtils.async())
                .compose(mLifecycleHandler.reload(R.id.movie_details_request))
                .subscribe(value -> {
                }, this::handleError);
    }

    @NonNull
    @Bindable
    public String getImageWidth() {
        return Images.WIDTH_780;
    }

    @NonNull
    @Bindable
    public String getYear() {
        return mMovie.getReleasedDate().substring(0, 4);
    }

    @NonNull
    @Bindable
    public String getVoteAverage() {
        String average = String.valueOf(mMovie.getVoteAverage());
        average = average.length() > 3 ? average.substring(0, 3) : average;
        average = average.length() == 3 && average.charAt(2) == '0' ? average.substring(0, 1) : average;
        return average;
    }

    @NonNull
    @Bindable
    public String getVoteMax() {
        return MAXIMUM_RATING;
    }

    @ColorRes
    @Bindable
    public int getExpandedTitleColor() {
        return android.R.color.transparent;
    }

    @Bindable
    public boolean isReviewsEnabled() {
        return !mReviews.isEmpty();
    }

    @Bindable
    public boolean isTrailersEnabled() {
        return !mTrailers.isEmpty();
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    @Bindable
    public boolean isFavourite() {
        return mMovie.isFavourite();
    }

    @DrawableRes
    @Bindable
    public int getFavouriteImage() {
        return mMovie.isFavourite() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off;
    }

    @NonNull
    @Bindable
    public RecyclerView.LayoutManager getReviewsLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setAutoMeasureEnabled(true);
        return layoutManager;
    }

    @NonNull
    @Bindable
    public RecyclerView.LayoutManager getTrailersLayoutManager() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setAutoMeasureEnabled(true);
        return layoutManager;
    }

    @NonNull
    @Bindable
    public ReviewsAdapter getReviewsAdapter() {
        return new ReviewsAdapter(mReviews);
    }

    @NonNull
    @Bindable
    public List<Review> getReviews() {
        return mReviews;
    }

    @NonNull
    @Bindable
    public TrailersAdapter getTrailersAdapter() {
        return new TrailersAdapter(mTrailers);
    }

    @NonNull
    @Bindable
    public List<Video> getTrailers() {
        return mTrailers;
    }

    public void onFavouriteButtonClick(@NonNull View view) {
        Observable<Boolean> observable;
        if (mMovie.isFavourite()) {
            observable = RepositoryProvider.getRepository().removeFromFavourite(mMovie);
        }
        else {
            observable = RepositoryProvider.getRepository().addToFavourite(mMovie);
        }

        observable.subscribe(isFavourite -> {
            mMovie.setFavourite(isFavourite);
            notifyPropertyChanged(BR.favouriteImage);
        });
    }

    public void onTrailerClick(@NonNull View view, @NonNull Object obj) {
        Video video = (Video) obj;
        Videos.browseVideo(mContext, video);
    }

    @VisibleForTesting
    void handleReviews(@NonNull List<Review> reviews) {
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyPropertyChanged(BR.reviews);
        notifyPropertyChanged(BR.reviewsEnabled);
    }

    @VisibleForTesting
    void handleVideos(@NonNull List<Video> videos) {
        mTrailers.clear();
        mTrailers.addAll(videos);
        notifyPropertyChanged(BR.trailers);
        notifyPropertyChanged(BR.trailersEnabled);
    }

    @VisibleForTesting
    void handleError(@NonNull Throwable throwable) {
        mTrailers.clear();
        mReviews.clear();
    }

    private boolean isNoError(@Nullable List<Review> reviews, @Nullable List<Video> videos) {
        return reviews != null && videos != null;
    }

    private void setLoading(boolean loading) {
        if (mIsLoading != loading) {
            mIsLoading = loading;
            notifyPropertyChanged(BR.loading);
        }
    }
}
