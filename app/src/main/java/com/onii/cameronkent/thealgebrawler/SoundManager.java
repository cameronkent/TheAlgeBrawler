package com.onii.cameronkent.thealgebrawler;

import android.content.Context;
import android.media.SoundPool;
import android.os.Build;

public class SoundManager {
    private SoundPool pool;
    private Context context;

    public SoundManager(Context context) {
        this.context = context;
        SoundPool.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new SoundPool.Builder();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setMaxStreams(10);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pool = builder.build();
        }
    }

    public int addSound(int resourceID) {
        return pool.load(context, resourceID, 1);
    }
    public void play(int soundID) {
        pool.play(soundID, 1, 1, 1, 0, 1);
    }
}