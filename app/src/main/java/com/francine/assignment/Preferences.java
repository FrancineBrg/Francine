package com.francine.assignment;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public void setPreferencesKey(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("key", key);
        ed.commit();
    }

    public String getPreferencesKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        String key = prefs.getString("key", null);
        return (key);
    }

    public void setPreferencesLabel(String label, Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("label", label);
        ed.commit();
    }

    public String getPreferencesLabel(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("preferences", context.MODE_PRIVATE);
        String label = prefs.getString("label", null);
        return (label);
    }
}
