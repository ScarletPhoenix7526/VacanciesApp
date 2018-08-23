package ru.coder.laboratory2_vacancies.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.coder.laboratory2_vacancies.BuildConfig;

public interface GetVacanciesService {
    @FormUrlEncoded
    @POST(BuildConfig.BASE_ENDPOINT)
    Call<List<VacancyModel>> getVacancies(@Field("login") String login,
                                          @Field("f") String f,
                                          @Field("limit") String limit,
                                          @Field("page") String page);

    @FormUrlEncoded
    @POST(BuildConfig.BASE_ENDPOINT)
    Call<List<VacancyModel>> getDetectedVacancies(@Field("login") String login,
                                                  @Field("f") String f,
                                                  @Field("limit") String limit,
                                                  @Field("page") String page,
                                                  @Field("salary") String salary,
                                                  @Field("term") String term,
                                                  @Field("search_str") String searchQuery);

    @FormUrlEncoded
    @POST(BuildConfig.BASE_ENDPOINT)
    Call<List<VacancyModel>> getPreferredVacancies(@Field("login") String login,
                                                   @Field("f") String f,
                                                   @Field("limit") String limit,
                                                   @Field("page") String page,
                                                   @Field("search_str") String searchQuery);
}