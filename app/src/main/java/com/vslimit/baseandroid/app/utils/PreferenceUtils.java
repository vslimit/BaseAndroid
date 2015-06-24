package com.vslimit.baseandroid.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.vslimit.baseandroid.app.MainApplication;

public class PreferenceUtils {

    public static final String KEY = "flag";
    public static final String REGION = "region";
    public static final String LINE = "line";
    public static final String ONLINE = "1";
    public static final String OFFLINE = "0";

    public static final String LOGIN = "1";
    public static final String LOGOUT = "0";

    public static final String TOKEN = "token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_NAME = "user_name";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_PIC = "user_pic";

    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MainApplication
                .getInstance());
    }


    public static void savePreference(String key, String value) {
        SharedPreferences preferences = getPreferences();
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreference(String key) {
        SharedPreferences preferences = getPreferences();
        return preferences.getString(key, null);
    }

    public static void clearPreference(String key) {
        SharedPreferences preferences = getPreferences();
        Editor editor = preferences.edit();
        editor.putString(key, null);
        editor.apply();
    }

    public static boolean containPreference(String key) {
        SharedPreferences preferences = getPreferences();
        return preferences.contains(key);
    }


    public static final String FUNCTION_SCROLLER_PLAYED = "function_scroller_played";

    public static boolean get(Context context, String name, boolean defaultValue) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean value = prefs.getBoolean(name, defaultValue);
        return value;
    }

    public static boolean set(Context context, String name, boolean value) {
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean(name, value);
        return editor.commit();
    }
}
