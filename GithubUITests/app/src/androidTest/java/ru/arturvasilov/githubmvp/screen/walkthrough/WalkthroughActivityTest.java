package ru.arturvasilov.githubmvp.screen.walkthrough;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughActivity;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class WalkthroughActivityTest {

    /**
     * TODO : task
     *
     * Write at least 5 tests for the {@link WalkthroughActivity} class
     * Test UI elements behaviour, new Activity starts and user actions
     */

    @Rule
    public final ActivityTestRule<WalkthroughActivity> mActivityRule = new ActivityTestRule<>(WalkthroughActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
