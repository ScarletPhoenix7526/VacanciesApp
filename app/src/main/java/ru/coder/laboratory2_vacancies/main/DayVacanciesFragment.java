package ru.coder.laboratory2_vacancies.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.data.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.data.network.GetVacanciesService;
import ru.coder.laboratory2_vacancies.data.network.VacancyModel;
import ru.coder.laboratory2_vacancies.details.DetailsPageActivity;

public class DayVacanciesFragment extends Fragment implements MainPageActivity.UpdateTerms {

    private GetVacanciesService mService;
    private ListView mVacanciesListView;
    private List<VacancyModel> mListWithVacancies;
    private DesirableVacanciesAdapter mAdapter;
    private SQLiteDB mDateBase;
    private SwipyRefreshLayout mAddingToList;
    private int addNewVacancies = 1;
    private String term = "", salary = "", search = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = StartApp.get(getContext()).getService();
        mDateBase = StartApp.get(getContext()).loadSQLiteDB();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_v, container, false);
        mVacanciesListView = view.findViewById(R.id.listVacancies);
        mAddingToList = view.findViewById(R.id.addingToList);
        mAddingToList.setOnRefreshListener(direction -> {
            addNewVacancies += 1;
            getNewData();
        });
        mVacanciesListView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), DetailsPageActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("mListWithVacancies", (Serializable) mListWithVacancies);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    @Override
    public void updateSearch(@Nullable String term, @Nullable String salary, @Nullable String search) {
        addNewVacancies = 1;
        if (term != null) {
            this.term = term;
        }
        if (salary != null) {
            this.salary = salary;
        }
        if (search != null) {
            this.search = search;
        }
        mAdapter.clear();
        getNewData();
    }

    @Override
    public void clearSearch() {
        this.search = null;
        mListWithVacancies.clear();
        getNewData();
    }

    @Override
    public void clearSalary() {
        this.salary = null;
        mListWithVacancies.clear();
        getNewData();
    }

    @Override
    public void clearTerm() {
        this.term = null;
        mListWithVacancies.clear();
        getNewData();
    }

    private void getData() {
        mService = StartApp.get(getContext()).getService();
        mService.getVacancies("au", "get_all_vacancies", "20", "1")
                .enqueue(new Callback<List<VacancyModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacancyModel>> call,
                                           @NonNull Response<List<VacancyModel>> response) {
                        mListWithVacancies = response.body();
                        if (getContext() != null) {
                            mAdapter = new DesirableVacanciesAdapter(getContext(),
                                    mListWithVacancies);
                            mVacanciesListView.setAdapter(mAdapter);
                        }
                        mDateBase.deleteVacanciesFromDB();
                        mDateBase.saveVacanciesFromAPI(mListWithVacancies);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacancyModel>> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Нет подключения к интернету",
                                Toast.LENGTH_SHORT).show();
                        List<VacancyModel> listWithVacancies = mDateBase.loadVacanciesFromDB();
                        if (getContext() != null) {
                            mAdapter = new DesirableVacanciesAdapter(getContext(),
                                    listWithVacancies);
                        }
                        mVacanciesListView.setAdapter(mAdapter);
                    }
                });
    }

    private void getNewData() {
        mService.getDetectedVacancies("au", "get_post_by_filter", "20", String.valueOf(addNewVacancies), salary, term, search)
                .enqueue(new Callback<List<VacancyModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<VacancyModel>> call,
                                           @NonNull Response<List<VacancyModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mListWithVacancies.addAll(response.body());
                            if (getContext() != null) {
                                mAdapter = new DesirableVacanciesAdapter(getContext(), mListWithVacancies);
                            }
                            mVacanciesListView.setAdapter(mAdapter);
                            mAddingToList.setRefreshing(false);
                        } else {
                            mAddingToList.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<VacancyModel>> call,
                                          @NonNull Throwable t) {
                        mAddingToList.setRefreshing(false);
                    }
                });
    }
}