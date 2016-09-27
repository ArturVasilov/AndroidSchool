package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Benefit;

/**
 * @author Artur Vasilov
 */
public class WalkthroughAdapter extends FragmentStatePagerAdapter {

    private final List<Benefit> mBenefits;

    public WalkthroughAdapter(FragmentManager fm, @NonNull List<Benefit> benefits) {
        super(fm);
        mBenefits = benefits;
    }

    @Override
    public Fragment getItem(int position) {
        return WalkthroughBenefitFragment.create(mBenefits.get(position));
    }

    @Override
    public int getCount() {
        return mBenefits.size();
    }
}
