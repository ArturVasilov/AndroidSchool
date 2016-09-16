package ru.gdgkazan.githubmosby.screen.repositories;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.content.Repository;
import ru.gdgkazan.githubmosby.widget.BaseAdapter;

/**
 * @author Artur Vasilov
 */
public class RepositoriesAdapter extends BaseAdapter<RepositoryHolder, Repository> {

    public RepositoriesAdapter(@NonNull List<Repository> items) {
        super(items);
    }

    @Override
    public RepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Repository repository = getItem(position);
        holder.bind(repository);
    }

}
