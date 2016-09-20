package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Benefit;

/**
 * @author Artur Vasilov
 */
public interface WalkthroughView {

    void showBenefits(@NonNull List<Benefit> benefits);

    void showActionButtonText(@StringRes int buttonTextId);

    void scrollToNextBenefit();

    void startAuth();

}
