package ru.coder.laboratory2_vacancies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ru.coder.laboratory2_vacancies.main.MainPageActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        Handler handler = new Handler();

        Runnable runnable = () -> {
            startActivity(new Intent(SplashActivity.this, MainPageActivity.class));
            finish();
        };
        handler.postDelayed(runnable, 2500);
    }
}