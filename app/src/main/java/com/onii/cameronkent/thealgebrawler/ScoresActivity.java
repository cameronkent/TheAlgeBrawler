package com.onii.cameronkent.thealgebrawler;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoresActivity extends AppCompatActivity {

    private ScoresDAOHelper scoresDAO;
    private LinearLayout scoreLayout;
    private ArrayList<Integer> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set activity to fullscreen
        setContentView(R.layout.activity_scores);

        scoresDAO = new ScoresDAOHelper(this);
        scoreLayout = (LinearLayout) findViewById(R.id.score_text_layout);
        scoreList = new ArrayList<>();

        getScoresDB();
        displayScores();
    }

    /**
     * Retrieve scores from the database store in array
     */
    private void getScoresDB() {
        Cursor cursor = scoresDAO.getReadableDatabase().rawQuery("select * from scores", null);
        while (cursor.moveToNext()) {
            scoreList.add(Integer.valueOf(cursor.getString(1)));
        }
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        for (int i = 0; i < scoreList.size(); i++) {
            System.out.println(scoreList.get(i));
        }
        cursor.close();
    }

    /**
     * display the top 5 scores from array
     */
    private void displayScores() {
        System.out.println("displayScores begin");
        TextView scoreText;
        for (int i = 0; i < 5; i++) {
            scoreText = new TextView(this);
            scoreText.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scoreText.setTextColor(Color.WHITE);
            scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            scoreText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            try {
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
                scoreText.setTypeface(myFont);
            } catch (Exception ignored) {
            }
            scoreText.setText(String.valueOf(scoreList.get(i)));
            scoreLayout.addView(scoreText);
        }
    }

    /**
     * Closes db on activity end
     */
    @Override
    protected void onDestroy() {
        scoresDAO.close();
        super.onDestroy();
    }

}
