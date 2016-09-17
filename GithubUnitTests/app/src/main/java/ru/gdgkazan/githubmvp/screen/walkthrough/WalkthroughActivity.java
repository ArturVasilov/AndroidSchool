package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;
import ru.gdgkazan.githubmvp.widget.PageChangeViewPager;

/**
 * @author Artur Vasilov
 */
public class WalkthroughActivity extends AppCompatActivity implements
        PageChangeViewPager.PagerStateListener, WalkthroughView {

    @BindView(R.id.pager)
    PageChangeViewPager mPager;

    @BindView(R.id.btn_walkthrough)
    Button mActionButton;

    private WalkthroughPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        ButterKnife.bind(this);
        mPager.setOnPageChangedListener(this);

        mPresenter = new WalkthroughPresenter(this);
        mPresenter.init();
    }

    @Override
    public void showBenefits(@NonNull List<Benefit> benefits) {
        mPager.setAdapter(new WalkthroughAdapter(getFragmentManager(), benefits));
    }

    @Override
    public void showActionButtonText(@StringRes int buttonTextId) {
        mActionButton.setText(buttonTextId);
    }

    @Override
    public void scrollToNextBenefit() {
        mPager.smoothScrollNext(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    @Override
    public void startAuth() {
        AuthActivity.start(this);
        finish();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_walkthrough)
    public void onActionButtonClick() {
        mPresenter.onActionButtonClick();
    }

    @Override
    public void onPageChanged(int selectedPage, boolean fromUser) {
        mPresenter.onPageChanged(selectedPage, fromUser);
    }

}
