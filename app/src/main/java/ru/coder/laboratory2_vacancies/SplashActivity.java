package ru.coder.laboratory2_vacancies;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.coder.laboratory2_vacancies.main_page.MainPageActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainPageActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 2500);
    }
}

