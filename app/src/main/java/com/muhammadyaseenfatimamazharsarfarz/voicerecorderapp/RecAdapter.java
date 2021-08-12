package com.muhammadyaseenfatimamazharsarfarz.voicerecorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecViewHolder> {
    private Context context;
    private List<File> fileList;
    private onSelectListener listener;

    public RecAdapter(Context context, List<File> fileList, onSelectListener listener) {
        this.context = context;
        this.fileList = fileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
            holder.txtName.setText(fileList.get(position).getName());
            holder.txtName.setSelected(true);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnSelected(fileList.get(position));
                }
            });

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
