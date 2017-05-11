package com.onii.cameronkent.thealgebrawler;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    Spinner userSpriteSpinner, comSpriteSpinner, themeSpinner;
    SharedPreferences SETTINGS;
    TextView userSpriteText, comSpriteText, gameThemeText;
    ArrayAdapter<CharSequence> userSpriteAdapter, comSpriteAdapter, themeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        /***/
        SETTINGS = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        userSpriteSpinner = (Spinner) findViewById(R.id.user_sprite_spinner);
        comSpriteSpinner = (Spinner) findViewById(R.id.com_sprite_spinner);
        themeSpinner = (Spinner) findViewById(R.id.game_theme_spinner);
        userSpriteText = (TextView) findViewById(R.id.player_textView);
        comSpriteText = (TextView) findViewById(R.id.com_textView);
        gameThemeText = (TextView) findViewById(R.id.theme_textView);

        /** Populate spinners with string array resources*/
        userSpriteAdapter = ArrayAdapter.createFromResource(this, R.array.sprites, R.layout.spinner_item);
        userSpriteAdapter.setDropDownViewResource(R.layout.spinner_item);
        userSpriteSpinner.setAdapter(userSpriteAdapter);

        comSpriteAdapter = ArrayAdapter.createFromResource(this, R.array.sprites, R.layout.spinner_item);
        comSpriteAdapter.setDropDownViewResource(R.layout.spinner_item);
        comSpriteSpinner.setAdapter(comSpriteAdapter);

        themeAdapter = ArrayAdapter.createFromResource(this, R.array.themes, R.layout.spinner_item);
        themeAdapter.setDropDownViewResource(R.layout.spinner_item);
        themeSpinner.setAdapter(themeAdapter);

        /** change typeface to imported font */
        try {
            Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
            userSpriteText.setTypeface(myFont);
            comSpriteText.setTypeface(myFont);
            gameThemeText.setTypeface(myFont);
        } catch (Exception e) {
        }
    }

    private void updateSettings() {
        String userSpriteChoice = userSpriteSpinner.getSelectedItem().toString();
        String comSpriteChoice = comSpriteSpinner.getSelectedItem().toString();
        String themeChoice = themeSpinner.getSelectedItem().toString();
        SETTINGS.edit().putString("userSpriteChoice", userSpriteChoice).apply();
        SETTINGS.edit().putString("comSpriteChoice", comSpriteChoice).apply();
        SETTINGS.edit().putString("themeChoice", themeChoice).apply();
    }

    @Override
    protected void onStart() {
        String userSpriteChoice = SETTINGS.getString("userSpriteChoice", "Knight");
        int userSpinnerPosition = userSpriteAdapter.getPosition(userSpriteChoice);
        userSpriteSpinner.setSelection(userSpinnerPosition);

        String comSpriteChoice = SETTINGS.getString("comSpriteChoice", "Ninja");
        int comSpinnerPosition = comSpriteAdapter.getPosition(comSpriteChoice);
        comSpriteSpinner.setSelection(comSpinnerPosition);

        String themeChoice = SETTINGS.getString("themeChoice", "Graveyard");
        int themeSpinnerPosition = themeAdapter.getPosition(themeChoice);
        themeSpinner.setSelection(themeSpinnerPosition);
        super.onStart();
    }

    @Override
    protected void onStop() {
        updateSettings();
        super.onStop();
    }
}