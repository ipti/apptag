package br.ipti.org.apptag.extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Filipi Andrade on 05-Jul-17.
 */

public class SavedSharedPreferences {

    private static final String PREF_USERNAME = "username";

    private static SharedPreferences getSharedPreferences(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static void setUsername(Context c, String u) {
        SharedPreferences.Editor editor = getSharedPreferences(c).edit();
        editor.putString(PREF_USERNAME, u);
        editor.commit();
        editor.apply();
    }

    public static String getUsername(Context c) {
        return getSharedPreferences(c).getString(PREF_USERNAME, "");
    }

    public static void logout(Context c) {
        SharedPreferences.Editor editor = getSharedPreferences(c).edit();
        editor.clear();
        editor.commit();
        editor.apply();
    }
}
