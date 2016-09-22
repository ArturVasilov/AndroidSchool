package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.BR;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Review;

/**
 * @author Artur Vasilov
 */
public class ReviewsAdapter extends BaseAdapter<RecyclerView.ViewHolder, Review> {

    public ReviewsAdapter(@NonNull List<Review> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding view = DataBindingUtil.inflate(inflater, R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ViewHolder) holder).bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mDataBinding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            viewDataBinding.executePendingBindings();
            mDataBinding = viewDataBinding;
        }

        private void bind(@NonNull Review review) {
            mDataBinding.setVariable(BR.review, review);
        }

    }

}
