package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class WalkthroughPresenter {

    private static final int PAGES_COUNT = 3;

    private final WalkthroughView mWalkthroughView;

    private int mCurrentItem = 0;

    public WalkthroughPresenter(@NonNull WalkthroughView walkthroughView) {
        mWalkthroughView = walkthroughView;
    }

    public void init() {
        if (RepositoryProvider.provideKeyValueStorage().isWalkthroughPassed()) {
            mWalkthroughView.startAuth();
        } else {
            mWalkthroughView.showBenefits(getBenefits());
            mWalkthroughView.showActionButtonText(R.string.next_uppercase);
        }
    }

    public void onActionButtonClick() {
        if (isLastBenefit()) {
            RepositoryProvider.provideKeyValueStorage().saveWalkthroughPassed();
            mWalkthroughView.startAuth();
        } else {
            mCurrentItem++;
            showBenefitText();
            mWalkthroughView.scrollToNextBenefit();
        }
    }

    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (fromUser) {
            mCurrentItem = selectedPage;
            showBenefitText();
        }
    }

    @NonNull
    private List<Benefit> getBenefits() {
        return new ArrayList<Benefit>() {
            {
                add(Benefit.WORK_TOGETHER);
                add(Benefit.CODE_HISTORY);
                add(Benefit.PUBLISH_SOURCE);
            }
        };
    }

    private boolean isLastBenefit() {
        return mCurrentItem == PAGES_COUNT - 1;
    }

    private void showBenefitText() {
        @StringRes int buttonTextId = isLastBenefit() ? R.string.finish_uppercase : R.string.next_uppercase;
        mWalkthroughView.showActionButtonText(buttonTextId);
    }
}
