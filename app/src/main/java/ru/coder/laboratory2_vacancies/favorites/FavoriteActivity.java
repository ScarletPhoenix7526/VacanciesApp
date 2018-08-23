package ru.coder.laboratory2_vacancies.favorites;

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

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.data.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.data.network.VacancyModel;
import ru.coder.laboratory2_vacancies.details.DetailsPageActivity;
import ru.coder.laboratory2_vacancies.main.ListVacanciesAdapter;

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<VacancyModel> mListWithVacancies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SQLiteDB mDataBase = StartApp.get(getApplicationContext()).loadSQLiteDB();
        ListView listView = findViewById(R.id.listVacancies);
        mListWithVacancies = mDataBase.loadFavoriteFromDB();
        if (mListWithVacancies.isEmpty()) {
            return;
        }
        ListVacanciesAdapter adapter = new ListVacanciesAdapter(getApplicationContext(), mListWithVacancies, false);
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
        intent.putExtra("mListWithVacancies", (Serializable) mListWithVacancies);
        startActivity(intent);
    }
}