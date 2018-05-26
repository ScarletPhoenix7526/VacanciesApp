package ru.coder.laboratory2_vacancies.page_main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.coder.laboratory2_vacancies.R;

/**
 * Created by macos_user on 5/10/18.
 */

public class DesirableVacanciesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desirable_v, container, false);
        return view;
    }
}
