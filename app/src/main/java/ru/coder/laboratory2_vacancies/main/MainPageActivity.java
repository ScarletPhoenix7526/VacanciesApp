package ru.coder.laboratory2_vacancies.main;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.ArrayList;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.data.localdata.SharedPreferencesHelper;
import ru.coder.laboratory2_vacancies.favorites.FavoriteActivity;
import ru.coder.laboratory2_vacancies.main.pager.PagerAdapter;
import ru.coder.laboratory2_vacancies.main.pager.PagerTabItem;
import ru.coder.laboratory2_vacancies.search.SearchingPageActivity;

public class MainPageActivity extends AppCompatActivity implements MainActivityListener, View.OnClickListener {

    private static final int RC_SEARCH = 100;

    public interface UpdateSearch {
        void updateSearch(@Nullable String search);
    }
    public interface UpdateTerms {

        void updateSearch(@Nullable String term, @Nullable String salary, @Nullable String search);

        void clearSearch();

        void clearTerm();

        void clearSalary();
    }

    private ArrayList<PagerTabItem> mTabs = new ArrayList<>();
    private Drawer mDrawer;
    private Toolbar mToolbar;
    private UpdateSearch mUpdatePreferableVacancies;
    private UpdateTerms mUpdateSearch;
    private LinearLayout mSearchBlock;
    private TextView mTermTextView;
    private LinearLayout mTermBlock;
    private TextView mSearchTextView;
    private LinearLayout mSalaryBlock;
    private TextView mSalaryTextView;

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
        mSearchBlock = findViewById(R.id.llSearchQueryBlock);
        mSearchTextView = findViewById(R.id.tvSearchQuery);
        ImageButton closeSearchButton = findViewById(R.id.ivClose);
        closeSearchButton.setOnClickListener(this);
        mTermBlock = findViewById(R.id.llTermQueryBlock);
        mTermTextView = findViewById(R.id.tvTermQuery);
        ImageButton closeSearchButtonTerm = findViewById(R.id.ivCloseTerm);
        closeSearchButtonTerm.setOnClickListener(this);
        mSalaryBlock = findViewById(R.id.llSalaryQueryBlock);
        mSalaryTextView = findViewById(R.id.tvSalaryQuery);
        ImageButton closeSearchButtonSalary = findViewById(R.id.ivCloseSalary);
        closeSearchButtonSalary.setOnClickListener(this);
        getIntent();

        if (isShowSettingsDialog()) {
            showDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_adding_prof, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        EditText editText = dialogView.findViewById(R.id.etEnterProf);
        Button cancelButton = dialogView.findViewById(R.id.btCancel);
        cancelButton.setOnClickListener(view -> alertDialog.hide());
        Button saveButton = dialogView.findViewById(R.id.btSave);
        saveButton.setOnClickListener(view -> {
            alertDialog.hide();
            saveSearchQuery(editText.getText().toString());
            if (mUpdatePreferableVacancies != null) {
                mUpdatePreferableVacancies.updateSearch(editText.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSearch:
                startActivityForResult(new Intent(getApplicationContext(), SearchingPageActivity.class), RC_SEARCH);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SEARCH && resultCode == RESULT_OK) {
            String search = data.getStringExtra(SearchingPageActivity.SEARCH_KEY);
            if(search != null && !search.trim().isEmpty()) {
                mSearchTextView.setText(search);
                mSearchBlock.setVisibility(View.VISIBLE);
            }
            String term = data.getStringExtra(SearchingPageActivity.TERM_KEY);
            if(term != null && !term.trim().isEmpty()) {
                mTermTextView.setText(term);
                mTermBlock.setVisibility(View.VISIBLE);
            }
            String salary = data.getStringExtra(SearchingPageActivity.SALARY_KEY);
            if(salary != null && !salary.trim().isEmpty()) {
                mSalaryTextView.setText(salary);
                mSalaryBlock.setVisibility(View.VISIBLE);
            }
            mUpdateSearch.updateSearch(term, salary, search);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
                mSearchTextView.setText("");
                mSearchBlock.setVisibility(View.GONE);
                mUpdateSearch.clearSearch();
                break;
            case R.id.ivCloseTerm:
                mTermTextView.setText("");
                mTermBlock.setVisibility(View.GONE);
                mUpdateSearch.clearTerm();
                break;
            case R.id.ivCloseSalary:
                mSalaryTextView.setText("");
                mSalaryBlock.setVisibility(View.GONE);
                mUpdateSearch.clearSalary();
                break;
        }
    }

    private void createToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void createTabs() {
        DayVacanciesFragment dayFragment = new DayVacanciesFragment();
        CharSequence titleLeft = "ВАКАНСИИ ЗА СУТКИ";
        PagerTabItem pagerItemDay = new PagerTabItem(dayFragment, titleLeft);
        if (dayFragment instanceof UpdateTerms) {
            mUpdateSearch = dayFragment;
        }

        DesirableVacanciesFragment desireFragment = new DesirableVacanciesFragment();
        CharSequence titleRight = "ПОДХОДЯЩИЕ";
        PagerTabItem pagerItemDesire = new PagerTabItem(desireFragment, titleRight);
        if (desireFragment instanceof UpdateSearch) {
            mUpdatePreferableVacancies = desireFragment;
        }

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
        PrimaryDrawerItem exit = new PrimaryDrawerItem()
                .withName(R.string.text_exit)
                .withIdentifier(4)
                .withIcon(R.drawable.ic_exit);

        Drawer.OnDrawerItemClickListener drawerOnClick = (view, position, drawerItem) -> {
            switch (position) {
                case 1:
                    startActivityForResult(new Intent(getApplicationContext(), SearchingPageActivity.class), RC_SEARCH);
                    break;

                case 2:
                    startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                    break;

                case 4:
                    finish();
                    break;
            }
            return false;
        };

        AccountHeader mHeader = new AccountHeaderBuilder()
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
                .addDrawerItems(searching, marked, new DividerDrawerItem(), exit)
                .build();
    }

    private boolean isShowSettingsDialog() {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this);
        return !helper.isDialogShown();
    }

    private void saveSearchQuery(String search) {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this);
        helper.saveShowDialog();
        helper.saveSearch(search);
    }
}