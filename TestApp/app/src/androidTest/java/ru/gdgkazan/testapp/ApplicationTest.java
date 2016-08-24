package ru.gdgkazan.testapp;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.openLinkWithText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.AllOf.allOf;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    private static final String LINK_TEXT = "школу";
    private static final String EXPECTED_URL = "http://www.e-legion.ru/lp/android";

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testStarted() throws Exception {
        assertNotNull(mActivityRule.getActivity());
    }

    @Test
    public void testTextShown() throws Exception {
        onView(withId(R.id.text)).check(matches(isDisplayed()));
    }

    @Test
    public void testLinksClickable() throws Exception {
        Matcher<Intent> expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(EXPECTED_URL));
        intending(expectedIntent).respondWith(new Instrumentation.ActivityResult(0, null));
        onView(withId(R.id.text)).perform(openLinkWithText(LINK_TEXT));
        intended(expectedIntent);
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}