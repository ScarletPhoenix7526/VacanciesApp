package ru.coder.laboratory2_vacancies.page_main.fragments;

import android.app.DialogFragment;
import android.content.Intent;
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
import ru.coder.laboratory2_vacancies.page_searching.SearchingActivity;

/**
 * Created by macos_user on 5/24/18.
 */

public class SearchingFragment extends DialogFragment implements View.OnClickListener {

    private RadioGroup radioModeGroup, radioSalaryGroup;
    private RadioButton btnsMode, btnsSalary, rbModeAny, rbSalaryAny;
    private boolean bMode = true, bSalary = true;
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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                radioModeGroup.clearCheck();
                radioSalaryGroup.clearCheck();
                break;

            case R.id.btnDone:
                    Intent intent = new Intent(getContext(), SearchingActivity.class);
                    startActivity(intent);
                    break;
                }
        }

        RadioGroup.OnCheckedChangeListener groupMode = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioModeGroup.clearCheck();
                btnsMode = group.findViewById(checkedId);


            }
        };

        RadioGroup.OnCheckedChangeListener groupSalary = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSalaryGroup.clearCheck();
                btnsSalary = group.findViewById(checkedId);


            }
        };
    }