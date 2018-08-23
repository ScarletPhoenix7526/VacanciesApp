package ru.coder.laboratory2_vacancies.main.pager;

import android.support.v4.app.Fragment;

public class PagerTabItem {
    private final Fragment mFragment;
    private final CharSequence mTitle;

    public PagerTabItem(Fragment fragment, CharSequence title) {
        this.mFragment = fragment;
        this.mTitle = title;
    }

    Fragment getFragment() {
        return mFragment;
    }

    public CharSequence getTitle() {
        return mTitle;
    }
}