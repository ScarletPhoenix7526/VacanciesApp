package ru.coder.laboratory2_vacancies.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;
import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.data.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.data.localdata.SharedPreferencesHelper;
import ru.coder.laboratory2_vacancies.data.network.GetVacanciesService;
import ru.coder.laboratory2_vacancies.data.network.VacancyModel;
import ru.coder.laboratory2_vacancies.details.DetailsPageActivity;

public class DesirableVacanciesFragment extends Fragment implements MainPageActivity.UpdateSearch {

    private GetVacanciesService mService;
    private RelativeLayout mEmptyBlock;
    private List<VacancyModel> mListWithVacancies = new ArrayList<>();
    private DesirableVacanciesAdapter mAdapter;
    private SQLiteDB mDateBase;
    private SwipyRefreshLayout mAddingToList;
    private int addNewVacancies = 1;
    private MainActivityListener mActivityListener;
    @Nullable
    private String mPredefinedSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = StartApp.get(getContext()).getService();
        mDateBase = StartApp.get(getContext()).loadSQLiteDB();
        mPredefinedSearch = getQuery();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_v, container, false);
        ListView vacanciesListView = view.findViewById(R.id.listVacancies);
        mEmptyBlock = view.findViewById(R.id.emptyBlock);
        Button addSearch = view.findViewById(R.id.add_search);
        addSearch.setOnClickListener(buttonView -> showDialog());
        mAddingToList = view.findViewById(R.id.addingToList);
        mAddingToList.setOnRefreshListener(direction -> {
            addNewVacancies++;
            updateSearch(mPredefinedSearch);
        });

        vacanciesListView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), DetailsPageActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("mListWithVacancies", (Serializable) mListWithVacancies);
            startActivity(intent);
        });

        if (getContext() != null) {
            mAdapter = new DesirableVacanciesAdapter(getContext(), new ArrayList<>());
        }
        vacanciesListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateSearch(mPredefinedSearch);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityListener) {
            mActivityListener = (MainActivityListener) context;
        }
    }

    @Override
    public void updateSearch(@Nullable String search) {
        if (search != null && !search.isEmpty()) {
            mPredefinedSearch = search;
            getData();
        } else {
            showEmptyState();
        }
    }

    private void showDialog() {
        mActivityListener.showDialog();
    }

    private void showEmptyState() {
        mAddingToList.setVisibility(View.GONE);
        mEmptyBlock.setVisibility(View.VISIBLE);
    }

    private void getData() {
        new Thread() {
            public void run() {
                try {
                    Response<List<VacancyModel>> response =
                            mService.getPreferredVacancies("au", "search", "20",
                                    String.valueOf(addNewVacancies), mPredefinedSearch).execute();
                    if (response.isSuccessful()) {
                        List<VacancyModel> dbVacancies = mDateBase.loadFavoriteFromDB();
                        List<VacancyModel> responseVacancies = response.body();
                        for (VacancyModel vacancyModel : responseVacancies) {
                            for (VacancyModel dbVacancy : dbVacancies) {
                                if (dbVacancy.getPid().equals(vacancyModel.getPid())) {
                                    vacancyModel.setChecked(true);
                                }
                            }
                            String date = transformingDate(vacancyModel.getData());
                            vacancyModel.setDate(date);
                        }
                        getActivity().runOnUiThread(() -> onResponse(responseVacancies));
                    } else {
                        onFailure();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
                .start();
    }

    private void onResponse(@NonNull List<VacancyModel> vacancyModels) {
        mAddingToList.setRefreshing(false);
        mListWithVacancies.addAll(vacancyModels);
        mAdapter.addVacancyModels(mListWithVacancies);
        mDateBase.deleteVacanciesFromDB();
        mDateBase.saveVacanciesFromAPI(vacancyModels);
        if (!mAdapter.isEmpty()) {
            mEmptyBlock.setVisibility(View.GONE);
            mAddingToList.setVisibility(View.VISIBLE);
        }
    }

    private void onFailure() {
        mAddingToList.setRefreshing(false);
        Toast.makeText(getContext(), R.string.error_loading_vacancies, Toast.LENGTH_SHORT).show();
        List<VacancyModel> listWithVacancies = mDateBase.loadVacanciesFromDB();
        mAdapter.addVacancyModels(listWithVacancies);
    }

    private String transformingDate(String date) {
        String givenTemplateDate = "yyyy-MM-dd HH:mm:ss";
        String newTemplateDate = "HH:mm dd MMM yyyy";
        Locale userLocation = Locale.getDefault();
        SimpleDateFormat givenFormDate = new SimpleDateFormat(givenTemplateDate, userLocation);
        SimpleDateFormat transformedDate = new SimpleDateFormat(newTemplateDate, userLocation);
        String newDateForm = null;
        try {
            Date javaDate = givenFormDate.parse(date);
            newDateForm = transformedDate.format(javaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateForm;
    }

    private String getQuery() {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(getContext());
        String query = helper.getSearch();
        if (helper.isDialogShown() && !query.isEmpty()) {
            return query;
        }

        return null;
    }
}