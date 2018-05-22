package ru.coder.laboratory2_vacancies.internet;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.coder.laboratory2_vacancies.database.SQLiteDB;

/**
 * Created by macos_user on 5/14/18.
 */

public class StartApp extends Application {
    private GetVacanciesService service;
    private final static String BASE_URL = "https://au.kg/";
    private SQLiteDB sqLiteDB;


    @Override
    public void onCreate() {
        super.onCreate();
        service = initService();
        sqLiteDB = new SQLiteDB(getApplicationContext());
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
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json;version=1");

                        return chain.proceed(builder.build());
                    }
                })
                .build();
    }

    public static StartApp get(Context context) {
        return (StartApp) context.getApplicationContext();
    }

    public GetVacanciesService getService() {
        return service;
    }

    public SQLiteDB loadSQLiteDB() {
        return sqLiteDB;
    }
}