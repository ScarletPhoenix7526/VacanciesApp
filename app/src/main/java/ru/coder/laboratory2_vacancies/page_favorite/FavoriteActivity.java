package ru.coder.laboratory2_vacancies.page_favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import ru.coder.laboratory2_vacancies.page_main.ListVacanciesAdapter;
import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.network.VacancyModel;
import ru.coder.laboratory2_vacancies.page_details.DetailsPageActivity;

/**
 * Created by macos_user on 5/23/18.
 */

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SQLiteDB mDataBase;
    private List<VacancyModel> listWithVacancies;
    private ListVacanciesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDataBase = StartApp.get(getApplicationContext()).loadSQLiteDB();
        ListView listView = findViewById(R.id.listVacancies);
        listWithVacancies = mDataBase.loadFavoriteFromDB();
        if (listWithVacancies.isEmpty()) {
            return;
        }
        adapter = new ListVacanciesAdapter(getApplicationContext(), listWithVacancies, false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsPageActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("marker", true);
        intent.putExtra("listWithVacancies", (Serializable) listWithVacancies);
        startActivity(intent);
    }
}