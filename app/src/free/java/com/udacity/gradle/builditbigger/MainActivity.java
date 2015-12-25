package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sheajohnh.android.jokedisplay.JokeDisplayActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        EndpointsAsyncTask.OnAsyncCompletedListener myListener = new EndpointsAsyncTask.OnAsyncCompletedListener() {
            @Override
            public void onCompleted(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
                intent.putExtra("joke", result);
                MainActivity.this.startActivity(intent);
            }
        };

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(myListener);
        endpointsAsyncTask.execute();
    }

}
