package ru.coder.laboratory2_vacancies.data;

import android.content.Context;

public class ResourcesHelper {
    private Context mContext;

    public ResourcesHelper(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}