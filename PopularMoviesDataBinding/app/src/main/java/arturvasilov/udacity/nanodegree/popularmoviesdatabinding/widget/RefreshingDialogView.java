package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.LoadingDialog;

/**
 * @author Artur Vasilov
 */
public class RefreshingDialogView extends View {

    private boolean mIsRefreshing;

    private LoadingDialog mDialog;

    public RefreshingDialogView(Context context) {
        super(context);
        init(null);
    }

    public RefreshingDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RefreshingDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing == mIsRefreshing) {
            return;
        }

        mIsRefreshing = refreshing;
        if (mIsRefreshing) {
            mDialog.show(((Activity) getContext()).getFragmentManager());
        }
        else {
            mDialog.cancel();
        }
    }

    private void init(@Nullable AttributeSet attrs) {
        mIsRefreshing = false;

        String text = null;
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RefreshingDialogView, 0, 0);
            text = array.getString(R.styleable.RefreshingDialogView_text);
            array.recycle();
        }
        if (text == null) {
            text = getResources().getString(R.string.loading_progress);
        }
        mDialog = LoadingDialog.create(text);
    }
}
