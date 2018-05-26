package ru.coder.laboratory2_vacancies.page_main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.coder.laboratory2_vacancies.ListVacanciesAdapter;
import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.internet.GetVacanciesService;
import ru.coder.laboratory2_vacancies.internet.VacanciesModel;
import ru.coder.laboratory2_vacancies.page_details.DetailsPageActivity;

/**
 * Created by macos_user on 5/10/18.
 */

public class DayVacanciesFragment extends Fragment {

    private GetVacanciesService service;
    private ListView vacanciesListView;
    private List<VacanciesModel> listWithVacancies;
    private ListVacanciesAdapter adapter;
    private SQLiteDB mDateBase;
    private SwipyRefreshLayout mAddingToList;
    private int addNewVacancies = 1;

    // private boolean internetOK = isOnline(getContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_v, container, false);
        vacanciesListView = view.findViewById(R.id.listVacancies);
        mAddingToList = view.findViewById(R.id.addingToList);
        mAddingToList.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                addNewVacancies += 1;
                getNewData();
            }
        });
        vacanciesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailsPageActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("listWithVacancies", (Serializable) listWithVacancies);
                startActivity(intent);
            }
        });

        mDateBase = StartApp.get(getContext()).loadSQLiteDB();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();

    }

    private void getData() {
        service = StartApp.get(getContext()).getService();
        service.getVacancies("au", "get_all_vacancies", "20", "1")
                .enqueue(new Callback<List<VacanciesModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacanciesModel>> call,
                                           @NonNull Response<List<VacanciesModel>> response) {
                        listWithVacancies = response.body();
                        if (getContext() != null) {
                            adapter = new ListVacanciesAdapter(getContext(), listWithVacancies, true);
                            vacanciesListView.setAdapter(adapter);
                        }
                        mDateBase.deleteVacanciesFromDB();
                        mDateBase.saveVacanciesFromAPI(listWithVacancies);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacanciesModel>> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Нет подключения к интернету",
                                Toast.LENGTH_SHORT).show();
                        List<VacanciesModel> listWithVacancies = mDateBase.loadVacanciesFromDB();
                        adapter = new ListVacanciesAdapter(getContext(), listWithVacancies, true);
                        vacanciesListView.setAdapter(adapter);
                    }
                });
    }

    private void getNewData() {
        service = StartApp.get(getContext()).getService();
        service.getDetectedVacancies("au", "get_post_by_filter", "20",
                String.valueOf(addNewVacancies), "", "")
                .enqueue(new Callback<List<VacanciesModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacanciesModel>> call,
                                           @NonNull Response<List<VacanciesModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listWithVacancies.addAll(response.body());
                            adapter = new ListVacanciesAdapter(getContext(), listWithVacancies, true);
                            vacanciesListView.setAdapter(adapter);
                            mAddingToList.setRefreshing(false);
                        } else {
                            mAddingToList.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<VacanciesModel>> call, Throwable t) {
                        mAddingToList.setRefreshing(false);
                    }
                });
    }
}