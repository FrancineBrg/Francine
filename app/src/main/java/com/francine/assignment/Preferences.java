package com.francine.assignment;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public void setPreferences(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("key", key);
        ed.commit();
    }

    public String getPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        String key = prefs.getString("key", null);
        return (key);
    }
}
