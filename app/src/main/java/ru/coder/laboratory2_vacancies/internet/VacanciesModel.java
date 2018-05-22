package ru.coder.laboratory2_vacancies.internet;

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
    @SerializedName("salary1")
    @Expose
    private String salary1;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("count1")
    @Expose
    private Object count1;
    @SerializedName("image_src")
    @Expose
    private Object image_src;
    @SerializedName("raiting")
    @Expose
    private Integer rating;
    @SerializedName("update_date")
    @Expose
    private String update_date;
    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("post_created")
    @Expose
    private String post_created;
    @SerializedName("au_post_id")
    @Expose
    private Object au_post_id;
    @SerializedName("paid")
    @Expose
    private String paid;

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

    public String getSalary1() {
        return salary1;
    }

    public void setSalary1(String salary1) {
        this.salary1 = salary1;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getCount1() {
        return count1;
    }

    public void setCount1(Object count1) {
        this.count1 = count1;
    }

    public Object getImage_src() {
        return image_src;
    }

    public void setImage_src(Object image_src) {
        this.image_src = image_src;
    }

    public int getRaiting() {
        return rating;
    }

    public void setRaiting(int raiting) {
        this.rating = raiting;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPost_created() {
        return post_created;
    }

    public void setPost_created(String post_created) {
        this.post_created = post_created;
    }

    public Object getAu_post_id() {
        return au_post_id;
    }

    public void setAu_post_id(Object au_post_id) {
        this.au_post_id = au_post_id;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}