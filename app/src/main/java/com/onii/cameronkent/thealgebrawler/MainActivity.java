package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // TODO: 20/04/2017 setup actionBar

    private Button playGameButton, highScoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        playGameButton = (Button) findViewById(R.id.play_game_button);
        highScoresButton = (Button) findViewById(R.id.high_scores_button);

        /***/
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

    /***/
    private void viewScores() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    /***/
    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}