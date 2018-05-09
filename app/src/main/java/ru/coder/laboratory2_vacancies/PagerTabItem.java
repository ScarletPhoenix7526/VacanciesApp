package ru.coder.laboratory2_vacancies;

import android.support.v4.app.Fragment;

/**
 * Created by macos_user on 5/10/18.
 */

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
