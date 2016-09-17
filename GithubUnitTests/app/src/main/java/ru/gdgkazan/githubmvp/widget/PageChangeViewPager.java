package ru.gdgkazan.githubmvp.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;

/**
 * @author Artur Vasilov
 */
public class PageChangeViewPager extends ViewPager {

    private PagerStateListener mListener;

    private final ViewPager.OnPageChangeListener mChangeListener;

    private ValueAnimator mDragAnimator;

    public PageChangeViewPager(Context context) {
        super(context);
        mChangeListener = new PagerListener();
    }

    public PageChangeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mChangeListener = new PagerListener();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnPageChangeListener(mChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeOnPageChangeListener(mChangeListener);
        super.onDetachedFromWindow();
    }

    @Override
    public void setCurrentItem(int position, boolean smoothScroll) {
        super.setCurrentItem(position, smoothScroll);
        if (mListener != null) {
            mListener.onPageChanged(position, false);
        }
    }

    @Override
    public void setCurrentItem(int position) {
        super.setCurrentItem(position);
        if (mListener != null) {
            mListener.onPageChanged(position, false);
        }
    }

    public void setOnPageChangedListener(@Nullable PagerStateListener listener) {
        mListener = listener;
    }

    public void smoothScrollNext(int duration) {
        if (mDragAnimator != null) {
            mDragAnimator.end();
        }
        mDragAnimator = ValueAnimator.ofInt(0, getWidth());
        mDragAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                endFakeDrag();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                endFakeDrag();
            }
        });

        mDragAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        mDragAnimator.addUpdateListener(new FakeDragUpdateListener());

        mDragAnimator.setDuration(duration);
        beginFakeDrag();
        mDragAnimator.start();
    }

    @Override
    public void endFakeDrag() {
        if (isFakeDragging()) {
            super.endFakeDrag();
        }
    }

    @Override
    public boolean beginFakeDrag() {
        return isFakeDragging() || super.beginFakeDrag();
    }

    private class PagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Do nothing
        }

        @Override
        public void onPageSelected(int position) {
            if (mListener != null) {
                mListener.onPageChanged(position, true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Do nothing
        }
    }

    private class FakeDragUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private int mOldDragPosition = 0;

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int dragPosition = (Integer) animation.getAnimatedValue();
            int dragOffset = dragPosition - mOldDragPosition;
            mOldDragPosition = dragPosition;
            if (!isFakeDragging()) {
                beginFakeDrag();
            }
            fakeDragBy(-dragOffset);
        }
    }

    public interface PagerStateListener {

        void onPageChanged(int selectedPage, boolean fromUser);

    }
}
