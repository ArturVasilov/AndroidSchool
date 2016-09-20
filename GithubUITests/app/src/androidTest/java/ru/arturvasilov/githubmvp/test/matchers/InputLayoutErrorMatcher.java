package ru.arturvasilov.githubmvp.test.matchers;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Artur Vasilov
 */
public class InputLayoutErrorMatcher extends TypeSafeMatcher<View> {

    @StringRes
    private final int mErrorTextId;

    private InputLayoutErrorMatcher(@StringRes int errorTextId) {
        mErrorTextId = errorTextId;
    }

    @NonNull
    public static InputLayoutErrorMatcher withInputError(@StringRes int errorTextId) {
        return new InputLayoutErrorMatcher(errorTextId);
    }

    @Override
    protected boolean matchesSafely(View item) {
        if (!(item instanceof TextInputLayout)) {
            return false;
        }

        String expectedError = item.getResources().getString(mErrorTextId);
        TextInputLayout layout = (TextInputLayout) item;
        return TextUtils.equals(expectedError, layout.getError());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with error: " + mErrorTextId);
    }

}
