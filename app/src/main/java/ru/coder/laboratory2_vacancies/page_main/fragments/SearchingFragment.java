package ru.coder.laboratory2_vacancies.page_main.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.network.GetVacanciesService;

/**
 * Created by macos_user on 5/24/18.
 */

public class SearchingFragment extends DialogFragment implements View.OnClickListener {

    private RadioGroup radioModeGroup, radioSalaryGroup;
    private RadioButton btnsMode, btnsSalary, rbModeAny, rbSalaryAny;
    private int modePosition, salaryPosition;
    private String[] modeString = {"Любой", "Полный день", "Гибкий", "Удаленно", "Ночной"};
    private int[] salaryInt = {0, 5000, 10000, 30000};
    private GetVacanciesService service;
    private Button btnReset, btnDone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searching, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        radioModeGroup = view.findViewById(R.id.radioModeGroup);
        radioSalaryGroup = view.findViewById(R.id.radioSalaryGroup);

        radioModeGroup.setOnCheckedChangeListener(groupMode);
        radioSalaryGroup.setOnCheckedChangeListener(groupSalary);

        rbModeAny = view.findViewById(R.id.rbModeAny);
        rbSalaryAny = view.findViewById(R.id.rbSalaryAny);

        btnReset = view.findViewById(R.id.btnReset);
        btnDone = view.findViewById(R.id.btnDone);

        btnReset.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                radioModeGroup.clearCheck();
                radioSalaryGroup.clearCheck();
                rbModeAny.setChecked(true);
                rbSalaryAny.setChecked(true);
                break;

            case R.id.btnDone:

                break;
        }
    }

    RadioGroup.OnCheckedChangeListener groupMode = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getCheckedRadioButtonId() != -1) {
                radioModeGroup.clearCheck();
                btnsMode = group.findViewById(checkedId);
                modePosition = group.getCheckedRadioButtonId();
            }

        }
    };

    RadioGroup.OnCheckedChangeListener groupSalary = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getCheckedRadioButtonId() != -1) {
                radioSalaryGroup.clearCheck();
                btnsSalary = group.findViewById(checkedId);
                salaryPosition = group.getCheckedRadioButtonId();
            }
        }
    };
}