package ru.coder.laboratory2_vacancies;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.coder.laboratory2_vacancies.data.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.data.network.GetVacanciesService;

public class StartApp extends Application {
    private GetVacanciesService mService;
    private final static String BASE_URL = BuildConfig.BASE_URL;
    private SQLiteDB mSqLiteDB;


    @Override
    public void onCreate() {
        super.onCreate();
        mService = initService();
        mSqLiteDB = new SQLiteDB(getApplicationContext());
    }

    public static GetVacanciesService initService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
                .create(GetVacanciesService.class);
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .addHeader("Accept", "application/json;version=1");

                    return chain.proceed(builder.build());
                })
                .build();
    }

    public static StartApp get(Context context) {
        return (StartApp) context.getApplicationContext();
    }

    public GetVacanciesService getService() {
        return mService;
    }

    public SQLiteDB loadSQLiteDB() {
        return mSqLiteDB;
    }
}