package ru.coder.laboratory2_vacancies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.coder.laboratory2_vacancies.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.internet.VacanciesModel;

/**
 * Created by macos_user on 5/13/18.
 */

public class ListVacanciesAdapter extends ArrayAdapter<VacanciesModel> {
    private boolean[] checkboxStatus;   // должен быть массив, так как много View
    private boolean flagCheckBox;
    private List<VacanciesModel> list;
    private SQLiteDB mDataBase = StartApp.get(getContext()).loadSQLiteDB();

    public ListVacanciesAdapter(@NonNull Context context, List<VacanciesModel> list, boolean flag) {
        super(context, 0, list);
        checkboxStatus = new boolean[list.size()];
        this.list = list;
        this.flagCheckBox = flag;
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
            holder.llViewed = convertView.findViewById(R.id.llViewed);
            holder.checkBox = convertView.findViewById(R.id.cbCheckbox);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final VacanciesModel model = getItem(position);

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
            // сохраняем состояние checkbox
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean flag) {
                    checkboxStatus[position] = flag;
                }
            });


            // выводим сообщение о добавлении в избранное, сохраняем и добавляем в избранное
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkboxStatus[position] /*== flagCheckBox*/) {
                        mDataBase.saveInFavorite(model);
                        Toast.makeText(getContext(),
                                "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                    } else {
                        mDataBase.deleteFavorite(model.getPid());
                        Toast.makeText(getContext(),
                                "Удалено из избранных", Toast.LENGTH_SHORT).show();
                    } // нужен notifyDataSetChanged - обновление списка после добавления чекбоксом
                }
            });

            ArrayList<VacanciesModel> listForFavorites =
                    (ArrayList<VacanciesModel>) mDataBase.loadFavoriteFromDB();
            if (listForFavorites != null) {
                for (int i = 0; i < list.size(); i++) {
                    checkboxStatus[i] = false;
                    for (int m = 0; m < listForFavorites.size(); m++) {
                        if (list.get(i).getPid().equals(listForFavorites.get(m).getPid())) {
                            checkboxStatus[i] = true;
                        }
                    }
                }
            }

            holder.checkBox.setChecked(checkboxStatus[position]);

        }
        return convertView;
    }

    class ViewHolder {
        TextView tvTopic, tvWhenCreated, tvPositionDescription, tvSalary;
        CheckBox checkBox;
        LinearLayout llViewed;
    }

    private String transformingDate(String date) {
        String givenTemplateDate = "yyyy-MM-dd HH:mm:ss";   // приходящий шаблон
        String newTemplateDate = "HH:mm dd MMM yyyy";       // шаблон, в который нужно трансформировать приходящий шаблон
        //Locale userLocation = Locale.forLanguageTag(String.valueOf(Locale.UNICODE_LOCALE_EXTENSION));
        Locale userLocation = Locale.getDefault();
        SimpleDateFormat givenFormDate = new SimpleDateFormat(givenTemplateDate, userLocation);
        SimpleDateFormat transformedDate = new SimpleDateFormat(newTemplateDate, userLocation);
        String newDateForm = null;      // если тип String, то null; если Int, то 0;
        try {
            Date javaDate = givenFormDate.parse(date); // javadate - объект класса Date языка Java.
            newDateForm = transformedDate.format(javaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateForm;
    }
}