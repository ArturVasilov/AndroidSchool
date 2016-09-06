package ru.gdgkazan.simpleweather.screen.weatherlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.gdgkazan.simpleweather.R;
import ru.gdgkazan.simpleweather.model.City;

/**
 * @author Artur Vasilov
 */
public class CitiesAdapter extends RecyclerView.Adapter<CityHolder> {

    private final List<City> mCities;

    private final OnItemClick mOnItemClick;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            City city = (City) view.getTag();
            mOnItemClick.onItemClick(city);
        }
    };

    public CitiesAdapter(@NonNull List<City> cities, @NonNull OnItemClick onItemClick) {
        mCities = cities;
        mOnItemClick = onItemClick;
    }

    public void changeDataSet(@NonNull List<City> cities) {
        mCities.clear();
        mCities.addAll(cities);
        notifyDataSetChanged();
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_city, parent, false);
        return new CityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        City city = mCities.get(position);
        holder.bind(city);
        holder.itemView.setTag(city);
        holder.itemView.setOnClickListener(mInternalListener);
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public interface OnItemClick {

        void onItemClick(@NonNull City city);

    }
}
