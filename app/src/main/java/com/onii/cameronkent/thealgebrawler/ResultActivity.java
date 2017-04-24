package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private SharedPreferences SCORE_PREF;
    private TextView scoreView;
    private EditText nameInput;
    private Button playButton;
    private Button scoresButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        SCORE_PREF = getSharedPreferences("SCORE_DATA", MODE_PRIVATE);
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
        super.onStart();
        int score = SCORE_PREF.getInt("new_score", 0);
        scoreView.setText(toString().valueOf(score));

    }

}
