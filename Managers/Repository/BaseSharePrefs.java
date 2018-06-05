package com.requestum.android.motoguy.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuliia on 01.03.18.
 */

public class BaseSharePrefs {
    public static final String PREFS_NAME = "app_prefs";

    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    public BaseSharePrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

}
