package com.sheajohnh.android.jokedisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String currentJoke;

        if (bundle != null) {
            currentJoke = bundle.getString("joke");

            if (currentJoke == null) {
                currentJoke = "Quick...Make up a joke!";
            }
        } else {
            currentJoke = "Quick...Make up a joke!";
        }

        TextView jokeDisplayTextView = (TextView)findViewById(R.id.jokeTextView);
        jokeDisplayTextView.setText(currentJoke);

    }
}
