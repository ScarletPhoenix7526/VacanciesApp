package ru.coder.laboratory2_vacancies.page_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.page_about_us.AboutUsActivity;
import ru.coder.laboratory2_vacancies.page_favorite.FavoriteActivity;
import ru.coder.laboratory2_vacancies.page_main.fragments.DayVacanciesFragment;
import ru.coder.laboratory2_vacancies.page_main.fragments.DesirableVacanciesFragment;
import ru.coder.laboratory2_vacancies.page_main.fragments.SearchingFragment;
import ru.coder.laboratory2_vacancies.page_main.pager.PagerAdapter;
import ru.coder.laboratory2_vacancies.page_main.pager.PagerTabItem;


public class MainPageActivity extends AppCompatActivity {

    private ArrayList<PagerTabItem> mTabs = new ArrayList<>();
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private Toolbar mToolbar;
    private SQLiteDB mDataBase;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSearch:
                SearchingFragment searchFragment = new SearchingFragment();
                searchFragment.show(getFragmentManager(), "searching");
                break;
        }
        return true;
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

        PrimaryDrawerItem searching = new PrimaryDrawerItem()
                .withName(R.string.text_drawer_searching)
                .withIdentifier(1)
                .withIcon(R.drawable.ic_searching);
        PrimaryDrawerItem marked = new PrimaryDrawerItem()
                .withName(R.string.text_marked_vacancies)
                .withIdentifier(2)
                .withIcon(R.drawable.ic_star);
        PrimaryDrawerItem about = new PrimaryDrawerItem()
                .withName(R.string.text_about)
                .withIdentifier(3)
                .withIcon(R.drawable.ic_info);
        PrimaryDrawerItem exit = new PrimaryDrawerItem()
                .withName(R.string.text_exit)
                .withIdentifier(4)
                .withIcon(R.drawable.ic_exit);

        Drawer.OnDrawerItemClickListener drawerOnClick = new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch (position) {
                    case 1:
                        SearchingFragment searchFragment = new SearchingFragment();
                        searchFragment.show(getFragmentManager(), "searching");
                        break;

                    case 2:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        break;

                    case 4:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        break;

                    case 5:
                        finish();
                        break;
                }
                return false;
            }
        };

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
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(drawerOnClick)
                .addDrawerItems(searching, marked, new DividerDrawerItem(), about, exit)
                .build();
    }
}