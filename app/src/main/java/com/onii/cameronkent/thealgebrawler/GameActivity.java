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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements ShakeEventManager.ShakeListener {

    private SharedPreferences SETTINGS;
    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();
    private TextView mQuestionView, userHPText, comHPText;
    private Button mChoice1Button, mChoice2Button, mChoice3Button;
    private ImageView bgTop, bgBottom, userSprite, comSprite;
    private int userHP = 10;
    private int comHP = 50;
    private String mAnswer;
    private int mQuestionNumber, numQuestions;
    private ShakeEventManager shakeManager;
    private SoundManager soundManager;
    private int kickSound;
    private AnimationDrawable userAttack, comAttack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);

        /** */
//        SCORE_PREF = getSharedPreferences("SCORE_DATA", MODE_PRIVATE);
        SETTINGS = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        numQuestions = mQuestionLibrary.getNumQuestions();

        /** Shake manager instantiation */
        shakeManager = new ShakeEventManager();
        shakeManager.setListener(this);
        shakeManager.init(this);

        /** sound effects */
        soundManager = new SoundManager(this);
        kickSound = soundManager.addSound(R.raw.kick);

        /** ui elements */
        mQuestionView = (TextView) findViewById(R.id.question_text);
        mChoice1Button = (Button) findViewById(R.id.choice1_button);
        mChoice2Button = (Button) findViewById(R.id.choice2_button);
        mChoice3Button = (Button) findViewById(R.id.choice3_button);
        userHPText = (TextView) findViewById(R.id.user_hp_text);
        comHPText = (TextView) findViewById(R.id.com_hp_text);
        bgBottom = (ImageView) findViewById(R.id.bg_bottom);
        bgTop = (ImageView) findViewById(R.id.bg_top);
