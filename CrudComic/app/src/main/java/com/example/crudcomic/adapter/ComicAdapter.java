package com.example.crudcomic.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudcomic.Interface.ItemClick;
import com.example.crudcomic.R;
import com.example.crudcomic.activity.DetailComic_Activity;
import com.example.crudcomic.models.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.viewHolder> {

    private List<Comic> list;
    ItemClick itemClick;

    public ComicAdapter(List<Comic> list, ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

// Constructor and other methods

    public ComicAdapter(List<Comic> list) {
        this.list = list;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truyen, parent, false);
        return new viewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Comic comic = list.get(position);
        holder.tv_tentitle.setText(comic.getTitle());
        Picasso.get().load(comic.getCoverImage()).into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClick(comic);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_tentitle;
        ConstraintLayout constraintLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_truyen);
            tv_tentitle = itemView.findViewById(R.id.tv_tentruyen);
            constraintLayout = itemView.findViewById(R.id.layot_tryen);

        }
    }
}
