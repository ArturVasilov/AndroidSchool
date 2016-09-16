package ru.gdgkazan.githubmosby.screen.repositories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmosby.R;
import ru.gdgkazan.githubmosby.content.Repository;
import ru.gdgkazan.githubmosby.screen.commits.CommitsActivity;
import ru.gdgkazan.githubmosby.screen.general.LoadingDialog;
import ru.gdgkazan.githubmosby.screen.general.LoadingView;
import ru.gdgkazan.githubmosby.widget.BaseAdapter;
import ru.gdgkazan.githubmosby.widget.DividerItemDecoration;
import ru.gdgkazan.githubmosby.widget.EmptyRecyclerView;

/**
 * @author Artur Vasilov
 */
public class RepositoriesActivity extends MvpActivity<RepositoriesView, RepositoriesPresenter>
        implements RepositoriesView, BaseAdapter.OnItemClickListener<Repository> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;

    private LoadingView mLoadingView;

    private RepositoriesAdapter mAdapter;

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

        getPresenter().init();
    }

    @NonNull
    @Override
    public RepositoriesPresenter createPresenter() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        return new RepositoriesPresenter(lifecycleHandler);
    }

    @Override
    public void onItemClick(@NonNull Repository item) {
        getPresenter().onItemClick(item);
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
