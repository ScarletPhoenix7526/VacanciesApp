package ru.coder.laboratory2_vacancies.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by macos_user on 5/13/18.
 */

public class VacanciesModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Object id;

    @SerializedName("pid")
    @Expose
    private String pid;

    @SerializedName("header")
    @Expose
    private String header;

    @SerializedName("profile")
    @Expose
    private String profile;

    @SerializedName("salary")
    @Expose
    private String salary;

    @SerializedName("telephone")
    @Expose
    private String telephone;

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("profession")
    @Expose
    private String profession;

    @SerializedName("site_address")
    @Expose
    private String site_address;

    @SerializedName("body")
    @Expose
    private String body;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSite_address() {
        return site_address;
    }

    public void setSite_address(String site_address) {
        this.site_address = site_address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}