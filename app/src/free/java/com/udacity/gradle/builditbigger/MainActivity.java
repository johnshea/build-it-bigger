package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sheajohnh.android.jokedisplay.JokeDisplayActivity;

public class MainActivity extends ActionBarActivity {

    InterstitialAd mInterstitialAd;
    View mFragment;
    View mProgressLayout;
    Boolean mReturningFromInterstitial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressLayout = findViewById(R.id.progressLayout);
        mFragment = findViewById(R.id.fragment);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        requestNewInterstitial();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( !mReturningFromInterstitial ) {
            mFragment.setVisibility(View.VISIBLE);
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){

        final EndpointsAsyncTask.OnAsyncCompletedListener myListener = new EndpointsAsyncTask.OnAsyncCompletedListener() {
            @Override
            public void onCompleted(String result) {
                Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                intent.putExtra("joke", result);

                mProgressLayout.setVisibility(View.INVISIBLE);

                mReturningFromInterstitial = false;

                MainActivity.this.startActivity(intent);
            }
        };

        if (mInterstitialAd.isLoaded()) {

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();

                    mProgressLayout.setVisibility(View.VISIBLE);

                    EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(myListener);
                    endpointsAsyncTask.execute();

                    requestNewInterstitial();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();

                    mFragment.setVisibility(View.INVISIBLE);
                }
            });

            mReturningFromInterstitial = true;

            mInterstitialAd.show();

        } else {

            EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(myListener);
            endpointsAsyncTask.execute();

        }
    }
}
