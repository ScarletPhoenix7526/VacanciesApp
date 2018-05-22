package ru.coder.laboratory2_vacancies.page_main;

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

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.page_details.DetailsPageActivity;
import ru.coder.laboratory2_vacancies.internet.GetVacanciesService;
import ru.coder.laboratory2_vacancies.internet.StartApp;
import ru.coder.laboratory2_vacancies.internet.VacanciesModel;

/**
 * Created by macos_user on 5/10/18.
 */

public class DayVacanciesFragment extends Fragment {

    private GetVacanciesService service;
    private ListView vacanciesListView;
    private List<VacanciesModel> listWithVacancies;
    private ListVacanciesAdapter adapter;
    private SQLiteDB mDateBase;
    // private boolean internetOK = isOnline(getContext());


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_v, container, false);

        vacanciesListView = view.findViewById(R.id.listVacancies);
        vacanciesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailsPageActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("listWithVacancies", (Serializable) listWithVacancies);
                startActivity(intent);
            }
        });

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
                    public void onResponse(Call<List<VacanciesModel>> call,
                                           Response<List<VacanciesModel>> response) {
                        listWithVacancies = response.body();
                        if (getContext() != null) {
                            adapter = new ListVacanciesAdapter(getContext(), listWithVacancies, true);
                            vacanciesListView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<VacanciesModel>> call, Throwable t) {

                    }
                });
    }

    /*public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }*/
}

