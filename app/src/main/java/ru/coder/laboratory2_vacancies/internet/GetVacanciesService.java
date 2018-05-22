package ru.coder.laboratory2_vacancies.internet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by macos_user on 5/14/18.
 */

public interface GetVacanciesService {
    @FormUrlEncoded
    @POST("mobile-api.php")
    Call<List<VacanciesModel>> getVacancies(@Field("login") String login,
                                            @Field("f") String f,
                                            @Field("limit") String limit,
                                            @Field("page") String page);

}
