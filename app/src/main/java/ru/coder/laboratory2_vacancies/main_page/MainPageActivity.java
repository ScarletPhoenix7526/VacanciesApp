package ru.coder.laboratory2_vacancies.main_page;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.ArrayList;

import ru.coder.laboratory2_vacancies.R;


public class MainPageActivity extends AppCompatActivity{

    private ArrayList<PagerTabItem> mTabs = new ArrayList<>();
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        createTabs();
        createToolbar();
        initDrawer();

        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mTabs);
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    private void createToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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

    private void initDrawer() {
        createToolbar();

        PrimaryDrawerItem searching = new PrimaryDrawerItem().withName(R.string.text_drawer_searching).withIcon(R.drawable.ic_searching);
        PrimaryDrawerItem marked = new PrimaryDrawerItem().withName(R.string.text_marked_vacancies).withIcon(R.drawable.ic_star);
        PrimaryDrawerItem about = new PrimaryDrawerItem().withName(R.string.text_about).withIcon(R.drawable.ic_info);
        PrimaryDrawerItem exit = new PrimaryDrawerItem().withName(R.string.text_exit).withIcon(R.drawable.ic_exit);

        mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorDarkPurple)
                .addProfiles(new ProfileDrawerItem()
                        .withIcon(R.mipmap.ic_launcher_round)
                        .withName(R.string.text_header_title)
                        .withEmail(R.string.text_header_version))
                .withAlternativeProfileHeaderSwitching(false)
                .withHeightDp(160)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)
                .addDrawerItems(searching, marked, new DividerDrawerItem(), about, exit)
                .build();
    }
}