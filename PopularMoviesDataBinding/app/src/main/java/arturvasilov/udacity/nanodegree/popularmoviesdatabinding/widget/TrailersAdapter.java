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
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;

/**
 * @author Artur Vasilov
 */
public class TrailersAdapter extends BaseAdapter<RecyclerView.ViewHolder, Video> {

    public TrailersAdapter(@NonNull List<Video> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding view = DataBindingUtil.inflate(inflater, R.layout.trailer_item, parent, false);
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

        private void bind(@NonNull Video video) {
            mDataBinding.setVariable(BR.trailer, video);
        }
    }

}
