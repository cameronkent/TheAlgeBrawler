package com.onii.cameronkent.thealgebrawler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
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
    private Button mChoice1Button, mChoice2Button, mChoice3Button;
    private ImageView healthBar, armorBar, userSprite, comSprite;
    private int mUserHP = 10;
    private int comDef = 50;
    private String mAnswer;
    private int mQuestionNumber, numQuestions;
    private AnimationDrawable userAttack, comAttack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);

        /***/
        SCORE_PREF = getSharedPreferences("SCORE_DATA", MODE_PRIVATE);

        mQuestionView = (TextView) findViewById(R.id.question_text);
        mChoice1Button = (Button) findViewById(R.id.choice1_button);
        mChoice2Button = (Button) findViewById(R.id.choice2_button);
        mChoice3Button = (Button) findViewById(R.id.choice3_button);
        healthBar = (ImageView) findViewById(R.id.health_bar);
        armorBar = (ImageView) findViewById(R.id.armor_bar);
        numQuestions = mQuestionLibrary.getNumQuestions();

        userSprite = (ImageView) findViewById(R.id.user_sprite_image);
        comSprite = (ImageView) findViewById(R.id.com_sprite_image);
        userSprite.setBackgroundResource(R.drawable.knight_attack_animation); // TODO: 2/05/2017  set to variable with sharedPref
        comSprite.setBackgroundResource(R.drawable.ninja_attack_animation);
        userAttack = (AnimationDrawable) userSprite.getBackground();
        comAttack = (AnimationDrawable) comSprite.getBackground();

        /**Initial UI setup*/
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
        userAttack.stop();
        userAttack.selectDrawable(0);
        userAttack.start();
        comDef = comDef - 1;
        android.view.ViewGroup.LayoutParams layoutParams = armorBar.getLayoutParams();
        layoutParams.width = (layoutParams.width / (comDef + 1)) * comDef;
        armorBar.setLayoutParams(layoutParams);
        if (comDef == 0) {
            gameOver();
        } else {
            updateQuestion();
        }
    }

    private void answerIncorrect() {
        comAttack.stop();
        comAttack.selectDrawable(0);
        comAttack.start();
        mUserHP = mUserHP - 1;
        android.view.ViewGroup.LayoutParams layoutParams = healthBar.getLayoutParams();
        layoutParams.width = (layoutParams.width / 10) * 9;
        healthBar.setLayoutParams(layoutParams);
        if (mUserHP == 0) {
            gameOver();
        } else {
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