package ru.coder.laboratory2_vacancies.page_main.pager;

import android.support.v4.app.Fragment;

public class PagerTabItem {
    private final Fragment fragment;
    private final CharSequence title;

    public PagerTabItem(Fragment fragment, CharSequence title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public CharSequence getTitle() {
        return title;
    }
}
