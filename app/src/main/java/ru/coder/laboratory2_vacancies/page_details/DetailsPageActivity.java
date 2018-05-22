package ru.coder.laboratory2_vacancies.page_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.internet.VacanciesModel;

/**
 * Created by macos_user on 5/19/18.
 */

public class DetailsPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private LinearLayout llPressNext, llPressPrevious;
    private TextView tvTopic, tvEmployerName, tvWhenCreated, tvSalary,
            tvFromSite, tvTelephoneNumber, tvDetailsAboutVacancy;
    private Button btnCallNumber;
    private List<VacanciesModel> listWithVacancies;
    private VacanciesModel model;
    private int mPositionCardView;

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
                (List<VacanciesModel>) intent.getSerializableExtra("listWithVacancies");

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
        llPressNext = findViewById(R.id.llPressNext);
        llPressNext.setOnClickListener(this);
        llPressPrevious = findViewById(R.id.llPressPrevious);
        llPressPrevious.setOnClickListener(this);

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
                if (mPositionCardView == 19) {
                    llPressNext.setVisibility(View.INVISIBLE);
                } else {
                    llPressPrevious.setVisibility(View.VISIBLE);
                    llPressNext.setVisibility(View.VISIBLE);
                    mPositionCardView++;
                    getDataFromApi();
                    break;
                }
            case R.id.llPressPrevious:
                if (mPositionCardView == 0) {
                    llPressPrevious.setVisibility(View.INVISIBLE);
                } else {
                    llPressPrevious.setVisibility(View.VISIBLE);
                    llPressNext.setVisibility(View.VISIBLE);
                    mPositionCardView--;
                    getDataFromApi();
                    break;
                }
            case R.id.btnCallNumber:
                callOnTelephone();
                break;
        }
    }

    private void getDataFromApi() {
        model = listWithVacancies.get(mPositionCardView);
        tvTopic.setText(model.getHeader());
        tvEmployerName.setText(model.getProfile());
        tvWhenCreated.setText(transformingDate(model.getData()));
        tvSalary.setText(model.getSalary());
        tvFromSite.setText(model.getSite_address());
        tvTelephoneNumber.setText(model.getTelephone());
        tvDetailsAboutVacancy.setText(model.getBody());

        if (model.getSalary().equals("")) {
            tvSalary.setText(R.string.text_in_tvSalary_when_nodata);
        } else {
            tvSalary.setText(model.getSalary());
        }
    }

    private void callOnTelephone() {
        String transferPhoneNumber = listWithVacancies
                .get(mPositionCardView)
                .getTelephone();
        if (!transferPhoneNumber.equals("")) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: " + transferPhoneNumber.replace(".", "")));
            startActivity(intent);
        }
    }

    private String transformingDate(String date) {
        String givenTemplateDate = "yyyy-MM-dd HH:mm:ss";   // приходящий шаблон
        String newTemplateDate = "HH:mm dd MMM yyyy";       // шаблон, в который нужно трансформировать приходящий шаблон
        //Locale userLocation = Locale.forLanguageTag(String.valueOf(Locale.UNICODE_LOCALE_EXTENSION));
        Locale userLocation = Locale.getDefault();// текущая Земная локация девайса пользователя
        SimpleDateFormat givenFormDate = new SimpleDateFormat(givenTemplateDate, userLocation);
        SimpleDateFormat transformedDate = new SimpleDateFormat(newTemplateDate, userLocation);
        String newDateForm = null;      // если тип String, то null; если Int, то 0;
        try {
            Date javaDate = givenFormDate.parse(date); // javadate - объект класса Date языка Java.
            newDateForm = transformedDate.format(javaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateForm;
    }
}


