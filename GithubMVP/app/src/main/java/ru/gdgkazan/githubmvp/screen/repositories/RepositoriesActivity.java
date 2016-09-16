package ru.gdgkazan.githubmvp.screen.repositories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.GithubRepository;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.commits.CommitsActivity;
import ru.gdgkazan.githubmvp.screen.general.LoadingDialog;
import ru.gdgkazan.githubmvp.screen.general.LoadingView;
import ru.gdgkazan.githubmvp.widget.BaseAdapter;
import ru.gdgkazan.githubmvp.widget.DividerItemDecoration;
import ru.gdgkazan.githubmvp.widget.EmptyRecyclerView;

/**
 * @author Artur Vasilov
 */
public class RepositoriesActivity extends AppCompatActivity implements RepositoriesView,
        BaseAdapter.OnItemClickListener<Repository> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;

    private LoadingView mLoadingView;

    private RepositoriesAdapter mAdapter;

    private RepositoriesPresenter mPresenter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RepositoriesActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setEmptyView(mEmptyView);

        mAdapter = new RepositoriesAdapter(new ArrayList<>());
        mAdapter.attachToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(this);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new RepositoriesPresenter(lifecycleHandler, this);
        mPresenter.init();
    }

    @Override
    public void onItemClick(@NonNull Repository item) {
        mPresenter.onItemClick(item);
    }

    @Override
    public void showRepositories(@NonNull List<Repository> repositories) {
        mAdapter.changeDataSet(repositories);
    }

    @Override
    public void showCommits(@NonNull Repository repository) {
        CommitsActivity.start(this, repository);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void showError() {
        mAdapter.clear();
    }
}
