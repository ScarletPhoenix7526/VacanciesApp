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

import java.util.List;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.StartApp;
import ru.coder.laboratory2_vacancies.data.database.SQLiteDB;
import ru.coder.laboratory2_vacancies.data.network.VacancyModel;

public class DesirableVacanciesAdapter extends ArrayAdapter<VacancyModel> {
    private List<VacancyModel> mList;
    private SQLiteDB mDataBase = StartApp.get(getContext()).loadSQLiteDB();

    public DesirableVacanciesAdapter(@NonNull Context context, List<VacancyModel> list) {
        super(context, 0, list);
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        final VacancyModel model = getItem(position);
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

        if (model == null) {
            return convertView;
        }

        holder.tvWhenCreated.setText(model.getDate());

        if (!model.getProfession().equals("Не определено")) {
            holder.tvTopic.setText(model.getProfession());
        } else {
            holder.tvTopic.setText(model.getHeader());
        }

        if (!model.getSalary().equals("")) {
            holder.tvSalary.setText(model.getSalary());
        }

        holder.tvPositionDescription.setText(model.getHeader());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(model.isChecked());
        holder.checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                model.setChecked(true);
                mDataBase.saveInFavorite(model);
                Toast.makeText(getContext(),
                        "Добавлено в избранное", Toast.LENGTH_SHORT).show();
            } else {
                mDataBase.deleteFavorite(model.getPid());
                Toast.makeText(getContext(),
                        "Удалено из избранных", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void addVacancyModels(List<VacancyModel> vacancyModels) {
        if (vacancyModels.isEmpty()) {
            return;
        }
        mList.addAll(vacancyModels);
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tvTopic, tvWhenCreated, tvPositionDescription, tvSalary;
        CheckBox checkBox;
    }
}