//        healthBar = (ImageView) findViewById(R.id.health_bar);
//        armorBar = (ImageView) findViewById(R.id.armor_bar);

        populateBackground();

        /** sprite animations */
        userSprite = (ImageView) findViewById(R.id.user_sprite_image);
        comSprite = (ImageView) findViewById(R.id.com_sprite_image);
        populateSprites();
        userAttack = (AnimationDrawable) userSprite.getBackground();
        comAttack = (AnimationDrawable) comSprite.getBackground();

        /** Initial UI setup */
        userHPText.setText(String.valueOf(userHP));
        comHPText.setText(String.valueOf(comHP));
        updateQuestion();

        /** change typeface to imported font */
        try {
            Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
            mQuestionView.setTypeface(myFont);
            mChoice1Button.setTypeface(myFont);
            mChoice2Button.setTypeface(myFont);
            mChoice3Button.setTypeface(myFont);
            userHPText.setTypeface(myFont);
            comHPText.setTypeface(myFont);
        } catch (Exception e) {
        }

        /** Button Listeners */
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

    /**
     * Generate background imagery based on user preferences
     */
    private void populateBackground() {
        String themeChoice = SETTINGS.getString("themeChoice", "Grassland");
        switch (themeChoice) {
            case "Grassland":
                bgBottom.setBackgroundResource(R.drawable.bg_green_bottom_dark);
                bgTop.setBackgroundResource(R.drawable.bg_green_top_dark);
                mChoice1Button.setBackgroundResource(R.drawable.bg_green_button);
                mChoice2Button.setBackgroundResource(R.drawable.bg_green_button);
                mChoice3Button.setBackgroundResource(R.drawable.bg_green_button);
                break;
            case "Desert":
                bgBottom.setBackgroundResource(R.drawable.bg_desert_bottom);
                bgTop.setBackgroundResource(R.drawable.bg_desert_top);
                mChoice1Button.setBackgroundResource(R.drawable.bg_desert_button);
                mChoice2Button.setBackgroundResource(R.drawable.bg_desert_button);
                mChoice3Button.setBackgroundResource(R.drawable.bg_desert_button);
                break;
            case "Winter":
                bgBottom.setBackgroundResource(R.drawable.bg_winter_bottom);
                bgTop.setBackgroundResource(R.drawable.bg_winter_top);
                mChoice1Button.setBackgroundResource(R.drawable.bg_winter_button);
                mChoice2Button.setBackgroundResource(R.drawable.bg_winter_button);
                mChoice3Button.setBackgroundResource(R.drawable.bg_winter_button);
                break;
            case "Graveyard":
                bgBottom.setBackgroundResource(R.drawable.bg_graveyard_bottom);
                bgTop.setBackgroundResource(R.drawable.bg_graveyard_top);
                mChoice1Button.setBackgroundResource(R.drawable.bg_graveyard_button);
                mChoice2Button.setBackgroundResource(R.drawable.bg_graveyard_button);
                mChoice3Button.setBackgroundResource(R.drawable.bg_graveyard_button);
                break;
            case "Sci-Fi":
                bgBottom.setBackgroundResource(R.drawable.bg_scifi_bottom);
                bgTop.setBackgroundResource(R.drawable.bg_scifi_top);
                mChoice1Button.setBackgroundResource(R.drawable.bg_scifi_button);
                mChoice2Button.setBackgroundResource(R.drawable.bg_scifi_button);
                mChoice3Button.setBackgroundResource(R.drawable.bg_scifi_button);
                break;
        }
    }

    /**
     * If user choose right answer plays animation and updates UI
     */
    private void answerCorrect() {
        userAttack.stop();
        userAttack.selectDrawable(0);
        userAttack.start();
        soundManager.play(kickSound);

        comHP = comHP - 1;
        comHPText.setText(String.valueOf(comHP));
        if (comHP == 0) {
            gameOver();
        } else {
            updateQuestion();
        }
    }

    /**
     * If user choose wrong answer plays animation and updates UI
     */
    private void answerIncorrect() {
        comAttack.stop();
        comAttack.selectDrawable(0);
        comAttack.start();
        soundManager.play(kickSound);

        userHP = userHP - 1;
        userHPText.setText(String.valueOf(userHP));
        if (userHP == 0) {
            gameOver();
        } else {
            updateQuestion();
        }
    }

    /**
     * When game over condition is met ends game goes to result
     */
    private void gameOver() {
        int score = 50 - comHP;
        SharedPreferences.Editor editor = SETTINGS.edit();
        editor.putInt("new_score", score).apply();
        if (comHP == 0) {
            editor.putBoolean("win_condition", true).apply();
        } else {
            editor.putBoolean("win_condition", false).apply();
        }
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    /**
     * Populate UI with question and options
     */
    private void updateQuestion() {
        Random rand = new Random();
        mQuestionNumber = rand.nextInt(numQuestions - 1) + 1;
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mChoice1Button.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mChoice2Button.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mChoice3Button.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mAnswer = mQuestionLibrary.getAnswer(mQuestionNumber);
    }

    /**
     * Generate sprites based on user preferences
     */
    private void populateSprites() {
        String userSpriteChoice = SETTINGS.getString("userSpriteChoice", "Knight");
        String comSpriteChoice = SETTINGS.getString("comSpriteChoice", "Ninja");
        switch (userSpriteChoice) {
            case "Knight":
                userSprite.setBackgroundResource(R.drawable.knight_attack_animation);
                break;
            case "Ninja":
                userSprite.setBackgroundResource(R.drawable.ninja_attack_animation);
                break;
            case "Robot":
                userSprite.setBackgroundResource(R.drawable.robot_attack_animation);
                break;
            case "Ninja Girl":
                userSprite.setBackgroundResource(R.drawable.ninja_girl_attack_animation);
                break;
            case "Cowgirl":
                userSprite.setBackgroundResource(R.drawable.cowgirl_attack_animation);
                break;
        }
        switch (comSpriteChoice) {
            case "Knight":
                comSprite.setBackgroundResource(R.drawable.knight_attack_animation);
                break;
            case "Ninja":
                comSprite.setBackgroundResource(R.drawable.ninja_attack_animation);
                break;
            case "Robot":
                comSprite.setBackgroundResource(R.drawable.robot_attack_animation);
                break;
            case "Ninja Girl":
                comSprite.setBackgroundResource(R.drawable.ninja_girl_attack_animation);
                break;
            case "Cowgirl":
                comSprite.setBackgroundResource(R.drawable.cowgirl_attack_animation);
                break;
        }
    }

    /**
     * When user shakes runs update question
     */
    @Override
    public void onShake() {
        updateQuestion();
        Toast.makeText(this, "New Question!", Toast.LENGTH_SHORT).show();
    }

    /** */
    @Override
    protected void onPause() {
        shakeManager.deregister();
        super.onPause();
    }

    /** */
    @Override
    protected void onResume() {
        shakeManager.register();
        super.onResume();
    }
}