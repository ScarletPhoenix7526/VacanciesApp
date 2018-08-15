package ru.coder.laboratory2_vacancies.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String TITLE = "ru.coder.laboratory2_vacancies";
    private static final String PROFESSION = "ru.coder.laboratory2_vacancies.data";
    private SharedPreferences mPreferences;

    public PreferencesHelper(Context context) {
        mPreferences = context.getSharedPreferences(TITLE, Context.MODE_PRIVATE);
    }

    public void setProfession(String prof) {
        mPreferences.edit()
                .putString(PROFESSION, prof)
                .apply();
    }

    public String getProfession() {
        return mPreferences.getString(PROFESSION, "");
    }
}
