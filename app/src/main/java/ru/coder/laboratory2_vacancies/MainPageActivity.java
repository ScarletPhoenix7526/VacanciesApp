package ru.coder.laboratory2_vacancies;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private ArrayList<PagerTabItem> mTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        createTabs();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mTabs);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    private void createTabs() {
        DayVacanciesFragment dayFragment = new DayVacanciesFragment();
        CharSequence titleLeft = "ВАКАНСИИ ЗА СУТКИ";
        PagerTabItem pagerItemDay = new PagerTabItem(dayFragment, titleLeft);

        DesirableVacanciesFragment desireFragment = new DesirableVacanciesFragment();
        CharSequence titleRight = "ПОДХОДЯЩИЕ";
        PagerTabItem pagerItemDesire = new PagerTabItem(desireFragment, titleRight);

        mTabs.add(pagerItemDay);
        mTabs.add(pagerItemDesire);
    }
}
