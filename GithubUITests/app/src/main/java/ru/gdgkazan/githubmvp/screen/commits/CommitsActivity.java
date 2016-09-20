package ru.gdgkazan.githubmvp.screen.commits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.widget.DividerItemDecoration;
import ru.gdgkazan.githubmvp.widget.EmptyRecyclerView;

/**
 * @author Artur Vasilov
 */
public class CommitsActivity extends AppCompatActivity {

    private static final String REPO_NAME_KEY = "repo_name_key";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;

    public static void start(@NonNull Activity activity, @NonNull Repository repository) {
        Intent intent = new Intent(activity, CommitsActivity.class);
        intent.putExtra(REPO_NAME_KEY, repository.getName());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setEmptyView(mEmptyView);

        String repositoryName = getIntent().getStringExtra(REPO_NAME_KEY);
        Snackbar.make(mRecyclerView, "Not implemented for " + repositoryName + " yet", Snackbar.LENGTH_LONG).show();

        /**
         * TODO : task
         *
         * Load commits info and display them
         * Use MVP pattern for managing logic and UI and Repository for requests and caching
         *
         * API docs can be found here https://developer.github.com/v3/repos/commits/
         */
    }
}
