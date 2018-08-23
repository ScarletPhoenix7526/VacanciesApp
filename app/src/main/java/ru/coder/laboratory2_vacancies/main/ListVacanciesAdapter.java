package ru.coder.laboratory2_vacancies.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.data.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.data.network.VacancyModel;

public class ListVacanciesAdapter extends ArrayAdapter<VacancyModel> {
    private boolean[] checkboxStatus;
    private boolean flagViewed;
    private List<VacancyModel> mList;
    private SQLiteDB mDataBase = StartApp.get(getContext()).loadSQLiteDB();

    public ListVacanciesAdapter(@NonNull Context context, List<VacancyModel> list, boolean flag) {
        super(context, 0, list);
        checkboxStatus = new boolean[list.size()];
        this.mList = list;
        this.flagViewed = flag;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext()).inflate(R.layout.item_vacancies_list, parent, false);

            holder = new ViewHolder();
            holder.tvPositionDescription = convertView.findViewById(R.id.tvPositionDescription);
            holder.tvSalary = convertView.findViewById(R.id.tvSalary);
            holder.tvTopic = convertView.findViewById(R.id.tvTopic);
            holder.tvWhenCreated = convertView.findViewById(R.id.tvWhenCreated);
            holder.checkBox = convertView.findViewById(R.id.cbCheckbox);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final VacancyModel model = getItem(position);

        if (model != null) {

            holder.tvWhenCreated.setText(transformingDate(model.getData()));

            if (!model.getProfession().equals("Не определено")) {
                holder.tvTopic.setText(model.getProfession());
            } else {
                holder.tvTopic.setText(model.getHeader());
            }

            if (!model.getSalary().equals("")) {
                holder.tvSalary.setText(model.getSalary());
            }

            holder.tvPositionDescription.setText(model.getHeader());

            holder.checkBox.setOnCheckedChangeListener((buttonView, flag) -> checkboxStatus[position] = flag);

            holder.checkBox.setOnClickListener(v -> {
                if (checkboxStatus[position]) {
                    mDataBase.saveInFavorite(model);
                    Toast.makeText(getContext(),
                            "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                } else {
                    mDataBase.deleteFavorite(model.getPid());
                    Toast.makeText(getContext(),
                            "Удалено из избранных", Toast.LENGTH_SHORT).show();
                }
            });

            ArrayList<VacancyModel> listForFavorites =
                    (ArrayList<VacancyModel>) mDataBase.loadFavoriteFromDB();
            if (listForFavorites != null) {
                for (int i = 0; i < mList.size(); i++) {
                    checkboxStatus[i] = false;
                    for (int m = 0; m < listForFavorites.size(); m++) {
                        if (mList.get(i).getPid().equals(listForFavorites.get(m).getPid())) {
                            checkboxStatus[i] = true;
                        }
                    }
                }
            }
        }
        holder.checkBox.setChecked(checkboxStatus[position]);

        return convertView;
    }

    class ViewHolder {
        TextView tvTopic, tvWhenCreated, tvPositionDescription, tvSalary;
        CheckBox checkBox;
    }

    private String transformingDate(String date) {
        String givenTemplateDate = "yyyy-MM-dd HH:mm:ss";
        String newTemplateDate = "HH:mm dd MMM yyyy";
        Locale userLocation = Locale.getDefault();
        SimpleDateFormat givenFormDate = new SimpleDateFormat(givenTemplateDate, userLocation);
        SimpleDateFormat transformedDate = new SimpleDateFormat(newTemplateDate, userLocation);
        String newDateForm = null;
        try {
            Date javaDate = givenFormDate.parse(date);
            newDateForm = transformedDate.format(javaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateForm;
    }
}