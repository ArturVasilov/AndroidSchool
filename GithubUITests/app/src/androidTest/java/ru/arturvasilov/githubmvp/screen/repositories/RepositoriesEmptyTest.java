package ru.arturvasilov.githubmvp.screen.repositories;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.arturvasilov.githubmvp.test.idling.TimeIdlingResource;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RepositoriesEmptyTest {

    private static final String ERROR = "error";

    @Rule
    public final ActivityTestRule<RepositoriesActivity> mActivityRule
            = new ActivityTestRule<>(RepositoriesActivity.class, false, false);

    @Nullable
    private IdlingResource idlingResource;

    @Before
    public void setUp() throws Exception {
        idlingResource = TimeIdlingResource.timeout(4000);
    }

    @Test
    public void testErrorDisplayed() throws Exception {
        RepositoryProvider.provideKeyValueStorage().saveToken(ERROR);
        launchActivity();

        onView(withId(R.id.empty)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())));

        onView(withId(R.id.empty)).check(matches(withText(R.string.empty_repositories)));
    }

    private void launchActivity() {
        Context context = InstrumentationRegistry.getContext();
        Intent intent = new Intent(context, RepositoriesActivity.class);
        mActivityRule.launchActivity(intent);
    }

    @After
    public void tearDown() throws Exception {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}