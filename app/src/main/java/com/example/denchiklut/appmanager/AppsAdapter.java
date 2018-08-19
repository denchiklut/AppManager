package com.example.denchiklut.appmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private List<AppInfo> apps = new ArrayList<>();
    private List<AppInfo> filteredApps = new ArrayList<>();
    private String query = "";

    public void setApps (List<AppInfo> apps) {
        this.apps = apps;
        filterApps();
    }

    public void filterApps() {
        filteredApps.clear();

        if (query.isEmpty()) {
            filteredApps.addAll(apps);
        }else {
            for (AppInfo app: apps) {
                if (app.getName().toLowerCase().contains(query)) {
                    filteredApps.add(app);
                }
            }
        }
    }

    public void setQuery(String s) {
        this.query = s;

        filterApps();
    }

    @NonNull
    // В этом методе мы создаем новую ячейку
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.view_item_app, viewGroup, false);

        return new ViewHolder(view);
    }

    // В этом методе мы привязываем данные к ячейке
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        AppInfo appInfo = filteredApps.get(i);

        viewHolder.nameTv.setText(appInfo.getName());
        viewHolder.versionTv.setText(appInfo.getVersionName());
        viewHolder.dirTv.setText(appInfo.getDir());
        viewHolder.iconIv.setImageDrawable(appInfo.getIcon());

    }

    // В этом методе мы возвращаем количество элементов списка
    @Override
    public int getItemCount() {
        return filteredApps.size();
    }


    /**
     * View holder
     * Хранит информацию о ячейке
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iconIv;
        private final TextView nameTv;
        private final TextView versionTv;
        private final TextView dirTv;

        public ViewHolder(View itemView) {
            super(itemView);

            iconIv = itemView.findViewById(R.id.icon_iv);
            nameTv = itemView.findViewById(R.id.name_tv);
            versionTv = itemView.findViewById(R.id.version_tv);
            dirTv = itemView.findViewById(R.id.dir_tv);
        }
    }

}
