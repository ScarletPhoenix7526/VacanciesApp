package ru.coder.laboratory2_vacancies.page_details;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.network.VacancyModel;

/**
 * Created by macos_user on 5/19/18.
 */

public class DetailsPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private LinearLayout llPressNext, llPressPrevious, linearPress;
    private TextView tvTopic, tvEmployerName, tvWhenCreated, tvSalary,
            tvFromSite, tvTelephoneNumber, tvDetailsAboutVacancy;
    private Button btnCallNumber;
    private CheckBox checkBox;
    private List<VacancyModel> listWithVacancies;
    private int mPositionCardView;
    private SQLiteDB mDataBase;
    private VacancyModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        listWithVacancies =
                (List<VacancyModel>) intent.getSerializableExtra("listWithVacancies");

        mPositionCardView = intent.getIntExtra("position", 0);
        tvTopic = findViewById(R.id.tvTopic);
        tvEmployerName = findViewById(R.id.tvEmployerName);
        tvWhenCreated = findViewById(R.id.tvWhenCreated);
        tvSalary = findViewById(R.id.tvSalary);
        tvFromSite = findViewById(R.id.tvFromSite);
        tvTelephoneNumber = findViewById(R.id.tvTelephoneNumber);
        tvDetailsAboutVacancy = findViewById(R.id.tvDetailsAboutVacancy);
        btnCallNumber = findViewById(R.id.btnCallNumber);
        btnCallNumber.setOnClickListener(this);
        linearPress = findViewById(R.id.linearPress);
        llPressNext = findViewById(R.id.llPressNext);
        if (listWithVacancies.size() == 1) llPressNext.setVisibility(View.INVISIBLE);
        llPressNext.setOnClickListener(this);
        llPressPrevious = findViewById(R.id.llPressPrevious);
        if (listWithVacancies.size() == 1) llPressPrevious.setVisibility(View.INVISIBLE);
        llPressPrevious.setOnClickListener(this);
        checkBox = findViewById(R.id.cbCheckbox);
        checkBox.setOnClickListener(this);
        mDataBase = StartApp.get(this).loadSQLiteDB();

        getDataFromApi();

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llPressNext:
                if (mPositionCardView == listWithVacancies.size() - 1) {
                    llPressNext.setVisibility(View.INVISIBLE);
                } else if (listWithVacancies.size() == 1) {
                    llPressPrevious.setVisibility(View.INVISIBLE);
                    llPressPrevious.setVisibility(View.INVISIBLE);
                } else {
                    llPressPrevious.setVisibility(View.VISIBLE);
                    llPressNext.setVisibility(View.VISIBLE);
                    llPressNext.setClickable(true);
                    llPressPrevious.setClickable(true);
                    mPositionCardView++;
                    getDataFromApi();
                    saveViewed(listWithVacancies.get(mPositionCardView));
                }
                break;

            case R.id.llPressPrevious:
                if (mPositionCardView == 0) {
                    llPressPrevious.setVisibility(View.INVISIBLE);
                } else if (listWithVacancies.size() == 1) {
                    llPressPrevious.setVisibility(View.INVISIBLE);
                    llPressPrevious.setVisibility(View.INVISIBLE);
                } else {
                    llPressPrevious.setVisibility(View.VISIBLE);
                    llPressNext.setVisibility(View.VISIBLE);
                    mPositionCardView--;
                    getDataFromApi();
                    saveViewed(listWithVacancies.get(mPositionCardView));
                }
                break;

            case R.id.btnCallNumber:
                callOnTelephone();
                break;

            case R.id.cbCheckbox:
                if (checkBox.isChecked()) {
                    saveFavorite(listWithVacancies.get(mPositionCardView));
                    Toast.makeText(getApplicationContext(), "Добавлено в избранное",
                            Toast.LENGTH_SHORT).show();
                } else {
                    deleteFavorite(listWithVacancies.get(mPositionCardView));
                    Toast.makeText(getApplicationContext(), "Удалено из избранных",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void saveFavorite(VacancyModel model) {
        mDataBase.saveInFavorite(model);
    }

    private void deleteFavorite(VacancyModel model) {
        mDataBase.deleteFavorite(model.getPid());
    }

    private void getDataFromApi() {
        model = listWithVacancies.get(mPositionCardView);
        tvTopic.setText(model.getHeader());
        tvEmployerName.setText(model.getProfile());
        tvWhenCreated.setText(transformingDate(model.getData()));
        tvSalary.setText(model.getSalary());
        tvFromSite.setText(model.getSite_address());
        tvDetailsAboutVacancy.setText(model.getBody());

        if (model.getTelephone().equals("")) {
            tvTelephoneNumber.setText(R.string.text_no_tel_num);
        } else {
            tvTelephoneNumber.setText(model.getTelephone());
        }

        if (model.getSalary().equals("")) {
            tvSalary.setText(R.string.text_in_tvSalary_when_nodata);
        } else {
            tvSalary.setText(model.getSalary());
        }
        checkBox.setChecked(getCheckboxState(model));
        saveViewed(model);


    }

    private void saveViewed(VacancyModel model) {
        mDataBase.saveViewedVacancy(model.getPid());
    }

    private boolean getCheckboxState(VacancyModel model) {
        ArrayList<VacancyModel> arrayList =
                (ArrayList<VacancyModel>) mDataBase.loadFavoriteFromDB();
        for (int i = 0; i < arrayList.size(); i++) {
            if (model.getPid().equals(arrayList.get(i).getPid())) {
                return true;
            }
        }
        return false;
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

    private void callOnTelephone() {
        String onPhoneCall = listWithVacancies.get(mPositionCardView).getTelephone();
        if (onPhoneCall.contains(";")) {
            onPhoneCall.replace(".", "");
            final String[] callList = onPhoneCall.split(";");
            AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
            buildDialog.setItems(callList, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentCall = new Intent(Intent.ACTION_CALL);
                    intentCall.setData(Uri.parse("tel: " + callList[which]));
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        startActivity(intentCall);
                    } else {
                        // todo request permission
                    }
                }
            });

            AlertDialog dialog = buildDialog.create();
            dialog.show();

        } else {
            Intent intentCall = new Intent(Intent.ACTION_CALL);
            intentCall.setData(Uri.parse("tel: " + onPhoneCall.replace(".", "")));
            startActivity(intentCall);
        }
    }
}