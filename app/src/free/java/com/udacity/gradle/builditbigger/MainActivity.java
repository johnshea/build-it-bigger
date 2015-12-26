package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sheajohnh.android.jokedisplay.JokeDisplayActivity;

public class MainActivity extends ActionBarActivity {

    InterstitialAd mInterstitialAd;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        requestNewInterstitial();

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
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                intent.putExtra("joke", result);

                mProgressBar.setVisibility(View.INVISIBLE);

                MainActivity.this.startActivity(intent);
            }
        };

        if (mInterstitialAd.isLoaded()) {

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();

                    mProgressBar.setVisibility(View.VISIBLE);

                    EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(myListener);
                    endpointsAsyncTask.execute();

                    requestNewInterstitial();
                }
            });

            mInterstitialAd.show();

        } else {

            mProgressBar.setVisibility(View.VISIBLE);

            EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(myListener);
            endpointsAsyncTask.execute();

        }
    }

}
