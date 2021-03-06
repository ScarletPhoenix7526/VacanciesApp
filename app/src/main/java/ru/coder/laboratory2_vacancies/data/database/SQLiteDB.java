package ru.coder.laboratory2_vacancies.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.coder.laboratory2_vacancies.data.network.VacancyModel;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "VacanciesDB";
    private static final int DB_VERSION = 2;

    private static final String pid = "pid";
    private static final String header = "header";
    private static final String profile = "profile";
    private static final String salary = "salary";
    private static final String telephone = "telephone";
    private static final String data = "data";
    private static final String profession = "profession";
    private static final String site_address = "site_address";
    private static final String body = "body";
    private static final String VACANCIES_TABLE = "Vacancies_Table";
    private static final String FAVORITE_VACANCIES_TABLE = "Favorite_Vacancies_Table";

    private final String CREATE_VACANCIES_TABLE = "CREATE TABLE IF NOT EXISTS " +
            VACANCIES_TABLE + "(" +
            pid + " TEXT, " +
            header + " TEXT, " +
            profile + " TEXT, " +
            salary + " TEXT, " +
            telephone + " TEXT, " +
            data + " TEXT, " +
            profession + " TEXT, " +
            site_address + " TEXT, " +
            body + " TEXT " + ");";

    private final String CREATE_FAVORITE_VACANCIES_TABLE = "CREATE TABLE IF NOT EXISTS " +
            FAVORITE_VACANCIES_TABLE + "(" +
            pid + " TEXT, " +
            header + " TEXT, " +
            profile + " TEXT, " +
            salary + " TEXT, " +
            telephone + " TEXT, " +
            data + " TEXT, " +
            profession + " TEXT, " +
            site_address + " TEXT, " +
            body + " TEXT " + ");";

    public SQLiteDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VACANCIES_TABLE);
        db.execSQL(CREATE_FAVORITE_VACANCIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VACANCIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_VACANCIES_TABLE);
        onCreate(db);
    }

    public void saveVacanciesFromAPI(List<VacancyModel> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        for (int i = 0; i < list.size(); i++) {
            VacancyModel model = list.get(i);
            cv.put(pid, model.getPid());
            cv.put(header, model.getHeader());
            cv.put(profile, model.getProfile());
            cv.put(salary, model.getSalary());
            cv.put(telephone, model.getTelephone());
            cv.put(data, model.getData());
            cv.put(profession, model.getProfession());
            cv.put(site_address, model.getSite_address());
            cv.put(body, model.getBody());

            long rowID = db.insert(VACANCIES_TABLE, null, cv);
            Log.d("VacanciesIsSave", "rows" + rowID + model.getPid());
        }
        db.close();
    }

    public List<VacancyModel> loadVacanciesFromDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<VacancyModel> list = new ArrayList<>();

        Cursor cursor = db.query(VACANCIES_TABLE,
                null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            int iPid = cursor.getColumnIndex(pid);
            int iHeader = cursor.getColumnIndex(header);
            int iProfile = cursor.getColumnIndex(profile);
            int iSalary = cursor.getColumnIndex(salary);
            int iTelephone = cursor.getColumnIndex(telephone);
            int iData = cursor.getColumnIndex(data);
            int iProfession = cursor.getColumnIndex(profession);
            int iSite = cursor.getColumnIndex(site_address);
            int iBody = cursor.getColumnIndex(body);

            do {
                VacancyModel model = new VacancyModel();
                model.setPid(cursor.getString(iPid));
                model.setHeader(cursor.getString(iHeader));
                model.setProfile(cursor.getString(iProfile));
                model.setSalary(cursor.getString(iSalary));
                model.setTelephone(cursor.getString(iTelephone));
                model.setData(cursor.getString(iData));
                model.setProfession(cursor.getString(iProfession));
                model.setSite_address(cursor.getString(iSite));
                model.setBody(cursor.getString(iBody));
                list.add(model);
            } while (cursor.moveToNext());
            Log.d("loadVacanciesFromAPI", "get");
        } else {
            Log.d("loadVacanciesFromAPI", "don't get");
        }

        cursor.close();
        db.close();
        return list;
    }

    public void deleteVacanciesFromDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VACANCIES_TABLE, null, null);
        db.close();
    }

    public void saveInFavorite(VacancyModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(pid, model.getPid());
        cv.put(header, model.getHeader());
        cv.put(profile, model.getProfile());
        cv.put(salary, model.getSalary());
        cv.put(telephone, model.getTelephone());
        cv.put(data, model.getData());
        cv.put(profession, model.getProfession());
        cv.put(site_address, model.getSite_address());
        cv.put(body, model.getBody());

        long rowID = db.insert(FAVORITE_VACANCIES_TABLE, null, cv);
        Log.d("VacanciesIsSave", "rows" + rowID + model.getPid());
        db.close();
    }

    public List<VacancyModel> loadFavoriteFromDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<VacancyModel> list = new ArrayList<>();

        Cursor cursor = db.query(FAVORITE_VACANCIES_TABLE,
                null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            int iPid, iHeader, iProfile, iSalary, iTelephone, iData, iProfession, iSite, iBody;
            iPid = cursor.getColumnIndex(pid);
            iHeader = cursor.getColumnIndex(header);
            iProfile = cursor.getColumnIndex(profile);
            iSalary = cursor.getColumnIndex(salary);
            iTelephone = cursor.getColumnIndex(telephone);
            iData = cursor.getColumnIndex(data);
            iProfession = cursor.getColumnIndex(profession);
            iSite = cursor.getColumnIndex(site_address);
            iBody = cursor.getColumnIndex(body);

            do {
                VacancyModel model = new VacancyModel();
                model.setPid(cursor.getString(iPid));
                model.setHeader(cursor.getString(iHeader));
                model.setProfile(cursor.getString(iProfile));
                model.setSalary(cursor.getString(iSalary));
                model.setTelephone(cursor.getString(iTelephone));
                model.setData(cursor.getString(iData));
                model.setProfession(cursor.getString(iProfession));
                model.setSite_address(cursor.getString(iSite));
                model.setBody(cursor.getString(iBody));
                list.add(model);

            } while (cursor.moveToNext());
            Log.d("loadFavoriteVacancy", "get");
        } else {
            Log.d("loadFavoriteVacancy", "don't get");
        }

        cursor.close();
        db.close();
        return list;
    }

    public void deleteFavorite(String pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAVORITE_VACANCIES_TABLE, pid + "=?", new String[] {pos});
        Log.d("VacanciesIsDeleted", "delete is ok" + pos);
        db.close();
    }
}