package com.onii.cameronkent.thealgebrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        /**
         * Populate array strings for sprites and themes */
        String[] SPRITES = new String[5];
        SPRITES[0] = "Knight";
        SPRITES[1] = "Ninja";
        SPRITES[2] = "Ninja Girl";
        SPRITES[3] = "Robot";
        SPRITES[4] = "Cowgirl";

        String[] THEMES = new String[5];
        THEMES[0] = "Grassland";
        THEMES[1] = "Winter";
        THEMES[2] = "Graveyard";
        THEMES[3] = "Sci-Fi";
        THEMES[4] = "Desert";

    }
}
//
//                WordAdapter itemsAdapter = new WordAdapter(this, words);
//                ListView listView = (ListView) findViewById(R.id.list);
//                listView.setAdapter(itemsAdapter);