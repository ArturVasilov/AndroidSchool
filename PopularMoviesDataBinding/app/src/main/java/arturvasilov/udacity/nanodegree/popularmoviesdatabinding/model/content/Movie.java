package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Movie implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleasedDate;

    @SerializedName("vote_average")
    private double mVoteAverage;

    private boolean mIsFavourite;

    public Movie() {
    }

    public Movie(int id, @NonNull String posterPath, @NonNull String overview,
                 @NonNull String title, @NonNull String releasedDate, double voteAverage) {
        mId = id;
        mPosterPath = posterPath;
        mOverview = overview;
        mTitle = title;
        mReleasedDate = releasedDate;
        mVoteAverage = voteAverage;
    }

    public Movie(Parcel in) {
        mId = in.readInt();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mTitle = in.readString();
        mReleasedDate = in.readString();
        mVoteAverage = in.readDouble();

        boolean[] values = new boolean[1];
        in.readBooleanArray(values);
        mIsFavourite = values[0];
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getPosterPath() {
        return mPosterPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getReleasedDate() {
        return mReleasedDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public boolean isFavourite() {
        return mIsFavourite;
    }

    public void setFavourite(boolean favourite) {
        mIsFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mTitle);
        parcel.writeString(mReleasedDate);
        parcel.writeDouble(mVoteAverage);
        parcel.writeBooleanArray(new boolean[]{mIsFavourite});
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @NonNull
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @NonNull
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };
}
