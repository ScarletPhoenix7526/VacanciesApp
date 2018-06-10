package ru.coder.laboratory2_vacancies.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by macos_user on 5/14/18.
 */

public interface GetVacanciesService {
    @FormUrlEncoded
    @POST("")
    Call<List<VacancyModel>> getVacancies();

    @FormUrlEncoded
    @POST("")
    Call<List<VacancyModel>> getDetectedVacancies();
}