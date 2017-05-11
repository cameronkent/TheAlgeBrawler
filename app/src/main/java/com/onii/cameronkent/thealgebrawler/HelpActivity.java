package com.onii.cameronkent.thealgebrawler;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set activity to fullscreen
        setContentView(R.layout.activity_help);

        TextView helpOne = (TextView) findViewById(R.id.help_text_one);
        TextView helpTwo = (TextView) findViewById(R.id.help_text_two);
        TextView helpThree = (TextView) findViewById(R.id.help_text_three);
        TextView helpFour = (TextView) findViewById(R.id.help_text_four);
        TextView helpFive = (TextView) findViewById(R.id.help_text_five);

        /** change typeface to imported font */
        try {
            Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
            helpOne.setTypeface(myFont);
            helpTwo.setTypeface(myFont);
            helpThree.setTypeface(myFont);
            helpFour.setTypeface(myFont);
            helpFive.setTypeface(myFont);
        } catch (Exception e) {
        }
    }
}
