package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set activity to fullscreen
        setContentView(R.layout.activity_main);

        TextView titleText = (TextView) findViewById(R.id.title_text);
        Button playGameButton = (Button) findViewById(R.id.play_game_button);
        Button highScoresButton = (Button) findViewById(R.id.high_scores_button);

        /** change typeface to imported font */
        try {
            Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
            playGameButton.setTypeface(myFont);
            highScoresButton.setTypeface(myFont);
            titleText.setTypeface(myFont);
        } catch (Exception e) {
        }

        /** Listeners for play game and high scores button */
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewScores();
            }
        });
    }

    /**
     * adds menu items to action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    /**
     * on menu selection starts new activity for settings or help
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.action_help:
                Intent help = new Intent(this, HelpActivity.class);
                startActivity(help);
        }
        return true;
    }

    /**
     * starts new high scores activity
     */
    private void viewScores() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    /**
     * starts new game activity
     */
    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}