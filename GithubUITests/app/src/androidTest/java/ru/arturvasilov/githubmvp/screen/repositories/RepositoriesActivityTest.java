package ru.arturvasilov.githubmvp.screen.repositories;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.commits.CommitsActivity;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class RepositoriesActivityTest {

    @Rule
    public final ActivityTestRule<RepositoriesActivity> mActivityRule = new ActivityTestRule<>(RepositoriesActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testRecyclerViewDisplayed() throws Exception {
        onView(withId(R.id.empty)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testScrollRecyclerView() throws Exception {
        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition(15))
                .perform(scrollToPosition(8))
                .perform(scrollToPosition(1))
                .perform(scrollToPosition(2))
                .perform(scrollToPosition(10))
                .perform(scrollToPosition(19));
    }

    @Test
    public void testClickOnItem() throws Exception {
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(14, click()));

        Intents.intended(hasComponent(CommitsActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        RepositoryProvider.provideKeyValueStorage().clear();
        Realm.getDefaultInstance().executeTransaction(realm -> realm.deleteAll());
    }

}
