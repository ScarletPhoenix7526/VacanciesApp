package ru.coder.laboratory2_vacancies.page_main.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ru.coder.laboratory2_vacancies.R;

/**
 * Created by macos_user on 5/24/18.
 */

public class SearchingFragment extends DialogFragment implements View.OnClickListener {

    private RadioGroup radioModeGroup, radioSalaryGroup;
    private RadioButton rbModeAny, rbModeFullDay, rbModeFlexible, rbModeNighty, rbModeDistant;
    private boolean flagMode, flagSalary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searching, container, false);

        radioModeGroup = view.findViewById(R.id.radioModeGroup);
        radioSalaryGroup = view.findViewById(R.id.radioSalaryGroup);

        int radioButtonId = radioModeGroup.getCheckedRadioButtonId();
        rbModeAny = view.findViewById(radioButtonId);
        rbModeFullDay = view.findViewById(radioButtonId);
        rbModeFlexible = view.findViewById(radioButtonId);
        rbModeNighty = view.findViewById(radioButtonId);
        rbModeDistant = view.findViewById(radioButtonId);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
            radioModeGroup.clearCheck();
            break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}