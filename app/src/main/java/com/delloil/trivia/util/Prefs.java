package com.delloil.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighest(int score){
            int currentScore = score;
            int lastScore = preferences.getInt("High_Score",0);

            if (currentScore>lastScore){
                preferences.edit().putInt("High_Score",currentScore).apply();
            }
    }

    public int getHigh(){
        return preferences.getInt("High_Score",0);
    }
}
