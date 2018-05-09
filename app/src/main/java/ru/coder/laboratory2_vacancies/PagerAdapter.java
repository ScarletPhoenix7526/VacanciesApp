package ru.coder.laboratory2_vacancies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by macos_user on 5/10/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<PagerTabItem> mTabs;

    public PagerAdapter(FragmentManager fm, ArrayList<PagerTabItem> mTabs) {
        super(fm);
        this.mTabs = mTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabs.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
}