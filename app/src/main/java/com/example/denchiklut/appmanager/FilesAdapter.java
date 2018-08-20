package com.example.denchiklut.appmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private static final int TYPE_DIR = 0;
    private static final int TYPE_FILE = 1;
    private List<File> files = new ArrayList<>();

    public void setFiles(List<File>files) {
        this.files = files;
    }

    @Override
    public int getItemViewType(int position) {
        File file = files.get(position);

        if (file.isDirectory()){
            return TYPE_DIR;
        }else {
            return TYPE_FILE;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view;

        if (i == TYPE_DIR) {
            view = layoutInflater.inflate(R.layout.view_item_directory, viewGroup, false);
        } else {
            view = layoutInflater.inflate(R.layout.view_item_file, viewGroup, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        File file = files.get(i);
        viewHolder.nameTv.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
        }
    }
}
