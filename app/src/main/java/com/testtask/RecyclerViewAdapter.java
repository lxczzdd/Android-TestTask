package com.testtask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.testtask.model.ImageModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private final Context context;
    private final ArrayList<ImageModel> imageModels;

    public RecyclerViewAdapter(Context context, ArrayList<ImageModel> list) {
        this.context = context;
        this.imageModels = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Glide.with(context)
                .load(imageModels.get(position)
                        .getUrls()
                        .getRegular())
                .into(holder.imageIV);
        holder.imageIV.setOnClickListener(n -> {
            Intent intent = new Intent(context, ImageDetail.class);
            intent.putExtra("image", imageModels.get(position).getUrls().getRegular());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }
    

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIV = itemView.findViewById(R.id.idRVImages);
        }
    }
}
