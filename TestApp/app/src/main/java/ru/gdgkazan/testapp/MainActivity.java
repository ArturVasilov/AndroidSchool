package ru.gdgkazan.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.gdgkazan.testapp.utils.HtmlCompat;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.text)
    TextView mTextView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setText(HtmlCompat.fromHtml(getString(R.string.large_text)));
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Snackbar.make(mFab, R.string.snackbar_text, Snackbar.LENGTH_SHORT)
                .setAction(R.string.snackar_action, null)
                .show();
    }
}
