package com.francine.assignment;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public void setPreferences(int key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt("key", key);
        ed.commit();
    }

    public int getPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        int key = prefs.getInt("key", 0);
        return (key);
    }
}
