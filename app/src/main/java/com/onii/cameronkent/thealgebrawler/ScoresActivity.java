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

public class ScoresActivity extends AppCompatActivity {

    private ScoresDAOHelper scoresDAO;
    private LinearLayout scoreLayout;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set activity to fullscreen
        setContentView(R.layout.activity_scores);

        scoresDAO = new ScoresDAOHelper(this);
        scoreLayout = (LinearLayout) findViewById(R.id.score_text_layout);

        updateScores();

    }

    private void updateScores() {
        TextView scoreText;
        Cursor cursor = scoresDAO.getReadableDatabase().rawQuery("select * from scores", null);
        while (cursor.moveToNext()) {
            scoreText = new TextView(this);
            scoreText.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            scoreText.setTextColor(Color.WHITE);
            scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            scoreText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            /** change typeface to imported font */
            try {
                Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/pink-kangaroo.regular.ttf");
                scoreText.setTypeface(myFont);
            } catch (Exception e) {
            }

            scoreText.setText(cursor.getString(1));
            scoreLayout.addView(scoreText);
        }
        cursor.close();


    }

    @Override
    protected void onDestroy() {
        scoresDAO.close();
        super.onDestroy();
    }

}
