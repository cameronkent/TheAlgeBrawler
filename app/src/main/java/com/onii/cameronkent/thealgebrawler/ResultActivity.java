package com.onii.cameronkent.thealgebrawler;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private SharedPreferences SETTINGS;
    private ImageView knockoutImage;
    private AnimationDrawable knockoutAnimation;
    private TextView scoreView;
    private Boolean winCondition = false;
    private ScoresDAOHelper scoresDAO;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set activity to fullscreen
        setContentView(R.layout.activity_result);

        /** */
        scoresDAO = new ScoresDAOHelper(this);
        SETTINGS = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        winCondition = SETTINGS.getBoolean("win_condition", false);
        knockoutImage = (ImageView) findViewById(R.id.result_image);
        setImage();
        knockoutAnimation = (AnimationDrawable) knockoutImage.getBackground();
        TextView scoreText = (TextView) findViewById(R.id.new_score_text);
        scoreView = (TextView) findViewById(R.id.new_score_num);
        Button playButton = (Button) findViewById(R.id.play_again_button);
        Button shareButton = (Button) findViewById(R.id.share_button);

        /** change typeface to imported font */
        try {
            Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
            scoreText.setTypeface(myFont);
            scoreView.setTypeface(myFont);
            playButton.setTypeface(myFont);
            shareButton.setTypeface(myFont);
        } catch (Exception e) {
        }


        /** Listeners for play and share buttons */
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

    /***/
    private void addScores() {
        SQLiteDatabase db = scoresDAO.getWritableDatabase();
        ContentValues value= new ContentValues();
        value.put("value", score);
        db.insert("scores", null, value);
    }

    /**
     * start new game activity
     */
    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * share message to other apps / social media
     */
    private void shareScore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Check out my score of " + scoreView.getText() + " in AlgeBRAWLER!");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    /**
     * retrieve score from game and display
     */
    @Override
    protected void onStart() {
        score = SETTINGS.getInt("new_score", 0);
        scoreView.setText(toString().valueOf(score));
        knockoutAnimation.start();
        addScores();
        super.onStart();
    }

    /***/
    @Override
    protected void onDestroy() {
        scoresDAO.close();
        super.onDestroy();
    }

    /**
     * Set image for animation based from sprite of 'loser'
     */
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
