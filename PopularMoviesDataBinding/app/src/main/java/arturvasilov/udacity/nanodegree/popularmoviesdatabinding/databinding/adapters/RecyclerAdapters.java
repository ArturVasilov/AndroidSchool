package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.BaseAdapter;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.EmptyRecyclerView;

/**
 * @author Artur Vasilov
 */
public class RecyclerAdapters {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"app:layoutManager", "app:adapter", "app:items", "app:empty", "app:onItemClick"}, requireAll = false)
    public static void initRecycler(@NonNull EmptyRecyclerView recycler, @NonNull RecyclerView.LayoutManager layoutManager,
                                    @NonNull BaseAdapter adapter, @NonNull List<?> items, @Nullable View emptyView,
                                    @Nullable BaseAdapter.OnItemClickListener listener) {
        recycler.setLayoutManager(layoutManager);
        adapter.attachToRecyclerView(recycler);
        adapter.setNewValues(items);
        if (emptyView != null) {
            recycler.setEmptyView(emptyView);
        }
        if (listener != null) {
            adapter.setOnItemClickListener(listener);
        }
        recycler.setNestedScrollingEnabled(false);
    }

}
