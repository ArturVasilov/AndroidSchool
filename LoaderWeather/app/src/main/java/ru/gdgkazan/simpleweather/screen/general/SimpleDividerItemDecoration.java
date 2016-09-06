package ru.gdgkazan.simpleweather.screen.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.gdgkazan.simpleweather.R;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final boolean mDrawTopDivider;

    public SimpleDividerItemDecoration(@NonNull Context context, boolean drawTopDivider) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider);
        mDrawTopDivider = drawTopDivider;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top;
            if (mDrawTopDivider) {
                top = child.getTop();
            } else {
                top = child.getBottom() + params.bottomMargin;
            }
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}