package ru.coder.laboratory2_vacancies.data.localdata;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final SharedPreferencesHelper instance = new SharedPreferencesHelper();
    private static SharedPreferences sharedPreferences;
    private static final String APP_SETTINGS = "app_settings";
    private static final String SHOW_DIALOG = "show_dialog";
    private static final String SEARCH_KEY = "search_key";

    public static synchronized SharedPreferencesHelper getInstance(Context context) {
        if (sharedPreferences == null) {
            init(context);
        }
        return instance;
    }

    public boolean isDialogShown() {
        return sharedPreferences.getBoolean(SHOW_DIALOG, false);
    }

    public void saveShowDialog() {
        sharedPreferences.edit().putBoolean(SHOW_DIALOG, true).apply();
    }

    public void saveSearch(String search) {
        sharedPreferences.edit().putString(SEARCH_KEY, search).apply();
    }

    public String getSearch() {
        return sharedPreferences.getString(SEARCH_KEY, "");
    }

    private static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    private SharedPreferencesHelper() {
    }
}