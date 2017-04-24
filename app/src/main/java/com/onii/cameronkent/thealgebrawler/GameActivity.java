package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private SharedPreferences SCORE_PREF;
    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    private TextView mQuestionView;
    private Button mChoice1Button;
    private Button mChoice2Button;
    private Button mChoice3Button;
    private TextView mUserHPView;
    private TextView mComHitsView;
    private ImageView healthBar;

    private int mUserHP = 10;
    private int comDef = 50;
    private String mAnswer;
    private int mQuestionNumber;
    private int numQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);

        /***/
        SCORE_PREF = getSharedPreferences("SCORE_DATA", MODE_PRIVATE);

        mUserHPView = (TextView) findViewById(R.id.user_hp_text);
        mComHitsView = (TextView) findViewById(R.id.com_hits_text);
        mQuestionView = (TextView) findViewById(R.id.question_text);
        mChoice1Button = (Button) findViewById(R.id.choice1_button);
        mChoice2Button = (Button) findViewById(R.id.choice2_button);
        mChoice3Button = (Button) findViewById(R.id.choice3_button);
        healthBar = (ImageView) findViewById(R.id.health_bar);

        numQuestions = mQuestionLibrary.getNumQuestions();

        /**Initial UI setup*/
        mUserHPView.setText(String.valueOf(mUserHP));
        mComHitsView.setText(String.valueOf(comDef));
        updateQuestion();

        /**Button1 Listener*/
        mChoice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChoice1Button.getText() == mAnswer) {
                    answerCorrect();
                } else {
                    answerIncorrect();
                }
            }
        });

        /**Button2 Listener*/
        mChoice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChoice2Button.getText() == mAnswer) {
                    answerCorrect();
                } else {
                    answerIncorrect();
                }
            }
        });

        /**Button3 Listener*/
        mChoice3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChoice3Button.getText() == mAnswer) {
                    answerCorrect();
                } else {
                    answerIncorrect();
                }
            }
        });

    }

    private void answerCorrect() {
        comDef = comDef - 1;
        if (comDef == 0) {
            gameOver();
        } else {
            mComHitsView.setText(String.valueOf(comDef));
            updateQuestion();
        }
    }

    private void answerIncorrect() {
        mUserHP = mUserHP - 1; //remove after bars implemented
        android.view.ViewGroup.LayoutParams layoutParams = healthBar.getLayoutParams();
        layoutParams.width = (layoutParams.width / 10) * 9;
        // TODO: 21/04/2017 fix height changing
        healthBar.setLayoutParams(layoutParams);
        if (mUserHP == 0) {
            gameOver();
        } else {
            mUserHPView.setText(String.valueOf(mUserHP));
            updateQuestion();
        }
    }

    /***/
    private void gameOver() {
        comDef = 50 - comDef;
        SharedPreferences.Editor editor = SCORE_PREF.edit();
        editor.putInt("new_score", comDef).apply();
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    /**
     * Populate UI with question and options
     */
    private void updateQuestion() {
        Random rand = new Random();
        mQuestionNumber = rand.nextInt((numQuestions - 1) + 1) + 1;
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mChoice1Button.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mChoice2Button.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mChoice3Button.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mAnswer = mQuestionLibrary.getAnswer(mQuestionNumber);
    }

}