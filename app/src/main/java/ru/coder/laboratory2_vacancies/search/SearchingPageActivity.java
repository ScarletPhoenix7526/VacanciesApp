package ru.coder.laboratory2_vacancies.search;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ru.coder.laboratory2_vacancies.R;

public class SearchingPageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SEARCH_KEY = "search_key";
    public static final String TERM_KEY = "term_key";
    public static final String SALARY_KEY = "salary_key";
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        searchView = findViewById(R.id.etSearch);
        Button searchBtn = findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(this);
        Button advancedSearch = findViewById(R.id.btAdvancedSearch);
        advancedSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                String searchQuery = searchView.getText().toString();
                if (isValid(searchQuery)) {
                    Intent output = new Intent();
                    output.putExtra(SEARCH_KEY, searchQuery);
                    setResult(RESULT_OK, output);
                    finish();
                }
                break;
            case R.id.btAdvancedSearch:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search_terms, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        RadioGroup groupTerms = dialogView.findViewById(R.id.searchTermsGroupTerms);
        RadioGroup groupSalary = dialogView.findViewById(R.id.searchTermsGroupSalary);

        Button cancelButton = dialogView.findViewById(R.id.btCancel);
        cancelButton.setOnClickListener(view -> alertDialog.hide());
        Button saveButton = dialogView.findViewById(R.id.btSave);
        saveButton.setOnClickListener(view -> {
            RadioButton terms = dialogView.findViewById(groupTerms.getCheckedRadioButtonId());
            RadioButton salary = dialogView.findViewById(groupSalary.getCheckedRadioButtonId());

            Intent output = new Intent();
            if (terms != null) {
                output.putExtra(TERM_KEY, terms.getText());
            }
            if (salary != null) {
                output.putExtra(SALARY_KEY, salary.getText());
            }
            setResult(RESULT_OK, output);
            alertDialog.dismiss();
            finish();
        });
    }

    private boolean isValid(@Nullable String searchQuery) {
        return searchQuery != null && !searchQuery.trim().isEmpty();
    }
}