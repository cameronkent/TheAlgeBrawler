package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private SharedPreferences SCORE_PREF, SETTINGS;
    private ImageView knockoutImage;
    private AnimationDrawable knockoutAnimation;
    private TextView scoreView;
    private EditText nameInput;
    private Button playButton, scoresButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        SCORE_PREF = getSharedPreferences("SCORE_DATA", MODE_PRIVATE);
        SETTINGS = getSharedPreferences("SETTINGS", MODE_PRIVATE);

        knockoutImage = (ImageView) findViewById(R.id.result_image);
//        setImage(); // TODO: 3/05/2017 fix this
        knockoutAnimation = (AnimationDrawable) knockoutImage.getBackground();

        scoreView = (TextView) findViewById(R.id.new_score_num);
        playButton = (Button) findViewById(R.id.play_again_button);
        scoresButton = (Button) findViewById(R.id.view_scores_button);

        // TODO: 19/04/2017 save and display multiple SCORE_PREF

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewScores();
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void viewScores() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        int score = SCORE_PREF.getInt("new_score", 0);
        scoreView.setText(toString().valueOf(score));
//        knockoutAnimation.start();

        super.onStart();
    }

    private void setImage() {
        String userSpriteChoice = SETTINGS.getString("userSpriteChoice", "Knight");
        switch (userSpriteChoice) {
            case "Knight":
                knockoutImage.setBackgroundResource(R.drawable.knight_dead_animation);
                break;
            case "Ninja":
                knockoutImage.setBackgroundResource(R.drawable.ninja_dead_animation);
                break;
            case "Robot":
                knockoutImage.setBackgroundResource(R.drawable.robot_dead_animation);
                break;
            case "Ninja Girl":
                knockoutImage.setBackgroundResource(R.drawable.ninja_girl_dead_animation);
                break;
        }
    }
}
