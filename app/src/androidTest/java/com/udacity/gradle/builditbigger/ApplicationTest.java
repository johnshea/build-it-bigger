package com.udacity.gradle.builditbigger;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    String mJokeResult = null;
    Boolean mAsyncTaskCompleted = false;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

    }

    public ApplicationTest() {
        super(MainActivity.class);
    }

    public ApplicationTest(Class activityClass) {
        super(MainActivity.class);
    }

    @MediumTest
    public void testCheckAsyncTask() {

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        EndpointsAsyncTask.OnAsyncCompletedListener listener = new EndpointsAsyncTask.OnAsyncCompletedListener() {
            @Override
            public void onCompleted(String result) {

                mJokeResult = result;
                mAsyncTaskCompleted = true;

                countDownLatch.countDown();
            }
        };

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(listener);
        endpointsAsyncTask.execute();

        try {
            countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.e("testCheckAsyncTask", "CountDownLatch threw an exception: "
                    + e.getMessage());
        }

        assertEquals("AsyncTask did not complete",
                Boolean.TRUE, mAsyncTaskCompleted);
        assertNotNull("AsyncTask returned a null value instead of a joke",
                mJokeResult);

    }

}