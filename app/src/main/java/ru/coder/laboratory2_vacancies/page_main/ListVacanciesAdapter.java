package ru.coder.laboratory2_vacancies.page_main;

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

import java.util.List;

import ru.coder.laboratory2_vacancies.R;
import ru.coder.laboratory2_vacancies.internet.VacanciesModel;

/**
 * Created by macos_user on 5/13/18.
 */

public class ListVacanciesAdapter extends ArrayAdapter <VacanciesModel> {
    private boolean[] checkboxStatus;   // должен быть массив, так как много View
    private boolean flagCheckBox;
    private List<VacanciesModel> list;

    ListVacanciesAdapter(@NonNull Context context, List<VacanciesModel> list, boolean flag) {
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
            holder.tvWhenCreated.setText(model.getData());
            holder.tvTopic.setText(model.getProfession());
            holder.tvSalary.setText(model.getSalary());
            holder.tvPositionDescription.setText(model.getHeader());
            // сохраняем состояние checkbox
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean flag) {
                    checkboxStatus[position] = flag;
                    if (checkboxStatus[position]) {
                        Toast.makeText(getContext(),
                                "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Удалено из избранных", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.checkBox.setChecked(checkboxStatus[position]);
            // выводим сообщение о добавлении в избранное
            /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkboxStatus[position] == flagCheckBox) {
                        Toast.makeText(getContext(),
                                "Добавлено в избранное" + checkboxStatus[position] +
                                        model.getHeader(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

        }
        return convertView;
    }

    class ViewHolder {
        TextView tvTopic, tvWhenCreated, tvPositionDescription, tvSalary;
        CheckBox checkBox;
        LinearLayout llViewed;
    }
}