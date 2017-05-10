package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
    private TextView scoreView, scoreText;
    private Button playButton, shareButton;
    private Boolean winCondition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_result);

        SCORE_PREF = getSharedPreferences("SCORE_DATA", MODE_PRIVATE);
        SETTINGS = getSharedPreferences("SETTINGS", MODE_PRIVATE);



        knockoutImage = (ImageView) findViewById(R.id.result_image);
        setImage();
        knockoutAnimation = (AnimationDrawable) knockoutImage.getBackground();

        scoreText = (TextView) findViewById(R.id.new_score_text);
        scoreView = (TextView) findViewById(R.id.new_score_num);
        playButton = (Button) findViewById(R.id.play_again_button);
        shareButton = (Button) findViewById(R.id.share_button);

        /** change typeface to imported font */
        try {
            Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
            scoreText.setTypeface(myFont);
            scoreView.setTypeface(myFont);
            playButton.setTypeface(myFont);
            shareButton.setTypeface(myFont);
        } catch (Exception e) {
        }

        // TODO: 19/04/2017 save and display multiple SCORE_PREF

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareScore();
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void shareScore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
//        String shareBody = "Check out my score of " + scoreView.getText() + "!";
        intent.putExtra(Intent.EXTRA_SUBJECT, "ALGEBRAWLER!");
        intent.putExtra(Intent.EXTRA_TEXT, "Check out my score of " + scoreView.getText() + "!");
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override
    protected void onStart() {
        int score = SCORE_PREF.getInt("new_score", 0);
        winCondition = SCORE_PREF.getBoolean("win_condition", false);
        scoreView.setText(toString().valueOf(score));
        knockoutAnimation.start();
        super.onStart();
    }

    /** Set image for animation based sprite of 'loser' */
    private void setImage() {
        String spriteChoice;
        if (winCondition) {
            spriteChoice = SETTINGS.getString("comSpriteChoice", "Ninja");
            knockoutImage.setScaleX(-1);
        } else {
            spriteChoice = SETTINGS.getString("userSpriteChoice", "Knight");
        }
        switch (spriteChoice) {
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
            case "Cowgirl":
                knockoutImage.setBackgroundResource(R.drawable.cowgirl_dead_animation);
                break;
        }
    }
}
