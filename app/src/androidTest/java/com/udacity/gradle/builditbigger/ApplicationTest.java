package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.TextView;

import com.sheajohnh.android.jokedisplay.JokeDisplayActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
//public class ApplicationTest extends ApplicationTestCase<Application> {
//    public ApplicationTest() {
//        super(Application.class);
//    }
//
//
//}

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    Activity mMainActivity;
    Activity mJokeDisplayActivity;

    Button mLoadJokeButton;
    TextView mDisplayJokeTextView;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mMainActivity = getActivity();
        mLoadJokeButton = (Button) mMainActivity.findViewById(R.id.loadJokeButton);
        mDisplayJokeTextView = (TextView) mMainActivity.findViewById(R.id.jokeTextView);

    }

    public ApplicationTest() {
        super(MainActivity.class);
    }

    public ApplicationTest(Class activityClass) {
        super(MainActivity.class);
    }

    @MediumTest
    public void testCheckAsyncTask() {

        Instrumentation.ActivityMonitor jokeDisplayActivityMonitor =
                getInstrumentation().addMonitor(JokeDisplayActivity.class.getName(), null, false);

        TouchUtils.clickView(this, mLoadJokeButton);

        JokeDisplayActivity jokeDisplayActivity =  (JokeDisplayActivity)
                jokeDisplayActivityMonitor.waitForActivityWithTimeout(10000);

        assertNotNull("jokeDisplayActivity is null", jokeDisplayActivity);
        assertEquals("Monitor for JokeDisplayActivity has not been called",
                1, jokeDisplayActivityMonitor.getHits());

        mDisplayJokeTextView = (TextView) jokeDisplayActivity.findViewById(R.id.jokeTextView);

        assertNotNull("jokeDisplayActivity is null", jokeDisplayActivity);
        assertNotNull("mDisplayJokeTextView is null", mDisplayJokeTextView);
        assertTrue("Joke text was empty", !mDisplayJokeTextView.getText().equals(""));

        getInstrumentation().removeMonitor(jokeDisplayActivityMonitor);
    }

}